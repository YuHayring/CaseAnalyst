package cn.hayring.caseanalyst.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

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
    protected int bloodType;
    public static final int A = 0;
    public static final int AB = 1;
    public static final int B = 2;
    public static final int O = 3;
    public static final int RHAB = 4;

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
     * String-ActiveUnit's name;
     */
    protected HashMap<String, ManThingRelationship> manThingRelationships;

    /***
     * 事件与人关系集合,字符串为事件的名字
     * String-ActiveUnit's name;
     */
    protected HashMap<String, ManEventRelationship> manEventRelationships;

    /***
     * 其他身份，字符串为对方名字
     * String-Other's name;
     */
    protected HashMap<String, Person> multipleIdentities;

    /***
     * 人与人关系集合
     * String-ActiveUnit's name;
     */
    protected HashMap<String, ManManRelationship> manManRelationships;


    public Person() {
        manEventRelationships = new HashMap<String, ManEventRelationship>();
        manThingRelationships = new HashMap<String, ManThingRelationship>();
        multipleIdentities = new HashMap<String, Person>();
        manManRelationships = new HashMap<String, ManManRelationship>();
    }

    public Person(String name, Boolean suspect, String info) {
        this();
        this.name = name;
        this.suspect = suspect;
        this.info = info;
    }


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

    public HashMap<String, ManThingRelationship> getManThingRelationships() {
        return manThingRelationships;
    }

    public void setManThingRelationships(HashMap<String, ManThingRelationship> manThingRelationships) {
        this.manThingRelationships = manThingRelationships;
    }

    public HashMap<String, ManEventRelationship> getManEventRelationships() {
        return manEventRelationships;
    }

    public void setManEventRelationships(HashMap<String, ManEventRelationship> manEventRelationships) {
        this.manEventRelationships = manEventRelationships;
    }

    public Boolean getSuspect() {
        return suspect;
    }

    public void setSuspect(Boolean suspect) {
        this.suspect = suspect;
    }

    public int getBloodType() {
        return bloodType;
    }

    public void setBloodType(int bloodType) {
        this.bloodType = bloodType;
    }

    public HashMap<String, Person> getMultipleIdentities() {
        return multipleIdentities;
    }

    public void setMultipleIdentities(HashMap<String, Person> multipleIdentities) {
        this.multipleIdentities = multipleIdentities;
    }


    public HashMap<String, ManManRelationship> getManManRelationships() {
        return manManRelationships;
    }

    public void setManManRelationships(HashMap<String, ManManRelationship> manManRelationships) {
        this.manManRelationships = manManRelationships;
    }
}
