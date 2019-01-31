package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;
import java.util.HashMap;

/***
 * 物件，痕迹，证物
 * @author Hayring
 */
public class Evidence implements Serializable {
    /***
     * 证物名称
     */
    protected String name;

    /***
     * 证物产生的时间
     */
    protected Time createdTime;

    /***
     * 数量
     */
    protected Integer count;

    /***
     * 产生地点
     */
    protected Place createdPlace;

    /***
     * 证物与能动单元关系集合,字符串为能动单位的名字
     * String-ActiveUnit's name;
     */
    protected HashMap<String, ManThingRelationship> relationships;

    /***
     * 证物信息
     */
    protected String info;


    public Evidence() {
        relationships = new HashMap<String, ManThingRelationship>();
        events = new HashMap<String, Event>();
    }

    public Evidence(String name, String info) {
        this();
        this.name = name;
        this.info = info;
    }

    protected HashMap<String, Event> events;


    @Override
    public String toString() {
        return name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Time createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Place getCreatedPlace() {
        return createdPlace;
    }

    public void setCreatedPlace(Place createdPlace) {
        this.createdPlace = createdPlace;
    }

    public HashMap<String, ManThingRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(HashMap<String, ManThingRelationship> relationships) {
        this.relationships = relationships;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public HashMap<String, Event> getEvents() {
        return events;
    }

    public void setEvents(HashMap<String, Event> events) {
        this.events = events;
    }
}
