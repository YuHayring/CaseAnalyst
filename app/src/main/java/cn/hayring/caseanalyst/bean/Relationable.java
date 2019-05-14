package cn.hayring.caseanalyst.bean;

public interface Relationable extends Listable {

    /***
     * 关系注册
     * @param instance
     */
    public void regRelationship(Relationship instance);

    /***
     * 删除时解除关系
     */
    public void removeSelf();
}
