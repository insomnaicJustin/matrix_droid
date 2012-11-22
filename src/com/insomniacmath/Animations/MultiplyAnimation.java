package com.insomniacmath.Animations;


import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insomniacmath.Animator;
import com.insomniacmath.MatrixWrapper;
import com.insomniacmath.Solver;

public class MultiplyAnimation extends Animation {

    public static final LinearLayout.LayoutParams FILL_WRAP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    TextView[] solvationTexts;
    private MatrixWrapper mW2;
    private View resultMW;
    int actions;
    int resMax;

    int[] colors = new int[]{cyan, viol, ros, blu, gree, yel};

    public MultiplyAnimation(Animator animator, LinearLayout solvationView, MatrixWrapper mW1, MatrixWrapper mW2) {
        super(animator, solvationView, mW1);
        this.mW2 = mW2;
        resultMW = solvationView.findViewById(Solver.RESULT_MATRIX);

        resMax = mW1.rows * mW1.rows;
        solvationTexts = new TextView[resMax];
        for (int i = 0; i < resMax; i++) {
            solvationTexts[i] = new TextView(solvation.getContext());
//            solvationTexts[i].setVisibility(View.GONE);
        }

        actions = 4 * mW1.columns - 1;


    }

    /**
     * converts number to string, wraps with brakets , if number is negative
     *
     * @param number
     * @return
     */
    public String bra(double number) {
        if (number == (int) number) {
            if (number < 0)
                return "(" + (int) number + ")";
            else return (int) number + "";
        } else {
            if (number < 0)
                return "(" + number + ")";
            else return number + "";
        }

    }

    StringBuilder builder = new StringBuilder();

    @Override
    public void tic(int t) {

        if (t == actions * resMax) {
            animator.stopExplain();
            return;
        }

        int resCount = t / actions;
        int rowIter = t % actions;

        if (rowIter == 0) {
            builder = new StringBuilder();
            solvationTexts[resCount].setTextSize(23);
            solvationTexts[resCount].setGravity(Gravity.CENTER_HORIZONTAL);
            solvationTexts[resCount].setTextColor(colors[resCount % 6]);
            solvation.addView(solvationTexts[resCount], FILL_WRAP);
        }

        int elementIter = rowIter / 4;
        int ro = resCount / mW1.columns;
        int col = resCount % mW1.columns;


        Log.d("ticzzzzz", rowIter + " col " + elementIter + "  row " + ro);
        switch (rowIter % 4) {
            case 0:
                builder.append(bra(mW1.m[ro][col + elementIter]));
                break;
            case 1:
                builder.append("*");
                break;
            case 2:
                builder.append(bra(mW2.m[col + elementIter][ro]));
                break;
            case 3:
                builder.append("+");
                break;
        }

        solvationTexts[resCount].setText(builder.toString());


//        solvationTexts[0].setText("+ ");
//        solvationTexts[0].setText("+ " + Utils.round(mW1.m[0][0]));
//        mW1.getCanvas().addCircle(0, 0, cyan);
//        solvationTexts[0].setText("+ " + Utils.round(mW1.m[0][0]) + "*" + Utils.round(mW1.m[1][1]));
//        mW1.getCanvas().addPath(0, 0, 1, 1, cyan);
//        mW1.getCanvas().addCircle(1, 1, cyan);
//        solvationTexts[0].setText("+ " + Utils.round(mW1.m[0][0]) + "*" + Utils.round(mW1.m[1][1]) + "*" + Utils.round(mW1.m[2][2]));
//        mW1.getCanvas().addPath(1, 1, 2, 2, cyan);
//        mW1.getCanvas().addCircle(2, 2, cyan);


    }
}