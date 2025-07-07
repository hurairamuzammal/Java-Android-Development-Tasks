package com.example.semester_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ScannerOverlay extends View {

    private Paint paint;
    private Paint borderPaint;
    private RectF frame;

    public ScannerOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Outer transparent paint
        paint = new Paint();
        paint.setColor(0xA0000000); // semi-transparent black

        // Inner border paint
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(0xFF00FF00); //border color
        borderPaint.setStrokeWidth(6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int boxWidth = width * 4 / 5;
        int boxHeight = height / 2;

        int left = (width - boxWidth) / 2;
        int top = (height - boxHeight) / 2;
        int right = left + boxWidth;
        int bottom = top + boxHeight;

        frame = new RectF(left, top, right, bottom);

        // Draw transparent background with clear box
        canvas.drawRect(0, 0, width, top, paint); // top
        canvas.drawRect(0, top, left, bottom, paint); // left
        canvas.drawRect(right, top, width, bottom, paint); // right
        canvas.drawRect(0, bottom, width, height, paint); // bottom

        // Draw green rectangle border
        canvas.drawRect(frame, borderPaint);
    }

    public RectF getFrameRect() {
        return frame;
    }
}
