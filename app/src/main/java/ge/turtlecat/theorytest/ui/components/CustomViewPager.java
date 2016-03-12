package ge.turtlecat.theorytest.ui.components;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by Alexander on 10/3/2014.
 */
public class CustomViewPager extends ViewPager {

    boolean isFingerSwipeable = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isFingerSwipeable)
            return super.onInterceptTouchEvent(arg0);
        return isFingerSwipeable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isFingerSwipeable)
            return super.onTouchEvent(event);
        return isFingerSwipeable;
    }

    public void setFingerSwipable(boolean flag) {
        isFingerSwipeable = flag;
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof WebView) {
            int threshold = v.getWidth() * 10 / 100;
            if (x > threshold && x < v.getWidth() - threshold) {
                return true;
            }
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}