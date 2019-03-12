package cn.hayring.caseanalyst.pojo;

/***
 * 人与事件的关系
 * @author Hayring
 */
public class ManEventRelationship extends Relationship<Person, Event> {

    public ManEventRelationship(Person activeUnit, Event event, String key) {
        this.itemT = activeUnit;
        this.itemE = event;
        this.key = key;
        activeUnit.getManEventRelationships().add(this);
        event.getManEventRelationships().add(this);
    }


    @Override
    public String toString() {
        return itemT.getName() + " and " + itemE.getName() + " key: " + key;
    }


    public ActiveUnit getActiveUnit() {
        return itemT;
    }

    public void setActiveUnit(Person activeUnit) {
        this.itemT = activeUnit;
    }

    public Event getEvent() {
        return itemE;
    }

    public void setEvent(Event event) {
        this.itemE = event;
    }

    @Override
    public String getInfo() {
        return toString();
    }
}
