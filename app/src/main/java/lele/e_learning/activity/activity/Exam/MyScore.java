package lele.e_learning.activity.activity.Exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class MyScore extends AppCompatActivity {

    TextView tvScore,tvTime,tvRate,tvErrorQuestion,tvDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);
        tvErrorQuestion = findViewById(R.id.tvErrorQuestion);
        tvRate = findViewById(R.id.tvRate);
        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);
        tvDay = findViewById(R.id.tvDay);
        HttpUtil.sendHttpRequest("GetMyScore?userID=" + ClientUser.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScore.setText("".concat(response.split("@")[0]));
                        tvTime.setText("用时:\n".concat(response.split("@")[1]).concat("秒"));
                        tvRate.setText("正确数/总题数:\n".concat(response.split("@")[2]));
                        tvErrorQuestion.setText("错误题号:\n".concat(response.split("@")[3]));
                        tvDay.setText("完成时间:\n".concat(response.split("@")[4]));
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
