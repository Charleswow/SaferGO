package com.jack.specialEffects;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.jack.isafety.R;


public class ThreeDSlidingLayout extends RelativeLayout implements View.OnTouchListener {

    /**
     * 滚动显示和隐藏左侧布局时，手指滑动需要达到的速度。
     */
    public static final int SNAP_VELOCITY = 200;

    /**
     * 滑动状态的一种，表示未进行任何滑动。
     */
    public static final int DO_NOTHING = 0;

    /**
     * 滑动状态的一种，表示正在滑出左侧菜单。
     */
    public static final int SHOW_MENU = 1;

    /**
     * 滑动状态的一种，表示正在隐藏左侧菜单。
     */
    public static final int HIDE_MENU = 2;

    /**
     * 记录当前的滑动状态
     */
    private int slideState;

    /**
     * 屏幕宽度值。
     */
    private int screenWidth;

    /**
     * 右侧布局最多可以滑动到的左边缘。
     */
    private int leftEdge = 0;

    /**
     * 右侧布局最多可以滑动到的右边缘。
     */
    private int rightEdge = 0;

    /**
     * 在被判定为滚动之前用户手指可以移动的最大值。
     */
    private int touchSlop;

    /**
     * 记录手指按下时的横坐标。
     */
    private float xDown;

    /**
     * 记录手指按下时的纵坐标。
     */
    private float yDown;

    /**
     * 记录手指移动时的横坐标。
     */
    private float xMove;

    /**
     * 记录手指移动时的纵坐标。
     */
    private float yMove;

    /**
     * 记录手机抬起时的横坐标。
     */
    private float xUp;

    /**
     * 左侧布局当前是显示还是隐藏。只有完全显示或隐藏时才会更改此值，滑动过程中此值无效。
     */
    private boolean isLeftLayoutVisible;

    /**
     * 是否正在滑动。
     */
    private boolean isSliding;

    /**
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private boolean loadOnce;

    /**
     * 左侧布局对象。
     */
    private View leftLayout;

    /**
     * 右侧布局对象。
     */
    private View rightLayout;

    /**
     * 在滑动过程中展示的3D视图
     */
    private Image3dView image3dView;

    /**
     * 用于监听侧滑事件的View。
     */
    private View mBindView;

    /**
     * 左侧布局的参数，通过此参数来重新确定左侧布局的宽度，以及更改leftMargin的值。
     */
    private MarginLayoutParams leftLayoutParams;

    /**
     * 右侧布局的参数，通过此参数来重新确定右侧布局的宽度。
     */
    private MarginLayoutParams rightLayoutParams;

    /**
     * 3D视图的参数，通过此参数来重新确定3D视图的宽度。
     */
    private ViewGroup.LayoutParams image3dViewParams;

    /**
     * 用于计算手指滑动的速度。
     */
    private VelocityTracker mVelocityTracker;

    /**
     * 重写SlidingLayout的构造函数，其中获取了屏幕的宽度。
     *
     * @param context
     * @param attrs
     */
    public ThreeDSlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 绑定监听侧滑事件的View，即在绑定的View进行滑动才可以显示和隐藏左侧布局。
     *
     * @param bindView
     *            需要绑定的View对象。
     */
    public void setScrollEvent(View bindView) {
        mBindView = bindView;
        mBindView.setOnTouchListener(this);
    }

    /**
     * 将屏幕滚动到左侧布局界面，滚动速度设定为10.
     */
    public void scrollToLeftLayout() {
        image3dView.clearSourceBitmap();
        new ScrollTask().execute(-10);
    }

    /**
     * 将屏幕滚动到右侧布局界面，滚动速度设定为-10.
     */
    public void scrollToRightLayout() {
        image3dView.clearSourceBitmap();
        new ScrollTask().execute(10);
    }

    /**
     * 左侧布局是否完全显示出来，或完全隐藏，滑动过程中此值无效。
     *
     * @return 左侧布局完全显示返回true，完全隐藏返回false。
     */
    public boolean isLeftLayoutVisible() {
        return isLeftLayoutVisible;
    }

    /**
     * 在onLayout中重新设定左侧布局和右侧布局的参数。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce) {
            // 获取左侧布局对象
            leftLayout = findViewById(R.id.more_tools_menu);
            leftLayoutParams = (MarginLayoutParams) leftLayout.getLayoutParams();
            rightEdge = -leftLayoutParams.width;
            // 获取右侧布局对象
            rightLayout = findViewById(R.id.more_tools_content);
            rightLayoutParams = (MarginLayoutParams) rightLayout.getLayoutParams();
            rightLayoutParams.width = screenWidth;
            rightLayout.setLayoutParams(rightLayoutParams);
            // 获取3D视图对象
            image3dView = (Image3dView) findViewById(R.id.more_tools_image_3d_view);
            // 将左侧布局传入3D视图中作为生成源
            image3dView.setSourceView(leftLayout);
            loadOnce = true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时，记录按下时的横坐标
                xDown = event.getRawX();
                yDown = event.getRawY();
                slideState = DO_NOTHING;
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整右侧布局的leftMargin值，从而显示和隐藏左侧布局
                xMove = event.getRawX();
                yMove = event.getRawY();
                int moveDistanceX = (int) (xMove - xDown);
                int moveDistanceY = (int) (yMove - yDown);
                checkSlideState(moveDistanceX, moveDistanceY);
                switch (slideState) {
                    case SHOW_MENU:
                        rightLayoutParams.rightMargin = -moveDistanceX;
                        onSlide();
                        break;
                    case HIDE_MENU:
                        rightLayoutParams.rightMargin = rightEdge - moveDistanceX;
                        onSlide();
                        break;
                    default:
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();
                int upDistanceX = (int) (xUp - xDown);
                if (isSliding) {
                    // 手指抬起时，进行判断当前手势的意图
                    switch (slideState) {
                        case SHOW_MENU:
                            if (shouldScrollToLeftLayout()) {
                                scrollToLeftLayout();
                            } else {
                                scrollToRightLayout();
                            }
                            break;
                        case HIDE_MENU:
                            if (shouldScrollToRightLayout()) {
                                scrollToRightLayout();
                            } else {
                                scrollToLeftLayout();
                            }
                            break;
                        default:
                            break;
                    }
                } else if (upDistanceX < touchSlop && isLeftLayoutVisible) {
                    scrollToRightLayout();
                }
                recycleVelocityTracker();
                break;
        }
        if (v.isEnabled()) {
            if (isSliding) {
                unFocusBindView();
                return true;
            }
            if (isLeftLayoutVisible) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 执行滑动过程中的逻辑操作，如边界检查，改变偏移值，可见性检查等。
     */
    private void onSlide() {
        checkSlideBorder();
        rightLayout.setLayoutParams(rightLayoutParams);
        image3dView.clearSourceBitmap();
        image3dViewParams = image3dView.getLayoutParams();
        image3dViewParams.width = -rightLayoutParams.rightMargin;
        // 滑动的同时改变3D视图的大小
        image3dView.setLayoutParams(image3dViewParams);
        // 保证在滑动过程中3D视图可见，左侧布局不可见
        showImage3dView();
    }

