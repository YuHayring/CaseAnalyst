package cn.hayring.caseanalyst.pojo;


import java.io.Serializable;
import java.util.HashMap;

/***
 * 案件
 * @author Hayring
 */
public class Case implements Serializable {
    /***
     * 名字
     */
    protected String name;

    /***
     * 案件发生的时间
     * Time when case happened;
     */
    protected Time time;

    /***
     * 案件信息
     */
    protected String info;

    /***
     * 能动单元集合
     */
    protected HashMap<String, ActiveUnit> activeUnits;

    /***
     * 证物集合
     */
    protected HashMap<String, Evidence> evidences;

    /***
     * 事件集合
     */
    protected HashMap<String, Event> events;

    /***
     * 主要能动单位
     */
    protected ActiveUnit mainActiveUnit;


    public Case() {
        activeUnits = new HashMap<String, ActiveUnit>();
        evidences = new HashMap<String, Evidence>();
        events = new HashMap<String, Event>();
    }

    public Case(String name, String info) {
        this();
        this.name = name;
        this.info = info;
    }


    public Event createEvent(String name, String info) {
        Event event = new Event(name, info);
        events.put(event.getName(), event);
        return event;
    }

    public Person createPerson(String name, Boolean suspect, String info) {
        Person person = new Person(name, suspect, info);
        activeUnits.put(person.getName(), person);
        return person;
    }

    public Organization createOrganization(String name, String info) {
        Organization org = new Organization(name, info);
        activeUnits.put(org.getName(), org);
        return org;
    }


    @Override
    public String toString() {
        return name;
    }


    public ActiveUnit getMainActiveUnit() {
        return mainActiveUnit;
    }

    public void setMainActiveUnit(ActiveUnit mainActiveUnit) {
        this.mainActiveUnit = mainActiveUnit;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public HashMap<String, Event> getEvents() {
        return events;
    }

    public void setEvents(HashMap<String, Event> events) {
        this.events = events;
    }


}
