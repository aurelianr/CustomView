package com.roscasend.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

    private static final String TAG = CustomView.class.getName();

    //create touch lock cases
    private final int TOUCH_STATE_UNLOCKED = 0;
    private final int TOUCH_STATE_LOCKED_TOP_LEFT = 1;
    private final int TOUCH_STATE_LOCKED_TOP_RIGHT = 2;
    private final int TOUCH_STATE_LOCKED_BOTTOM_LEFT = 3;
    private final int TOUCH_STATE_LOCKED_BOTTOM_RIGHT = 4;
    private final int TOUCH_STATE_LOCKED_MIDDLE_LEFT = 5;
    private final int TOUCH_STATE_LOCKED_MIDDLE_BOTTOM = 6;
    private final int TOUCH_STATE_LOCKED_MIDDLE_TOP = 7;
    private final int TOUCH_STATE_LOCKED_MIDDLE_RIGHT = 8;

    private int TOUCH_STATE = TOUCH_STATE_UNLOCKED;

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
        float xCoordinate = 16;
        float yCoordinate = 16;
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();

        if (eventX < 16) {
            xCoordinate = 16;
        } else if (eventX > getWidth() - 16) {
            xCoordinate = getWidth() - 16;
        } else {
            xCoordinate = eventX;
        }

        if (eventY < 16) {
            yCoordinate = 16;
        } else if (eventY > getHeight() - 16) {
            yCoordinate = getHeight() - 16;
        } else {
            yCoordinate = eventY;
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        /* Touch event handling in brief,  read about touch event handling,

        in ACTION_DOWN lock touch event for a particular point.


         */
            case MotionEvent.ACTION_DOWN:

                // lock touch event for a point . subsequent touch events
                if (isInsideTopLeft(event.getX(), event.getY())) {
                    TOUCH_STATE = TOUCH_STATE_LOCKED_TOP_LEFT;
                } else if (isInsideTopRight(event.getX(), event.getY())) {
                    TOUCH_STATE = TOUCH_STATE_LOCKED_TOP_RIGHT;
                } else if (isInsideBottomLeft(event.getX(), event.getY())) {
                    TOUCH_STATE = TOUCH_STATE_LOCKED_BOTTOM_LEFT;
                } else if (isInsideBottomRight(event.getX(), event.getY())) {
                    TOUCH_STATE = TOUCH_STATE_LOCKED_BOTTOM_RIGHT;
                } else if (isInsideMiddleLeft(event.getX(), event.getY())) {
                    TOUCH_STATE = TOUCH_STATE_LOCKED_MIDDLE_LEFT;
                } else if (isInsideMiddleBottom(event.getX(), event.getY())) {
                    TOUCH_STATE = TOUCH_STATE_LOCKED_MIDDLE_BOTTOM;
                } else if (isInsideMiddleTop(event.getX(), event.getY())) {
                    TOUCH_STATE = TOUCH_STATE_LOCKED_MIDDLE_TOP;
                } else if (isInsideMiddleRight(event.getX(), event.getY())) {
                    TOUCH_STATE = TOUCH_STATE_LOCKED_MIDDLE_RIGHT;
                }

                Log.e(TAG, "onTouchEvent: TOUCH_STATE = " + TOUCH_STATE);
                break;

            case MotionEvent.ACTION_MOVE:
                // simply check for locked case
                if (TOUCH_STATE == TOUCH_STATE_LOCKED_TOP_LEFT) {
                    Log.e(TAG, "onTouchEvent: top left locked");
                    topLeftPoint.set(xCoordinate, yCoordinate);

                    middleTopPoint.set((topRightPoint.x + topLeftPoint.x) / 2,
                            (topRightPoint.y + topLeftPoint.y) / 2);
                    middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                            (bottomLeftPoint.y + topLeftPoint.y) / 2);
                    // working fine with full invalidate also.
                    invalidate();
                } else  if (TOUCH_STATE == TOUCH_STATE_LOCKED_TOP_RIGHT) {
                    Log.e(TAG, "onTouchEvent: top right locked");
                    topRightPoint.set(xCoordinate, yCoordinate);

                    middleTopPoint.set((topRightPoint.x + topLeftPoint.x) / 2,
                            (topRightPoint.y + topLeftPoint.y) / 2);
                    middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                            (bottomRightPoint.y + topRightPoint.y) / 2);
                    // working fine with full invalidate also.
                    invalidate();
                } else  if (TOUCH_STATE == TOUCH_STATE_LOCKED_BOTTOM_LEFT) {
                    Log.e(TAG, "onTouchEvent: top right locked");
                    bottomLeftPoint.set(xCoordinate, yCoordinate);

                    middleBottomPoint.set((bottomLeftPoint.x + bottomRightPoint.x) / 2,
                            (bottomLeftPoint.y + bottomRightPoint.y) / 2);
                    middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                            (bottomLeftPoint.y + topLeftPoint.y) / 2);
                    invalidate();
                } else  if (TOUCH_STATE == TOUCH_STATE_LOCKED_BOTTOM_RIGHT) {
                    Log.e(TAG, "onTouchEvent: top right locked");
                    bottomRightPoint.set(xCoordinate, yCoordinate);

                    middleBottomPoint.set((bottomLeftPoint.x + bottomRightPoint.x) / 2,
                            (bottomLeftPoint.y + bottomRightPoint.y) / 2);
                    middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                            (bottomRightPoint.y + topRightPoint.y) / 2);
                    invalidate();
                } else if (TOUCH_STATE == TOUCH_STATE_LOCKED_MIDDLE_BOTTOM) {
                    Log.e(TAG, "onTouchEvent: top right locked");
                    bottomLeftPoint.set(bottomLeftPoint.x, yCoordinate);
                    bottomRightPoint.set(bottomRightPoint.x, yCoordinate);

                    middleBottomPoint.set((bottomLeftPoint.x + bottomRightPoint.x) / 2,
                            (bottomLeftPoint.y + bottomRightPoint.y) / 2);
                    middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                            (bottomRightPoint.y + topRightPoint.y) / 2);
                    middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                            (bottomLeftPoint.y + topLeftPoint.y) / 2);
                    invalidate();
                } else if (TOUCH_STATE == TOUCH_STATE_LOCKED_MIDDLE_TOP) {
                    Log.e(TAG, "onTouchEvent: top right locked");
                    topLeftPoint.set(topLeftPoint.x, yCoordinate);
                    topRightPoint.set(topRightPoint.x, yCoordinate);

                    middleTopPoint.set((topRightPoint.x + topLeftPoint.x) / 2,
                            (topRightPoint.y + topLeftPoint.y) / 2);
                    middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                            (bottomRightPoint.y + topRightPoint.y) / 2);
                    middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                            (bottomLeftPoint.y + topLeftPoint.y) / 2);
                    invalidate();
                } else if (TOUCH_STATE == TOUCH_STATE_LOCKED_MIDDLE_LEFT) {
                    Log.e(TAG, "onTouchEvent: top right locked");
                    topLeftPoint.set(xCoordinate, topLeftPoint.y);
                    bottomLeftPoint.set(xCoordinate, bottomLeftPoint.y);

                    middleBottomPoint.set((bottomLeftPoint.x + bottomRightPoint.x) / 2,
                            (bottomLeftPoint.y + bottomRightPoint.y) / 2);
                    middleLeftPoint.set((bottomLeftPoint.x + topLeftPoint.x) / 2,
                            (bottomLeftPoint.y + topLeftPoint.y) / 2);
                    middleTopPoint.set((topRightPoint.x + topLeftPoint.x) / 2,
                            (topRightPoint.y + topLeftPoint.y) / 2);

                    invalidate();
                } else if (TOUCH_STATE == TOUCH_STATE_LOCKED_MIDDLE_RIGHT) {
                    Log.e(TAG, "onTouchEvent: top right locked");
                    topRightPoint.set(xCoordinate, topRightPoint.y);
                    bottomRightPoint.set(xCoordinate, bottomRightPoint.y);

                    middleTopPoint.set((topRightPoint.x + topLeftPoint.x) / 2,
                            (topRightPoint.y + topLeftPoint.y) / 2);
                    middleBottomPoint.set((bottomLeftPoint.x + bottomRightPoint.x) / 2,
                            (bottomLeftPoint.y + bottomRightPoint.y) / 2);
                    middleRightPoint.set((bottomRightPoint.x + topRightPoint.x) / 2,
                            (bottomRightPoint.y + topRightPoint.y) / 2);
                    invalidate();
                }
                // handle other cases also
                break;

            case MotionEvent.ACTION_UP:
                TOUCH_STATE = TOUCH_STATE_UNLOCKED;  // unlock

        }

        return true;
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

    private boolean isInsideMiddleTop(float finalPointX, float finalPointY) {
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

    private boolean isInsideMiddleRight(float finalPointX, float finalPointY) {
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

