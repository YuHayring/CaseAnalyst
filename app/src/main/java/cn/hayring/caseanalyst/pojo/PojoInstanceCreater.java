package cn.hayring.caseanalyst.pojo;

import cn.hayring.caseanalyst.pojo.Case;
import cn.hayring.caseanalyst.pojo.Event;
import cn.hayring.caseanalyst.pojo.Evidence;
import cn.hayring.caseanalyst.pojo.ManEventRelationship;
import cn.hayring.caseanalyst.pojo.ManManRelationship;
import cn.hayring.caseanalyst.pojo.ManThingRelationship;
import cn.hayring.caseanalyst.pojo.Person;

public class PojoInstanceCreater {

    private static Case caseInstance;

    static {
        caseInstance = new Case();

        //定义案件
        caseInstance.setName("名侦探柯南");
        caseInstance.setInfo("追查黑衣组织测试数据结构");
        //Time time = new Time(new Date("2000-01-01 00:00:00"),new Date("2000-06-30 23:59:59"));
        //mainCase.setTime(time);

        //定义人物
        Person shiJi, gin;
        shiJi = caseInstance.createPerson("工藤新一", false, "变小的名侦探真实身份是工藤新一");
        shiJi.setAge(17);
        shiJi.setGender(Person.MALE);
        gin = caseInstance.createPerson("琴酒", true, "黑衣组织高官，喂工藤新一喝药的人");
        gin.setAge(45);
        gin.setGender(Person.MALE);

        Organization blackOrg = caseInstance.createOrganization("黑衣组织", "乌丸集团");

        //事件发生
        Event mainEvent = caseInstance.createEvent("新一服用APTX4869", "新一被琴酒灌下APTX4869");
        //Time time2 = new Time(new Date("2000-01-01 18:00:00"),new Date("2000-01-01 18:30:00"));
        //mainEvent.setTime(time2);
        //人与事件建立关系
        new ManEventRelationship(shiJi, mainEvent, "服药的人");
        new ManEventRelationship(gin, mainEvent, "灌药的人");
        //new orgEventRelationship(blackOrg, mainEvent, "幕后组织");
        Relationship oE = new Relationship(Relationship.ORG_EVENT);
        oE.setItemT(blackOrg);
        oE.setItemE(mainEvent);
        oE.setKey("幕后组织");
        blackOrg.regRelationship(oE);
        mainEvent.regRelationship(oE);


        //人与人建立关系
        new ManManRelationship(shiJi, "敌对关系", gin, false);


        Evidence APTX4869 = mainEvent.createEvidence("APTX4869", "黑衣组织神秘药物");
        mainEvent.getEvidences().add(APTX4869);
        new ManThingRelationship(shiJi, APTX4869, "被服用");
        new ManThingRelationship(gin, APTX4869, "凶器使用者");
        //new ManThingRelationship(blackOrg, APTX4869, "开发组织");

        Relationship oT = new Relationship(Relationship.ORG_EVIDENCE);
        oT.setItemT(blackOrg);
        oT.setItemE(APTX4869);
        oT.setKey("开发组织");
        blackOrg.regRelationship(oT);
        mainEvent.regRelationship(oT);
    }

    public static Case getConanCase() {
        return caseInstance;
    }

}
