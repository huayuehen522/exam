package exam.nsj.com.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import exam.nsj.com.exam.R;
import exam.nsj.com.exam.model.ExamModel;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ExamModel exam);
        void onItemLongClick(ExamModel exam);
    }

    private List<ExamModel> exams = new ArrayList<>();
    private OnItemClickListener listener;

    public ExamAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setExams(List<ExamModel> exams) {
        this.exams = exams != null ? exams : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        ExamModel exam = exams.get(position);
        holder.bind(exam, listener);
    }

    @Override
    public int getItemCount() { return exams.size(); }

    static class ExamViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvNumber;
        TextView tvType;
        TextView tvContent;
        TextView tvAnswer;

        ExamViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvType = itemView.findViewById(R.id.tvType);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
        }

        void bind(ExamModel exam, OnItemClickListener listener) {
            tvNumber.setText(String.format("No.%d", exam.getIdOrig()));
            tvType.setText(exam.getType() != null ? exam.getType() : "");
            tvContent.setText(exam.getContent() != null ? exam.getContent() : "");
            String answer = exam.getAnswer() != null ? exam.getAnswer() : "";
            tvAnswer.setText(itemView.getContext().getString(R.string.answer_label) + answer);

            Context ctx = itemView.getContext();
            String type = exam.getType() != null ? exam.getType() : "";
            int color;
            switch (type) {
                case "\u5355\u9009\u9898": color = ctx.getColor(R.color.type_single); break;
                case "\u591a\u9009\u9898": color = ctx.getColor(R.color.type_multi); break;
                case "\u5224\u65ad\u9898": color = ctx.getColor(R.color.type_judge); break;
                case "\u586b\u7a7a\u9898": color = ctx.getColor(R.color.type_fill); break;
                default: color = ctx.getColor(R.color.type_other); break;
            }
            tvType.setBackgroundColor(color);

            itemView.setOnClickListener(v -> listener.onItemClick(exam));
            itemView.setOnLongClickListener(v -> {
                listener.onItemLongClick(exam);
                return true;
            });
        }
    }
}
