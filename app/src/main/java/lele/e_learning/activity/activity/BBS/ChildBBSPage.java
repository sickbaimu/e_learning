package lele.e_learning.activity.activity.BBS;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lele.e_learning.R;
import lele.e_learning.activity.entity.ChildBBS;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class ChildBBSPage extends AppCompatActivity {

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_bbs);
        TextView tv_head = findViewById(R.id.tv_head);
        tv_head.setText(getIntent().getStringExtra("content"));
        id = getIntent().getStringExtra("id");
        Button buttonRePost = findViewById(R.id.buttonRePost);
        buttonRePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editRePost = findViewById(R.id.editRePost);
                HttpUtil.sendHttpRequest("RePost?mainID=" + id + "&user=" + ClientUser.getId() + "&content=" + editRePost.getText().toString(), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if(response.equals("0"))
                        {
                            ShowToast(getApplicationContext(),"回复成功");
                            startActivity(new Intent(getApplicationContext(),ChildBBS.class));
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getApplicationContext(),"回复失败");
                    }
                });

            }
        });
        HttpUtil.sendHttpRequest("GetChildBBS?id="+id, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList<ChildBBS> childBBSArrayList = new ArrayList<>();
                            for(int i =0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                childBBSArrayList.add(new ChildBBS(
                                        jsonObject.getString("content"),
                                        jsonObject.getString("user"),
                                        jsonObject.getString("time"),
                                        i
                                ));
                            }
                            RecyclerView recyclerView =  findViewById(R.id.recycler_view);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            ChildBBSAdapter roomAdapter = new ChildBBSAdapter(childBBSArrayList);
                            recyclerView.setAdapter(roomAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    class ChildBBSAdapter extends RecyclerView.Adapter<ChildBBSAdapter.ViewHolder> {
        private List<ChildBBS> childBBSList;
        class ViewHolder extends RecyclerView.ViewHolder {
            View view;//整个控件组
            TextView tv_content;
            TextView tv_info;
            ViewHolder(View view) {
                super(view);
                this.view = view;
                tv_content = view.findViewById(R.id.content);
                tv_info = view.findViewById(R.id.info);
            }
        }

        ChildBBSAdapter(List<ChildBBS> childBBSList) {
            this.childBBSList = childBBSList;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_bbs, parent, false);
            ChildBBSAdapter.ViewHolder holder = new ChildBBSAdapter.ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChildBBSAdapter.ViewHolder holder, int position) {
            ChildBBS childBBS = childBBSList.get(position);
            holder.tv_content.setText(childBBS.getContent());
            String info = childBBS.getUser()+" "+childBBS.getTime();
            if(position==0)
                info = info.concat(" 楼主");
            else{
                info = info.concat(" #"+position);
            }
            holder.tv_info.setText(info);
        }

        @Override
        public int getItemCount() {
            return childBBSList.size();
        }
    }
}
