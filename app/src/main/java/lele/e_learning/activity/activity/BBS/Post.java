package lele.e_learning.activity.activity.BBS;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import lele.e_learning.R;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class Post extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Button buttonPost = findViewById(R.id.buttonPost);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTheme = findViewById(R.id.editTheme);
                EditText editContent = findViewById(R.id.editContent);
                HttpUtil.sendHttpRequest("Post?theme="+editTheme.getText().toString()+"&content="+editContent.getText().toString()+"&user="+ ClientUser.getId(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if(response.equals("0"))
                            ShowToast(getApplicationContext(),"发表成功");
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });
    }
}
