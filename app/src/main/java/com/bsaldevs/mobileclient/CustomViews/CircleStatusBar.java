package com.bsaldevs.mobileclient.CustomViews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bsaldevs.mobileclient.R;

public class CircleStatusBar extends View {

    private static final int SIZE = 100;
    private static final int ANIMATION_DURATION_DEF = 1000;

    private int centerX;
    private int centerY;
    private int radius;
    private Paint mDegreesPaint;
    private Paint mCirclePaint;
    private RectF mRect;

    private int backgroundCircleColor;
    private int statusArcColor;
    private int strokeWidth;
    private float value;
    private float minValue;
    private float maxValue;

    private ValueAnimator valueAnimator;
    private float valueToDraw;
    private boolean isAnimated = true;

    private boolean isValueVisible;

    public CircleStatusBar(Context context) {
        super(context);
        init(null);
    }

    public CircleStatusBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleStatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CircleStatusBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {

        if (attributeSet == null)
            return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircleStatusBar);

        backgroundCircleColor = typedArray.getColor(R.styleable.CircleStatusBar_background_circle_color, Color.GRAY);
        statusArcColor = typedArray.getColor(R.styleable.CircleStatusBar_status_arc_color, Color.BLUE);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleStatusBar_stroke_width, 10);
        value = typedArray.getInt(R.styleable.CircleStatusBar_value, 50);
        valueToDraw = value;
        minValue = typedArray.getInt(R.styleable.CircleStatusBar_min_value, 0);
        maxValue = typedArray.getInt(R.styleable.CircleStatusBar_max_value, 100);
        isValueVisible = typedArray.getBoolean(R.styleable.CircleStatusBar_value_visible, true);

        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int startTop = strokeWidth / 2;
        int startLeft = startTop;

        int endBottom = getHeight() - startTop;
        int endRight = endBottom;

        mRect = new RectF(startTop, startLeft, endRight, endBottom);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = getWidth() / 2 - strokeWidth / 2;

        drawCircleBackground(canvas);
        drawStatusArc(canvas);
        if (isValueVisible) {
            drawValue(canvas);
        }
    }

    private void drawValue(Canvas canvas) {
        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize(30);
        fontPaint.setStyle(Paint.Style.STROKE);
        Rect bounds = new Rect();
        String text = String.valueOf(valueToDraw);
        fontPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        int y = (canvas.getHeight() / 2) - (bounds.height() / 2);
        canvas.drawText(text, x, y, fontPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // Мимиметр представляет собой окружность,
        // занимающую все доступное пространство.
        // Установите размеры элемента, вычислив наименьшую величину
        // (высоту или ширину).
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int chosenWidth = chooseDimension(widthMode, widthSize);
        int chosenHeight = chooseDimension(heightMode, heightSize);

        int chosenDimension = Math.min(chosenWidth, chosenHeight);

        setMeasuredDimension(chosenDimension, chosenDimension);
        Log.d("CDA", "chosenDimension = " + chosenDimension);
    }

    private int chooseDimension(int mode, int size) {
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.EXACTLY) {
            return size;
        } else { // (mode == MeasureSpec.UNSPECIFIED)
            return getPreferredSize();
        }
    }

    // Если размер не задан, то устанавливаем значение по умолчанию
    private int getPreferredSize() {
        return SIZE;
    }

    private void drawCircleBackground(Canvas canvas) {
        initBackgroundCirclePaint();
        canvas.drawCircle(centerX, centerY, radius, mCirclePaint);
    }

    private void initBackgroundCirclePaint() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(strokeWidth);
        mCirclePaint.setColor(backgroundCircleColor);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void drawStatusArc(Canvas canvas) {
        initStatusArcPaint();
        canvas.drawArc(mRect, -90, getDegreesFromValue(valueToDraw), false, mDegreesPaint);
    }

    private void initStatusArcPaint() {
        mDegreesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDegreesPaint.setStyle(Paint.Style.STROKE);
        mDegreesPaint.setStrokeWidth(strokeWidth);
        mDegreesPaint.setColor(statusArcColor);
        mDegreesPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private int getDegreesFromValue(float value) {
        return (int) (360 / (maxValue - minValue) * (value - minValue));
    }

    public void setValue(float newValue) {
        float previousValue = valueToDraw;
        if (newValue < minValue) {
            value = 0;
        } else if (newValue > maxValue) {
            value = maxValue;
        } else {
            value = newValue;
        }

        if (valueAnimator != null) {
            valueAnimator.cancel();
        }

        if (isAnimated) {
            valueAnimator = ValueAnimator.ofFloat(previousValue, value);
            valueAnimator.setDuration(ANIMATION_DURATION_DEF);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    valueToDraw = (float) valueAnimator.getAnimatedValue();
                    Log.d("CDA_CSB", "valueToDraw = " + valueToDraw);
                    CircleStatusBar.this.invalidate();
                }
            });

            valueAnimator.start();
            Log.d("CDA_CSB", "animation started");
        } else {
            valueToDraw = value;
        }

        invalidate();
    }

}
