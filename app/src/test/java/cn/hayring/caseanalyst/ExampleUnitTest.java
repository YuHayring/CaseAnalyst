package cn.hayring.caseanalyst;

import org.junit.Test;

import cn.hayring.caseanalyst.pojo.Case;
import cn.hayring.caseanalyst.pojo.Event;
import cn.hayring.caseanalyst.pojo.Evidence;
import cn.hayring.caseanalyst.pojo.ManEventRelationship;
import cn.hayring.caseanalyst.pojo.ManManRelationship;
import cn.hayring.caseanalyst.pojo.ManThingRelationship;
import cn.hayring.caseanalyst.pojo.Person;

import static org.junit.Assert.*;

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
    public void test() {
        Case mainCase = new Case();

        //定义案件
        mainCase.setName("名侦探柯南");
        mainCase.setInfo("追查黑衣组织测试数据结构");
        //Time time = new Time(new Date("2000-01-01 00:00:00"),new Date("2000-06-30 23:59:59"));
        //mainCase.setTime(time);

        //定义人物
        Person shiJi, gin;
        shiJi = mainCase.createPerson("工藤新一", false, "变小的名侦探真实身份是工藤新一");
        shiJi.setAge(17);
        shiJi.setGender(Person.MALE);
        gin = mainCase.createPerson("琴酒", true, "黑衣组织高官，喂工藤新一喝药的人");
        gin.setGender(Person.MALE);

        //事件发生
        Event mainEvent = mainCase.createEvent("新一服用APTX4869", "新一被琴酒灌下APTX4869");
        //Time time2 = new Time(new Date("2000-01-01 18:00:00"),new Date("2000-01-01 18:30:00"));
        //mainEvent.setTime(time2);
        //人与事件建立关系
        new ManEventRelationship(shiJi, mainEvent, "服药的人");
        new ManEventRelationship(gin, mainEvent, "灌药的人");


        //人与人建立关系
        new ManManRelationship(shiJi, "敌对关系", gin, false);


        Evidence APTX4869 = mainEvent.createEvidence("APTX4869", "黑衣组织神秘药物");
        mainEvent.getEvidences().put(APTX4869.getName(), APTX4869);
        new ManThingRelationship(shiJi, APTX4869, "被服用");
        new ManThingRelationship(gin, APTX4869, "凶器使用者");

        println("OK");
    }

    public static void println(String a) {
        System.out.println(a);
    }
}