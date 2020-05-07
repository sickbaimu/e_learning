package lele.e_learning.activity.activity.Learn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lele.e_learning.R;
import lele.e_learning.activity.activity.BBS.Notice;
import lele.e_learning.activity.activity.BBS.Post;
import lele.e_learning.activity.activity.Exam.Exam;
import lele.e_learning.activity.activity.Exam.MyScore;
import lele.e_learning.activity.activity.Exam.QuestionCollection;
import lele.e_learning.activity.activity.PointRank;
import lele.e_learning.activity.activity.PointToday;
import lele.e_learning.activity.activity.Vote.VoteList;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;
import lele.e_learning.activity.tools.Pack;

public class LearningType extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_1,ll_2,ll_3;
    TextView tv_example_img_1,tv_example_img_2,tv_example_img_3;
    TextView tv_example_media_1,tv_example_media_2,tv_example_media_3;
    TextView tvTextRate,tvPhotoRate,tvMediaRate;
    String imgNames,mediaNames,rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_type);
        tv_example_img_1 = findViewById(R.id.tv_example_img_1);
        tv_example_img_2 = findViewById(R.id.tv_example_img_2);
        tv_example_img_3 = findViewById(R.id.tv_example_img_3);
        tv_example_media_1 = findViewById(R.id.tv_example_media_1);
        tv_example_media_2 = findViewById(R.id.tv_example_media_2);
        tv_example_media_3 = findViewById(R.id.tv_example_media_3);
        tvTextRate = findViewById(R.id.tvTextRate);
        tvPhotoRate = findViewById(R.id.tvPhotoRate);
        tvMediaRate = findViewById(R.id.tvMediaRate);
        ll_1 = findViewById(R.id.ll_1);
        ll_2 = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);

        //获取文本目录
        HttpUtil.sendHttpRequest("GetHomeInfo?userID="+ ClientUser.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String photoList = jsonObject.getString("photoList");
                            String mediaList = jsonObject.getString("mediaList");
                            String rate = jsonObject.getString("rate");
                            ShowList(new String[]{null,photoList,mediaList,null,rate});
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

    public void ShowList(final String[] response) {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                //显示图片章节名
                imgNames = response[1];
                String[] photoNames = response[1].split("-");
                if (photoNames.length > 0)
                    tv_example_img_1.setText(photoNames[0]);
                if (photoNames.length > 1)
                    tv_example_img_2.setText(photoNames[1]);
                if (photoNames.length > 2)
                    tv_example_img_3.setText(photoNames[2]);

                //显示视频章节名
                mediaNames = response[2];
                String[] mediaNames = response[2].split("-");
                if (mediaNames.length > 0)
                    tv_example_media_1.setText(mediaNames[0]);
                if (mediaNames.length > 1)
                    tv_example_media_2.setText(mediaNames[1]);
                if (mediaNames.length > 2)
                    tv_example_media_3.setText(mediaNames[2]);

                //显示学习进度
                rate = response[4];
                tvTextRate.setText("文本阅读 " + response[4].split(" ")[0]);
                tvPhotoRate.setText("经典图示 " + response[4].split(" ")[1]);
                tvMediaRate.setText("视频资源 " + response[4].split(" ")[2]);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_1:
                Intent intent0 = new Intent(getApplicationContext(), TextLearnList.class);
                intent0.putExtra("rate",rate.split(" ")[0]);
                startActivity(intent0);break;
            case R.id.ll_2:
                Intent intent1 = new Intent(getApplicationContext(), PhotoLearnList.class);
                intent1.putExtra("names",imgNames);
                intent1.putExtra("rate",rate.split(" ")[1]);
                startActivity(intent1);break;
            case R.id.ll_3:
                Intent intent2 = new Intent(getApplicationContext(), MediaLearnList.class);
                intent2.putExtra("names",mediaNames);
                intent2.putExtra("rate",rate.split(" ")[2]);
                startActivity(intent2);break;
            default:break;
        }
    }
}
