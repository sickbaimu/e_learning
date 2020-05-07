package lele.e_learning.activity.activity.Learn;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lele.e_learning.R;

public class Mind extends AppCompatActivity {

    TextView x,y;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind);
        final ImageView mind = findViewById(R.id.mind);
        x = findViewById(R.id.x);
        y = findViewById(R.id.y);
        mind.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                x.setText(motionEvent.getX()+"");
                y.setText(motionEvent.getY()+"");
                Toast.makeText(getApplicationContext(),mind.getWidth()+":"+mind.getHeight(),Toast.LENGTH_SHORT).show();

                return false;
            }
        });


    }
}

