package cn.hayring.caseanalyst.bean;

/**
 * @author hayring
 */
public interface Relationable extends Listable {

    /***
     * 关系注册
     * @param instance
     */
    void regRelationship(Relationship instance);

    /***
     * 删除时解除关系
     */
    void removeSelf();
}
