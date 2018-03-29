package exam.nsj.com.exam.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import exam.nsj.com.exam.Application;
import exam.nsj.com.exam.model.ExamModel;
import exam.nsj.com.exam.model.IExam;
import exam.nsj.com.exam.sql.DBSQLiteOpenHelper;

import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_ANSWER;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_CONTENT;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_ID;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_ID_ORIG;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_OPTION_1;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_OPTION_2;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_OPTION_3;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_OPTION_4;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_OPTION_5;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_OPTION_6;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.EXAM_TYPE;
import static exam.nsj.com.exam.sql.DBSQLiteOpenHelper.TABLE_EXAM;

/**
 * Created by Ni Shaojian on 2018/3/29.
 */

public class DBController {
    public static DBController instance;
    private static DBSQLiteOpenHelper dbHelper;
    private static SQLiteDatabase database;
    public static final String[] allColumns = { EXAM_ID, EXAM_ID_ORIG, EXAM_CONTENT,
            EXAM_TYPE, EXAM_OPTION_1, EXAM_OPTION_2, EXAM_OPTION_3, EXAM_OPTION_4,
            EXAM_OPTION_5, EXAM_OPTION_6, EXAM_ANSWER};

    private DBController() {
        open();
    }

    public static DBController getInstance() {
        if (instance == null){
            instance = new DBController();
        }
        return instance;
    }

    public void open() throws SQLException {
        if (dbHelper == null) {
            dbHelper = new DBSQLiteOpenHelper(Application.getAppContext());
        }
        if (database == null) {
            database = dbHelper.getWritableDatabase();
        }
    }

    public void createExamItem(long examIdOrig, String examContent, String examType,
                               String examOption1, String examOption2, String examOption3,
                               String examOption4, String examOption5, String examOption6,
                               String examAnswer) {
        ContentValues values = new ContentValues();
        ;
        values.put(EXAM_ID_ORIG, examIdOrig);
        values.put(EXAM_CONTENT, examContent);
        values.put(EXAM_TYPE, examType);
        values.put(EXAM_OPTION_1, examOption1);
        values.put(EXAM_OPTION_2, examOption2);
        values.put(EXAM_OPTION_3, examOption3);
        values.put(EXAM_OPTION_4, examOption4);
        values.put(EXAM_OPTION_5, examOption5);
        values.put(EXAM_OPTION_6, examOption6);
        values.put(EXAM_ANSWER, examAnswer);
        database.insert(TABLE_EXAM, null, values);
    }

    public void deleteExamItemByID(long examId) {
        database.delete(TABLE_EXAM, EXAM_ID + " = " + examId, null);
    }

    public void updateExamItemByID(long examId, ContentValues values) {
        database.update(TABLE_EXAM, values, EXAM_ID + " = \"" + examId + "\"", null);
    }

    public List<IExam> getExamItemList(){
        List<IExam> examList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(" SELECT ");
        for (int i = 0; i < allColumns.length; i++) {
            String s = allColumns[i];
            queryBuilder.append(s);
            if (i < allColumns.length - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(" FROM " + TABLE_EXAM);
        queryBuilder.append(" ORDER BY " + EXAM_ID_ORIG);
        String query = queryBuilder.toString();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            examList.add(cursorToModel(cursor));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return examList;
    }

    private ExamModel cursorToModel(Cursor cursor) {
        ExamModel examModel = new ExamModel();
        int index = 0;
        examModel.setId(cursor.getLong(index++));
        examModel.setIdOrig(cursor.getLong(index++));
        examModel.setContent(cursor.getString(index++));
        examModel.setType(cursor.getString(index++));
        examModel.setOption1(cursor.getString(index++));
        examModel.setOption2(cursor.getString(index++));
        examModel.setOption3(cursor.getString(index++));
        examModel.setOption4(cursor.getString(index++));
        examModel.setOption5(cursor.getString(index++));
        examModel.setOption6(cursor.getString(index++));
        examModel.setAnswer(cursor.getString(index++));
        return examModel;
    }
}
