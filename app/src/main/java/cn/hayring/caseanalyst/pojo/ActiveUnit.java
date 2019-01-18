package cn.hayring.caseanalyst.pojo;

import java.util.HashMap;

/***
 * 能动单元
 * A dynamic unit may be a person, a group or an organization.
 * @author Hayring
 */
public interface ActiveUnit {
    /***
     *
     * @return name
     */
    public String getName();

    /***
     *
     * @return false-person or true-org
     */
    public Boolean isOrganization();

    public HashMap<String, ManThingRelationship> getManThingRelationships();

    public HashMap<String, ManEventRelationship> getManEventRelationships();


}
