package cn.hayring.caseanalyst.domain;



import java.util.ArrayList;

/***
 * 一个团伙或者组织
 * A group or an organization.
 * @author Hayring
 */
public class Organization implements Avatars {

    /***
     * 头像资源id
     */
    protected Integer imageIndex;
    /***
     * 名字
     */
    protected String name;


    /***
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /***
     * 组织信息描述
     */
    protected String info;

    /***
     * 组织与证物关系集合
     */
    protected ArrayList<Relationship<Organization, Evidence>> orgThingRelationships;


    /***
     * 事件与组织关系集合
     */
    protected ArrayList<Relationship<Organization, Event>> orgEventRelationships;

    /***
     *  组织与组织的关系
     */
    protected ArrayList<Relationship<Organization, Organization>> orgOrgRelationships;

    /***
     * 组织与个人的关系
     */
    protected ArrayList<Relationship<Person, Organization>> manOrgRelationships;

    /***
     * 所属案件
     */
    protected Case parentCase;

    public ArrayList<Person> getMembers() {
        return members;
    }

    /***
     * 成员
     */
    protected ArrayList<Person> members;

    public Case getParentCase() {
        return parentCase;
    }

    public void setParentCase(Case parentCase) {
        this.parentCase = parentCase;
    }

    /***
     * 私有构造器，初始化各种集合
     */
    protected Organization() {
        orgThingRelationships = new ArrayList<Relationship<Organization, Evidence>>();
        orgEventRelationships = new ArrayList<Relationship<Organization, Event>>();
        orgOrgRelationships = new ArrayList<Relationship<Organization, Organization>>();
        manOrgRelationships = new ArrayList<Relationship<Person, Organization>>();
        members = new ArrayList<Person>();

    }

    public Organization(String name, String info) {
        this();
        this.name = name;
        this.info = info;
    }


    @Override
    public String toString() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public Integer getImageIndex() {
        return imageIndex;
    }

    @Override
    public void removeSelf() {
        Relationship.removeAllRelationship(orgEventRelationships);
        Relationship.removeAllRelationship(orgOrgRelationships);
        Relationship.removeAllRelationship(orgThingRelationships);
        Relationship.removeAllRelationship(manOrgRelationships);
        //????????
        for (Person person : members) {
            person.clearOrganization();
        }
    }

    public void setImageIndex(Integer imageIndex) {
        this.imageIndex = imageIndex;
    }

    /***
     * 关系注册
     * @param instance
     */
    @Override
    public void regRelationship(Relationship instance) {
        switch (instance.getType()) {
            case Relationship.ORG_EVENT: {
                orgEventRelationships.add(instance);
            }
            break;
            case Relationship.ORG_EVIDENCE: {
                orgThingRelationships.add(instance);
            }
            break;
            case Relationship.ORG_ORG: {
                orgOrgRelationships.add(instance);
            }
            break;
            case Relationship.MAN_ORG: {
                manOrgRelationships.add(instance);
            }
            break;
            default:
                throw new IllegalArgumentException("ERROR Relationship type");
        }
    }

    public ArrayList<Relationship<Organization, Evidence>> getOrgThingRelationships() {
        return orgThingRelationships;
    }

    public void setOrgThingRelationships(ArrayList<Relationship<Organization, Evidence>> orgThingRelationships) {
        this.orgThingRelationships = orgThingRelationships;
    }

    public ArrayList<Relationship<Organization, Event>> getOrgEventRelationships() {
        return orgEventRelationships;
    }

    public void setOrgEventRelationships(ArrayList<Relationship<Organization, Event>> orgEventRelationships) {
        this.orgEventRelationships = orgEventRelationships;
    }

    public ArrayList<Relationship<Organization, Organization>> getOrgOrgRelationships() {
        return orgOrgRelationships;
    }

    public void setOrgOrgRelationships(ArrayList<Relationship<Organization, Organization>> orgOrgRelationships) {
        this.orgOrgRelationships = orgOrgRelationships;
    }

    public ArrayList<Relationship<Person, Organization>> getManOrgRelationships() {
        return manOrgRelationships;
    }

    public void setManOrgRelationships(ArrayList<Relationship<Person, Organization>> manOrgRelationships) {
        this.manOrgRelationships = manOrgRelationships;
    }
}
