package lele.e_learning.activity.activity.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import lele.e_learning.R;
import lele.e_learning.activity.activity.PointRank;
import lele.e_learning.activity.activity.Vote.VoteRank;

public class TeacherRank extends AppCompatActivity {

    LinearLayout buttonPointRank,buttonVoteRank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_rank);
        buttonPointRank = findViewById(R.id.buttonPointRank);
        buttonPointRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PointRank.class));
            }
        });
        buttonVoteRank = findViewById(R.id.buttonVoteRank);
        buttonVoteRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VoteRank.class));
            }
        });
    }
}
