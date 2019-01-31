package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;
import java.util.ArrayList;

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
    protected ArrayList<ManThingRelationship> relationships;

    /***
     * 证物信息
     */
    protected String info;

    /***
     * 保护构造器，初始化各种集合
     */
    protected Evidence() {
        relationships = new ArrayList<ManThingRelationship>();
        events = new ArrayList<Event>();
    }

    public Evidence(String name, String info) {
        this();
        this.name = name;
        this.info = info;
    }

    protected ArrayList<Event> events;




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

    public ArrayList<ManThingRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(ArrayList<ManThingRelationship> relationships) {
        this.relationships = relationships;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
