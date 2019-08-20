package lele.e_learning.activity.activity.Vote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lele.e_learning.R;
import lele.e_learning.activity.activity.BBS.ChildBBSPage;
import lele.e_learning.activity.entity.ChildBBS;
import lele.e_learning.activity.entity.RankRecord;
import lele.e_learning.activity.entity.WorkCommit;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class VotePage extends AppCompatActivity {

    private static ImageView img;
    private TextView head;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_page);
            initView();
        }
        private void initView(){
            img = findViewById(R.id.img);
            head = findViewById(R.id.head);
            Intent intent = getIntent();
            id = intent.getStringExtra("id");

            //主要的语句---将当前Activity的View和自己定义的Key绑定起来
            ViewCompat.setTransitionName(img, "shared_elements");
            Bundle b=intent.getExtras();
            byte[] bytes=b.getByteArray("bitmap");
            if(bytes==null)
                return;
            Bitmap bmp=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            img.setImageBitmap(bmp);
            head.setText(getIntent().getStringExtra("head"));

            HttpUtil.sendHttpRequest("GetCommit?workID="+getIntent().getStringExtra("id"), new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<WorkCommit> workCommits = new ArrayList<>();

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    workCommits.add(new WorkCommit(
                                            jsonObject.getString("userName"),
                                            jsonObject.getString("commit")
                                    ));
                                }

                                RecyclerView recyclerView = findViewById(R.id.recycler_view);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(linearLayoutManager);

                                WorkCommitAdapter workCommitAdapter = new WorkCommitAdapter(workCommits);
                                recyclerView.setAdapter(workCommitAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

                @Override
                public void onError(Exception e) {

                }
            });

            /*评论功能*/
            Button buttonCommit = findViewById(R.id.buttonCommit);
            buttonCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editCommit = findViewById(R.id.editCommit);
                    HttpUtil.sendHttpRequest("VoteCommit?workID="+id+"&userID=" + ClientUser.getId() + "&commit="+editCommit.getText().toString(), new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            if(response.equals("0"))
                                ShowToast(getApplicationContext(),"评论成功！");
                            else
                                ShowToast(getApplicationContext(),"评论失败！");
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
            });
            /*投票功能*/
            Button buttonVote = findViewById(R.id.buttonVote);
            buttonVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpUtil.sendHttpRequest("Vote?from=" + ClientUser.getId() + "&to="+id, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            if(response.equals("0"))
                                ShowToast(getApplicationContext(),"投票成功！");
                            else if(response.equals("-1"))
                                ShowToast(getApplicationContext(),"您已为该作品投过票！");
                            else
                                ShowToast(getApplicationContext(),"投票失败！");

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
            });

        }

    class WorkCommitAdapter extends RecyclerView.Adapter<WorkCommitAdapter.ViewHolder> {
        private List<WorkCommit> workCommitList;
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

        WorkCommitAdapter(List<WorkCommit> workCommitList) {
            this.workCommitList = workCommitList;
        }

        @Override
        @NonNull
        public WorkCommitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_bbs, parent, false);
            WorkCommitAdapter.ViewHolder holder = new WorkCommitAdapter.ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull WorkCommitAdapter.ViewHolder holder, int position) {
            WorkCommit workCommit = workCommitList.get(position);
            holder.tv_content.setText(workCommit.getCommit());
            String info = workCommit.getUserName();
            holder.tv_info.setText(info);
        }

        @Override
        public int getItemCount() {
            return workCommitList.size();
        }
    }

}
