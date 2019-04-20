package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/***
 * 事件片段
 */
public class EventClip implements Serializable {
    /***
     * 参与者
     */
    //protected Person person;

    /***
     * 证物
     */
    //protected Evidence evidence;

    /***
     * 行为
     */
    //protected String action;

    /***
     * 信息
     */
    protected String info;

    /***
     * 具体时刻
     */
    protected Calendar timePoint;
/*
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }*/

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Calendar getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Calendar timePoint) {
        this.timePoint = timePoint;
    }


    public EventClip() {
        this.timePoint = new GregorianCalendar(0, 0, 0);
        {
        }
    }
}
