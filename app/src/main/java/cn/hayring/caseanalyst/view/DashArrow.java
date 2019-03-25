package cn.hayring.caseanalyst.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/***
 * 曲线类
 */
public class DashArrow extends View {

    protected boolean redrawed;

    public boolean isRedrawed() {
        return redrawed;
    }

    public void setRedrawed() {
        this.redrawed = true;
    }

    public void reSetRedrawed() {
        this.redrawed = false;
    }

    private int lastX;
    private int lastY;
    private Circle startView;
    private Circle endView;

    Context context;

    float x1 = 0;
    float y1 = 0;

    float x2 = 0;
    float y2 = 0;

    int height;


    public DashArrow(Context context, Circle startView, Circle endView, int height) {
        super(context);
        this.context = context;
        this.startView = startView;
        this.endView = endView;
        this.height = height;
        this.startView.getLines().add(this);
        this.endView.getLines().add(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calculate();
        // 创建画笔
        Paint p = new Paint();
        p.setColor(Color.BLACK);  // 设置颜色
        p.setStrokeWidth(dip2px(context, 1));   // 设置宽度
        p.setAntiAlias(true);   // 抗锯齿


        //画贝塞尔曲线
        p.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(x1, y1);

        float quaX = x1 / 4;
        float quaY = (y1 + y2) / 2;
        if (y2 - y1 < 0) {
            quaX = (x1 + x2) / 2;
            quaY = y2 - 100;
        } else if (y2 - y1 < 50) {
            quaX = (x1 + x2) / 2;
            quaY = y1 - 50;
        }
        path.quadTo(quaX, quaY, x2, y2);

        canvas.drawPath(path, p);


    }

    private void calculate() {
        int[] location = new int[2];
        startView.getLocationInWindow(location);
        x1 = location[0] + startView.getWidth() / 2;
        y1 = location[1] - height + startView.getHeight() / 2;
        endView.getLocationInWindow(location);
        x2 = location[0] + endView.getWidth() / 2;
        y2 = location[1] - height + endView.getHeight() / 2;
    }


    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}