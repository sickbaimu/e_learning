package lele.e_learning.activity.activity.teacher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import lele.e_learning.R;
import lele.e_learning.activity.fragment.NoteFragment;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class TeacherPhotoLearnPage extends AppCompatActivity {

    private static ImageView img;
    private EditText editDes;
    private TextView tvHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_photo_learn_page);
            initView();
        }
        private void initView(){
            img = findViewById(R.id.img);
            tvHead = findViewById(R.id.tvHead);
            editDes = findViewById(R.id.editDes);
            Intent intent = getIntent();
            //主要的语句---将当前Activity的View和自己定义的Key绑定起来
            ViewCompat.setTransitionName(img, "shared_elements");
            Bundle b=intent.getExtras();
            byte[] bytes=b.getByteArray("bitmap");
            if(bytes==null)
                return;
            Bitmap bmp=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            img.setImageBitmap(bmp);
            tvHead.setText(getIntent().getStringExtra("head"));
            HttpUtil.sendHttpRequest("GetPhotoDescription?photoName="+getIntent().getStringExtra("head")+"&userID="+ClientUser.getId(),new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    editDes.setText(response);
                }
                @Override
                public void onError(Exception e) {

                }
            });
        }

}
