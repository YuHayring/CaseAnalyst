package cn.hayring.caseanalyst.pojo;

import java.util.ArrayList;

/***
 * 事件
 * @author Hayring
 */
public class Event implements Relationable {
    /***
     * 事件名称
     */
    protected String name;

    /***
     * 事件发生时间
     */
    protected Time time;

    /***
     * 事件与人关系集合
     */
    protected ArrayList<Relationship<Person, Event>> manEventRelationships;

    /***
     * 事件与组织关系集合
     */
    protected ArrayList<Relationship<Organization, Event>> orgEventRelationships;

    /***
     * 证物与能动关系集合
     */
    protected ArrayList<ManThingRelationship> manThingRelationships;

    /***
     * 事件所参与的证物的集合-----是否存在有待考量
     */
    protected ArrayList<Evidence> evidences;

    /***
     * 事件信息
     */
    protected String info;

    /***
     * 地点
     */
    protected Place place;

    /***
     * 因素集
     */
    protected ArrayList<Event> factors;

    /***
     * 结果集
     */
    protected ArrayList<Event> results;

    /***
     * 所属案件
     */
    protected Case parentCase;


    /***
     * 保护构造器，初始化各种集合
     */
    protected Event() {
        manEventRelationships = new ArrayList<Relationship<Person, Event>>();
        orgEventRelationships = new ArrayList<Relationship<Organization, Event>>();
        manThingRelationships = new ArrayList<ManThingRelationship>();
        evidences = new ArrayList<Evidence>();
        factors = new ArrayList<Event>();
        results = new ArrayList<Event>();
    }

    /***
     * 赋值
     * @param name
     * @param info
     */
    public Event(String name, String info) {
        this();
        this.name = name;
        this.info = info;
    }

    /***
     * 新建并注册证据
     * @param name
     * @param info
     * @return
     */
    public Evidence createEvidence(String name, String info) {
        Evidence evidence = new Evidence(name, info);
        evidences.add(evidence);
        parentCase.getEvidences().add(evidence);
        return evidence;
    }

    /***
     * 添加证据
     * @param evidence
     */
    public void addEvidence(Evidence evidence) {
        evidences.add(evidence);
        evidence.getEvents().add(this);
    }

    /***
     * 显示名字
     * @return it's name
     */
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public ArrayList<ManThingRelationship> getManThingRelationships() {
        return manThingRelationships;
    }

    public void setManThingRelationships(ArrayList<ManThingRelationship> manThingRelationships) {
        this.manThingRelationships = manThingRelationships;
    }


    public ArrayList<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(ArrayList<Evidence> evidences) {
        this.evidences = evidences;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public ArrayList<Event> getFactors() {
        return factors;
    }

    public void setFactors(ArrayList<Event> factors) {
        this.factors = factors;
    }

    public ArrayList<Event> getResults() {
        return results;
    }

    public void setResults(ArrayList<Event> results) {
        this.results = results;
    }

    public Case getParentCase() {
        return parentCase;
    }

    public void setParentCase(Case parentCase) {
        this.parentCase = parentCase;
    }

    public ArrayList<Relationship<Person, Event>> getManEventRelationships() {
        return manEventRelationships;
    }

    public void setManEventRelationships(ArrayList<Relationship<Person, Event>> manEventRelationships) {
        this.manEventRelationships = manEventRelationships;
    }

    public ArrayList<Relationship<Organization, Event>> getOrgEventRelationships() {
        return orgEventRelationships;
    }

    public void setOrgEventRelationships(ArrayList<Relationship<Organization, Event>> orgEventRelationships) {
        this.orgEventRelationships = orgEventRelationships;
    }

    /***
     * 关系注册
     * @param instance
     */
    @Override
    public void regRelationship(Relationship instance) {
        if (instance.getItemE().equals(this)) {
            if (instance.getItemT().getClass().equals(Person.class)) {
                manEventRelationships.add(instance);
            } else {
                orgEventRelationships.add(instance);
            }
        } else {
            if (instance.getItemE().getClass().equals(Person.class)) {
                manEventRelationships.add(instance);
            } else {
                orgEventRelationships.add(instance);
            }
        }
    }

    @Override
    public Integer getImageIndex() {
        return null;
    }
}
