package lele.e_learning.activity.activity.Exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;

import lele.e_learning.R;
import lele.e_learning.activity.activity.Learn.TextLearnList;
import lele.e_learning.activity.entity.Question;
import lele.e_learning.activity.entity.TextSection;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;
import lele.e_learning.activity.tools.Pack;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class OrderExam extends AppCompatActivity {

    ArrayList<Question> questions;
    TextView tvHead,tvQuestion,tvType,tvRate,tvAnswer;
    CheckBox cbComplexSelectorA,cbComplexSelectorB,cbComplexSelectorC,cbComplexSelectorD;
    RadioButton rbSingleSelectorA,rbSingleSelectorB,rbSingleSelectorC,rbSingleSelectorD;
    RadioGroup singleGroup;
    EditText fillGroup;
    LinearLayout complexGroup;
    Button buttonSubmit;
    String answer ="";
    int page = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        init();
        String type = getIntent().getStringExtra("type");
        if(type.equals("Order"))
            tvHead.setText("顺序模式");
        else
            tvHead.setText("随机模式");

        HttpUtil.sendHttpRequest("GetExam?Type="+type, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                questions = new ArrayList<>();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        questions.add(new Question(
                                jsonObject.getString("id"),
                                jsonObject.getInt("order"),
                                jsonObject.getString("type"),
                                jsonObject.getString("question"),
                                jsonObject.getString("answer")
                        ));
                    }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                ShowQuestion();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    void init(){
        tvHead = findViewById(R.id.tvHead);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvType = findViewById(R.id.tvType);
        tvRate = findViewById(R.id.tvRate);
        tvAnswer = findViewById(R.id.tvAnswer);
        rbSingleSelectorA = findViewById(R.id.rbSingleSelectorA);
        rbSingleSelectorB = findViewById(R.id.rbSingleSelectorB);
        rbSingleSelectorC = findViewById(R.id.rbSingleSelectorC);
        rbSingleSelectorD = findViewById(R.id.rbSingleSelectorD);
        cbComplexSelectorA = findViewById(R.id.cbComplexSelectorA);
        cbComplexSelectorB = findViewById(R.id.cbComplexSelectorB);
        cbComplexSelectorC = findViewById(R.id.cbComplexSelectorC);
        cbComplexSelectorD = findViewById(R.id.cbComplexSelectorD);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        complexGroup = findViewById(R.id.complexGroup);
        singleGroup = findViewById(R.id.singleGroup);
        fillGroup = findViewById(R.id.fillGroup);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //若当前不是最后一题
                if(page+1<questions.size()){
                    addAnswer();
                    page++;
                    ShowQuestion();
                }else{//若当前是最后一题
                    addAnswer();
                    ShowToast(getApplicationContext(),"恭喜您已经完成了所有题目"+answer);
                }
            }
        });
    }

    void ShowQuestion(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //显示问题
                String question = questions.get(page).getQuestion();
                tvQuestion.setText(question.split("@")[0]);
                //显示已回答的题目数和总题目数
                String rate = (page+1)+"/"+questions.size();
                tvRate.setText(rate);
                //显示题型
                String type = questions.get(page).getType();
                switch (type){
                    case "Radio":tvType.setText("单选题");
                        //显示选项
                        complexGroup.setVisibility(View.GONE);
                        singleGroup.setVisibility(View.VISIBLE);
                        fillGroup.setVisibility(View.GONE);
                        rbSingleSelectorA.setChecked(false);
                        rbSingleSelectorB.setChecked(false);
                        rbSingleSelectorC.setChecked(false);
                        rbSingleSelectorD.setChecked(false);
                        rbSingleSelectorA.setText("A. ".concat(question.split("@")[1]));
                        rbSingleSelectorB.setText("B. ".concat(question.split("@")[2]));
                        rbSingleSelectorC.setText("C. ".concat(question.split("@")[3]));
                        rbSingleSelectorD.setText("D. ".concat(question.split("@")[4]));
                        break;
                    case "Check":
                        complexGroup.setVisibility(View.VISIBLE);
                        singleGroup.setVisibility(View.GONE);
                        fillGroup.setVisibility(View.GONE);
                        cbComplexSelectorA.setChecked(false);
                        cbComplexSelectorB.setChecked(false);
                        cbComplexSelectorC.setChecked(false);
                        cbComplexSelectorD.setChecked(false);
                        cbComplexSelectorA.setText("A. ".concat(question.split("@")[1]));
                        cbComplexSelectorB.setText("B. ".concat(question.split("@")[2]));
                        cbComplexSelectorC.setText("C. ".concat(question.split("@")[3]));
                        cbComplexSelectorD.setText("D. ".concat(question.split("@")[4]));
                        tvType.setText("多选题");break;
                    case "Fill":
                        fillGroup.setText("");
                        complexGroup.setVisibility(View.GONE);
                        singleGroup.setVisibility(View.GONE);
                        fillGroup.setVisibility(View.VISIBLE);
                        tvType.setText("填空题");break;
                    default:tvType.setText("未知题型");break;
                }

            }
        });
    }

    void addAnswer() {
        String type = questions.get(page).getType();
        String tempAnswer = "";
        switch (type) {
            case "Radio":
                if (rbSingleSelectorA.isChecked())
                    tempAnswer = "A";
                else if (rbSingleSelectorB.isChecked())
                    tempAnswer = "B";
                else if (rbSingleSelectorC.isChecked())
                    tempAnswer = "C";
                else if (rbSingleSelectorD.isChecked())
                    tempAnswer = "D";
                else
                    tempAnswer = "?";
                if (tempAnswer.equals(questions.get(page).getAnswer()))
                    answer += "T";
                else
                {
                    tvAnswer.setText("正确答案：".concat(questions.get(page).getAnswer()));
                    answer += "F";
                }
                break;
            case "Check":
                tempAnswer = "";
                if (cbComplexSelectorA.isChecked())
                    tempAnswer += "A";
                if (cbComplexSelectorB.isChecked())
                    tempAnswer += "B";
                if (cbComplexSelectorC.isChecked())
                    tempAnswer += "C";
                if (cbComplexSelectorD.isChecked())
                    tempAnswer += "D";
                else
                    tempAnswer += "??";
                if (tempAnswer.equals(questions.get(page).getAnswer()))
                    answer += "T";
                else
                {
                    answer += "F";
                    tvAnswer.setText("正确答案：".concat(questions.get(page).getAnswer()));
                }
                break;
            case "Fill":
                if (fillGroup.getText().toString().equals(questions.get(page).getAnswer()))
                    answer += "T";
                else
                {
                    answer += "F";
                    tvAnswer.setText("正确答案：".concat(questions.get(page).getAnswer()));
                }
                break;
            default:
                tvType.setText("未知题型");
                break;
        }
    }
}
