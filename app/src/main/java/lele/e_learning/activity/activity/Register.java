package lele.e_learning.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import lele.e_learning.R;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class Register extends AppCompatActivity {

    ImageView img_sex;
    String sex;
    EditText editName,editID,editPassword,editPasswordRepeat;
    Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*控件初始化*/
        editName = findViewById(R.id.editName);
        editID = findViewById(R.id.editID);
        editPassword = findViewById(R.id.editPassword);
        editPasswordRepeat = findViewById(R.id.editPasswordRepeat);
        buttonRegister = findViewById(R.id.buttonRegister);


        sex = "girl";
        img_sex = findViewById(R.id.img_sex);
        img_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sex.equals("girl")){
                    sex = "boy";
                    img_sex.setImageDrawable(getResources().getDrawable(R.drawable.boy));
                }
                else{
                    sex = "girl";
                    img_sex.setImageDrawable(getResources().getDrawable(R.drawable.girl));
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*输入合法性确认*/
                if(!editPassword.getText().toString().equals(editPasswordRepeat.getText().toString())){
                    ShowToast(getApplicationContext(),"您两次输入的密码不一致");
                    return;
                }
                /*发送请求*/
                String request = "AddUser?"+
                        "Name="+editName.getText().toString()+
                        "&Sex="+sex+
                        "&ID="+editID.getText().toString()+
                        "&Password="+editPassword.getText().toString();
                ShowToast(getApplicationContext(),request);
                HttpUtil.sendHttpRequest(request,new HttpCallbackListener(){
                    @Override
                    public void onFinish(String response) {
                        if(response.equals("0"))
                            ShowToast(getApplicationContext(),"注册成功");

                    }
                    @Override
                    public void onError(Exception e) {
                        ShowToast(getApplicationContext(),"未连接到服务器");
                    }
                });
            }
        });
    }
}
