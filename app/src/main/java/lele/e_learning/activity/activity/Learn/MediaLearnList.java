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

public class MediaLearnList extends AppCompatActivity {
    private GridView gridView;
    MediaAdapter myAdapter;
    static String[] names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_learn_list);
        names = getIntent().getStringExtra("names").split("-");
        RecyclerView recyclerView =  findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        MediaAdapter mediaAdapter = new MediaAdapter(getApplicationContext(),names);
        recyclerView.setAdapter(mediaAdapter);
        //配置适配器

        /*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置被点击的View的共享元素过渡名
                Intent intent = new Intent(getApplicationContext(),PhotoLearnPage.class);
                intent.putExtra("position",position);
                LinearLayout linearLayout = (LinearLayout)view;
                ImageView imageView = (ImageView)linearLayout.getChildAt(0);
                imageView.setTransitionName("shared_elements");

                TextView textView = (TextView)linearLayout.getChildAt(1);
                intent.putExtra("head",textView.getText().toString());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ((BitmapDrawable)imageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytes=baos.toByteArray();
                Bundle b = new Bundle();
                b.putByteArray("bitmap", bytes);
                intent.putExtras(b);
                //以"shared_elements"为名的过渡动画打开新的Activity
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MediaLearnList.this,view,"shared_elements").toBundle());
            }
        });
        */
        //ShowToast(getApplicationContext(),""+gridView.getAdapter().getItem(0));
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
                    String item = textView.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), MediaLearnPage.class);
                    intent.putExtra("item","https://v.youku.com/v_show/id_XNTA4ODE5MTQ4.html?spm=a2h0k.11417342.soresults.dtitle");
                    startActivity(intent);
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
