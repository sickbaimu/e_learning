package lele.e_learning.activity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import lele.e_learning.R;
import lele.e_learning.activity.activity.Exam.OrderExam;
import lele.e_learning.activity.entity.Question;


public class ErrorInfo extends DialogFragment implements DialogInterface.OnClickListener{
    /**
     * 自定义的DialogFragment，
     */
    Question question;
    Button buttonContinue,buttonCollection;
    TextView tvCorrectAnswer;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*初始化界面*/
        Bundle bundle = getArguments();
        if (bundle != null) {
            question = (Question) bundle.getSerializable("question");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.error_dialog,null);

        buttonCollection = view.findViewById(R.id.buttonCollection);
        buttonContinue = view.findViewById(R.id.buttonContinue);
        tvCorrectAnswer = view.findViewById(R.id.tvCorrectAnswer);
        tvCorrectAnswer.setText(question.getAnswer());
        builder.setView(view);

        /*
         * 收藏按钮响应函数
         */
        buttonCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*
         * 继续按钮响应函数
         */
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderExam orderExam = (OrderExam)getActivity();
                orderExam.page++;
                orderExam .ShowQuestion();
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
