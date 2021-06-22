package cn.hayring.caseanalyst.domain;

import java.io.Serializable;

/***
 * 地点
 * @author Hayring
 */
public class Place implements Serializable, Listable {

    /**
     * id
     */
    private Long id;

    /***
     * 地点名称
     */
    protected String name;

    /***
     * 地点描述
     */
    protected String info;

    public Place(String name, String info) {
        this.name = name;
        this.info = info;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
