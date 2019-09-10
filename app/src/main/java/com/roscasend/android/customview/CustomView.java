package com.roscasend.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

    private static final String TAG = CustomView.class.getName();

    private Paint paint;

    private PointF topLeftPoint;
    private PointF topRightPoint;
    private PointF bottomLeftPoint;
    private PointF bottomRightPoint;
    private PointF middleLeftPoint;
    private PointF middleRightPoint;
    private PointF middleBottomPoint;
    private PointF middleTopPoint;
    private float[] points;
    private float initialWidth = 400;
    private Canvas canvasTmp;
    private Path path;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(8f);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        canvasTmp = new Canvas();
        path = new Path();

        topLeftPoint = new PointF(16f, 16f);
        topRightPoint = new PointF(initialWidth,16f);
        bottomLeftPoint = new PointF(16f, initialWidth);
        bottomRightPoint = new PointF(initialWidth,initialWidth);

        middleLeftPoint = new PointF((bottomLeftPoint.x + topLeftPoint.x)/2,
                (bottomLeftPoint.y + topLeftPoint.y)/2);
        middleTopPoint = new PointF((topRightPoint.x + topLeftPoint.x)/2,
                (topRightPoint.y + topLeftPoint.y)/2);

        middleRightPoint = new PointF((bottomRightPoint.x + topRightPoint.x)/2,
                (bottomRightPoint.y + topRightPoint.y)/2);
        middleBottomPoint = new PointF((bottomRightPoint.x + bottomLeftPoint.x)/2,
                (bottomRightPoint.y + bottomLeftPoint.y)/2);

        points = new float[16];
        initPoints();
        invalidate();
    };

    private void initPoints() {
        points[0]= topLeftPoint.x;
        points[1]= topLeftPoint.y;
        points[2]= topRightPoint.x;
        points[3]= topRightPoint.y;

        points[4]= topRightPoint.x;
        points[5]= topRightPoint.y;
        points[6]= bottomRightPoint.x;
        points[7]= bottomRightPoint.y;


        points[8]= bottomRightPoint.x;
        points[9]= bottomRightPoint.y;
        points[10]= bottomLeftPoint.x;
        points[11]= bottomLeftPoint.y;

        points[12]= bottomLeftPoint.x;
        points[13]= bottomLeftPoint.y;
        points[14]= topLeftPoint.x;
        points[15]= topLeftPoint.y;

    }
    @Override
    protected void onDraw(Canvas canvas) {
        initPoints();
//      canvasTmp.save();
        path.reset();
        path.addCircle(topLeftPoint.x,topLeftPoint.y, 16f, Path.Direction.CW);
        canvas.drawLines(points, paint);

        path.addCircle(middleTopPoint.x, middleTopPoint.y, 16f, Path.Direction.CW);
        path.addCircle(topRightPoint.x, topRightPoint.y, 16f, Path.Direction.CW);

//      canvas.drawLine(topRightPoint.x, topRightPoint.y, bottomRightPoint.x, bottomRightPoint.y, paint);
        path.addCircle(bottomRightPoint.x, bottomRightPoint.y, 16f, Path.Direction.CW);

        path.addCircle(middleRightPoint.x, middleRightPoint.y, 16f, Path.Direction.CW);
//      canvas.drawLine(bottomRightPoint.x, bottomRightPoint.y, bottomLeftPoint.x, bottomLeftPoint.y, paint);
        path.addCircle(bottomLeftPoint.x, bottomLeftPoint.y, 16f, Path.Direction.CW);
        path.addCircle(middleBottomPoint.x, middleBottomPoint.y, 16f, Path.Direction.CW);

//      canvas.drawLine(bottomLeftPoint.x, bottomLeftPoint.y, topLeftPoint.x, topLeftPoint.y, paint);
        path.addCircle(topRightPoint.x, topRightPoint.y, 16f, Path.Direction.CW);
        path.addCircle(middleLeftPoint.x, middleLeftPoint.y, 16f, Path.Direction.CW);
        path.close();
        canvas.drawPath(path, paint);
            }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        this.widthMeasureSpec = widthMeasureSpec;
