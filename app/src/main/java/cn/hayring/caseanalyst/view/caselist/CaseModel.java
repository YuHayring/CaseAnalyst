package cn.hayring.caseanalyst.view.caselist;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import java.util.List;

import cn.hayring.caseanalyst.domain.Case;

/**
 * @author hayring
 * @date 6/29/21 6:15 PM
 */
public class CaseModel implements CaseRepository {

    /**
     * 单例私有构造器
     */
    private CaseModel() {
    }

    /**
     * neo4j地址
     */
//    private String url;

    /**
     * 用户名
     */
//    private String username;

    /**
     * 密码
     */
//    private String password;


    /**
     * 输入了地址，用户名密码的 driver
     */
    Driver driver;


    /**
     * 单例静态内部类
     */
    private static class Singleton {
        private static CaseModel instance = new CaseModel();
    }

    public static CaseModel getInstance(String url, String username, String password) {
        CaseModel instance = Singleton.instance;
//        if (instance.url.equals(url) && instance.username.equals(instance.username) && instance.password.equals(password)) {
//            return instance;
//        }
//        instance.url = url;
//        instance.username = username;
//        instance.password = password;

        //释放driver
        if (instance.driver != null) instance.driver.close();
        instance.driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
        return instance;
    }


    @Override
    public List<Case> getCases() {
        return null;
    }

    @Override
    public void deleteCase(Long id) {

    }

    @Override
    public Case getCase(Long id) {
        return null;
    }

    @Override
    public long addCase() {
        return 0;
    }

    @Override
    public void updateCase(Case caxe) {

    }
}
