package cn.hayring.caseanalyst.pojo;


import java.util.ArrayList;

/***
 * 案件
 * @author Hayring
 */
public class Case implements Listable {
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
     * 证物集合
     */
    protected ArrayList<Evidence> evidences;

    /***
     * 事件集合
     */
    protected ArrayList<Event> events;

    /***
     * 人物集合
     */
    protected ArrayList<Person> persons;

    /***
     * 组织集合
     */
    protected ArrayList<Organization> organizations;

    public Case() {
        organizations = new ArrayList<Organization>();
        persons = new ArrayList<Person>();
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

    public Event createEvent() {
        Event event = new Event();
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
        persons.add(person);
        person.setParentCase(this);
        return person;
    }

    public Person createPerson() {
        Person person = new Person();
        persons.add(person);
        person.setParentCase(this);
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
        organizations.add(org);
        org.setParentCase(this);
        return org;
    }

    public Organization createOrganization() {
        Organization org = new Organization();
        organizations.add(org);
        org.setParentCase(this);
        return org;
    }


    /***
     * 证物生成并注册
     * Create Evidenct and reg it
     */
    public Evidence createEvidence() {
        Evidence evidence = new Evidence();
        evidences.add(evidence);
        evidence.setParentCase(this);
        return evidence;
    }

    /***
     * 显示名字
     * @return it's name
     */
    @Override
    public String toString() {
        return name;
    }


    /***
     * 按所需类型获取List
     * @return ListbaleList
     */
    public ArrayList getListableList(Class clazz) {
        if (clazz.equals(Person.class)) {
            return persons;
        }

        if (clazz.equals(Organization.class)) {
            return organizations;
        }

        if (clazz.equals(Event.class)) {
            return events;
        }

        if (clazz.equals(Evidence.class)) {
            return evidences;
        }

        throw new IllegalArgumentException("Error class! Not listable");
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

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(ArrayList<Organization> organizations) {
        this.organizations = organizations;
    }
}
