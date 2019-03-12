package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;

/***
 * 人与人之间的关系
 * @author Hayring
 */
public class ManManRelationship extends Relationship<Person, Person> {

    /***
     * 方向性，当两者关系关于key有向，则为true
     * directional it is true when "itemT key itemE";
     */
    private boolean directional;

    public ManManRelationship(Person itemT, String key, Person b, boolean directional) {
        this.itemT = itemT;
        this.itemE = b;
        this.key = key;
        this.directional = directional;
        itemT.getManManRelationships().add(this);//这样写不知道符不符合思想
        b.getManManRelationships().add(this);
    }


    @Override
    public String toString() {
        return itemT.getName() + " and " + itemE.getName() + " key: " + key;
    }


    public Person getA() {
        return itemT;
    }

    public void setA(Person a) {
        this.itemT = a;
    }

    public Person getB() {
        return itemE;
    }

    public void setB(Person b) {
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
