package exam.nsj.com.exam.model;

import android.os.Parcel;

/**
 * Created by Ni Shaojian on 2018/3/29.
 */

public class ExamModel extends BaseModel implements IExam {
    private long id;
    private long idOrig;
    private String content;
    private String type;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String option6;
    private String answer;

    public ExamModel(){
    }

    public ExamModel(long idOrig, String content, String type, String option1, String option2,
                     String option3, String option4, String option5, String option6, String answer){
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
    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getIdOrig() {
        return idOrig;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getOption1() {
        return option1;
    }

    @Override
    public String getOption2() {
        return option2;
    }

    @Override
    public String getOption3() {
        return option3;
    }

    @Override
    public String getOption4() {
        return option4;
    }

    @Override
    public String getOption5() {
        return option5;
    }

    @Override
    public String getOption6() {
        return option6;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdOrig(long idOrig) {
        this.idOrig = idOrig;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public void setOption6(String option6) {
        this.option6 = option6;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
