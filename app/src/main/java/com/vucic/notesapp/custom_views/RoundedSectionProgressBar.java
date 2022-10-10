package com.vucic.notesapp.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.vucic.notesapp.R;

public class RoundedSectionProgressBar extends View {
    // The amount of degrees that we wanna reserve for the divider between 2 sections
    private static final float DIVIDER_ANGLE = 7;
    public static final float DEGREES_IN_CIRCLE = 360;
    public static final int PADDING = 18;
    public static final int PADDING2 = 12;

    protected final Paint paint = new Paint();
    protected final Paint waitingPaint = new Paint();
    protected final Paint backgroundPaint = new Paint();
    private int totalSections = 5;
    private int fullSections = 2;
    private int waiting = 3; // The outer ring. You can omit this
    private RectF rect = new RectF();

    public RoundedSectionProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public RoundedSectionProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedSectionProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Can come from attrs if need be?
        int strokeWidth = 3;
        setupPaint(context, strokeWidth, paint, R.color.color_1);
        setupPaint(context, strokeWidth, waitingPaint, R.color.color_2);
        setupPaint(context, strokeWidth, backgroundPaint, R.color.color_3);
    }

    private void setupPaint(Context context, int strokeWidth, Paint backgroundPaint, int colorRes) {
        backgroundPaint.setStrokeCap(Paint.Cap.SQUARE);
        backgroundPaint.setColor(context.getResources().getColor(colorRes));
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setStyle(Paint.Style.STROKE);
    }

    public int getTotalSections() {
        return totalSections;
    }

    public void setTotalSections(int totalSections) {
        this.totalSections = totalSections;
        invalidate();
    }

    public int getFullSections() {
        return fullSections;
    }

    public void setNumberOfSections(int fullSections, int totalSections, int waiting) {
        this.fullSections = fullSections;
        this.totalSections = totalSections;
        this.waiting = waiting;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.set(getLeft() + PADDING, getTop() + PADDING, getRight() - PADDING, getBottom() - PADDING);
        float angleOfSection = (DEGREES_IN_CIRCLE / totalSections) - DIVIDER_ANGLE;
        // Drawing the inner ring
        for (int i = 0; i < totalSections; i++) {
            // -90 because it doesn't start at the top, so rotate by -90
            // divider_angle/2 especially in 2 sections, it's visibly rotated by Divider angle, so we split this between last and first
            float startAngle = -90 + i * (angleOfSection + DIVIDER_ANGLE) + DIVIDER_ANGLE / 2;
            if (i < fullSections) {
                canvas.drawArc(rect, startAngle, angleOfSection, false, paint);
            } else {
                canvas.drawArc(rect, startAngle, angleOfSection, false, backgroundPaint);
            }
        }
        // Drawing the outer ring
        rect.set(getLeft() + PADDING2, getTop() + PADDING2, getRight() - PADDING2, getBottom() - PADDING2);
        for (int i = 0; i < waiting; i++) {
            float startAngle = -90 + i * (angleOfSection + DIVIDER_ANGLE) + DIVIDER_ANGLE / 2;
            canvas.drawArc(rect, startAngle, angleOfSection, false, waitingPaint);
        }
    }
}
