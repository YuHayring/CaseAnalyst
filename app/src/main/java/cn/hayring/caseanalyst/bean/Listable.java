package cn.hayring.caseanalyst.bean;


import java.io.Serializable;

/***
 * 继承Serializable 是为了putExtra(Serializable)
 */
public interface Listable extends Serializable {
    public String getName();

    public String getInfo();
}
