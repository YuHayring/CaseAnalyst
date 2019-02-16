package cn.hayring.caseanalyst.pojo;


import java.io.Serializable;
import java.util.ArrayList;

/***
 * 案件
 * @author Hayring
 */
public class Case implements Serializable, Listable {
    /***
     * 名字
     */
    protected String name;

    /***
     * 案件信息
     */
    protected String info;

    /***
     * 案件发生的时间
     * Time when case happened;
     */
    protected Time time;

    /***
     * 能动单元集合
     */
    protected ArrayList<ActiveUnit> activeUnits;

    /***
     * 证物集合
     */
    protected ArrayList<Evidence> evidences;

    /***
     * 事件集合
     */
    protected ArrayList<Event> events;

    /***
     * 主要能动单位
     */
    //protected ActiveUnit mainActiveUnit;

    public Case() {
        activeUnits = new ArrayList<ActiveUnit>();
        evidences = new ArrayList<Evidence>();
        events = new ArrayList<Event>();
    }

    public Case(String name, String info) {
        this();
        this.name = name;
        this.info = info;
    }


    /***
     * 时间生成并注册
     * Create Event and reg it
     * @param name
     * @param info
     * @return new Event
     */
    public Event createEvent(String name, String info) {
        Event event = new Event(name, info);
        events.add(event);
        event.setParentCase(this);
        return event;
    }

    /***
     * 人生成并注册
     * Create Person and reg it
     * @param name
     * @param suspect
     * @param info
     * @return
     */
    public Person createPerson(String name, Boolean suspect, String info) {
        Person person = new Person(name, suspect, info);
        activeUnits.add(person);
        return person;
    }

    /***
     * 组织生成并注册
     * Create Org and reg it
     * @param name
     * @param info
     * @return
     */
    public Organization createOrganization(String name, String info) {
        Organization org = new Organization(name, info);
        activeUnits.add(org);
        return org;
    }

    /***
     * 显示名字
     * @return it's name
     */
    @Override
    public String toString() {
        return name;
    }






    /*
    public ActiveUnit getMainActiveUnit() {
        return mainActiveUnit;
    }

    public void setMainActiveUnit(ActiveUnit mainActiveUnit) {
        this.mainActiveUnit = mainActiveUnit;
    }
    */

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

    public ArrayList<ActiveUnit> getActiveUnits() {
        return activeUnits;
    }

    public void setActiveUnits(ArrayList<ActiveUnit> activeUnits) {
        this.activeUnits = activeUnits;
    }

    public ArrayList<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(ArrayList<Evidence> evidences) {
        this.evidences = evidences;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }


}
