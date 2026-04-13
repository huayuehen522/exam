package exam.nsj.com.exam.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import exam.nsj.com.exam.db.ExamDao;
import exam.nsj.com.exam.db.ExamDatabase;
import exam.nsj.com.exam.model.ExamModel;

public class ExamRepository {
    private final ExamDao examDao;
    private final LiveData<List<ExamModel>> allExams;
    private final ExecutorService executor;

    public interface Callback {
        void onComplete();
    }

    public interface DataCallback<T> {
        void onComplete(T data);
    }

    public ExamRepository(Context context) {
        ExamDatabase db = ExamDatabase.getInstance(context);
        examDao = db.examDao();
        allExams = examDao.getAllExams();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ExamModel>> getAllExams() {
        return allExams;
    }

    public void insert(ExamModel exam, Callback callback) {
        executor.execute(() -> {
            examDao.insert(exam);
            if (callback != null) callback.onComplete();
        });
    }

    public void insertAll(List<ExamModel> exams, Callback callback) {
        executor.execute(() -> {
            examDao.insertAll(exams);
            if (callback != null) callback.onComplete();
        });
    }

    public void update(ExamModel exam, Callback callback) {
        executor.execute(() -> {
            examDao.update(exam);
            if (callback != null) callback.onComplete();
        });
    }

    public void delete(ExamModel exam, Callback callback) {
        executor.execute(() -> {
            examDao.delete(exam);
            if (callback != null) callback.onComplete();
        });
    }

    public void deleteAll(Callback callback) {
        executor.execute(() -> {
            examDao.deleteAll();
            if (callback != null) callback.onComplete();
        });
    }

    public void getAllExamsSync(DataCallback<List<ExamModel>> callback) {
        executor.execute(() -> {
            List<ExamModel> exams = examDao.getAllExamsSync();
            if (callback != null) callback.onComplete(exams);
        });
    }
}
