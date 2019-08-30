package lele.e_learning.activity.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import lele.e_learning.activity.activity.Exam.QuestionCollection;
import lele.e_learning.activity.activity.Learn.MediaLearnList;
import lele.e_learning.activity.activity.Learn.PhotoLearnList;
import lele.e_learning.activity.activity.Learn.TextLearnList;
import lele.e_learning.activity.activity.Vote.VoteList;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;
import lele.e_learning.activity.tools.Pack;

public class Home extends Activity implements View.OnClickListener {

    LinearLayout module_learn,module_bbs,module_exam,ll_1,ll_2,ll_3;
    LinearLayout linearNotice,linearPost,linearVote;
    Button switch_learn,switch_bbs,switch_exam;
    Button buttonOrderExam,buttonRandomExam,buttonFinalExam,buttonMyScore,buttonCollection;
    Button buttonNote;
    TextView tv_example_text_1,tv_example_text_2,tv_example_text_3;
    TextView tv_example_img_1,tv_example_img_2,tv_example_img_3;
    TextView tv_example_media_1,tv_example_media_2,tv_example_media_3;
    TextView tvNextPage,tvLastPage,tvNowPage;
    LinearLayout[] ll_bbs;
    String imgNames,mediaNames;
    final static int model_learn = 1;
    final static int model_bbs = 2;
    final static int model_exam = 3;
    static int MODEL = model_bbs;
    int page = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        fresh_model();
    }
    void init(){
        init_ui();
        //绑定控件和对象
        module_learn = findViewById(R.id.module_learn);
        module_bbs = findViewById(R.id.module_bbs);
        module_exam = findViewById(R.id.module_exam);

        switch_learn = findViewById(R.id.switch_learn);
        switch_bbs = findViewById(R.id.switch_bbs);
        switch_exam = findViewById(R.id.switch_exam);

        tv_example_text_1 = findViewById(R.id.tv_example_text_1);
        tv_example_text_2 = findViewById(R.id.tv_example_text_2);
        tv_example_text_3 = findViewById(R.id.tv_example_text_3);
        tv_example_img_1 = findViewById(R.id.tv_example_img_1);
        tv_example_img_2 = findViewById(R.id.tv_example_img_2);
        tv_example_img_3 = findViewById(R.id.tv_example_img_3);
        tv_example_media_1 = findViewById(R.id.tv_example_media_1);
        tv_example_media_2 = findViewById(R.id.tv_example_media_2);
        tv_example_media_3 = findViewById(R.id.tv_example_media_3);

        ll_1 = findViewById(R.id.ll_1);
        ll_2 = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);

        linearNotice = findViewById(R.id.linearNotice);
        linearPost = findViewById(R.id.linearPost);
        linearVote = findViewById(R.id.linearVote);

        tvNextPage = findViewById(R.id.tvNextPage);
        tvLastPage = findViewById(R.id.tvLastPage);
        tvNowPage = findViewById(R.id.tvNowPage);

        buttonOrderExam = findViewById(R.id.buttonOrderExam);
        buttonRandomExam = findViewById(R.id.buttonRandomExam);
        buttonFinalExam = findViewById(R.id.buttonFinalExam);
        buttonMyScore = findViewById(R.id.buttonMyScore);
        buttonCollection = findViewById(R.id.buttonCollection);

        buttonNote = findViewById(R.id.buttonNote);
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

        //设置监听函数
        switch_learn.setOnClickListener(this);
        switch_bbs.setOnClickListener(this);
        switch_exam.setOnClickListener(this);

        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);

        linearNotice.setOnClickListener(this);
        linearPost.setOnClickListener(this);
        linearVote.setOnClickListener(this);

        tvNextPage.setOnClickListener(this);
        tvLastPage.setOnClickListener(this);
        tvLastPage.setEnabled(false);

        buttonCollection.setOnClickListener(this);
        buttonMyScore.setOnClickListener(this);
        buttonOrderExam.setOnClickListener(this);
        buttonFinalExam.setOnClickListener(this);
        buttonRandomExam.setOnClickListener(this);

        buttonNote.setOnClickListener(this);
        MODEL = model_learn;
    }

    void fresh_model(){
        module_learn.setVisibility(View.GONE);
        module_bbs.setVisibility(View.GONE);
        module_exam.setVisibility(View.GONE);
        switch_learn.setTextColor(getResources().getColor(R.color.White));
        switch_learn.setBackgroundColor(getResources().getColor(R.color.DeepBlue));
        switch_bbs.setTextColor(getResources().getColor(R.color.White));
        switch_bbs.setBackgroundColor(getResources().getColor(R.color.DeepBlue));
        switch_exam.setTextColor(getResources().getColor(R.color.White));
        switch_exam.setBackgroundColor(getResources().getColor(R.color.DeepBlue));
        switch (MODEL){
            case model_learn:
                module_learn.setVisibility(View.VISIBLE);
                switch_learn.setTextColor(getResources().getColor(R.color.Black));
                switch_learn.setBackgroundColor(getResources().getColor(R.color.White));
                break;
            case model_bbs:
                module_bbs.setVisibility(View.VISIBLE);
                switch_bbs.setTextColor(getResources().getColor(R.color.Black));
                switch_bbs.setBackgroundColor(getResources().getColor(R.color.White));
                break;
            case model_exam:
                module_exam.setVisibility(View.VISIBLE);
                switch_exam.setTextColor(getResources().getColor(R.color.Black));
                switch_exam.setBackgroundColor(getResources().getColor(R.color.White));
                break;
            default:break;
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.switch_learn:MODEL = model_learn;fresh_model();break;
            case R.id.switch_bbs:MODEL = model_bbs;fresh_model();break;
            case R.id.switch_exam:MODEL = model_exam;fresh_model();break;
            case R.id.ll_1:startActivity(new Intent(getApplicationContext(), TextLearnList.class));break;
            case R.id.ll_2:
                Intent intent = new Intent(getApplicationContext(), PhotoLearnList.class);
                intent.putExtra("names",imgNames);
                //Toast.makeText(getApplicationContext(),names,Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;
            case R.id.ll_3:
                Intent intent2 = new Intent(getApplicationContext(), MediaLearnList.class);
                intent2.putExtra("names",mediaNames);
                //Toast.makeText(getApplicationContext(),names,Toast.LENGTH_LONG).show();
                startActivity(intent2);
                break;
            case R.id.linearNotice:startActivity(new Intent(getApplicationContext(), Notice.class));break;
            case R.id.linearPost:startActivity(new Intent(getApplicationContext(), Post.class));break;
            case R.id.linearVote:startActivity(new Intent(getApplicationContext(), VoteList.class));break;
            case R.id.tvNextPage:FreshPage(1);break;
            case R.id.tvLastPage:FreshPage(-1);break;
            case R.id.buttonOrderExam:Intent intent1 = new Intent(getApplicationContext(), Exam.class);intent1.putExtra("type","Order");startActivity(intent1);break;
            case R.id.buttonRandomExam:Intent intent3 = new Intent(getApplicationContext(), Exam.class);intent3.putExtra("type","Random");startActivity(intent3);break;
            case R.id.buttonFinalExam:Intent intent4 = new Intent(getApplicationContext(), Exam.class);intent4.putExtra("type","Final");startActivity(intent4);break;
            case R.id.buttonCollection:startActivity(new Intent(getApplicationContext(),QuestionCollection.class));
            case R.id.buttonNote:break;
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
                //显示文本章节名
                String[] textNames = response[0].split("-");
                if(textNames.length>0)
                    tv_example_text_1.setText(Pack.pack("1",textNames[0],"章"));
                if(textNames.length>1)
                    tv_example_text_2.setText(Pack.pack("2",textNames[1],"章"));
                if(textNames.length>2)
                    tv_example_text_3.setText(Pack.pack("3",textNames[2],"章"));

                //显示图片章节名
                imgNames = response[1];
                String[] photoNames = response[1].split("-");
                if(photoNames.length>0)
                    tv_example_img_1.setText(photoNames[0]);
                if(photoNames.length>1)
                    tv_example_img_2.setText(photoNames[1]);
                if(photoNames.length>2)
                    tv_example_img_3.setText(photoNames[2]);

                //显示视频章节名
                mediaNames = response[2];
                String[] mediaNames = response[2].split("-");
                if(mediaNames.length>0)
                    tv_example_media_1.setText(mediaNames[0]);
                if(mediaNames.length>1)
                    tv_example_media_2.setText(mediaNames[1]);
                if(mediaNames.length>2)
                    tv_example_media_3.setText(mediaNames[2]);

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
