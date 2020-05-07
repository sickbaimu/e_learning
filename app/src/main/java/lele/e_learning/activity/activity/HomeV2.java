package lele.e_learning.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import lele.e_learning.R;
import lele.e_learning.activity.activity.BBS.MainBBSPage;
import lele.e_learning.activity.activity.BBS.Notice;
import lele.e_learning.activity.activity.Exam.ExamHome;
import lele.e_learning.activity.activity.Learn.CheckNote;
import lele.e_learning.activity.activity.Learn.LearningType;
import lele.e_learning.activity.activity.Vote.VoteList;

public class HomeV2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_v2);
        //论坛交流
        LinearLayout layout_bbs = findViewById(R.id.layout_bbs);
        layout_bbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainBBSPage.class));
            }
        });
        //课程公告
        LinearLayout layout_notice = findViewById(R.id.layout_notice);
        layout_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Notice.class));
            }
        });
        //成长积分
        LinearLayout layout_point = findViewById(R.id.layout_point);
        layout_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PointToday.class));
            }
        });
        //课程介绍
        LinearLayout layout_introduction = findViewById(R.id.layout_introduction);
        layout_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Introduction.class));

            }
        });
        //作品展示
        LinearLayout layout_vote = findViewById(R.id.layout_vote);
        layout_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VoteList.class));

            }
        });
        //课程学习
        LinearLayout layout_learning = findViewById(R.id.layout_learning);
        layout_learning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LearningType.class));
            }
        });
        //每日一练
        LinearLayout layout_exam = findViewById(R.id.layout_exam);
        layout_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ExamHome.class));
            }
        });
        //
        //笔记
        LinearLayout layout_note = findViewById(R.id.layout_note);
        layout_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CheckNote.class));
            }
        });
    }
}
