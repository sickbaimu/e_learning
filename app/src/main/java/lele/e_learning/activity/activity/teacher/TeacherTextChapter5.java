package lele.e_learning.activity.activity.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.activity.Learn.TextLearnPage;

public class TeacherTextChapter5 extends AppCompatActivity implements View.OnClickListener {

    TextView[] textViews = new TextView[10];
    int[] id = new int[]{
            R.id.s_5_1, R.id.s_5_2, R.id.s_5_3, R.id.s_5_4, R.id.s_5_5,
            R.id.s_5_6, R.id.s_5_7, R.id.s_5_8, R.id.s_5_9, R.id.s_5_10
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chapter5);
        for(int i=0;i<textViews.length;i++){
            textViews[i] = findViewById(id[i]);
            textViews[i].setOnClickListener(this);
        }
    }

    public void jump(int chapter_id,int section_order){
        Intent intent = new Intent(getApplicationContext(),TeacherTextLearnPage.class);
        intent.putExtra("chapter_id",String.valueOf(chapter_id));
        intent.putExtra("section_order",String.valueOf(section_order));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        for(int i=0;i<id.length;i++){
            if(view.getId()==id[i])
                jump(5,i+1);
        }
    }
}
