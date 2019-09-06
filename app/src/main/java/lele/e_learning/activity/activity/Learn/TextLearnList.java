package lele.e_learning.activity.activity.Learn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import lele.e_learning.R;
import lele.e_learning.activity.entity.TextSection;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;
import lele.e_learning.activity.tools.Pack;


public class TextLearnList extends Activity {

    LinearLayout layout_content;
    String chapter_name,chapter_order;
    TextView textView;
    TextView tvRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_learn_list);
        tvRate = findViewById(R.id.tvRate);
        tvRate.setText("你已经学习了"+getIntent().getStringExtra("rate"));
        layout_content = findViewById(R.id.layout_content);
        HttpUtil.sendHttpRequest("GetTextList",new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            LinearLayout linearLayout;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String s = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(s);
                                chapter_name = jsonObject.getString("name");
                                chapter_order = jsonObject.getString("order");
                                linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_text_list_chapter, layout_content, false);
                                textView = (TextView) linearLayout.getChildAt(0);
                                textView.setText(Pack.pack(chapter_order, chapter_name, "章"));
                                layout_content.addView(linearLayout, i);
                                JSONArray jsonArray1 = new JSONArray(jsonObject.getString("textSections"));
                                ArrayList<TextSection> list = new ArrayList<>();
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    String s1 = jsonArray1.getString(j);
                                    JSONObject jsonObject1 = new JSONObject(s1);
                                    list.add(new TextSection(
                                            jsonObject1.getString("id"),
                                            jsonObject1.getString("order"),
                                            jsonObject1.getString("chapter_id"),
                                            jsonObject1.getString("name"))
                                    );
                                }
                                RecyclerView recyclerView = (RecyclerView) linearLayout.getChildAt(1);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(linearLayoutManager);
                                SectionAdapter sectionAdapter = new SectionAdapter(list);
                                recyclerView.setAdapter(sectionAdapter);
                                recyclerView.setMinimumHeight((int)(list.size()*(16*getResources().getDisplayMetrics().densityDpi*1.33+10*getResources().getDisplayMetrics().densityDpi))/160);
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

    class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder>{
        private List<TextSection> list;
        class ViewHolder extends RecyclerView.ViewHolder{
            View view;//整个控件组
            TextView textView;//显示文字信息
            TextView chapter_id,section_order;
            ViewHolder(View view) {
                super(view);
                this.view = view;
                textView = view.findViewById(R.id.text);
                chapter_id = view.findViewById(R.id.chapter_id);
                section_order = view.findViewById(R.id.section_order);
            }
        }

        SectionAdapter(List<TextSection> list)
        {
            this.list = list;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_list_section,parent,false);
            SectionAdapter.ViewHolder holder = new ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    LinearLayout linearLayout = (LinearLayout) view;
                    TextView chapter_id = linearLayout.findViewById(R.id.chapter_id);
                    TextView section_order = linearLayout.findViewById(R.id.section_order);
                    Intent intent = new Intent(getApplicationContext(),TextLearnPage.class);
                    intent.putExtra("chapter_id",chapter_id.getText());
                    intent.putExtra("section_order",section_order.getText());
                    startActivity(intent);
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position){
            TextSection section = list.get(position);
            holder.textView.setText(Pack.pack(section.getOrder(),section.getName(),"节"));
            holder.chapter_id.setText(section.getChapter_id());
            holder.section_order.setText(section.getOrder());
        }

        @Override
        public int getItemCount(){
            return list.size();
        }
    }
}
