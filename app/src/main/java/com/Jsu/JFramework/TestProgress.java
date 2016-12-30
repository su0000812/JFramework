package com.Jsu.JFramework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by JSu on 2016/6/23.
 */

public class TestProgress extends View {

    private float sweepAngle = 0;
    private Handler handler;

    public TestProgress(Context context) {
        super(context);
    }

    public TestProgress(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

//        HandlerThread handlerThread = new HandlerThread("drawCircle");
//        handlerThread.start();
        handler = new Handler();
        mDrawRunnable = new DrawRunnable();
        handler.postDelayed(mDrawRunnable, 2000);
    }

    private DrawRunnable mDrawRunnable;

    private class DrawRunnable implements Runnable {

        @Override
        public void run() {
            if (sweepAngle < 300) {
                sweepAngle += 1;
                postInvalidate();
                handler.postDelayed(mDrawRunnable, 20);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private static final int DEFAULT_MIN_WIDTH = 400;

    private int measure(int origin) {
        int result = DEFAULT_MIN_WIDTH;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint circle = new Paint();
        circle.setColor(getContext().getResources().getColor(android.R.color.holo_orange_light));
        circle.setAntiAlias(true);
        circle.setStrokeWidth(7.0f);
        circle.setStyle(Paint.Style.STROKE);

        RectF area = new RectF(10, 10, 200, 200);
        canvas.drawArc(area, 0f, sweepAngle, false, circle);


    }
}
