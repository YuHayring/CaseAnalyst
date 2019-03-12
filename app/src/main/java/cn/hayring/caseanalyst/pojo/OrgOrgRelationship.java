package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;

/***
 * 组织与组织的关系
 * @author Hayring
 */
public class OrgOrgRelationship extends Relationship<Organization, Organization> {

    /***
     * 方向性，当两者关系关于key有向，则为true
     * directional it is true when itemT key itemE;
     */
    private boolean directional;

    public OrgOrgRelationship(Organization a, Organization b, String key, boolean directional) {
        this.itemT = a;
        this.itemE = b;
        this.key = key;
        this.directional = directional;
        a.getOrgOrgRelationships().add(this);
        b.getOrgOrgRelationships().add(this);
    }

    @Override
    public String toString() {
        return itemT.getName() + " and " + itemE.getName() + " key: " + key;
    }


    public Organization getA() {
        return itemT;
    }

    public void setA(Organization a) {
        this.itemT = a;
    }

    public Organization getB() {
        return itemE;
    }

    public void setB(Organization b) {
        this.itemE = b;
    }


    public boolean isDirectional() {
        return directional;
    }

    public void setDirectional(boolean directional) {
        this.directional = directional;
    }


    @Override
    public String getInfo() {
        return toString();
    }


}
