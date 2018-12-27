package com.bsaldevs.mobileclient.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class BarChart extends View {

    private static final int LEFT = 50;
    private static final int RIGHT = 50;
    private static final int TOP = 50;
    private static final int BOTTOM = 50;
    private static final int RECTANGLE_WIDTH = 50;
    private static final int strokeSize = 5;

    private List<Rect> rectSquare;
    private Paint paintSquare;

    public BarChart(Context context) {
        super(context);
        init(null, 0);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        rectSquare = new ArrayList<>();

        int xStart = LEFT;

        Rect rect1 = new Rect();
        rect1.left = LEFT;
        rect1.top = TOP;
        rect1.right = rect1.left + RECTANGLE_WIDTH;
        rect1.bottom = rect1.top + 100;

        xStart += RECTANGLE_WIDTH;

        Rect rect2 = new Rect();
        rect2.left = rect1.right;
        rect2.top = TOP;
        rect2.right = rect2.left + RECTANGLE_WIDTH;
        rect2.bottom = rect2.top + 80;

        rectSquare.add(rect1);
        rectSquare.add(rect2);

        paintSquare = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintSquare.setColor(Color.GREEN);
        paintSquare.setStrokeWidth(strokeSize);
        paintSquare.setStyle(Paint.Style.STROKE);

        canvas.drawRect(rectSquare.get(0), paintSquare);
        canvas.drawRect(rectSquare.get(1), paintSquare);
    }
}
