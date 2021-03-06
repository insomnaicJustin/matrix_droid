package com.insomniacmath.math.solvers;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animations.Det2x2Animation;
import com.insomniacmath.Animations.Det3x3Animation;
import com.insomniacmath.Animations.MatrixCanvas;
import com.insomniacmath.Controller;
import com.insomniacmath.R;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.MatrixUtils;
import com.insomniacmath.math.exceptions.BadSymbolException;
import com.insomniacmath.math.exceptions.NotSquareException;
import com.insomniacmath.ui.LParams;

public class DeterminantSolver extends Solver {

    private TextView resultText;


    public DeterminantSolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);
        setResult();
    }

    @Override
    public void onBackPressed() {
        if (controller.state == STATE_DETERMIN_EXPLAINING ||
                controller.state == STATE_DETERMIN_EXPLAINED) {
            controller.state = STATE_DETERMIN_PRESSED;
            xplainButton.setVisibility(View.VISIBLE);
            explainThread.interrupt();
            mainMatrixView.bodyMatrix.removeView(mainMatrixView.getCanvas());
            if (solvationView != null)
                mainView.removeView(solvationView);
            explaining.setVisibility(View.GONE);
        } else if (controller.state == STATE_DETERMIN_PRESSED) {
            mainView.removeView(resultView);
            controller.actionButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.GONE);
            controller.state = STATE_INITIAL;
            xplainButton.setVisibility(View.GONE);
            controller.bottomPlusHolder.setVisibility(View.VISIBLE);
            controller.rightPlusHolder.setVisibility(View.VISIBLE);
            onDestroySolver();
        }
    }

    public void setResult() {
        try {
            MatrixModel model = mainMatrixView.model;
            for (int i = 0; i < model.rows; i++) {
                for (int j = 0; j < model.columns; j++) {
                    if (model.mFrac[i][j] == null)
                        throw new BadSymbolException();
                }
            }

            if (model.rows != model.columns)
                throw new NotSquareException();
            Fraction determin = MatrixUtils.determin(model.mFrac);

            resultText = new TextView(mainView.getContext());
            resultText.setTextSize(20);
            resultText.setPadding(20, 20, 20, 20);
            resultText.setGravity(Gravity.CENTER_HORIZONTAL);
            resultView.addView(resultText, LParams.L_WRAP_WRAP);
            resultText.setText("Determinant = " + determin.toString());
            resultText.setTextColor(Color.WHITE);
//            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixModel.rows, mainMatrixModel.columns);
            controller.state = STATE_DETERMIN_PRESSED;
            if (model.rows == 2 || model.rows == 3)
                showXplainButton();
            controller.bottomPlusHolder.setVisibility(View.GONE);
            controller.rightPlusHolder.setVisibility(View.GONE);
        } catch (BadSymbolException e) {
            message.setText(mainView.getContext().getString(R.string.bad_elements));
            message.setTextColor(Color.RED);
            message.setVisibility(View.VISIBLE);
        } catch (NotSquareException e) {
            message.setText("Matrix must be square");
            message.setTextColor(Color.RED);
            message.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onExplainClicked() {
        super.onExplainClicked();
        controller.state = STATE_DETERMIN_EXPLAINING;
        mainMatrixView.setCanvas(new MatrixCanvas(context));

        if (mainMatrixView.model.rows == 2)
            animation = new Det2x2Animation(solvationView, mainMatrixView);
        else
            animation = new Det3x3Animation(solvationView, mainMatrixView);

        explainThread = new ExplainThread();
        explainThread.start();
    }

}
