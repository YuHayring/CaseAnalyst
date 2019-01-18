package cn.hayring.caseanalyst.pojo;

/***
 * 地点
 * @author Hayring
 */
public class Place {
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
