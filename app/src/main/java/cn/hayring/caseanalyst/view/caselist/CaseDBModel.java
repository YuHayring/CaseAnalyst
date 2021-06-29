package cn.hayring.caseanalyst.view.caselist;

import android.app.Application;
import android.content.ContentValues;
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
public class CaseDBModel implements CaseRepository {

    /**
     * 全局 application
     */
    Application application;

    /**
     * 数据库
     */
    SQLiteDatabase caseDB;

    //列名
    private static final String ID_COLUMN_NAME = "case_id";
    private static final String NAME_COLUMN_NAME = "case_name";
    private static final String INFO_COLUMN_NAME = "case_info";


    /**
     * 数据库文件名
     */
    private static final String CASE_DB_NAME = "cases.db";

    private static final String TABLE = "\"case\"";

    /**
     * 建表 SQL
     */
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " +
            "\"case\"(case_id integer primary key AUTOINCREMENT, " +
            "case_name char(16), " +
            "case_info varchar(64))";

    /**
     * id where 语句
     */
    private static final String ID_WHERE_CLAUSE = ID_COLUMN_NAME + "=?";


    private CaseDBModel() {
        application = CaseAnalystApplication.getInstance();
        //创建或打开数据库
        caseDB = application.openOrCreateDatabase(CASE_DB_NAME, MODE_PRIVATE, null);
        caseDB.execSQL(CREATE_TABLE_SQL);
    }

    private static class Singleton {
        private static CaseDBModel instance = new CaseDBModel();
    }


    /**
     * 获取案件列表
     *
     * @return 案件列表
     */
    public List<Case> getCases() {
        List<Case> cases = new ArrayList<>();
        Cursor cursor = caseDB.query(TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Case caxe = new Case();
            caxe.setId(cursor.getLong(0));
            caxe.setName(cursor.getString(1));
            caxe.setInfo(cursor.getString(2));
            cases.add(caxe);
        }
        cursor.close();
        return cases;
    }

    /**
     * 删除案件
     *
     * @param id 案件 id
     */
    public void deleteCase(Long id) {
        caseDB.delete(TABLE, ID_WHERE_CLAUSE, new String[]{id.toString()});
    }


    /**
     * 获取案件
     *
     * @param id 案件 id
     * @return 案件
     */
    public Case getCase(Long id) {
        Cursor cursor = caseDB.query(TABLE, null, ID_WHERE_CLAUSE, new String[]{id.toString()}, null, null, null);
        try {
            if (cursor.moveToNext()) {
                Case caxe = new Case();
                caxe.setId(cursor.getLong(0));
                caxe.setName(cursor.getString(1));
                caxe.setInfo(cursor.getString(2));
                return caxe;
            } else {
                return null;
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * 增加空案件，返回自增id
     * @return 案件 id
     */
    public long addCase() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN_NAME, "");
        contentValues.put(INFO_COLUMN_NAME, "");
        caseDB.insert(TABLE, NAME_COLUMN_NAME, contentValues);
        Cursor cursor = caseDB.rawQuery("select last_insert_rowid() from \"case\"", null);
        long id = 0L;
        if (cursor.moveToFirst()) id = cursor.getLong(0);
        cursor.close();
        return id;


    }

    /**
     * 更新案件信息
     *
     * @param caxe 更新后的案件
     */
    public void updateCase(Case caxe) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN_NAME, caxe.getName());
        contentValues.put(INFO_COLUMN_NAME, caxe.getInfo());
        caseDB.update(TABLE, contentValues, ID_WHERE_CLAUSE, new String[]{caxe.getId().toString()});
    }


    public static CaseDBModel getInstance() {
        return Singleton.instance;
    }


}
