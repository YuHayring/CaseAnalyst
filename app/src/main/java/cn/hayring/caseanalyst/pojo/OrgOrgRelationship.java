package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;

/***
 * 组织与组织的关系
 * @author Hayring
 */
public class OrgOrgRelationship implements Serializable {
    /***
     * 建立关系的两个组织
     */
    private Organization a;
    private Organization b;

    /***
     * 关系信息
     */
    private String key;

    /***
     * 方向性，当两者关系关于key有向，则为true
     * directional it is true when a key b;
     */
    private boolean directional;

    public OrgOrgRelationship(Organization a, Organization b, String key, boolean directional) {
        this.a = a;
        this.b = b;
        this.key = key;
        this.directional = directional;
        a.getOrgOrgRelationship().put(b.getName(), this);
        b.getOrgOrgRelationship().put(a.getName(), this);
    }

    @Override
    public String toString() {
        return a.getName() + " and " + b.getName() + " key: " + key;
    }


    public Organization getA() {
        return a;
    }

    public void setA(Organization a) {
        this.a = a;
    }

    public Organization getB() {
        return b;
    }

    public void setB(Organization b) {
        this.b = b;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isDirectional() {
        return directional;
    }

    public void setDirectional(boolean directional) {
        this.directional = directional;
    }


}
