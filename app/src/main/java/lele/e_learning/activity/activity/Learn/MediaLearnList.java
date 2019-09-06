package lele.e_learning.activity.activity.Learn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.activity.teacher.TeacherMediaLearnPage;
import lele.e_learning.activity.fragment.MediaFragment;
import lele.e_learning.activity.fragment.QuestionFragment;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class MediaLearnList extends AppCompatActivity {
    static String[] names;
    TextView tvRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_learn_list);
        tvRate = findViewById(R.id.tvRate);
        tvRate.setText("你已经学习了"+getIntent().getStringExtra("rate"));
        names = getIntent().getStringExtra("names").split("-");
        RecyclerView recyclerView =  findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        MediaAdapter mediaAdapter = new MediaAdapter(getApplicationContext(),names);
        recyclerView.setAdapter(mediaAdapter);
    }

    class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {
        String[] names;
        Context context;

        class ViewHolder extends RecyclerView.ViewHolder{
            View view;//整个控件组
            ImageView imageView;//显示图片
            TextView textView;//显示文字信息
            ViewHolder(View view) {
                super(view);
                this.view = view;
                imageView = view.findViewById(R.id.img);
                textView = view.findViewById(R.id.text);
            }
        }

        @Override
        public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
            MediaAdapter.ViewHolder holder = new MediaAdapter.ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    CardView cardView = (CardView)view;
                    TextView textView = cardView.findViewById(R.id.text);
                    final String item = textView.getText().toString();
                    HttpUtil.sendHttpRequest("GetMediaPath?name=" + item+"&userID="+ ClientUser.getId(), new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            Intent intent = new Intent(getApplicationContext(), TeacherMediaLearnPage.class);
                            intent.putExtra("item",response);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });



                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(MediaAdapter.ViewHolder holder, int position){
            holder.textView.setText(names[position]);
            holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.example_cv));
        }
        //构造方法
        public MediaAdapter(Context context, String[] names) {
            this.context = context;
            this.names = names;
        }

        @Override
        public int getItemCount(){
            return names.length;
        }

        //获得当前选项id
        public long getItemId(int id) {
            return id;
        }
    }
}
