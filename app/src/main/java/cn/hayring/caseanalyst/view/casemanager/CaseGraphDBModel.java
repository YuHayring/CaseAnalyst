package cn.hayring.caseanalyst.view.casemanager;

import android.app.Application;

import cn.hayring.caseanalyst.CaseAnalystApplication;

import static android.content.Context.MODE_PRIVATE;

import org.neo4j.graphdb.*;

/**
 * @author hayring
 * @date 6/23/21 8:10 PM
 */

public class CaseGraphDBModel {

    /**
     * 全局 Application
     */
    private Application application;

    private CaseGraphDBModel() {
        application = CaseAnalystApplication.getInstance();

    }

    private static class Singleton {
        private static CaseGraphDBModel instance = new CaseGraphDBModel();
    }



    private static final File databaseDirectory = new File("target/graph.db");
    GraphDatabaseService graphDb;

    ///申明结点标签
    private static enum Labels implements Label {
        PEOPLE
    }

    ///申明关系类型
    private static enum RelTypes implements RelationshipType {
        KNOWS
    }

    public static void main(final String[] args) throws IOException {
        EmbeddedNeo4j hello = new EmbeddedNeo4j();
        hello.createDb();
        hello.shutDown();
    }

    void createDb() throws IOException {
        FileUtils.deleteRecursively(databaseDirectory);

        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(databaseDirectory);
        registerShutdownHook(graphDb);

        try (Transaction tx = graphDb.beginTx()) {
            ///创建结点
            Node n1 = graphDb.createNode();
            n1.setProperty("n", "张三");
            n1.addLabel(Labels.PEOPLE);
            Node n2 = graphDb.createNode();
            n2.setProperty("n", "李四");
            n2.addLabel(Labels.PEOPLE);
            Node n3 = graphDb.createNode();
            n3.setProperty("n", "王五");
            n3.addLabel(Labels.PEOPLE);
            Node n4 = graphDb.createNode();
            n4.setProperty("n", "赵六");
            n4.addLabel(Labels.PEOPLE);

            ///创建关系
            n1.createRelationshipTo(n2, RelTypes.KNOWS);
            n2.createRelationshipTo(n3, RelTypes.KNOWS);
            n3.createRelationshipTo(n4, RelTypes.KNOWS);
            n4.createRelationshipTo(n1, RelTypes.KNOWS);

            tx.success();
        }
    }

    void shutDown() {
        System.out.println();
        System.out.println("Shutting down database ...");
        graphDb.shutdown();
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }



    public static CaseGraphDBModel getInstance() {
        new
    }

}