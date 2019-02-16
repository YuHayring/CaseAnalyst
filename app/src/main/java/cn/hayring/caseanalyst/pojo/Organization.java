package cn.hayring.caseanalyst.pojo;



import java.io.Serializable;
import java.util.ArrayList;

/***
 * 一个团伙或者组织
 * A group or an organization.
 * @author Hayring
 */
public class Organization implements ActiveUnit, Serializable {
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
     *
     * @return false-person or true-org
     */
    @Override
    public Boolean isOrganization() {
        return true;
    }

    /***
     * 组织信息描述
     */
    protected String info;

    /***
     * 组织与证物关系集合
     */
    protected ArrayList<ManThingRelationship> manThingRelationships;


    /***
     * 事件与组织关系集合
     */
    protected ArrayList<ManEventRelationship> manEventRelationships;

    /***
     *  组织与组织的关系
     */
    protected ArrayList<OrgOrgRelationship> orgOrgRelationship;

    /***
     * 组织与个人的关系,待实现
     */


    /***
     * 私有构造器，初始化各种集合
     */
    protected Organization() {
        manThingRelationships = new ArrayList<ManThingRelationship>();
        manEventRelationships = new ArrayList<ManEventRelationship>();
        orgOrgRelationship = new ArrayList<OrgOrgRelationship>();
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

    public ArrayList<ManThingRelationship> getManThingRelationships() {
        return manThingRelationships;
    }

    public void setManThingRelationships(ArrayList<ManThingRelationship> manThingRelationships) {
        this.manThingRelationships = manThingRelationships;
    }

    public ArrayList<ManEventRelationship> getManEventRelationships() {
        return manEventRelationships;
    }

    public void setManEventRelationships(ArrayList<ManEventRelationship> manEventRelationships) {
        this.manEventRelationships = manEventRelationships;
    }

    public ArrayList<OrgOrgRelationship> getOrgOrgRelationship() {
        return orgOrgRelationship;
    }

    public void setOrgOrgRelationship(ArrayList<OrgOrgRelationship> orgOrgRelationship) {
        this.orgOrgRelationship = orgOrgRelationship;
    }
}
