package lele.e_learning.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import lele.e_learning.R;

public class TeacherHome extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layoutLearnText,layoutLearnPhoto,layoutLearnVideo,layoutNotice,layoutBBS,layoutRank,layoutExamEdit,layoutExamResult,layoutRate;
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
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutLearnText:break;
            case R.id.layoutLearnPhoto:break;
            case R.id.layoutLearnVideo:break;
            case R.id.layoutNotice:break;
            case R.id.layoutBBS:break;
            case R.id.layoutRank:break;
            case R.id.layoutExamEdit:break;
            case R.id.layoutExamResult:break;
            case R.id.layoutRate:break;
            default:break;

        }
    }
}
