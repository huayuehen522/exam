package exam.nsj.com.exam.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import exam.nsj.com.exam.model.ExamModel;

@Dao
public interface ExamDao {
    @Query("SELECT * FROM exam ORDER BY exam_id_orig ASC")
    LiveData<List<ExamModel>> getAllExams();

    @Query("SELECT * FROM exam ORDER BY exam_id_orig ASC")
    List<ExamModel> getAllExamsSync();

    @Insert
    void insert(ExamModel exam);

    @Insert
    void insertAll(List<ExamModel> exams);

    @Update
    void update(ExamModel exam);

    @Delete
    void delete(ExamModel exam);

    @Query("DELETE FROM exam")
    void deleteAll();
}
