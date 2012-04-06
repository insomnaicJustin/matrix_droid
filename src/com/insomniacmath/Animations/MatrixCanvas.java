package com.insomniacmath.Animations;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class MatrixCanvas extends SurfaceView implements Runnable {


    Thread thread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    ArrayList<line> pathList;
    ArrayList<circle> circleList;

    public void clearPaths() {
        pathList.clear();
        circleList.clear();
    }

    class line {
        final static int shiftX = 40;
        final static int shiftY = 40;
        final static int shift = 80;

        public int startx;
        public int starty;
        public int endx;
        public int endy;
        public int lifeCounter = 0;
        int color;

        public line(int startx, int starty, int endx, int endy, int color) {
            this.color = color;
            this.startx = startx * shift + shiftX;
            this.starty = starty * shift + shiftY;
            this.endx = endx * shift + shiftX;
            this.endy = endy * shift + shiftY;
        }
    }

    class circle {
        final static int shiftX = 40;
        final static int shiftY = 40;
        final static int shift = 80;

        public int x;
        public int y;
        public int lifeCounter = 0;
        int color;

        public circle(int x, int y, int color) {
            this.color = color;
            this.x = x * shift + shiftX;
            this.y = y * shift + shiftY;
        }
    }


    public MatrixCanvas(Context context) {
        super(context);
        this.setVisibility(GONE);
        this.setVisibility(VISIBLE);
        surfaceHolder = getHolder();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        pathList = new ArrayList<line>();
        circleList = new ArrayList<circle>();

        onResumeMySurfaceView();
    }

    public void addPath(int startx, int starty, int endx, int endy, int color) {
        pathList.add(new line(startx, starty, endx, endy, color));
    }

    public void addCircle(int x, int y, int color) {
        circleList.add(new circle(x, y, color));
    }

    public MatrixCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setVisibility(GONE);
    }

    public MatrixCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setVisibility(GONE);
    }

    public void onResumeMySurfaceView() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void onPauseMySurfaceView() {
        boolean retry = true;
        running = false;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void run() {
        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                Canvas canvas = surfaceHolder.lockCanvas();
                //... actual drawing on canvas
                canvas.drawColor(Color.BLACK);


                for (int i = 0; i < circleList.size(); i++) {
                    paint.setColor(circleList.get(i).color);
                    canvas.drawCircle(circleList.get(i).x, circleList.get(i).y, 20, paint);
                }

                for (int i = 0; i < pathList.size(); i++) {
                    paint.setColor(pathList.get(i).color);
                    canvas.drawLine(pathList.get(i).startx, pathList.get(i).starty, pathList.get(i).endx, pathList.get(i).endy, paint);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
