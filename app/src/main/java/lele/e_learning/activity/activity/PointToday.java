package lele.e_learning.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class PointToday extends AppCompatActivity {

    TextView tvPointLogin,tvPointCollection,tvPointBBS,tvPointTP,tvPointTextTime,tvPointMediaTime,tvPointAnswer,tvPointAnswerTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_today);
        tvPointLogin = findViewById(R.id.tvPointLogin);
        tvPointCollection = findViewById(R.id.tvPointCollection);
        tvPointBBS = findViewById(R.id.tvPointBBS);
        tvPointTP = findViewById(R.id.tvPointTP);
        tvPointTextTime = findViewById(R.id.tvPointTextTime);
        tvPointMediaTime = findViewById(R.id.tvPointMediaTime);
        tvPointAnswer = findViewById(R.id.tvPointAnswer);
        tvPointAnswerTime = findViewById(R.id.tvPointAnswerTime);

        HttpUtil.sendHttpRequest("GetPointToday?userID=" + ClientUser.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                tvPointLogin.setText(response.split("-")[0].concat("/1"));
                tvPointCollection.setText(response.split("-")[1].concat("/1"));
                tvPointBBS.setText(response.split("-")[2].concat("/1"));
                tvPointTP.setText(response.split("-")[3].concat("/6"));
                tvPointTextTime.setText(response.split("-")[4].concat("/6"));
                tvPointMediaTime.setText(response.split("-")[5].concat("/6"));
                tvPointAnswer.setText(response.split("-")[6].concat("/6"));
                tvPointAnswerTime.setText(response.split("-")[7].concat("/6"));
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
