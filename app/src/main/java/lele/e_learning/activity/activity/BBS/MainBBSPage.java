package lele.e_learning.activity.activity.BBS;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lele.e_learning.R;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class MainBBSPage extends Activity implements View.OnClickListener {

    LinearLayout[] ll_bbs;
    TextView tvNextPage,tvLastPage,tvNowPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b_b_s_page);

        ll_bbs = new LinearLayout[]{
                findViewById(R.id.ll_bbs_1),findViewById(R.id.ll_bbs_2),
                findViewById(R.id.ll_bbs_3),findViewById(R.id.ll_bbs_4),
                findViewById(R.id.ll_bbs_5),findViewById(R.id.ll_bbs_6),
                findViewById(R.id.ll_bbs_7),findViewById(R.id.ll_bbs_8),
                findViewById(R.id.ll_bbs_9),findViewById(R.id.ll_bbs_10)
        };
        tvNextPage = findViewById(R.id.tvNextPage);
        tvLastPage = findViewById(R.id.tvLastPage);
        tvNowPage = findViewById(R.id.tvNowPage);
        tvNextPage.setOnClickListener(this);
        tvLastPage.setOnClickListener(this);
        tvLastPage.setEnabled(false);

        TextView post = findViewById(R.id.linearPost);
        post.setOnClickListener(this);

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
        FreshPage(0);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.linearPost:startActivity(new Intent(getApplicationContext(), Post.class));break;
            case R.id.tvNextPage:FreshPage(1);break;
            case R.id.tvLastPage:FreshPage(-1);break;
            default:break;
        }
    }

    int page = 0;
    void FreshPage(int i) {
        page = page + i;
        tvLastPage.setEnabled(true);
        tvNextPage.setEnabled(true);
        if (page == 0)
            tvLastPage.setEnabled(false);
        tvNowPage.setText("第" + (page + 1) + "页");
        HttpUtil.sendHttpRequest("FreshPage?page=" + page, new HttpCallbackListener() {
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
}
