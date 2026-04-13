package exam.nsj.com.exam.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import exam.nsj.com.exam.model.ExamModel;
import exam.nsj.com.exam.repository.ExamRepository;

public class ExamViewModel extends AndroidViewModel {
    private final ExamRepository repository;
    private final LiveData<List<ExamModel>> allExams;

    public ExamViewModel(Application application) {
        super(application);
        repository = new ExamRepository(application);
        allExams = repository.getAllExams();
    }

    public LiveData<List<ExamModel>> getAllExams() {
        return allExams;
    }

    public void insert(ExamModel exam, ExamRepository.Callback callback) {
        repository.insert(exam, callback);
    }

    public void update(ExamModel exam, ExamRepository.Callback callback) {
        repository.update(exam, callback);
    }

    public void delete(ExamModel exam, ExamRepository.Callback callback) {
        repository.delete(exam, callback);
    }

    public void deleteAll(ExamRepository.Callback callback) {
        repository.deleteAll(callback);
    }

    public void importExams(List<ExamModel> exams, ExamRepository.Callback callback) {
        repository.insertAll(exams, callback);
    }

    public void getAllExamsSync(ExamRepository.DataCallback<List<ExamModel>> callback) {
        repository.getAllExamsSync(callback);
    }
}