//        this.heightMeasureSpec = heightMeasureSpec;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {

        int action = event.getActionMasked();

        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_MOVE
                || event.getAction() == MotionEvent.ACTION_UP) {

            float xCoordinate = 16;
            float yCoordinate = 16;

            if (event.getX() < 16) {
                xCoordinate = 16;
            } else if (event.getX() > getWidth() - 16) {
                xCoordinate = getWidth() - 16;
            } else {
                xCoordinate = event.getX();
            }

            if (event.getY() < 16) {
                yCoordinate = 16;
            } else if (event.getY() > getHeight() - 16) {
                yCoordinate = getHeight() - 16;
            } else {
                yCoordinate = event.getY();
            }
            if (isInsideTopLeft(event.getX(), event.getY())) {
                topLeftPoint.set(xCoordinate, yCoordinate);

                middleTopPoint.set((topRightPoint.x + topLeftPoint.x) / 2,
                        (topRightPoint.y + topLeftPoint.y) / 2);
                middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                        (bottomLeftPoint.y + topLeftPoint.y) / 2);
                postInvalidate((int)xCoordinate, (int)yCoordinate, getWidth(), getHeight());
                return true;
            } else if (isInsideTopRight(event.getX(), event.getY())) {
                topRightPoint.set(xCoordinate, yCoordinate);

                middleTopPoint.set((topRightPoint.x + topLeftPoint.x) / 2,
                        (topRightPoint.y + topLeftPoint.y) / 2);
                middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                        (bottomRightPoint.y + topRightPoint.y) / 2);
                invalidate();
                return true;
            } else if (isInsideBottomRight(event.getX(), event.getY())) {
                bottomRightPoint.set(xCoordinate, yCoordinate);

                middleBottomPoint.set((bottomLeftPoint.x + bottomRightPoint.x) / 2,
                        (bottomLeftPoint.y + bottomRightPoint.y) / 2);
                middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                        (bottomRightPoint.y + topRightPoint.y) / 2);
                invalidate();
                return true;
            } else if (isInsideBottomLeft(event.getX(), event.getY())) {
                bottomLeftPoint.set(xCoordinate, yCoordinate);

                middleBottomPoint.set((bottomLeftPoint.x + bottomRightPoint.x) / 2,
                        (bottomLeftPoint.y + bottomRightPoint.y) / 2);
                middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                        (bottomLeftPoint.y + topLeftPoint.y) / 2);
                invalidate();
                return true;
            } else if (isInsideMidleTop(event.getX(), event.getY())) {

                topLeftPoint.set(topLeftPoint.x, yCoordinate);
                topRightPoint.set(topRightPoint.x, yCoordinate);

                middleTopPoint.set((topRightPoint.x + topLeftPoint.x) / 2,
                        (topRightPoint.y + topLeftPoint.y) / 2);
                middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                        (bottomRightPoint.y + topRightPoint.y) / 2);
                middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                        (bottomLeftPoint.y + topLeftPoint.y) / 2);
                return true;
            } else if (isInsideMiddleBottom(event.getX(), event.getY())) {

                bottomLeftPoint.set(bottomLeftPoint.x, yCoordinate);
                bottomRightPoint.set(bottomRightPoint.x, yCoordinate);

                middleBottomPoint.set((bottomLeftPoint.x + bottomRightPoint.x) / 2,
                        (bottomLeftPoint.y + bottomRightPoint.y) / 2);
                middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                        (bottomRightPoint.y + topRightPoint.y) / 2);
                middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                        (bottomLeftPoint.y + topLeftPoint.y) / 2);
                invalidate();
                return true;
            }
        }
//        else if (isInsideMidleRight(event.getX(), event.getY())) {
//
//            topRightPoint.set(xCoordinate, topRightPoint.y);
//            bottomRightPoint.set(xCoordinate, topRightPoint.y);
//
//
//
//            invalidate();
//            return true;
//        } else if (isInsideMiddleLeft(event.getX(), event.getY())) {
//
//            topLeftPoint.set(bottomLeftPoint.x, yCoordinate);
//            bottomLeftPoint.set(bottomRightPoint.x, yCoordinate);
//
//
//
//            invalidate();
//
//            return true;
//        }

        return super.onTouchEvent(event);
    }

    private double distance (PointF initialPoint, float finalPointX, float finalPointY) {
        return Math.sqrt(
                Math.pow(Math.abs(finalPointX - initialPoint.x), 2) +
                        Math.pow(Math.abs(finalPointY - initialPoint.y), 2));
    }

    private boolean isInsideTopLeft(float finalPointX, float finalPointY) {
        if (distance(topLeftPoint, finalPointX, finalPointY) < 50) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isInsideTopRight(float finalPointX, float finalPointY) {
        if (distance(topRightPoint, finalPointX, finalPointY) < 50) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isInsideBottomRight(float finalPointX, float finalPointY) {
        if (distance(bottomRightPoint, finalPointX, finalPointY) < 50) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isInsideBottomLeft(float finalPointX, float finalPointY) {
        if (distance(bottomLeftPoint, finalPointX, finalPointY) < 50) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isInsideMidleTop(float finalPointX, float finalPointY) {
        if (distance(middleTopPoint, finalPointX, finalPointY) < 50) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isInsideMiddleBottom(float finalPointX, float finalPointY) {
        if (distance(middleBottomPoint, finalPointX, finalPointY) < 50) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isInsideMidleRight(float finalPointX, float finalPointY) {
        if (distance(middleRightPoint, finalPointX, finalPointY) < 50) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isInsideMiddleLeft(float finalPointX, float finalPointY) {
        if (distance(middleLeftPoint, finalPointX, finalPointY) < 50) {
            return true;
        } else {
            return false;
        }
    }

    public void setMaximumSize() {
        topLeftPoint = new PointF(16f, 16f);
        topRightPoint = new PointF(getWidth() - 16,16f);
        bottomLeftPoint = new PointF(16f, getHeight() -16);
        bottomRightPoint = new PointF(getWidth()-16,getHeight() -16);

        middleLeftPoint = new PointF((bottomLeftPoint.x + topLeftPoint.x)/2,
                (bottomLeftPoint.y + topLeftPoint.y)/2);
        middleTopPoint = new PointF((topRightPoint.x + topLeftPoint.x)/2,
                (topRightPoint.y + topLeftPoint.y)/2);

        middleRightPoint = new PointF((bottomRightPoint.x + topRightPoint.x)/2,
                (bottomRightPoint.y + topRightPoint.y)/2);
        middleBottomPoint = new PointF((bottomRightPoint.x + bottomLeftPoint.x)/2,
                (bottomRightPoint.y + bottomLeftPoint.y)/2);

        invalidate();
    }
}

