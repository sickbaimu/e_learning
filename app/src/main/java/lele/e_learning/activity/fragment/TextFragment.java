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
    private Button buttonAdd, buttonUpdate, buttonDelete, buttonCancel;
    String id = "-1";
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

        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        builder.setView(view);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            editHead.setText(bundle.getString("name"));
            editContent.setText(bundle.getString("content"));
            editSection.setText(bundle.getString("sectionOrder"));
            editChapter.setText(bundle.getString("chapterID"));
            id = bundle.getString("id");
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.sendHttpRequest("AddText?name="+editHead.getText().toString()+
                        "&content="+editContent.getText().toString()+
                        "&order="+editSection.getText().toString()+
                        "&chapter="+editChapter.getText().toString(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getActivity(),"添加成功");
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getActivity(),"添加失败");
                    }
                });
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.sendHttpRequest("UpdateText?name="+editHead.getText().toString()+
                        "&content="+editContent.getText().toString()+
                        "&order="+editContent.getText().toString()+
                        "&chapter="+editContent.getText().toString()+
                        "&id="+id, new HttpCallbackListener() {
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

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.sendHttpRequest("DeleteText?id="+id, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getActivity(),"删除成功");
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getActivity(),"删除失败");
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
