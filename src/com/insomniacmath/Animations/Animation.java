package com.insomniacmath.Animations;


import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.insomniacmath.Constants;
import com.insomniacmath.ui.MatrixView;

abstract public class Animation implements Constants {

    public static final int SOLV_TEXT_SIZE = 19;

    public static final LinearLayout.LayoutParams FILL_WRAP =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    protected Animator animator;
    public LinearLayout solvation;
    public MatrixView mW1;

    Animation(Animator animator, LinearLayout solvation, MatrixView parent) {
        this.animator = animator;
        this.solvation = solvation;
        this.mW1 = parent;
    }


    public abstract void animate();
}
