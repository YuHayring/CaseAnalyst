package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;

/***
 * 人与事件的关系
 * @author Hayring
 */
public class ManEventRelationship implements Serializable {
    /***
     * 能动单位
     */
    private ActiveUnit activeUnit;

    /***
     * 证物
     */
    private Event event;

    /***
     * 关键词
     */
    private String key;

    public ManEventRelationship(ActiveUnit activeUnit, Event event, String key) {
        this.activeUnit = activeUnit;
        this.event = event;
        this.key = key;
        activeUnit.getManEventRelationships().put(event.getName(), this);
        event.getManEventRelationships().put(activeUnit.getName(), this);
    }


    @Override
    public String toString() {
        return activeUnit.getName() + " and " + event.getName() + " key: " + key;
    }


    public ActiveUnit getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(ActiveUnit activeUnit) {
        this.activeUnit = activeUnit;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
