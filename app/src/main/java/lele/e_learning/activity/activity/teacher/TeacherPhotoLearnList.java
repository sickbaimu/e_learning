package lele.e_learning.activity.activity.teacher;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lele.e_learning.R;

import static lele.e_learning.activity.tools.HttpUtil.getIp_address;

public class TeacherPhotoLearnList extends AppCompatActivity {
    private GridView gridView;
    MyAdapter myAdapter;
    static String[] names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_photo_learn_list);
        names = getIntent().getStringExtra("names").split("-");
        gridView = findViewById(R.id.grid_view);
        //配置适配器
        myAdapter = new MyAdapter(getApplicationContext(),names);
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置被点击的View的共享元素过渡名
                Intent intent = new Intent(getApplicationContext(), TeacherPhotoLearnPage.class);
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
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TeacherPhotoLearnList.this,view,"shared_elements").toBundle());
            }
        });
        //ShowToast(getApplicationContext(),""+gridView.getAdapter().getItem(0));
    }

    class MyAdapter extends BaseAdapter {
        String[] names;
        Context context;
        //构造方法
        public MyAdapter(Context context, String[] names) {
            this.context = context;
            this.names = names;
        }

        //获得数量
        public int getCount() {
            return names.length;
        }

        //获得当前选项
        public Object getItem(int item) {
            return item;
        }

        //获得当前选项id
        public long getItemId(int id) {
            return id;
        }

        //创建View方法
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout linearLayout;
            if (convertView == null) {
                linearLayout = (LinearLayout) View.inflate(context, R.layout.item, null);
                ImageView img = (ImageView) linearLayout.getChildAt(0);
                setImage("photo/"+names[position]+".jpg", img);
                TextView textView = (TextView)linearLayout.getChildAt(1);
                textView.setText(names[position]);

            } else {
                linearLayout = (LinearLayout) convertView;
            }
            //为ImageView设置图片资源
            return linearLayout;
        }
    }

    public void setImage(final String path,final ImageView imageView) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(getIp_address() + path).openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
