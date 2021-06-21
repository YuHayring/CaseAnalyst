package cn.hayring.caseanalyst.view.caselist;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.hayring.caseanalyst.CaseAnalystApplication;
import cn.hayring.caseanalyst.domain.Case;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author hayring
 * @date 6/21/21 7:36 PM
 */
public class CaseDBModel {

    /**
     * 全局 application
     */
    Application application;

    /**
     * 数据库
     */
    SQLiteDatabase caseDB;


    /**
     * 数据库文件名
     */
    private static final String CASE_DB_NAME = "cases.db";

    /**
     * 建表 SQL
     */
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " +
            "\"case\"(case_id integer primary key, " +
            "case_name char(16), " +
            "case_info varchar(64))";

    private CaseDBModel() {
        application = CaseAnalystApplication.getInstance();
        //创建或打开数据库
        caseDB = application.openOrCreateDatabase(CASE_DB_NAME, MODE_PRIVATE, null);
        caseDB.execSQL(CREATE_TABLE_SQL);
    }

    private static class Singleton {
        private static CaseDBModel instance = new CaseDBModel();
    }


    public List<Case> getCases() {
        List<Case> cases = new ArrayList<>();
        Cursor cursor = caseDB.query("case", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Case caxe = new Case();
            caxe.setId(cursor.getLong(0));
            caxe.setName(cursor.getString(1));
            caxe.setInfo(cursor.getString(2));
            cases.add(caxe);
        }
        return cases;
    }

    public static CaseDBModel getInstance() {
        return Singleton.instance;
    }


}
