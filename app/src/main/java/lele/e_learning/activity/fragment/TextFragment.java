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
   EditText editHead,editChapter,editSection,editContent;
   Button buttonSure,buttonCancel;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*初始化界面*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_text,null);

        editHead = view.findViewById(R.id.editHead);
        editChapter = view.findViewById(R.id.editChapter);
        editSection = view.findViewById(R.id.editSection);
        editContent = view.findViewById(R.id.editContent);
        buttonSure = view.findViewById(R.id.buttonSure);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        builder.setView(view);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            /*
            type = bundle.getString("type");
            if(type!=null)
                tvType.setText("类型：".concat(type));
            title = bundle.getString("title");
                if(title!=null)
            tvAddress.setText("位置：".concat(title));
            */
        }
        /*
         * 确定按钮响应函数
         */
        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                HttpUtil.sendHttpRequest("AddNote?userID=" + ClientUser.getId()+"&type="+type+"&title="+title+"&TAG="+editTAG.getText().toString()+"&content="+editContent.getText().toString(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getActivity(),"添加成功");
                        dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getActivity(),"添加失败");

                    }
                });
                */
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
