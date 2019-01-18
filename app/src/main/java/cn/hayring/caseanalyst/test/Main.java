package cn.hayring.caseanalyst.test;

import cn.hayring.caseanalyst.pojo.*;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static Case mainCase;
    static Scanner sc = new Scanner(System.in);

    static Event event;
    static Evidence evidence;
    static ManThingRelationship manThingRelationship;
    static ManEventRelationship manEventRelationship;
    static Organization organization;
    static Person person;
    static Place place;
    static Time time;

    public static void main(String[] args) {
        int input;
        println("Menu");
        println("1----Init Case");
        println("2----Edit Case");
        input = sc.nextInt();
        while (true) {
            switch (input) {
                case 1:
                    initCase();
                    break;
                case 2:
                    editCase();
                    break;
                case 0:
                default:
                    break;
            }
            input = sc.nextInt();
        }


    }

    public static void println(String a) {
        System.out.println(a);
    }

    public static void initCase() {
        mainCase = new Case();
        println("Input name");
        mainCase.setName(sc.next());
        println("Input info");
        mainCase.setInfo(sc.next());
    }

    public static void editCase() {
        println("Which value?");
        println("1----name");
        println("2----time");
        println("3----info");
        println("4----MainActiveUnit");
        println("5----exit");
        switch (sc.nextInt()) {
            case 1: {
                println("Input");
            }
            break;
            default:
                return;
        }
    }
}
