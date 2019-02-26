package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * 案件参与的人
 * @author Hayring
 */
public class Person implements ActiveUnit, Serializable {
    /***
     * 姓名
     */
    protected String name;

    /***
     * 昵称
     */
    protected String nickName;

    /***
     * 年龄
     */
    protected Integer age;

    /***
     * 是否为嫌犯
     */
    protected Boolean suspect;
    /***
     * 性别
     */
    protected Boolean gender;
    public static final Boolean MALE = true;
    public static final Boolean FEMALE = false;

    /***
     * 血型
     */
    protected String bloodType;
    public static final String A = "A";
    public static final String AB = "AB";
    public static final String B = "B";
    public static final String O = "O";
    public static final String RHAB = "RHAB";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean isOrganization() {
        return false;
    }

    /***
     * 人物描述
     */
    protected String info;

    /***
     * 人与证物关系集合
     */
    protected ArrayList<ManThingRelationship> manThingRelationships;

    /***
     * 事件与人关系集合
     */
    protected ArrayList<ManEventRelationship> manEventRelationships;

    /***
     * 其他身份
     */
    protected ArrayList<Person> multipleIdentities;

    /***
     * 人与人关系集合
     */
    protected ArrayList<ManManRelationship> manManRelationships;


    /***
     * 所属案件
     */
    protected Case parentCase;

    public void setParentCase(Case parentCase) {
        this.parentCase = parentCase;
    }

    public Case getParentCase() {
        return parentCase;
    }

    /***
     * 保护构造器，初始化各种集合
     */
    protected Person() {
        manEventRelationships = new ArrayList<ManEventRelationship>();
        manThingRelationships = new ArrayList<ManThingRelationship>();
        multipleIdentities = new ArrayList<Person>();
        manManRelationships = new ArrayList<ManManRelationship>();
    }

    public Person(String name, Boolean suspect, String info) {
        this();
        this.name = name;
        this.suspect = suspect;
        this.info = info;
    }

    /***
     * 显示名字
     * @return it's name
     */
    @Override
    public String toString() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<ManThingRelationship> getManThingRelationships() {
        return manThingRelationships;
    }

    public void setManThingRelationships(ArrayList<ManThingRelationship> manThingRelationships) {
        this.manThingRelationships = manThingRelationships;
    }

    public ArrayList<ManEventRelationship> getManEventRelationships() {
        return manEventRelationships;
    }

    public void setManEventRelationships(ArrayList<ManEventRelationship> manEventRelationships) {
        this.manEventRelationships = manEventRelationships;
    }

    public Boolean getSuspect() {
        return suspect;
    }

    public void setSuspect(Boolean suspect) {
        this.suspect = suspect;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public ArrayList<Person> getMultipleIdentities() {
        return multipleIdentities;
    }

    public void setMultipleIdentities(ArrayList<Person> multipleIdentities) {
        this.multipleIdentities = multipleIdentities;
    }


    public ArrayList<ManManRelationship> getManManRelationships() {
        return manManRelationships;
    }

    public void setManManRelationships(ArrayList<ManManRelationship> manManRelationships) {
        this.manManRelationships = manManRelationships;
    }
}
