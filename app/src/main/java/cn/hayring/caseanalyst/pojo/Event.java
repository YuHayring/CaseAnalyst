package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;
import java.util.HashMap;

/***
 * 事件
 * @author Hayring
 */
public class Event implements Serializable {
    /***
     * 事件名称
     */
    protected String name;

    /***
     * 事件发生时间
     */
    protected Time time;

    /***
     * 事件与能动单元关系集合,字符串为能动单位的名字
     * String-ActiveUnit's name;
     */
    protected HashMap<String, ManEventRelationship> manEventRelationships;

    /***
     * 证物与能动关系集合，字符串为证物的名字
     * String-Evidence's name;
     */
    protected HashMap<String, ManThingRelationship> manThingRelationships;

    /***
     * 事件所参与的能动单位的集合-----是否存在有待考量
     * String-ActiveUnit's name;
     */
    protected HashMap<String, ActiveUnit> activeUnits;

    /***
     * 事件所参与的证物的集合-----是否存在有待考量
     */
    protected HashMap<String, Evidence> evidences;

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
    protected HashMap<String, Event> factors;

    /***
     * 结果集
     */
    protected HashMap<String, Event> results;


    public Event() {
        manEventRelationships = new HashMap<String, ManEventRelationship>();
        manThingRelationships = new HashMap<String, ManThingRelationship>();
        activeUnits = new HashMap<String, ActiveUnit>();
        evidences = new HashMap<String, Evidence>();
        factors = new HashMap<String, Event>();
        results = new HashMap<String, Event>();
    }

    public Event(String name, String info) {
        this();
        this.name = name;
        this.info = info;
    }

    public Evidence createEvidence(String name, String info) {
        Evidence evidence = new Evidence(name, info);
        evidences.put(evidence.getName(), evidence);
        return evidence;
    }

    public void putEvidence(Evidence evidence) {
        evidences.put(evidence.getName(), evidence);
        evidence.getEvents().put(this.name, this);
    }

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

    public HashMap<String, ManEventRelationship> getManEventRelationships() {
        return manEventRelationships;
    }

    public void setManEventRelationships(HashMap<String, ManEventRelationship> manEventRelationships) {
        this.manEventRelationships = manEventRelationships;
    }

    public HashMap<String, ManThingRelationship> getManThingRelationships() {
        return manThingRelationships;
    }

    public void setManThingRelationships(HashMap<String, ManThingRelationship> manThingRelationships) {
        this.manThingRelationships = manThingRelationships;
    }

    public HashMap<String, ActiveUnit> getActiveUnits() {
        return activeUnits;
    }

    public void setActiveUnits(HashMap<String, ActiveUnit> activeUnits) {
        this.activeUnits = activeUnits;
    }

    public HashMap<String, Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(HashMap<String, Evidence> evidences) {
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

    public HashMap<String, Event> getFactors() {
        return factors;
    }

    public void setFactors(HashMap<String, Event> factors) {
        this.factors = factors;
    }

    public HashMap<String, Event> getResults() {
        return results;
    }

    public void setResults(HashMap<String, Event> results) {
        this.results = results;
    }
}
