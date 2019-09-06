package lele.e_learning.activity.activity.teacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class TeacherNotice extends AppCompatActivity {

    Button buttonSure;
    EditText noticeContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_notice);
        noticeContent = findViewById(R.id.noticeContent);
        buttonSure = findViewById(R.id.buttonSure);
        HttpUtil.sendHttpRequest("GetNotice", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                noticeContent.setText(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.sendHttpRequest("UpdateNotice?Content=" + noticeContent.getText().toString(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getApplicationContext(),"修改成功");
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });
    }
}
