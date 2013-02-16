package com.insomniacmath.Animations;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.insomniacmath.Animations.*;
import com.insomniacmath.ui.MatrixView;

public class Animator {

    static final int ANIM_DETERMINANT = 0;
    static final int ANIM_MULTIPLICATION = 1;
    static final int ANIM_SYSTEM_GAUSS = 2;
    static final int ANIM_INVERT = 3;

    TicTac tic;

    //    MatrixCanvas canvas;
    MatrixView mainMW;
    private MatrixView resMW;

    public void setSecMW(MatrixView secMW) {
        this.secMW = secMW;
    }

    public void setResultMW(MatrixView resMW) {
        this.resMW = resMW;
    }

    MatrixView secMW;
    int animType;
    private int rows;
    private int columns;

    private LinearLayout solvationView;
    Animation anim;

    public int getAnimType() {
        return animType;
    }

    public void setAnimType(int animType, int rows, int columns) {
        this.animType = animType;
        this.rows = rows;
        this.columns = columns;
    }

    public Animator(MatrixView MatrixModel) {
        mainMW = MatrixModel;
        mainMW.getCanvas().setVisibility(View.INVISIBLE);
    }

    public void startExplaining() {
        mainMW.getCanvas().setVisibility(View.VISIBLE);
        tic = new TicTac();
        if (animType == ANIM_DETERMINANT) {
            if (rows == 2)
                anim = new Det2x2Animation(this, solvationView, mainMW);
            if (rows == 3)
                anim = new Det3x3Animation(this, solvationView, mainMW);
        } else if (animType == ANIM_MULTIPLICATION) {
            anim = new MultiplyAnimation(this, solvationView, mainMW, secMW, resMW);
        } else if (animType == ANIM_SYSTEM_GAUSS) {
            anim = new GausAnimation(this, solvationView, mainMW);
        } else if (animType == ANIM_INVERT) {
            anim = new InverseAnimation(this, solvationView, mainMW, resMW);
        }
        tic.execute();
    }


    public void stopExplain() {
        if (tic != null)
            tic.cancel(true);
        if (mainMW != null)
            mainMW.getCanvas().clear();
    }

    public void setView(LinearLayout solvationView) {
        this.solvationView = solvationView;
    }

    public void changeSpeed(boolean b) {
        timeout *= b ? 0.5f : 2;
    }

    static int timeout = 1000;

    class TicTac extends AsyncTask {

        short counter = 0;

        @Override
        protected Object doInBackground(Object... objects) {
            while (!this.isCancelled()) {
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            Log.d("zzz", "zzz");
            anim.tic(counter);
            counter++;
        }
    }


}