package com.jack.specialEffects;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Jack
 * 可拖拉  缩放的imageView
 */
public class DragZoomImageView extends AppCompatImageView
{
    private TouchListener touchListener;
    public DragZoomImageView(Context context)
    {
        super(context);
        touchListener = new TouchListener();
        this.setOnTouchListener(touchListener);
    }

    public DragZoomImageView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        touchListener = new TouchListener();
        this.setOnTouchListener(touchListener);
    }

    public DragZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        touchListener = new TouchListener();
        this.setOnTouchListener(touchListener);
    }

    @Override
    public boolean performClick()
    {
        return super.performClick();
    }

    private final class TouchListener implements OnTouchListener
    {
        /**
         * 记录是拖拉照片模式还是放大缩小照片模式
         * 初始状态
         */
        private int mode = 0;
        /**
         * 拖拉照片模式
         */
        private static final int MODE_DRAG = 1;
        /**
         * 放大缩小照片模式
         */
        private static final int MODE_ZOOM = 2;

        /**
         * 用于记录开始时候的坐标位置
         */
        private PointF startPoint = new PointF();
        /**
         * 用于记录拖拉图片移动的坐标位置
         */
        private Matrix matrix = new Matrix();
        /**
         * 用于记录图片要进行拖拉时候的坐标位置
         */
        private Matrix currentMatrix = new Matrix();

        /**
         * 两个手指的开始距离
         */
        private double startDis;
        /**
         * 两个手指的中间点
         */
        private PointF midPoint;

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            /* 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
            switch (event.getAction() & MotionEvent.ACTION_MASK)
            {
                /*手指压下屏幕*/
                case MotionEvent.ACTION_DOWN:
                    /**判断当前scaleType是不是matrix，不是则设置*/
                    if(getScaleType() != ScaleType.MATRIX)
                    {
                        setScaleType(ScaleType.MATRIX);
                    }
                    mode = MODE_DRAG;
                    /*记录ImageView当前的移动位置*/
                    currentMatrix.set(getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    break;
                /*手指在屏幕上移动，改事件会被不断触发*/
                case MotionEvent.ACTION_MOVE:
                    /*拖拉图片*/
                    if (mode == MODE_DRAG)
                    {
                        /*得到x轴的移动距离*/
                        float dx = event.getX() - startPoint.x;
                        /*得到x轴的移动距离*/
                        float dy = event.getY() - startPoint.y;
                        /*在没有移动之前的位置上进行移动*/
                        matrix.set(currentMatrix);
                        matrix.postTranslate(dx, dy);
                    }
                    /*放大缩小图片*/
                    else if (mode == MODE_ZOOM)
                    {
                        /*结束距离*/
                        double endDis = distance(event);
                        /*两个手指并拢在一起的时候像素大于10*/
                        if (endDis > 10f)
                        {
                            /*得到缩放倍数*/
                            double scale = endDis / startDis;
                            matrix.set(currentMatrix);
                            matrix.postScale((float) scale, (float) scale, midPoint.x, midPoint.y);
                        }
                    }
                    break;
                /*手指离开屏幕*/
                case MotionEvent.ACTION_UP:
                    performClick();
                    /*当触点离开屏幕，但是屏幕上还有触点(手指)*/
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;
                /*当屏幕上已经有触点(手指)，再有一个触点压下屏幕*/
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    /* 计算两个手指间的距离 */
                    startDis = distance(event);
                    /* 计算两个手指间的中间点 */
                    if (startDis > 10f)
                    {
                        /*两个手指并拢在一起的时候像素大于10*/
                        midPoint = mid(event);
                        /*记录当前ImageView的缩放倍数*/
                        currentMatrix.set(getImageMatrix());
                    }
                    break;
                default:
                    break;
            }
            setImageMatrix(matrix);
            return true;
        }

        /**
         * 计算两个手指间的距离
         */
        private double distance(MotionEvent event)
        {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /* 使用勾股定理返回两点之间的距离 */
            return Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 计算两个手指间的中间点
         */
        private PointF mid(MotionEvent event)
        {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }
    }
}
