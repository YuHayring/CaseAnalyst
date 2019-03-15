package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;

/***
 * 能动单位与证物的关系
 * @author Hayring
 */
public class ManThingRelationship extends Relationship<Person, Evidence> {

    public ManThingRelationship(Person itemT, Evidence itemE, String key) {
        this.itemT = itemT;
        this.itemE = itemE;
        this.key = key;
        itemT.getManThingRelationships().add(this);
        itemE.getManThingRelationships().add(this);
    }


    @Override
    public String toString() {
        return itemT.getName() + " and " + itemE.getName() + " key: " + key;
    }


    public void setActiveUnit(Person person) {
        this.itemT = person;
    }

    public Evidence getEvidence() {
        return itemE;
    }

    public void setEvidence(Evidence evidence) {
        this.itemE = evidence;
    }


}
