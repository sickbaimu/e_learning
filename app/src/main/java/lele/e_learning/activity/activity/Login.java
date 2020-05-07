package lele.e_learning.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import lele.e_learning.R;
import lele.e_learning.activity.activity.Learn.LearningType;
import lele.e_learning.activity.activity.Learn.Mind;
import lele.e_learning.activity.activity.Learn.TextLearnList;
import lele.e_learning.activity.activity.teacher.TeacherHome;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class Login extends Activity implements View.OnClickListener{

    Button b_login,b_register;
    EditText edit_username,edit_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    void init(){
        b_login = findViewById(R.id.b_login);
        b_register = findViewById(R.id.b_register);
        b_login.setOnClickListener(this);
        b_register.setOnClickListener(this);
        edit_username = findViewById(R.id.edit_username);
        edit_pwd = findViewById(R.id.edit_pwd);

    }
    public void onClick(View v){
        switch (v.getId()){
            /*登录验证*/
            case R.id.b_login:
                HttpUtil.sendHttpRequest("Login?username="+edit_username.getText()+"&pwd="+edit_pwd.getText(),new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        /*用户名或密码错误*/
                        if(response.equals("-1"))
                            ShowToast(getApplicationContext(),"用户名或密码错误！");
                        /*教师端登录验证*/
                        else if(response.equals("-2"))
                        {
                            ClientUser.setId("999999");//将当前登录用户保存到客户端
                            startActivity(new Intent(getApplicationContext(), TeacherHome.class));
                        }
                        /*一般用户登录验证*/
                        else{
                            ClientUser.setId(response);//将当前登录用户保存到客户端
                            startActivity(new Intent(getApplicationContext(), HomeV2.class));
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        ShowToast(getApplicationContext(),"未连接到服务器");
                    }
                });break;
                /*跳转到注册界面*/
            case R.id.b_register:startActivity(new Intent(getApplicationContext(),Register.class));break;
            default:break;
        }
    }
}
