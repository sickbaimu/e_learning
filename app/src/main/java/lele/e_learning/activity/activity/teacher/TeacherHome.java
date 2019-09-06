package lele.e_learning.activity.activity.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import lele.e_learning.R;
import lele.e_learning.activity.activity.Learn.MediaLearnList;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class TeacherHome extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layoutLearnText,layoutLearnPhoto,layoutLearnVideo,layoutNotice,layoutBBS,layoutRank,layoutExamEdit,layoutExamResult,layoutRate;
    String photoList,mediaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        layoutLearnText = findViewById(R.id.layoutLearnText);
        layoutLearnPhoto = findViewById(R.id.layoutLearnPhoto);
        layoutLearnVideo = findViewById(R.id.layoutLearnVideo);
        layoutNotice = findViewById(R.id.layoutNotice);
        layoutBBS = findViewById(R.id.layoutBBS);
        layoutRank = findViewById(R.id.layoutRank);
        layoutExamEdit = findViewById(R.id.layoutExamEdit);
        layoutExamResult = findViewById(R.id.layoutExamResult);
        layoutRate = findViewById(R.id.layoutRate);

        layoutLearnText.setOnClickListener(this);
        layoutLearnPhoto.setOnClickListener(this);
        layoutLearnVideo.setOnClickListener(this);
        layoutNotice.setOnClickListener(this);
        layoutBBS.setOnClickListener(this);
        layoutRank.setOnClickListener(this);
        layoutExamEdit.setOnClickListener(this);
        layoutExamResult.setOnClickListener(this);
        layoutRate.setOnClickListener(this);

        HttpUtil.sendHttpRequest("GetHomeInfo?userID="+ ClientUser.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            photoList = jsonObject.getString("photoList");
                            mediaList = jsonObject.getString("mediaList");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutLearnText:startActivity(new Intent(getApplicationContext(), TeacherTextLearnList.class));break;
            case R.id.layoutLearnPhoto:Intent intent1 = new Intent(getApplicationContext(), TeacherPhotoLearnList.class);
                intent1.putExtra("names",photoList);
                startActivity(intent1);break;
            case R.id.layoutLearnVideo:Intent intent2 = new Intent(getApplicationContext(), TeacherMediaLearnList.class);
                intent2.putExtra("names",mediaList);
                startActivity(intent2);break;
            case R.id.layoutNotice:startActivity(new Intent(getApplicationContext(), TeacherNotice.class));break;
            case R.id.layoutBBS:startActivity(new Intent(getApplicationContext(), TeacherMBBS.class));break;
            case R.id.layoutRank:startActivity(new Intent(getApplicationContext(), TeacherRank.class));break;
            case R.id.layoutExamEdit:startActivity(new Intent(getApplicationContext(), TeacherQuestionList.class));break;
            case R.id.layoutExamResult:startActivity(new Intent(getApplicationContext(), TeacherScore.class));break;
            case R.id.layoutRate:startActivity(new Intent(getApplicationContext(), TeacherRate.class));break;
            default:break;

        }
    }
}
