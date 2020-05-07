package lele.e_learning.activity.activity.Learn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import lele.e_learning.R;

public class TextChapter1 extends AppCompatActivity implements View.OnClickListener{

    TextView[] textViews = new TextView[23];
    int[] id = new int[]{
            R.id.s_1_1, R.id.s_1_2, R.id.s_1_3, R.id.s_1_4, R.id.s_1_5,
            R.id.s_1_6, R.id.s_1_7, R.id.s_1_8, R.id.s_1_9, R.id.s_1_10,
            R.id.s_1_11, R.id.s_1_12, R.id.s_1_13, R.id.s_1_14, R.id.s_1_15,
            R.id.s_1_16, R.id.s_1_17, R.id.s_1_18, R.id.s_1_19, R.id.s_1_20,
            R.id.s_1_21, R.id.s_1_22, R.id.s_1_23
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chapter1);
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
                jump(1,i+1);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
