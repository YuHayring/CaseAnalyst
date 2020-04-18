/**
 *
 */
package cn.hayring.caseanalyst.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;

/**
 *
 * 自定义的View,绘制线调条
 *
 * @author 邵磊2016年3月22日 14:15:34
 *
 */
@SuppressLint("DrawAllocation")
public class DashArrowV3 extends DashArrowV2 {
    public static final float PAINT_WIDTH = 5.0f;


    public DashArrowV3(Context context, Circle startView, Circle endView, int height, HVScrollView scroller) {
        super(context, startView, endView, height, scroller);
        calculate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        calculate();
        Paint paint = new Paint(); // 红色画笔
        paint.setAntiAlias(true); // 抗锯齿效果,显得绘图平滑
        paint.setColor(Color.WHITE); // 设置画笔颜色
        paint.setStrokeWidth(PAINT_WIDTH);// 设置笔触宽度
        paint.setStyle(Style.STROKE);// 设置画笔的填充类型(完全填充)
        paint.setTextSize(50);//字体

        Path mPath = new Path();

        if (x1 > x2) {
            float temp;
            temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        if (y1 == y2) {
            mPath.moveTo(2.5f, 2.5f);
            mPath.lineTo(Math.abs(x1 - x2) + 2.5f, 2.5f);
        } else if (x1 == x2) {
            mPath.moveTo(2.5f, 2.5f);
            mPath.lineTo(2.5f, Math.abs(y1 - y2) + 2.5f);
        } else if (y2 < y1) {
            mPath.moveTo(2.5f, y1 - y2 + 2.5f);
            mPath.cubicTo(2.5f, y1 - y2 + 2.5f, 2.5f, 2.5f, x2 - x1 + 2.5f, 2.5f);
        } else {
            mPath.moveTo(2.5f, 2.5f);
            mPath.cubicTo(2.5f, 2.5f, 2.5f, y2 - y1 + 2.5f, x2 - x1 + 2.5f, y2 - y1 + 2.5f);
        }


        //起点
/*		mPath.moveTo(0, 0+height);
		//贝塞尔曲线
		mPath.cubicTo(0 , 0+height, 0 , y2-y1+height, x2-x1, y2-y1+height);*/

        //起点
/*		mPath.moveTo(x1, y1);
		//贝塞尔曲线
		mPath.cubicTo(x1 , y1, x1 , y2, x2, y2);*/


        //画path
        canvas.drawPath(mPath, paint);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        calculate();
        setMeasuredDimension((int) PAINT_WIDTH + (int) Math.abs(x1 - x2), (int) PAINT_WIDTH + (int) Math.abs(y1 - y2));
    }

/*	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		float l = Math.min(x1,x2);
		float t = Math.min(y1,y2);
		float width = Math.abs(x1-x2);
		float height = Math.abs(y1-y2);
		layout((int)l, (int)t, (int)(l+width), (int)(t+height));
	}*/

    public int[] getMargin() {
        float l = Math.min(x1, x2);
        float t = Math.min(y1, y2);
        int[] maigins = {(int) l, (int) t, 0, 0};
        return maigins;
    }
}
