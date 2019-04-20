package cn.hayring.caseanalyst.pojo;

public interface Relationable extends Listable {

    /***
     * 关系注册
     * @param instance
     */
    public void regRelationship(Relationship instance);

    /***
     * 返回头像id
     */
    public Integer getImageIndex();

    /***
     * 删除时解除关系
     */
    public void removeSelf();
}
