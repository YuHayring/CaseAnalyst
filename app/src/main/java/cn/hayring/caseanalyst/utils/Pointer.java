package cn.hayring.caseanalyst.utils;

import java.io.Serializable;

public class Pointer {
    private static Serializable point;

    public static Serializable getPoint() {
        return point;
    }

    public static void setPoint(Serializable point) {
        Pointer.point = point;
    }
}
