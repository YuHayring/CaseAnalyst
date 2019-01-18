package cn.hayring.caseanalyst.pojo;

/***
 * 人与人之间的关系
 * @author Hayring
 */
public class ManManRelationship {
    /***
     * 建立关系的两个人
     */
    private Person a;
    private Person b;

    /***
     * 关系信息
     */
    private String key;

    /***
     * 方向性，当两者关系关于key有向，则为true
     * directional it is true when a key b;
     */
    private boolean directional;

    public ManManRelationship(Person a, String key, Person b, boolean directional) {
        this.a = a;
        this.b = b;
        this.key = key;
        this.directional = directional;
        a.getManManRelationships().put(b.getName(), this);//这样写不知道符不符合思想
        b.getManManRelationships().put(a.getName(), this);
    }


    @Override
    public String toString() {
        return a.getName() + " and " + b.getName() + " key: " + key;
    }


    public Person getA() {
        return a;
    }

    public void setA(Person a) {
        this.a = a;
    }

    public Person getB() {
        return b;
    }

    public void setB(Person b) {
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
