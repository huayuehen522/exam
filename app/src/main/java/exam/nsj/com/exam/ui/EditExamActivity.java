package exam.nsj.com.exam.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import exam.nsj.com.exam.R;
import exam.nsj.com.exam.model.ExamModel;
import exam.nsj.com.exam.viewmodel.ExamViewModel;

public class EditExamActivity extends AppCompatActivity {

    private static final String EXTRA_EXAM = "extra_exam";
    private static final String[] TYPES = {"\u5355\u9009\u9898", "\u591a\u9009\u9898", "\u5224\u65ad\u9898", "\u586b\u7a7a\u9898"};

    private ExamViewModel viewModel;
    private ExamModel editingExam;

    private TextInputEditText etIdOrig, etContent, etOption1, etOption2, etOption3,
                              etOption4, etOption5, etOption6, etAnswer;
    private AutoCompleteTextView spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exam);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(ExamViewModel.class);

        initViews();
        setupTypeSpinner();

        editingExam = getIntent().getParcelableExtra(EXTRA_EXAM);
        if (editingExam != null) {
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.edit_exam);
            populateForm(editingExam);
        } else {
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.add_exam);
        }

        MaterialButton btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> saveExam());
    }

    private void initViews() {
        etIdOrig = findViewById(R.id.etIdOrig);
        etContent = findViewById(R.id.etContent);
        spinnerType = findViewById(R.id.spinnerType);
        etOption1 = findViewById(R.id.etOption1);
        etOption2 = findViewById(R.id.etOption2);
        etOption3 = findViewById(R.id.etOption3);
        etOption4 = findViewById(R.id.etOption4);
        etOption5 = findViewById(R.id.etOption5);
        etOption6 = findViewById(R.id.etOption6);
        etAnswer = findViewById(R.id.etAnswer);
    }

    private void setupTypeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, TYPES);
        spinnerType.setAdapter(adapter);
        spinnerType.setText(TYPES[0], false);
    }

    private void populateForm(ExamModel exam) {
        etIdOrig.setText(String.valueOf(exam.getIdOrig()));
        etContent.setText(exam.getContent());
        if (exam.getType() != null) spinnerType.setText(exam.getType(), false);
        etOption1.setText(exam.getOption1());
        etOption2.setText(exam.getOption2());
        etOption3.setText(exam.getOption3());
        etOption4.setText(exam.getOption4());
        etOption5.setText(exam.getOption5());
        etOption6.setText(exam.getOption6());
        etAnswer.setText(exam.getAnswer());
    }

    private void saveExam() {
        String content = getText(etContent);
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, R.string.content_required, Toast.LENGTH_SHORT).show();
            return;
        }
        String answer = getText(etAnswer);
        if (TextUtils.isEmpty(answer)) {
            Toast.makeText(this, R.string.answer_required, Toast.LENGTH_SHORT).show();
            return;
        }

        long idOrig = 0;
        try { idOrig = Long.parseLong(getText(etIdOrig)); } catch (Exception ignored) {}

        String type = spinnerType.getText().toString().trim();
        if (type.isEmpty()) type = TYPES[0];

        if (editingExam == null) {
            ExamModel exam = new ExamModel(idOrig, content, type,
                getText(etOption1), getText(etOption2), getText(etOption3),
                getText(etOption4), getText(etOption5), getText(etOption6), answer);
            viewModel.insert(exam, () -> runOnUiThread(() -> {
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
                finish();
            }));
        } else {
            editingExam.setIdOrig(idOrig);
            editingExam.setContent(content);
            editingExam.setType(type);
            editingExam.setOption1(getText(etOption1));
            editingExam.setOption2(getText(etOption2));
            editingExam.setOption3(getText(etOption3));
            editingExam.setOption4(getText(etOption4));
            editingExam.setOption5(getText(etOption5));
            editingExam.setOption6(getText(etOption6));
            editingExam.setAnswer(answer);
            viewModel.update(editingExam, () -> runOnUiThread(() -> {
                Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show();
                finish();
            }));
        }
    }

    private String getText(TextInputEditText et) {
        if (et.getText() == null) return "";
        return et.getText().toString().trim();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
