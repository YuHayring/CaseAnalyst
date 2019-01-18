package cn.hayring.caseanalyst.pojo;


import java.util.HashMap;

/***
 * 一个团伙或者组织
 * A group or an organization.
 * @author Hayring
 */
public class Organization implements ActiveUnit {
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
     * 组织与证物关系集合，字符串为证物名字
     */
    protected HashMap<String, ManThingRelationship> manThingRelationships;


    /***
     * 事件与组织关系集合,字符串为事件的名字
     * String-ActiveUnit's name;
     */
    protected HashMap<String, ManEventRelationship> manEventRelationships;

    /***
     *  组织与组织的关系
     */
    protected HashMap<String, OrgOrgRelationship> orgOrgRelationship;

    /***
     * 组织与个人的关系,待实现
     */


    public Organization() {
        manThingRelationships = new HashMap<String, ManThingRelationship>();
        manEventRelationships = new HashMap<String, ManEventRelationship>();
        orgOrgRelationship = new HashMap<String, OrgOrgRelationship>();
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

    public HashMap<String, ManThingRelationship> getManThingRelationships() {
        return manThingRelationships;
    }

    public void setManThingRelationships(HashMap<String, ManThingRelationship> manThingRelationships) {
        this.manThingRelationships = manThingRelationships;
    }

    public HashMap<String, ManEventRelationship> getManEventRelationships() {
        return manEventRelationships;
    }

    public void setManEventRelationships(HashMap<String, ManEventRelationship> manEventRelationships) {
        this.manEventRelationships = manEventRelationships;
    }

    public HashMap<String, OrgOrgRelationship> getOrgOrgRelationship() {
        return orgOrgRelationship;
    }

    public void setOrgOrgRelationship(HashMap<String, OrgOrgRelationship> orgOrgRelationship) {
        this.orgOrgRelationship = orgOrgRelationship;
    }
}
