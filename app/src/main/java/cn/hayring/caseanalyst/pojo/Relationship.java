package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;

public class Relationship<T extends Relationable, E extends Relationable> implements Serializable {

    protected int type;

    public static final int MAN_MAN = 0;
    public static final int MAN_ORG = 1;
    public static final int ORG_ORG = 2;
    public static final int MAN_EVIDENCE = 3;
    public static final int MAN_EVENT = 4;
    public static final int ORG_EVIDENCE = 5;
    public static final int ORG_EVENT = 6;

    protected Relationship() {
    }

    protected Relationship(int type) {
        this.type = type;
    }

    public static Relationship createRelationship(int type) {
        switch (type) {
            case MAN_MAN: {
                return new Relationship<Person, Person>(MAN_MAN);
            }
            case MAN_ORG: {
                return new Relationship<Person, Organization>(MAN_ORG);
            }
            case ORG_ORG: {
                return new Relationship<Organization, Organization>(ORG_ORG);
            }
            case MAN_EVIDENCE: {
                return new Relationship<Person, Evidence>(MAN_EVIDENCE);
            }
            case MAN_EVENT: {
                return new Relationship<Person, Event>(MAN_EVENT);
            }
            case ORG_EVIDENCE: {
                return new Relationship<Organization, Evidence>(ORG_EVIDENCE);
            }
            case ORG_EVENT: {
                return new Relationship<Organization, Event>(ORG_EVENT);
            }
            default:
                throw new IllegalArgumentException("ERROR Relationship type");
        }
    }

    protected T itemT;

    protected E itemE;

    protected String key;

    protected String info;


    @Override
    public String toString() {
        return itemT.getName() + " and " + itemE.getName() + " key: " + key;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getItemT() {
        return itemT;
    }

    public void setItemT(T itemT) {
        this.itemT = itemT;
    }

    public E getItemE() {
        return itemE;
    }

    public void setItemE(E itemE) {
        this.itemE = itemE;
    }

    public Class getTClass() {
        return itemT.getClass();
    }

    public Class getEClass() {
        return itemE.getClass();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
