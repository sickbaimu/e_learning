package lele.e_learning.activity.activity.Learn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import lele.e_learning.R;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;
import lele.e_learning.activity.tools.Pack;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class TextLearnPage extends Activity implements View.OnClickListener{

    TextView tv_content,tv_chapter,tv_section;
    Button b_last_page,b_next_page,b_back;
    String chapter_id,section_order;
    int chapter_size = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_learn_page);
        tv_content = findViewById(R.id.tv_content);
        tv_chapter = findViewById(R.id.tv_chapter);
        tv_section = findViewById(R.id.tv_section);
        b_last_page = findViewById(R.id.b_last_page);
        b_next_page = findViewById(R.id.b_next_page);
        b_back = findViewById(R.id.b_back);
        chapter_id = getIntent().getStringExtra("chapter_id");
        section_order = getIntent().getStringExtra("section_order");
        HttpUtil.sendHttpRequest("GetTextContent?" + "chapter_id=" + chapter_id+"&&section_order="+section_order, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            tv_chapter.setText(Pack.pack(jsonObject.getString("chapter_order"),jsonObject.getString("chapter_name"),"章"));
                            tv_section.setText(Pack.pack(jsonObject.getString("section_order"),jsonObject.getString("section_name"),"节"));
                            tv_content.setText(jsonObject.getString("content"));
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

        HttpUtil.sendHttpRequest("GetChapterSize?chapter_id=" + chapter_id, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                chapter_size = Integer.valueOf(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        b_last_page.setOnClickListener(this);
        b_next_page.setOnClickListener(this);
        b_back.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent intent = new Intent(getApplicationContext(),TextLearnPage.class);
        switch (v.getId()){
            case R.id.b_last_page://上一页
                if(Integer.valueOf(section_order)==1)
                {
                    ShowToast(getApplicationContext(),"已至章首，请返回目录。");
                    break;
                }
                intent.putExtra("section_order",String.valueOf(Integer.valueOf(section_order)-1));
                intent.putExtra("chapter_id",chapter_id);startActivity(intent);
                overridePendingTransition(0,0);
                finish();
                break;
            case R.id.b_next_page://下一页
                if(Integer.valueOf(section_order)==chapter_size)
                {
                    ShowToast(getApplicationContext(),"已至章尾，请返回目录。");
                    break;
                }
                intent.putExtra("section_order",String.valueOf(Integer.valueOf(section_order)+1));
                intent.putExtra("chapter_id",chapter_id);startActivity(intent);
                overridePendingTransition(0,0);
                finish();
                break;
            case R.id.b_back://返回目录
                startActivity(new Intent(getApplicationContext(),TextLearnList.class));
                overridePendingTransition(0,0);
                finish();
                break;
            default:break;
        }
    }

}
