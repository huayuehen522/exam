package exam.nsj.com.exam.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import exam.nsj.com.exam.util.Constants;

/**
 * Created by Ni Shaojian on 2018/3/29.
 */
public class DBSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_EXAM = "exam";
    public static final String EXAM_ID = "exam_id";
    public static final String EXAM_ID_ORIG = "exam_id_orig";
    public static final String EXAM_CONTENT = "exam_content";
    public static final String EXAM_TYPE = "exam_type";
    public static final String EXAM_OPTION_1 = "exam_option_1";
    public static final String EXAM_OPTION_2 = "exam_option_2";
    public static final String EXAM_OPTION_3 = "exam_option_3";
    public static final String EXAM_OPTION_4 = "exam_option_4";
    public static final String EXAM_OPTION_5 = "exam_option_5";
    public static final String EXAM_OPTION_6 = "exam_option_6";
    public static final String EXAM_ANSWER = "exam_answer";

    public static final String TABLE_EXAM_CREATE = "create table " + TABLE_EXAM
            + "(" + EXAM_ID + " integer primary key autoincrement , "
            + EXAM_ID_ORIG + " long not null , "
            + EXAM_CONTENT + " text null, "
            + EXAM_TYPE + " text null, "
            + EXAM_OPTION_1 + " text null, "
            + EXAM_OPTION_2 + " text null, "
            + EXAM_OPTION_3 + " text null, "
            + EXAM_OPTION_4 + " text null, "
            + EXAM_OPTION_5 + " text null, "
            + EXAM_OPTION_6 + " text null, "
            + EXAM_ANSWER + " text null);";

    public DBSQLiteOpenHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_EXAM_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
