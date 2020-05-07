package lele.e_learning.activity.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;


public class TextFragment extends DialogFragment implements DialogInterface.OnClickListener{
    /**
     * 自定义的DialogFragment，
     */
    EditText editHead,editContent;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*初始化界面*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_text,null);

        editHead = view.findViewById(R.id.editHead);

        editContent = view.findViewById(R.id.editContent);

        Button buttonUpdate = view.findViewById(R.id.buttonUpdate);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        builder.setView(view);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            editContent.setText(bundle.getString("content"));
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.sendHttpRequest("UpdateText?"+
                        "&content="+editContent.getText().toString()+
                        "&id="+bundle.getString("chapterID")+"_"+bundle.getString("sectionOrder")
                        , new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getActivity(),"修改成功");
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getActivity(),"修改失败");
                    }
                });
            }
        });


        /*
         * 取消按钮响应函数
         */
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
