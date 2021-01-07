package cn.hayring.caseanalyst.view.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/***
 * 人物关系图下的点，用于确定连线位置
 */
public class Circle extends View {
    //    定义画笔
    Paint paint;

    /***
     * 连接到此点的线集合
     */
    ArrayList<DashArrow> lines = new ArrayList<DashArrow>();

    public ArrayList<DashArrow> getLines() {
        return lines;
    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //    重写draw方法
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

//        实例化画笔对象
        paint = new Paint();
//        给画笔设置颜色
        paint.setColor(Color.BLACK);
//        设置画笔属性
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        //paint.setStyle(Paint.Style.STROKE);//画笔属性是空心圆
        paint.setStrokeWidth(8);//设置画笔粗细

        /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔
                */
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 8, paint);

    }


}
