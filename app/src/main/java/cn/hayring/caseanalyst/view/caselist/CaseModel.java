package cn.hayring.caseanalyst.view.caselist;

import android.util.Log;

import org.neo4j.driver.Driver;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.neo4j.driver.internal.InternalNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hayring.caseanalyst.dao.Neo4jRepository;
import cn.hayring.caseanalyst.domain.Case;
import cn.hayring.caseanalyst.viewmodel.AsyncCallBack;

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


    Neo4jRepository neo4jdao;

    private static final String ID = "identity";

    private static final String PROPERTY_NAME = "name";

    private static final String PROPERTY_INFO = "info";

    private static final String PARAMTER_ID = "ID";

    private static final String PARAMTER_NAME = "NAME";

    private static final String PARAMETER_INFO = "INFO";


    /**
     * 单例静态内部类
     */
    private static class Singleton {
        private static CaseModel instance = new CaseModel();
    }

    public static CaseModel getInstance() {
        CaseModel instance = Singleton.instance;
        if ((instance.neo4jdao = Neo4jRepository.getInstance()) == null) return null;
        return instance;
    }


    @Override
    public void getCases(AsyncCallBack<List<Case>> asyncCallBack) {
        Driver driver = neo4jdao.getDriver();

        String query = "MATCH (c:Case) RETURN c";

        AsyncSession session = driver.asyncSession();

        Log.i("thread", Thread.currentThread().getName());

        session.runAsync(query)
                .thenCompose(cursor -> cursor.listAsync(record -> {
                    InternalNode node = (InternalNode) record.get(0).asNode();
                    Case caxe = new Case();
                    caxe.setId(node.id());
                    caxe.setName(node.get(PROPERTY_NAME).asString());
                    caxe.setInfo(node.get(PROPERTY_INFO).asString());
                    Log.i("thread", Thread.currentThread().getName());
                    return caxe;
                }))
                .exceptionally(error ->
                {
                    // query execution failed, print error and fallback to empty list of titles
                    error.printStackTrace();
                    return null;
                })
                .thenAccept(list -> {
                    Log.i("thread", Thread.currentThread().getName());
                    session.closeAsync();
                    asyncCallBack.callBack(list);
                });

    }

    @Override
    public void deleteCase(Long id, AsyncCallBack<Boolean> callBack) {
        Driver driver = neo4jdao.getDriver();
        String queryDeleteRelationship = "MATCH (c)-[]->(e)-[r]->() WHERE id(c) = {ID} delete r";
        String queryDeleteNode = "MATCH (c)-[r]->(e) WHERE id(c) = {ID} delete r,e";
        String queryDeleteCase = "MATCH (c) WHERE id(c) = {ID} delete c";
        Map<String, Object> parameters = Collections.singletonMap(PARAMTER_ID, id);
        AsyncSession session = driver.asyncSession();
        session.beginTransactionAsync()
                .thenComposeAsync(tx ->
                        tx.runAsync(queryDeleteRelationship, parameters)
                                .exceptionally(e -> {
                                    e.printStackTrace();
                                    return null;
                                })
                                .thenApply(ignore -> tx))
                .thenComposeAsync(tx ->
                        tx.runAsync(queryDeleteNode, parameters)
                                .exceptionally(e -> {
                                    e.printStackTrace();
                                    return null;
                                })
                                .thenApply(ignore -> tx))
                .thenComposeAsync(tx ->
                        tx.runAsync(queryDeleteCase, parameters)
                                .exceptionally(e -> {
                                    e.printStackTrace();
                                    return null;
                                })
                                .thenApply(ignore -> tx))
                .thenComposeAsync(tx ->
                        tx.commitAsync().thenRun(session::closeAsync)
                );
    }

    @Override
    public void getCase(Long id, AsyncCallBack<Case> callBack) {
        Driver driver = neo4jdao.getDriver();
        String query = "MATCH (c:Case) WHERE id(c) = {ID} RETURN c";
        Map<String, Object> parameters = Collections.singletonMap(PARAMTER_ID, id);

        AsyncSession session = driver.asyncSession();

        session.runAsync(query, parameters)
                .thenCompose(ResultCursor::nextAsync)
                .exceptionally(error ->
                {
                    // query execution failed, print error and fallback to empty list of titles
                    error.printStackTrace();
                    return null;
                })
                .thenAccept(record -> {
                    InternalNode node = (InternalNode) record.get(0).asNode();
                    Case caxe = new Case();
                    caxe.setId(node.id());
                    caxe.setName(node.get(PROPERTY_NAME).asString());
                    caxe.setInfo(node.get(PROPERTY_INFO).asString());
                    session.closeAsync();
                    callBack.callBack(caxe);
                });
    }

    @Override
    public void addCase(AsyncCallBack<Long> callBack) {
        Driver driver = neo4jdao.getDriver();
        String query = "CREATE (c:Case) return id(c)";

        AsyncSession session = driver.asyncSession();
        session.runAsync(query)
                .thenCompose(ResultCursor::nextAsync)
                .exceptionally(error ->
                {
                    // query execution failed, print error and fallback to empty list of titles
                    error.printStackTrace();
                    return null;
                })
                .thenAccept(record -> {
                    Long id = Long.valueOf(record.get(0).toString());
                    session.closeAsync();
                    callBack.callBack(id);
                });
    }

    @Override
    public void updateCase(Case caxe, AsyncCallBack<Boolean> callBack) {
        Driver driver = neo4jdao.getDriver();
        String query = "MATCH (c) WHERE id(c) = {ID} SET c.name = \"{NAME}\", c.info = \"{INFO}\" ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(PARAMTER_ID, caxe.getId());
        parameters.put(PARAMTER_NAME, caxe.getName());
        parameters.put(PARAMETER_INFO, caxe.getInfo());
        AsyncSession session = driver.asyncSession();
        session.runAsync(query, parameters)
                .exceptionally(error ->
                {
                    // query execution failed, print error and fallback to empty list of titles
                    error.printStackTrace();
                    return null;
                })
                .thenRun(() -> {
                    session.closeAsync();
                    callBack.callBack(true);
                });
    }
}
