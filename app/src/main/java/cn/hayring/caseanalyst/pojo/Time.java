package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;
import java.util.Date;

/***
 * 模糊时间
 * @author Hayring
 */
public class Time implements Serializable {
    public final boolean TIME_POINT = true;
    public final boolean TIME_FRAME = false;

    /***
     * 时间类型
     */
    private boolean type;
    /***
     * 时间上限
     */
    private Date earlier;

    /***
     * 中间时刻
     */
    private Date middle;

    /***
     * 时间下限
     */
    private Date later;

    /***
     * 时间偏移，单位毫秒
     */
    private long deviation;

    /***
     * 构造时间，并计算上下限
     *
     */
    public Time(Date middle, long deviation, boolean type) {
        this.middle = middle;
        this.deviation = deviation;
        this.type = type;
        this.later = new Date(middle.getTime() - deviation);
        this.earlier = new Date(middle.getTime() + deviation);
    }

    public Time(Date earlier, Date later) {
        this.later = later;
        this.earlier = earlier;
        this.deviation = (later.getTime() + earlier.getTime()) / 2L;
        this.middle = new Date(earlier.getTime() + deviation);

    }


    @Override
    public String toString() {
        return "From " + earlier.toString() + " to " + later.toString();
    }


    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Date getEarlier() {
        return earlier;
    }

    public void setEarlier(Date earlier) {
        this.earlier = earlier;
    }

    public Date getMiddle() {
        return middle;
    }

    public void setMiddle(Date middle) {
        this.middle = middle;
    }

    public Date getLater() {
        return later;
    }

    public void setLater(Date later) {
        this.later = later;
    }

    public long getDeviation() {
        return deviation;
    }

    public void setDeviation(long deviation) {
        this.deviation = deviation;
    }
}
