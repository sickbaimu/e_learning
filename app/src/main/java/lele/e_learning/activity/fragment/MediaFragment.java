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


public class MediaFragment extends DialogFragment implements DialogInterface.OnClickListener{
    /**
     * 自定义的DialogFragment，
     */
    EditText editOrder,editName,editPath;
    Button buttonUpdate,buttonCancel,buttonAdd,buttonDelete;
    String id = "-1";
    String name = "-1";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*初始化界面*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_media,null);

        editOrder = view.findViewById(R.id.editOrder);
        editName = view.findViewById(R.id.editName);
        editPath = view.findViewById(R.id.editPath);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonDelete = view.findViewById(R.id.buttonDelete);


        builder.setView(view);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            HttpUtil.sendHttpRequest("GetMedia?name=" + name, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        id = jsonObject.getString("id");
                        editOrder.setText(jsonObject.getString("order"));
                        editName.setText(jsonObject.getString("name"));
                        editPath.setText(jsonObject.getString("path"));
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

                HttpUtil.sendHttpRequest("UpdateMedia?id="+id+"&order="+editOrder.getText().toString()+"&name="+editName.getText().toString()
                        +"&path="+editPath.getText().toString(), new HttpCallbackListener() {
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

                HttpUtil.sendHttpRequest("AddMedia?order="+editOrder.getText().toString()+"&name="+editName.getText().toString()
                        +"&path="+editPath.getText().toString(), new HttpCallbackListener() {
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
                HttpUtil.sendHttpRequest("DeleteMedia?id="+id, new HttpCallbackListener() {
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
