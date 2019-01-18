package cn.hayring.caseanalyst.pojo;

/***
 * 能动单位与证物的关系
 * @author Hayring
 */
public class ManThingRelationship {
    /***
     * 能动单位
     */
    private ActiveUnit activeUnit;

    /***
     * 证物
     */
    private Evidence evidence;

    /***
     * 关键词
     */
    private String key;

    public ManThingRelationship(ActiveUnit activeUnit, Evidence evidence, String key) {
        this.activeUnit = activeUnit;
        this.evidence = evidence;
        this.key = key;
        activeUnit.getManThingRelationships().put(evidence.getName(), this);
        evidence.getRelationships().put(activeUnit.getName(), this);
    }


    @Override
    public String toString() {
        return activeUnit.getName() + " and " + evidence.getName() + " key: " + key;
    }


    public ActiveUnit getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(ActiveUnit activeUnit) {
        this.activeUnit = activeUnit;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
