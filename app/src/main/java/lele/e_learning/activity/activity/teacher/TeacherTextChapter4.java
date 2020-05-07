package lele.e_learning.activity.activity.teacher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.activity.Learn.TextLearnPage;

public class TeacherTextChapter4 extends AppCompatActivity implements View.OnClickListener{

    TextView[] textViews = new TextView[38];
    int[] id = new int[]{
            R.id.s_4_1, R.id.s_4_2, R.id.s_4_3, R.id.s_4_4, R.id.s_4_5,
            R.id.s_4_6, R.id.s_4_7, R.id.s_4_8, R.id.s_4_9, R.id.s_4_10,
            R.id.s_4_11, R.id.s_4_12, R.id.s_4_13, R.id.s_4_14, R.id.s_4_15,
            R.id.s_4_16, R.id.s_4_17, R.id.s_4_18, R.id.s_4_19, R.id.s_4_20,
            R.id.s_4_21, R.id.s_4_22, R.id.s_4_23, R.id.s_4_24, R.id.s_4_25,
            R.id.s_4_26, R.id.s_4_27, R.id.s_4_28, R.id.s_4_29, R.id.s_4_30,
            R.id.s_4_31, R.id.s_4_32, R.id.s_4_33, R.id.s_4_34, R.id.s_4_35,
            R.id.s_4_36, R.id.s_4_37, R.id.s_4_38
    };
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chapter4);
        for(int i=0;i<textViews.length;i++){
            textViews[i] = findViewById(id[i]);
            textViews[i].setOnClickListener(this);
        }
        final ImageView mind = findViewById(R.id.mind);
        mind.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX() * 100 / mind.getWidth();
                float y = motionEvent.getY() * 100 / mind.getHeight();
                addListener(x,y,67,79,19,28,4,1);
                addListener(x,y,85,95,13,18,4,2);
                addListener(x,y,85,95,20,23,4,3);
                addListener(x,y,86,94,26,30,4,4);
                addListener(x,y,68,80,38,44,4,5);
                addListener(x,y,88,93,32,36,4,6);
                addListener(x,y,88,93,38,42,4,7);
                addListener(x,y,88,93,45,48,4,8);
                addListener(x,y,88,93,50,54,4,9);
                addListener(x,y,70,79,60,70,4,10);
                addListener(x,y,86,91,57,61,4,11);
                addListener(x,y,86,91,63,67,4,12);
                addListener(x,y,87,98,70,73,4,13);
                addListener(x,y,87,98,76,79,4,14);
                addListener(x,y,87,95,82,86,4,15);
                addListener(x,y,34,39,4,11,4,16);
                addListener(x,y,23,29,2,6,4,17);
                addListener(x,y,19,28,8,12,4,18);
                addListener(x,y,33,39,28,33,4,19);
                addListener(x,y,17,28,16,23,4,20);
                addListener(x,y,3,12,13,16,4,21);
                addListener(x,y,2,12,19,22,4,22);
                addListener(x,y,3,12,25,28,4,23);
                addListener(x,y,17,27,36,41,4,24);
                addListener(x,y,7,11,31,35,4,25);
                addListener(x,y,2,11,37,41,4,26);
                addListener(x,y,2,11,43,47,4,27);
                addListener(x,y,7,11,50,53,4,28);
                addListener(x,y,33,38,57,42,4,29);
                addListener(x,y,19,28,51,55,4,30);
                addListener(x,y,19,28,58,61,4,31);
                addListener(x,y,19,28,64,67,4,32);
                addListener(x,y,17,27,70,74,4,33);
                addListener(x,y,27,37,78,83,4,34);
                addListener(x,y,14,22,76,80,4,35);
                addListener(x,y,14,22,82,86,4,36);
                addListener(x,y,14,22,88,92,4,37);
                addListener(x,y,14,22,95,98,4,38);
                return false;
            }
        });
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
                jump(4,i+1);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void addListener(float x,float y,int x1,int x2,int y1,int y2,int chapter_id,int section_order){
        if(x >= x1 && x <= x2 && y >= y1 && y <= y2){
            jump(chapter_id,section_order);
        }
    }
}
