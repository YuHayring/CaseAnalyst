package cn.hayring.caseanalyst.pojo;


public class PojoInstanceCreater {

    private static Case caseInstance;

    static {
        caseInstance = new Case();

        //定义案件
        caseInstance.setName("名侦探柯南");
        caseInstance.setInfo("追查黑衣组织测试数据结构");

        //定义人物
        Person shiJi, gin;
        shiJi = caseInstance.createPerson("工藤新一", false, "变小的名侦探真实身份是工藤新一");
        shiJi.setAge(17);
        shiJi.setGender(Person.MALE);
        caseInstance.getPersons().add(shiJi);
        gin = caseInstance.createPerson("琴酒", true, "黑衣组织高官，喂工藤新一喝药的人");
        gin.setAge(45);
        gin.setGender(Person.MALE);
        caseInstance.getPersons().add(gin);

        Person akai = caseInstance.createPerson("赤井秀一", false, "FBI顶尖搜查官");
        akai.setAge(32);
        akai.setGender(Person.MALE);
        caseInstance.getPersons().add(akai);
        Relationship.createManManRelationship(shiJi, "追踪", gin);
        Relationship.createManManRelationship(shiJi, "伙伴", akai);
        Relationship.createManManRelationship(gin, "敌人", akai);









        Organization blackOrg = caseInstance.createOrganization("黑衣组织", "乌丸集团");
        caseInstance.getOrganizations().add(blackOrg);

        //事件发生
        Event mainEvent = caseInstance.createEvent("新一服用APTX4869", "新一被琴酒灌下APTX4869");
        caseInstance.getEvents().add(mainEvent);
        //人与事件建立关系
        Relationship.createManEventRelationship(shiJi, "服药的人", mainEvent);
        Relationship.createManEventRelationship(gin, "灌药的人", mainEvent);
        //new orgEventRelationship(blackOrg, mainEvent, "幕后组织");
        Relationship oE = new Relationship(Relationship.ORG_EVENT);
        oE.setItemT(blackOrg);
        oE.setItemE(mainEvent);
        oE.setKey("幕后组织");
        blackOrg.regRelationship(oE);
        mainEvent.regRelationship(oE);



        Evidence APTX4869 = mainEvent.createEvidence("APTX4869", "黑衣组织神秘药物");
        mainEvent.getEvidences().add(APTX4869);
        Relationship.createManThingRelationship(shiJi, "被服用", APTX4869);
        Relationship.createManThingRelationship(gin, "凶器使用者", APTX4869);
        Relationship.createOrgThingRelationship(blackOrg, "开发组织", APTX4869);

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
