package exam.nsj.com.exam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import exam.nsj.com.exam.adapter.ExamAdapter;
import exam.nsj.com.exam.excel.ExcelHelper;
import exam.nsj.com.exam.model.ExamModel;
import exam.nsj.com.exam.ui.EditExamActivity;
import exam.nsj.com.exam.viewmodel.ExamViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMPORT = 1001;
    private static final int REQUEST_EXPORT = 1002;
    private static final String EXTRA_EXAM = "extra_exam";

    private ExamViewModel viewModel;
    private ExamAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        tvEmpty = findViewById(R.id.tvEmpty);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ExamAdapter(new ExamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExamModel exam) {
                Intent intent = new Intent(MainActivity.this, EditExamActivity.class);
                intent.putExtra(EXTRA_EXAM, exam);
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(ExamModel exam) {
                showDeleteDialog(exam);
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ExamViewModel.class);
        viewModel.getAllExams().observe(this, exams -> {
            adapter.setExams(exams);
            tvEmpty.setVisibility(exams == null || exams.isEmpty() ? View.VISIBLE : View.GONE);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditExamActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_import) {
            openFilePicker();
            return true;
        } else if (id == R.id.action_export) {
            openExportPicker();
            return true;
        } else if (id == R.id.action_clear) {
            showClearAllDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimeTypes = {
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/octet-stream"
        };
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, REQUEST_IMPORT);
    }

    private void openExportPicker() {
        String filename = "exam_export_" +
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".xlsx";
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.putExtra(Intent.EXTRA_TITLE, filename);
        startActivityForResult(intent, REQUEST_EXPORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        Uri uri = data.getData();
        if (uri == null) return;

        if (requestCode == REQUEST_IMPORT) {
            importFromUri(uri);
        } else if (requestCode == REQUEST_EXPORT) {
            exportToUri(uri);
        }
    }

    private void importFromUri(Uri uri) {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try (InputStream is = getContentResolver().openInputStream(uri)) {
                List<ExamModel> exams = ExcelHelper.importFromStream(is);
                viewModel.importExams(exams, () ->
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, getString(R.string.import_success, exams.size()), Toast.LENGTH_SHORT).show();
                    })
                );
            } catch (Exception e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, getString(R.string.import_failed) + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private void exportToUri(Uri uri) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getAllExamsSync(exams -> {
            try (OutputStream os = getContentResolver().openOutputStream(uri)) {
                ExcelHelper.exportToStream(exams, os);
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, getString(R.string.export_success, exams.size()), Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, getString(R.string.export_failed) + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void showDeleteDialog(ExamModel exam) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.delete_title)
            .setMessage(R.string.delete_message)
            .setPositiveButton(R.string.confirm, (d, w) -> viewModel.delete(exam, null))
            .setNegativeButton(R.string.cancel, null)
            .show();
    }

    private void showClearAllDialog() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.clear_title)
            .setMessage(R.string.clear_message)
            .setPositiveButton(R.string.confirm, (d, w) -> {
                viewModel.deleteAll(null);
                Toast.makeText(this, R.string.cleared, Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton(R.string.cancel, null)
            .show();
    }
}
