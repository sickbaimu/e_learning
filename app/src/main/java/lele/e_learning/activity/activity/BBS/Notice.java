package lele.e_learning.activity.activity.BBS;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class Notice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        HttpUtil.sendHttpRequest("GetNotice", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                TextView noticeContent = findViewById(R.id.noticeContent);
                noticeContent.setText(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
