package lele.e_learning.activity.activity.Learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import lele.e_learning.R;

public class TextChapter2 extends AppCompatActivity implements View.OnClickListener{

    TextView[] textViews = new TextView[15];
    int[] id = new int[]{
            R.id.s_2_1, R.id.s_2_2, R.id.s_2_3, R.id.s_2_4, R.id.s_2_5,
            R.id.s_2_6, R.id.s_2_7, R.id.s_2_8, R.id.s_2_9, R.id.s_2_10,
            R.id.s_2_11, R.id.s_2_12, R.id.s_2_13, R.id.s_2_14, R.id.s_2_15
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chapter2);
        for(int i=0;i<textViews.length;i++){
            textViews[i] = findViewById(id[i]);
            textViews[i].setOnClickListener(this);
        }
    }

    public void jump(int chapter_id,int section_order){
        Intent intent = new Intent(getApplicationContext(),TextLearnPage.class);
        intent.putExtra("chapter_id",String.valueOf(chapter_id));
        intent.putExtra("section_order",String.valueOf(section_order));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        for(int i=0;i<id.length;i++){
            if(view.getId()==id[i])
                jump(2,i+1);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
