package lele.e_learning.activity.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import lele.e_learning.R;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;


public class QuestionFragment extends DialogFragment implements DialogInterface.OnClickListener{
    /**
     * 自定义的DialogFragment，
     */
    EditText editOrder,editQuestion,editAnswer;
    Spinner spType;
    Button buttonUpdate,buttonCancel,buttonAdd,buttonDelete;
    String id = "-1";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*初始化界面*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_question,null);

        editOrder = view.findViewById(R.id.editOrder);
        spType = view.findViewById(R.id.spType);
        editQuestion = view.findViewById(R.id.editQuestion);
        editAnswer = view.findViewById(R.id.editAnswer);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        String[] type = new String[]{"Radio","Check","Fill"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, type);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        spType.setAdapter(adapter);

        builder.setView(view);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            HttpUtil.sendHttpRequest("GetQuestion?id=" + id, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        editOrder.setText(jsonObject.getString("order"));
                        editAnswer.setText(jsonObject.getString("answer"));
                        editQuestion.setText(jsonObject.getString("question"));
                        switch (jsonObject.getString("type")){
                            case "Radio":spType.setSelection(0);break;
                            case "Check":spType.setSelection(1);break;
                            case "Fill":spType.setSelection(2);break;
                            default:break;
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
        /*
         * 修改按钮响应函数
         */
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpUtil.sendHttpRequest("UpdateQuestion?id="+id+"&order="+editOrder.getText().toString()+"&type="+spType.getSelectedItem()
                        +"&question="+editQuestion.getText().toString()+"&answer="+editAnswer.getText().toString(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getActivity(),"修改成功");
                        dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getActivity(),"修改失败");
                    }
                });

            }
        });
        /*
         * 新增按钮响应函数
         */
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpUtil.sendHttpRequest("AddQuestion?order="+editOrder.getText().toString()+"&type="+spType.getSelectedItem()
                        +"&question="+editQuestion.getText().toString()+"&answer="+editAnswer.getText().toString(), new HttpCallbackListener() {
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

            }
        });
        /*
         * 删除按钮响应函数
         */
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.sendHttpRequest("DeleteQuestion?id="+id, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getActivity(),"删除成功");
                        dismiss();
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
