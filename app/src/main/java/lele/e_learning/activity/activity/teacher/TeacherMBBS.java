package lele.e_learning.activity.activity.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lele.e_learning.R;
import lele.e_learning.activity.activity.BBS.ChildBBSPage;
import lele.e_learning.activity.activity.BBS.Notice;
import lele.e_learning.activity.activity.BBS.Post;
import lele.e_learning.activity.activity.Exam.Exam;
import lele.e_learning.activity.activity.Exam.MyScore;
import lele.e_learning.activity.activity.Exam.QuestionCollection;
import lele.e_learning.activity.activity.Learn.CheckNote;
import lele.e_learning.activity.activity.Learn.MediaLearnList;
import lele.e_learning.activity.activity.Learn.PhotoLearnList;
import lele.e_learning.activity.activity.Learn.TextLearnList;
import lele.e_learning.activity.activity.PointRank;
import lele.e_learning.activity.activity.PointToday;
import lele.e_learning.activity.activity.Vote.VoteList;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;
import lele.e_learning.activity.tools.Pack;

public class TeacherMBBS extends AppCompatActivity implements View.OnClickListener{

    TextView tvNextPage,tvLastPage,tvNowPage;
    LinearLayout[] ll_bbs;
    LinearLayout linearPost;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mbbs);
        tvNextPage = findViewById(R.id.tvNextPage);
        tvLastPage = findViewById(R.id.tvLastPage);
        tvNowPage = findViewById(R.id.tvNowPage);
        linearPost = findViewById(R.id.linearPost);
        ll_bbs = new LinearLayout[]{
                findViewById(R.id.ll_bbs_1),findViewById(R.id.ll_bbs_2),
                findViewById(R.id.ll_bbs_3),findViewById(R.id.ll_bbs_4),
                findViewById(R.id.ll_bbs_5),findViewById(R.id.ll_bbs_6),
                findViewById(R.id.ll_bbs_7),findViewById(R.id.ll_bbs_8),
                findViewById(R.id.ll_bbs_9),findViewById(R.id.ll_bbs_10)
        };
        for(final LinearLayout linearLayout:ll_bbs){
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv_content = (TextView)linearLayout.getChildAt(0);
                    TextView tv_id = (TextView)linearLayout.getChildAt(2);
                    Intent intent = new Intent(getApplicationContext(), ChildBBSPage.class);
                    intent.putExtra("content",tv_content.getText().toString());
                    intent.putExtra("id",tv_id.getText().toString());
                    startActivity(intent);
                }
            });
        }
        tvNextPage.setOnClickListener(this);
        tvLastPage.setOnClickListener(this);
        tvLastPage.setEnabled(false);
        linearPost.setOnClickListener(this);
        init_ui();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvNextPage:FreshPage(1);break;
            case R.id.tvLastPage:FreshPage(-1);break;
            case R.id.buttonNote:startActivity(new Intent(getApplicationContext(), CheckNote.class));break;
            case R.id.buttonPointRank:startActivity(new Intent(getApplicationContext(), PointRank.class));break;
            case R.id.buttonPointToday:startActivity(new Intent(getApplicationContext(), PointToday.class));break;
            case R.id.linearPost:startActivity(new Intent(getApplicationContext(), Post.class));break;
            default:break;
        }
    }

    void FreshPage(int i){
        page = page + i;
        tvLastPage.setEnabled(true);
        tvNextPage.setEnabled(true);
        if(page==0)
            tvLastPage.setEnabled(false);
        tvNowPage.setText("第"+(page+1)+"页");
        HttpUtil.sendHttpRequest("FreshPage?page="+page, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                //显示论坛主题帖
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < 10; i++) {
                                TextView tv_content = (TextView) ll_bbs[i].getChildAt(0);
                                TextView tv_info = (TextView) ll_bbs[i].getChildAt(1);
                                TextView tv_id = (TextView) ll_bbs[i].getChildAt(2);
                                if (i < jsonArray.length()) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    tv_content.setText(jsonObject.getString("content"));
                                    tv_info.setText(jsonObject.getString("user").concat("  ").concat(jsonObject.getString("time")));
                                    tv_id.setText(jsonObject.getString("id"));
                                } else {
                                    tvNextPage.setEnabled(false);
                                    tv_content.setText("");
                                    tv_info.setText("");
                                    tv_id.setText("");
                                }
                            }
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
    }
    void init_ui(){
        //获取文本目录
        HttpUtil.sendHttpRequest("GetHomeInfo", new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String textList = jsonObject.getString("textList");
                            String photoList = jsonObject.getString("photoList");
                            String mediaList = jsonObject.getString("mediaList");
                            String bbsList = jsonObject.getString("bbsList");
                            ShowList(new String[]{textList,photoList,mediaList,bbsList});
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

    public void ShowList(final String[] response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //显示论坛主题帖
                try{
                    JSONArray jsonArray = new JSONArray(response[3]);
                    for (int i = 0; i < 10; i++) {
                        TextView tv_content = (TextView)ll_bbs[i].getChildAt(0);
                        TextView tv_info = (TextView)ll_bbs[i].getChildAt(1);
                        TextView tv_id = (TextView)ll_bbs[i].getChildAt(2);
                        if(i<jsonArray.length()){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            tv_content.setText(jsonObject.getString("content"));
                            tv_info.setText(jsonObject.getString("user").concat("  ").concat(jsonObject.getString("time")));
                            tv_id.setText(jsonObject.getString("id"));
                        }
                        else{
                            tvNextPage.setEnabled(false);
                            tv_content.setText("");
                            tv_info.setText("");
                            tv_id.setText("");
                        }

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
