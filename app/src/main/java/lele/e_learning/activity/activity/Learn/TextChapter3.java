package lele.e_learning.activity.activity.Learn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lele.e_learning.R;

public class TextChapter3 extends AppCompatActivity implements View.OnClickListener{

    TextView[] textViews = new TextView[39];
    int[] id = new int[]{
            R.id.s_3_1, R.id.s_3_2, R.id.s_3_3, R.id.s_3_4, R.id.s_3_5,
            R.id.s_3_6, R.id.s_3_7, R.id.s_3_8, R.id.s_3_9, R.id.s_3_10,
            R.id.s_3_11, R.id.s_3_12, R.id.s_3_13, R.id.s_3_14, R.id.s_3_15,
            R.id.s_3_16, R.id.s_3_17, R.id.s_3_18, R.id.s_3_19, R.id.s_3_20,
            R.id.s_3_21, R.id.s_3_22, R.id.s_3_23, R.id.s_3_24, R.id.s_3_25,
            R.id.s_3_26, R.id.s_3_27, R.id.s_3_28, R.id.s_3_29, R.id.s_3_30,
            R.id.s_3_31, R.id.s_3_32, R.id.s_3_33, R.id.s_3_34, R.id.s_3_35,
            R.id.s_3_36, R.id.s_3_37, R.id.s_3_38, R.id.s_3_39
    };
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_chapter3);
        final ImageView mind = findViewById(R.id.mind);
        mind.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX()*100/mind.getWidth();
                float y = motionEvent.getY()*100/mind.getHeight();
                addListener(x,y,58,68,10,14,3,1);
                addListener(x,y,73,80,3,7,3,2);
                addListener(x,y,72,78,9,14,3,3);
                addListener(x,y,72,81,15,19,3,4);
                addListener(x,y,73,78,21,26,3,5);
                addListener(x,y,69,77,31,35,3,6);
                addListener(x,y,82,88,30,34,3,7);
                addListener(x,y,82,90,36,40,3,8);
                addListener(x,y,69,77,56,60,3,9);
                addListener(x,y,80,86,45,48,3,10);
                addListener(x,y,90,95,40,44,3,11);
                addListener(x,y,90,96,46,50,3,12);
                addListener(x,y,90,95,53,56,3,13);
                addListener(x,y,80,86,62,66,3,14);
                addListener(x,y,91,96,57,61,3,15);
                addListener(x,y,91,97,63,67,3,16);
                addListener(x,y,91,98,70,73,3,17);
                addListener(x,y,70,77,79,83,3,18);
                addListener(x,y,82,91,78,82,3,19);
                addListener(x,y,82,88,84,88,3,20);
                addListener(x,y,22,33,10,14,3,21);
                addListener(x,y,14,18,9,13,3,22);
                addListener(x,y,12,18,15,19,3,23);
                addListener(x,y,14,20,32,36,3,24);
                addListener(x,y,4,10,29,33,3,25);
                addListener(x,y,3,10,35,38,3,26);
                addListener(x,y,1,10,41,40,3,27);
                addListener(x,y,14,20,51,55,3,28);
                addListener(x,y,4,10,47,51,3,29);
                addListener(x,y,4,10,53,57,3,30);
                addListener(x,y,4,10,59,63,3,31);
                addListener(x,y,12,20,68,72,3,32);
                addListener(x,y,5,8,64,68,3,33);
                addListener(x,y,5,8,71,74,3,34);
                addListener(x,y,5,8,76,80,3,35);
                addListener(x,y,14,20,87,90,3,36);
                addListener(x,y,5,10,83,86,3,37);
                addListener(x,y,5,10,89,92,3,38);
                addListener(x,y,5,10,95,98,3,39);
                return false;
            }
        });
        for(int i=0;i<textViews.length;i++){
            textViews[i] = findViewById(id[i]);
            textViews[i].setOnClickListener(this);
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
                jump(3,i+1);
        }
    }
}
