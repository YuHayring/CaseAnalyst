package cn.hayring.caseanalyst.utils;

import java.io.Serializable;

import cn.hayring.caseanalyst.domain.Relationable;

/***
 * 引用传输类，线程不安全
 */
@Deprecated
public class Pointer {
    private static Serializable point;

    private static Relationable connector;

    public static Serializable getPoint() {
        return point;
    }

    public static void setPoint(Serializable point) {
        Pointer.point = point;
    }

    public static Relationable getConnector() {
        return connector;
    }

    public static void setConnector(Relationable connector) {
        Pointer.connector = connector;
    }
}
