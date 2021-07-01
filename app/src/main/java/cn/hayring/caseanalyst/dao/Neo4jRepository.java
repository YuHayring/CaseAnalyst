package cn.hayring.caseanalyst.dao;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import cn.hayring.caseanalyst.CaseAnalystApplication;

/**
 * @author hayring
 * @date 6/30/21 3:05 PM
 */
public class Neo4jRepository {

    /**
     * 私有构造器
     */
    private Neo4jRepository() {
    }

    /**
     * neo4j地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 输入了地址，用户名密码的 driver
     */
    private Driver driver;


    /**
     * 单例静态内部类
     */
    private static class Singleton {
        private static Neo4jRepository instance = new Neo4jRepository();
    }

    /**
     * 初始化驱动
     *
     * @param url      地址
     * @param username 用户名
     * @param password 密码
     */
    public static void initInstance(String url, String username, String password) {
        Neo4jRepository instance = Singleton.instance;
        //释放driver
        if (instance.driver != null) instance.driver.close();
        instance.driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
    }


    /**
     * 获取持久层单例
     *
     * @return 单例
     */
    public static Neo4jRepository getInstance() {

        reloadDriver();
        return Singleton.instance;

    }

    public static void reloadDriver() {
        Application application = CaseAnalystApplication.getInstance();
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(application);

        String url = "bolt://" + spf.getString("domain", "127.0.0.1") + ":" + spf.getString("port", "7687");
        String username = spf.getString("username", "neo4j");
        String password = spf.getString("password", "neo4j");
        Singleton.instance.driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
    }


    public Driver getDriver() {
        return driver;
    }
}