    /**
     * 根据手指移动的距离，判断当前用户的滑动意图，然后给slideState赋值成相应的滑动状态值。
     *
     * @param moveDistanceX
     *            横向移动的距离
     * @param moveDistanceY
     *            纵向移动的距离
     */
    private void checkSlideState(int moveDistanceX, int moveDistanceY) {
        if (isLeftLayoutVisible) {
            if (!isSliding && Math.abs(moveDistanceX) >= touchSlop && moveDistanceX < 0) {
                isSliding = true;
                slideState = HIDE_MENU;
            }
        } else if (!isSliding && Math.abs(moveDistanceX) >= touchSlop && moveDistanceX > 0
                && Math.abs(moveDistanceY) < touchSlop) {
            isSliding = true;
            slideState = SHOW_MENU;
        }
    }

    /**
     * 在滑动过程中检查左侧菜单的边界值，防止绑定布局滑出屏幕。
     */
    private void checkSlideBorder() {
        if (rightLayoutParams.rightMargin > leftEdge) {
            rightLayoutParams.rightMargin = leftEdge;
        } else if (rightLayoutParams.rightMargin < rightEdge) {
            rightLayoutParams.rightMargin = rightEdge;
        }
    }

    /**
     * 判断是否应该滚动将左侧布局展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
     * 就认为应该滚动将左侧布局展示出来。
     *
     * @return 如果应该滚动将左侧布局展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToLeftLayout() {
        return xUp - xDown > leftLayoutParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断是否应该滚动将右侧布局展示出来。如果手指移动距离加上leftLayoutPadding大于屏幕的1/2，
     * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将右侧布局展示出来。
     *
     * @return 如果应该滚动将右侧布局展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToRightLayout() {
        return xDown - xUp > leftLayoutParams.width / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 创建VelocityTracker对象，并将触摸事件加入到VelocityTracker当中。
     *
     * @param event
     *            右侧布局监听控件的滑动事件
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 获取手指在右侧布局的监听View上的滑动速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * 使用可以获得焦点的控件在滑动的时候失去焦点。
     */
    private void unFocusBindView() {
        if (mBindView != null) {
            mBindView.setPressed(false);
            mBindView.setFocusable(false);
            mBindView.setFocusableInTouchMode(false);
        }
    }

    /**
     * 保证此时让左侧布局不可见，3D视图可见，从而让滑动过程中产生3D的效果。
     */
    private void showImage3dView() {
        if (image3dView.getVisibility() != View.VISIBLE) {
            image3dView.setVisibility(View.VISIBLE);
        }
        if (leftLayout.getVisibility() != View.INVISIBLE) {
            leftLayout.setVisibility(View.INVISIBLE);
        }
    }

    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int rightMargin = rightLayoutParams.rightMargin;
            // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
            while (true) {
                rightMargin = rightMargin + speed[0];
                if (rightMargin < rightEdge) {
                    rightMargin = rightEdge;
                    break;
                }
                if (rightMargin > leftEdge) {
                    rightMargin = leftEdge;
                    break;
                }
                publishProgress(rightMargin);
                // 为了要有滚动效果产生，每次循环使线程睡眠5毫秒，这样肉眼才能够看到滚动动画。
                sleep(5);
            }
            if (speed[0] > 0) {
                isLeftLayoutVisible = false;
            } else {
                isLeftLayoutVisible = true;
            }
            isSliding = false;
            return rightMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... rightMargin) {
            rightLayoutParams.rightMargin = rightMargin[0];
            rightLayout.setLayoutParams(rightLayoutParams);
            image3dViewParams = image3dView.getLayoutParams();
            image3dViewParams.width = -rightLayoutParams.rightMargin;
            image3dView.setLayoutParams(image3dViewParams);
            showImage3dView();
            unFocusBindView();
        }

        @Override
        protected void onPostExecute(Integer rightMargin) {
            rightLayoutParams.rightMargin = rightMargin;
            rightLayout.setLayoutParams(rightLayoutParams);
            image3dViewParams = image3dView.getLayoutParams();
            image3dViewParams.width = -rightLayoutParams.rightMargin;
            image3dView.setLayoutParams(image3dViewParams);
            if (isLeftLayoutVisible) {
                // 保证在滑动结束后左侧布局可见，3D视图不可见。
                image3dView.setVisibility(View.INVISIBLE);
                leftLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param millis
     *            指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
