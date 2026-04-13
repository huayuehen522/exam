package exam.nsj.com.exam.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import exam.nsj.com.exam.model.ExamModel;
import exam.nsj.com.exam.util.Constants;

@Database(entities = {ExamModel.class}, version = 1, exportSchema = false)
public abstract class ExamDatabase extends RoomDatabase {
    private static volatile ExamDatabase INSTANCE;

    public abstract ExamDao examDao();

    public static ExamDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ExamDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ExamDatabase.class,
                            Constants.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
