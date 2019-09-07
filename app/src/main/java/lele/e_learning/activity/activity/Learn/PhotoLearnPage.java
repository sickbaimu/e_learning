package lele.e_learning.activity.activity.Learn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lele.e_learning.R;
import lele.e_learning.activity.fragment.NoteFragment;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class PhotoLearnPage extends AppCompatActivity {

    private static ImageView img;
    private TextView head,text;
    Button buttonNote;
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
            buttonNote = findViewById(R.id.buttonNote);
            buttonNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteFragment myDialogFragment = new NoteFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "photo");
                    bundle.putString("title",getIntent().getStringExtra("head"));
                    myDialogFragment.setArguments(bundle);
                    myDialogFragment.show(getFragmentManager(), "Dialog");
                }
            });

            Intent intent = getIntent();
            int position = intent.getIntExtra("position",0);
            //img.setImageResource(TeacherPhotoLearnList.getIcon()[position]);
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
            HttpUtil.sendHttpRequest("GetPhotoDescription?photoName="+getIntent().getStringExtra("head")+"&userID="+ClientUser.getId(),new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        text.setText(jsonObject.getString("des"));
                    } catch (JSONException e) {
                    e.printStackTrace();
                }

                }
                @Override
                public void onError(Exception e) {

                }
            });
        }

}
