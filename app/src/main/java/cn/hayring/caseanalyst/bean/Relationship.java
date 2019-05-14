package cn.hayring.caseanalyst.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Relationship<T extends Relationable, E extends Relationable> implements Serializable {

    protected int type;

    public static final int MAN_MAN = 0;
    public static final int MAN_ORG = 1;
    public static final int ORG_ORG = 2;
    public static final int MAN_EVIDENCE = 3;
    public static final int MAN_EVENT = 4;
    public static final int ORG_EVIDENCE = 5;
    public static final int ORG_EVENT = 6;


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

    public void removeSelf() {
        switch (type) {
            case MAN_MAN: {
                ((Person) itemT).getManManRelationships().remove(this);
                ((Person) itemE).getManManRelationships().remove(this);
            }
            break;
            case MAN_ORG: {
                ((Person) itemT).getManOrgRelationships().remove(this);
                ((Organization) itemE).getManOrgRelationships().remove(this);
            }
            break;
            case ORG_ORG: {
                ((Organization) itemT).getOrgOrgRelationships().remove(this);
                ((Organization) itemE).getOrgOrgRelationships().remove(this);
            }
            break;
            case MAN_EVIDENCE: {
                ((Person) itemT).getManThingRelationships().remove(this);
                ((Evidence) itemE).getManThingRelationships().remove(this);
            }
            break;
            case MAN_EVENT: {
                ((Person) itemT).getManEventRelationships().remove(this);
                ((Event) itemE).getManEventRelationships().remove(this);
            }
            break;
            case ORG_EVIDENCE: {
                ((Organization) itemT).getOrgThingRelationships().remove(this);
                ((Evidence) itemE).getOrgThingRelationships().remove(this);
            }
            break;
            case ORG_EVENT: {
                ((Organization) itemT).getOrgEventRelationships().remove(this);
                ((Event) itemE).getOrgEventRelationships().remove(this);
            }
            break;
            default:
                throw new IllegalArgumentException("ERROR Relationship type");
        }
    }

    public static Relationship<Person, Person> createManManRelationship(Person itemT, String key, Person itemE) {
        Relationship relationship = new Relationship(MAN_MAN);
        relationship.itemT = itemT;
        relationship.itemE = itemE;
        relationship.key = key;
        itemT.getManManRelationships().add(relationship);
        itemE.getManManRelationships().add(relationship);
        return relationship;
    }


    public static Relationship<Person, Organization> createManOrgRelationship(Person itemT, String key, Organization itemE) {
        Relationship relationship = new Relationship(MAN_ORG);
        relationship.itemT = itemT;
        relationship.itemE = itemE;
        relationship.key = key;
        itemT.getManOrgRelationships().add(relationship);
        itemE.getManOrgRelationships().add(relationship);
        return relationship;
    }

    public static Relationship<Organization, Organization> createOrgOrgRelationship(Organization itemT, String key, Organization itemE) {
        Relationship relationship = new Relationship(ORG_ORG);
        relationship.itemT = itemT;
        relationship.itemE = itemE;
        relationship.key = key;
        itemT.getOrgOrgRelationships().add(relationship);
        itemE.getOrgOrgRelationships().add(relationship);
        return relationship;
    }

    public static Relationship<Person, Event> createManEventRelationship(Person itemT, String key, Event itemE) {
        Relationship relationship = new Relationship(MAN_EVENT);
        relationship.itemT = itemT;
        relationship.itemE = itemE;
        relationship.key = key;
        itemT.getManEventRelationships().add(relationship);
        itemE.getManEventRelationships().add(relationship);
        return relationship;
    }

    public static Relationship<Person, Evidence> createManThingRelationship(Person itemT, String key, Evidence itemE) {
        Relationship relationship = new Relationship(MAN_EVIDENCE);
        relationship.itemT = itemT;
        relationship.itemE = itemE;
        relationship.key = key;
        itemT.getManThingRelationships().add(relationship);
        itemE.getManThingRelationships().add(relationship);
        return relationship;
    }

    public static Relationship<Organization, Evidence> createOrgThingRelationship(Organization itemT, String key, Evidence itemE) {
        Relationship relationship = new Relationship(ORG_EVIDENCE);
        relationship.itemT = itemT;
        relationship.itemE = itemE;
        relationship.key = key;
        itemT.getOrgThingRelationships().add(relationship);
        itemE.getOrgThingRelationships().add(relationship);
        return relationship;
    }

    public static Relationship<Organization, Event> createOrgEventRelationship(Organization itemT, String key, Event itemE) {
        Relationship relationship = new Relationship(ORG_EVENT);
        relationship.itemT = itemT;
        relationship.itemE = itemE;
        relationship.key = key;
        itemT.getOrgEventRelationships().add(relationship);
        itemE.getOrgEventRelationships().add(relationship);
        return relationship;
    }

    public static void removeAllRelationship(ArrayList arrayList) {
        if (!arrayList.isEmpty() && !(arrayList.get(0) instanceof Relationship)) {
            throw new IllegalArgumentException("Error type: its not Relationship!");
        }
        while (!arrayList.isEmpty()) {
            Relationship relationship = (Relationship) arrayList.remove(0);
            relationship.removeSelf();
        }
    }


}




