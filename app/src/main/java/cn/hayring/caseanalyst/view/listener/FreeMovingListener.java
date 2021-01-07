package cn.hayring.caseanalyst.view.listener;

import android.view.MotionEvent;
import android.view.View;

/***
 * 自由移动监听器
 */
public class FreeMovingListener implements View.OnTouchListener {

    private int lastX;
    private int lastY;

    private View aimView;
    boolean dispatched;

    public FreeMovingListener(View view, boolean dispatched) {
        this.aimView = view;
        this.dispatched = dispatched;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = x;
                lastY = y;
                break;
            }

            //让元素跟随手指移动
            case MotionEvent.ACTION_MOVE: {
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                /*aimView.offsetLeftAndRight(offsetX);
                aimView.offsetTopAndBottom(offsetY);*/
                view.layout(view.getLeft() + offsetX, view.getTop() + offsetY, view.getRight() + offsetX, view.getBottom() + offsetY);
                break;
            }
        }
        return dispatched;
    }
}
