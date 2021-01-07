package cn.hayring.caseanalyst.view.listener;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import cn.hayring.caseanalyst.view.myview.Circle;
import cn.hayring.caseanalyst.view.myview.DashArrow;

/***
 * 人物关系图中的元素触摸监听器
 */
public class PersonItemOnTouchListener implements View.OnTouchListener {

    private int lastX;
    private int lastY;

    private ViewGroup aimView;

    public PersonItemOnTouchListener(ViewGroup view) {
        this.aimView = view;
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
                aimView.offsetLeftAndRight(offsetX);
                aimView.offsetTopAndBottom(offsetY);
                Circle point = (Circle) aimView.getChildAt(2);
                for (DashArrow line : point.getLines()) {
                    line.invalidate();
                }


                //scrollBy(-offsetX,-offsetY);



                /*ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
                layoutParams.leftMargin = getLeft() + offsetX;
                layoutParams.topMargin = getTop() + offsetY;
                setLayoutParams(layoutParams);*/
                break;
            }
        }
        return true;
    }
}
