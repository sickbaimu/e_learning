package lele.e_learning.activity.activity.Exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lele.e_learning.R;

public class ExamHome extends AppCompatActivity implements View.OnClickListener{

    Button buttonOrderExam,buttonRandomExam,buttonFinalExam,buttonMyScore,buttonCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_home);
        buttonOrderExam = findViewById(R.id.buttonOrderExam);
        buttonRandomExam = findViewById(R.id.buttonRandomExam);
        buttonFinalExam = findViewById(R.id.buttonFinalExam);
        buttonMyScore = findViewById(R.id.buttonMyScore);
        buttonCollection = findViewById(R.id.buttonCollection);
        buttonCollection.setOnClickListener(this);
        buttonMyScore.setOnClickListener(this);
        buttonOrderExam.setOnClickListener(this);
        buttonFinalExam.setOnClickListener(this);
        buttonRandomExam.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonOrderExam:
                Intent intent3 = new Intent(getApplicationContext(), Exam.class);intent3.putExtra("type","Order");startActivity(intent3);break;
            case R.id.buttonRandomExam:Intent intent4 = new Intent(getApplicationContext(), Exam.class);intent4.putExtra("type","Random");startActivity(intent4);break;
            case R.id.buttonFinalExam:Intent intent5 = new Intent(getApplicationContext(), Exam.class);intent5.putExtra("type","Final");startActivity(intent5);break;
            case R.id.buttonCollection:startActivity(new Intent(getApplicationContext(),QuestionCollection.class));break;
            case R.id.buttonMyScore:startActivity(new Intent(getApplicationContext(), MyScore.class));break;
            default:break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
