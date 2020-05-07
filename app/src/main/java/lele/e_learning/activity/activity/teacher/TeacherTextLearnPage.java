package lele.e_learning.activity.activity.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import lele.e_learning.R;
import lele.e_learning.activity.activity.Learn.TextLearnList;
import lele.e_learning.activity.activity.Learn.TextLearnPage;
import lele.e_learning.activity.fragment.NoteFragment;
import lele.e_learning.activity.fragment.TextFragment;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;
import lele.e_learning.activity.tools.Pack;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class TeacherTextLearnPage extends AppCompatActivity implements View.OnClickListener{

    TextView tv_content,tv_chapter,tv_section;
    Button b_last_page,b_next_page,b_back,buttonEdit;
    String chapter_id,section_order,name,content,id;
    int chapter_size = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_text_learn_page);
        tv_content = findViewById(R.id.tv_content);
        tv_chapter = findViewById(R.id.tv_chapter);
        tv_section = findViewById(R.id.tv_section);
        b_last_page = findViewById(R.id.b_last_page);
        b_next_page = findViewById(R.id.b_next_page);
        b_back = findViewById(R.id.b_back);
        buttonEdit = findViewById(R.id.buttonEdit);

        chapter_id = getIntent().getStringExtra("chapter_id");
        section_order = getIntent().getStringExtra("section_order");
        tv_chapter.setText("第"+chapter_id+"章");
        tv_section.setText("第"+section_order+"节");

        HttpUtil.sendHttpRequest("GetTextContent?" + "chapter_id=" + chapter_id+"&&section_order="+section_order, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        content = response;
                        tv_content.setText(response);
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });


        b_last_page.setOnClickListener(this);
        b_next_page.setOnClickListener(this);
        b_back.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);

    }

    public void onClick(View v){
        Intent intent = new Intent(getApplicationContext(), TextLearnPage.class);
        switch (v.getId()){
            case R.id.b_last_page://上一页
                if(Integer.parseInt(section_order)==1)
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
                int order = -1;
                switch (chapter_id){
                    case "1":order = 23;break;
                    case "2":order = 15;break;
                    case "3":order = 39;break;
                    case "4":order = 38;break;
                    case "5":order = 10;break;
                    case "6":order = 22;break;
                    default:break;
                }
                if(Integer.parseInt(section_order)==order)
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
                startActivity(new Intent(getApplicationContext(), TextLearnList.class));
                overridePendingTransition(0,0);
                finish();
                break;

            case R.id.buttonEdit:
                TextFragment myDialogFragment = new TextFragment();
                Bundle bundle = new Bundle();
                bundle.putString("chapterID", chapter_id);
                bundle.putString("sectionOrder",section_order);
                bundle.putString("content",content);
                myDialogFragment.setArguments(bundle);
                myDialogFragment.show(getFragmentManager(), "Dialog");
                break;
            default:break;
        }
    }
}
