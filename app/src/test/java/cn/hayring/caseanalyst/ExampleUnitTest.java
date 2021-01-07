package cn.hayring.caseanalyst;

import org.junit.Test;

import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.domain.Event;
import cn.hayring.caseanalyst.domain.Evidence;
import cn.hayring.caseanalyst.domain.Person;
import cn.hayring.caseanalyst.domain.Relationship;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public Case test() {
        Case mainCase = new Case();

        //定义案件
        mainCase.setName("名侦探柯南");
        mainCase.setInfo("追查黑衣组织测试数据结构");

        //定义人物
        Person shiJi, gin;
        shiJi = mainCase.createPerson("工藤新一", false, "变小的名侦探真实身份是工藤新一");
        shiJi.setAge(17);
        shiJi.setGender(Person.MALE);
        gin = mainCase.createPerson("琴酒", true, "黑衣组织高官，喂工藤新一喝药的人");
        gin.setGender(Person.MALE);

        //事件发生
        Event mainEvent = mainCase.createEvent("新一服用APTX4869", "新一被琴酒灌下APTX4869");
        //人与事件建立关系
        Relationship.createManEventRelationship(shiJi, "服药的人", mainEvent);
        Relationship.createManEventRelationship(gin, "灌药的人", mainEvent);


        //人与人建立关系
        Relationship.createManManRelationship(shiJi, "敌对关系", gin);


        Evidence APTX4869 = mainCase.createEvidence("APTX4869", "黑衣组织神秘药物");
        Relationship.createManThingRelationship(shiJi, "被服用", APTX4869);
        Relationship.createManThingRelationship(gin, "凶器使用者", APTX4869);

        println("OK");
        return mainCase;
    }

    public static void println(String a) {
        System.out.println(a);
    }
}