package exam.nsj.com.exam.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exam")
public class ExamModel extends BaseModel implements IExam {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exam_id")
    private long id;

    @ColumnInfo(name = "exam_id_orig")
    private long idOrig;

    @ColumnInfo(name = "exam_content")
    private String content;

    @ColumnInfo(name = "exam_type")
    private String type;

    @ColumnInfo(name = "exam_option1")
    private String option1;

    @ColumnInfo(name = "exam_option2")
    private String option2;

    @ColumnInfo(name = "exam_option3")
    private String option3;

    @ColumnInfo(name = "exam_option4")
    private String option4;

    @ColumnInfo(name = "exam_option5")
    private String option5;

    @ColumnInfo(name = "exam_option6")
    private String option6;

    @ColumnInfo(name = "exam_answer")
    private String answer;

    public ExamModel() {}

    public ExamModel(long idOrig, String content, String type, String option1, String option2,
                     String option3, String option4, String option5, String option6, String answer) {
        this.idOrig = idOrig;
        this.content = content;
        this.type = type;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.option6 = option6;
        this.answer = answer;
    }

    protected ExamModel(Parcel in) {
        id = in.readLong();
        idOrig = in.readLong();
        content = in.readString();
        type = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        option5 = in.readString();
        option6 = in.readString();
        answer = in.readString();
    }

    public static final Parcelable.Creator<ExamModel> CREATOR = new Parcelable.Creator<ExamModel>() {
        @Override
        public ExamModel createFromParcel(Parcel in) {
            return new ExamModel(in);
        }
        @Override
        public ExamModel[] newArray(int size) {
            return new ExamModel[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(idOrig);
        dest.writeString(content);
        dest.writeString(type);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeString(option5);
        dest.writeString(option6);
        dest.writeString(answer);
    }

    @Override public long getId() { return id; }
    @Override public long getIdOrig() { return idOrig; }
    @Override public String getContent() { return content; }
    @Override public String getType() { return type; }
    @Override public String getOption1() { return option1; }
    @Override public String getOption2() { return option2; }
    @Override public String getOption3() { return option3; }
    @Override public String getOption4() { return option4; }
    @Override public String getOption5() { return option5; }
    @Override public String getOption6() { return option6; }
    @Override public String getAnswer() { return answer; }

    public void setId(long id) { this.id = id; }
    public void setIdOrig(long idOrig) { this.idOrig = idOrig; }
    public void setContent(String content) { this.content = content; }
    public void setType(String type) { this.type = type; }
    public void setOption1(String option1) { this.option1 = option1; }
    public void setOption2(String option2) { this.option2 = option2; }
    public void setOption3(String option3) { this.option3 = option3; }
    public void setOption4(String option4) { this.option4 = option4; }
    public void setOption5(String option5) { this.option5 = option5; }
    public void setOption6(String option6) { this.option6 = option6; }
    public void setAnswer(String answer) { this.answer = answer; }
}
