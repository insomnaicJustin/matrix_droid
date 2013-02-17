package com.insomniacmath.math.solvers;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Controller;
import com.insomniacmath.math.Fraction;
import com.insomniacmath.math.MatrixModel;
import com.insomniacmath.math.MatrixUtils;
import com.insomniacmath.math.exceptions.NotSquareException;
import com.insomniacmath.ui.LParams;

public class DeterminantSolver extends Solver {


    private final TextView resultText;

    public DeterminantSolver(LinearLayout mainView, Controller controller) {
        super(mainView, controller);
        resultText = new TextView(mainView.getContext());
        resultText.setTextSize(20);
        resultText.setPadding(20, 20, 20, 20);
        resultText.setGravity(Gravity.CENTER_HORIZONTAL);
        resultView.addView(resultText, LParams.L_WRAP_WRAP);
        setResult();
    }

    @Override
    void onBack() {


    }

    public void setResult() {
        try {
            MatrixModel model = controller.mainMatrixView.model;
            if (model.rows != model.columns)
                throw new NotSquareException();
            Fraction determin = MatrixUtils.determin(model.mFrac);
            resultText.setText("Determinant = " + determin.toString());
            resultText.setTextColor(Color.WHITE);
//            animator.setAnimType(Animator.ANIM_DETERMINANT, mainMatrixModel.rows, mainMatrixModel.columns);
            Controller.state = STATE_DETERMIN_PRESSED;
            if (model.rows == 2 || model.rows == 3)
                showXplainButton();
            controller.bottomPlusHolder.setVisibility(View.GONE);
            controller.rightPlusHolder.setVisibility(View.GONE);
        } /*catch (BadSymbolException e) {
            resultText.setText(mainView.getContext().getString(R.string.bad_elements));
            resultText.setTextColor(Color.RED);
        } */ catch (NotSquareException e) {
            resultText.setText("Matrix must be square");
            resultText.setTextColor(Color.RED);
        }
    }

}
