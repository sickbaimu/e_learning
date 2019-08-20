package lele.e_learning.activity.activity.Learn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lele.e_learning.R;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class PhotoLearnPage extends AppCompatActivity {

    private static ImageView img;
    private TextView head,text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_learn_page);
            initView();
        }
        private void initView(){
            img = findViewById(R.id.img);
            text = findViewById(R.id.text);
            head = findViewById(R.id.head);
            Intent intent = getIntent();
            int position = intent.getIntExtra("position",0);
            //img.setImageResource(PhotoLearnList.getIcon()[position]);
            text.setText("描述文字：");

            //主要的语句---将当前Activity的View和自己定义的Key绑定起来
            ViewCompat.setTransitionName(img, "shared_elements");
            Bundle b=intent.getExtras();
            byte[] bytes=b.getByteArray("bitmap");
            if(bytes==null)
                return;
            Bitmap bmp=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            img.setImageBitmap(bmp);


            head.setText(getIntent().getStringExtra("head"));
            HttpUtil.sendHttpRequest("GetPhotoDescription?photoName="+getIntent().getStringExtra("head"),new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    text.setText(response);
                }
                @Override
                public void onError(Exception e) {

                }
            });


            //getImage("http://192.168.1.17:8080/photo/"+PhotoLearnList.names[position]+".jpg");
        }

    public static void getImage(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
