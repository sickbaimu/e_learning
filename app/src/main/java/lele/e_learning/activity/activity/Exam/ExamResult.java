package lele.e_learning.activity.activity.Exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lele.e_learning.R;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class ExamResult extends AppCompatActivity {

    TextView tvScore,tvTime,tvRate,tvErrorQuestion,tvDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);
        tvErrorQuestion = findViewById(R.id.tvErrorQuestion);
        tvRate = findViewById(R.id.tvRate);
        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);
        tvDay = findViewById(R.id.tvDay);
        tvRate.setText("正确数/总题数:\n".concat(getIntent().getStringExtra("rate")));
        tvScore.setText(getIntent().getStringExtra("point"));
        tvTime.setText("用时:\n".concat(getIntent().getStringExtra("time")+"秒"));
        String errorNum = "";
        for(int i=0;i<getIntent().getStringExtra("answer").length();i++)
        {
            if(getIntent().getStringExtra("answer").charAt(i)=='F')
                errorNum += (i+1)+"、";
        }
        errorNum = errorNum.substring(0,errorNum.length()-1);
        tvErrorQuestion.setText("错误题号:\n".concat(errorNum));
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        tvDay.setText("完成时间:\n".concat(bartDateFormat.format(new Date())));
        if(getIntent().getStringExtra("model").equals("Final")){
            HttpUtil.sendHttpRequest("AddScore?userID="+ ClientUser.getId()+
                    "&score="+getIntent().getStringExtra("point")+
                    "&time="+getIntent().getStringExtra("time")+
                    "&rate="+getIntent().getStringExtra("rate")+
                    "&errorNum="+errorNum+
                    "&day="+bartDateFormat.format(new Date()), new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }
}
