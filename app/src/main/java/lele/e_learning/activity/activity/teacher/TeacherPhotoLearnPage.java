package lele.e_learning.activity.activity.teacher;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import lele.e_learning.R;
import lele.e_learning.activity.activity.Vote.VoteUpload;
import lele.e_learning.activity.fragment.NoteFragment;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.activity.Vote.VoteUpload.bitmapToBase64;
import static lele.e_learning.activity.tools.Pack.ShowToast;

public class TeacherPhotoLearnPage extends AppCompatActivity {

    private ImageView img;
    String id;
    private EditText editHead,editOrder, editDes;
    private Button buttonAdd, buttonUpdate, buttonDelete, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_photo_learn_page);
        initView();
    }

    private void initView() {
        img = findViewById(R.id.img);
        editHead = findViewById(R.id.editHead);
        editOrder = findViewById(R.id.editOrder);
        editDes = findViewById(R.id.editDes);

        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDelete = findViewById(R.id.buttonDelete);

        Intent intent = getIntent();
        //主要的语句---将当前Activity的View和自己定义的Key绑定起来
        ViewCompat.setTransitionName(img, "shared_elements");
        Bundle b = intent.getExtras();
        byte[] bytes = b.getByteArray("bitmap");
        if (bytes == null)
            return;
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img.setImageBitmap(bmp);
        editHead.setText(getIntent().getStringExtra("head"));
        HttpUtil.sendHttpRequest("GetPhotoDescription?photoName=" + getIntent().getStringExtra("head") + "&userID=" + ClientUser.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            id = jsonObject.getString("id");
                            editOrder.setText(jsonObject.getString("order"));
                            editDes.setText(jsonObject.getString("des"));

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
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TeacherPhotoLearnPage.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });

        /*
         * 修改按钮响应函数
         */
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpUtil.sendHttpRequest("UpdatePhoto?id=" + id + "&order=" + editOrder.getText().toString() + "&name=" + editHead.getText().toString()
                        + "&des=" + editDes.getText().toString()+
                        "&base64=" + bitmapToBase64(((BitmapDrawable) img.getDrawable()).getBitmap()), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getApplicationContext(), "修改成功");
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getApplicationContext(), "修改失败");
                    }
                });

            }
        });
        /*
         * 新增按钮响应函数
         */
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpUtil.sendHttpRequest("AddPhoto?order=" + editOrder.getText().toString() + "&name=" + editHead.getText().toString()
                        + "&des=" + editDes.getText().toString()+"&base64=" + bitmapToBase64(((BitmapDrawable) img.getDrawable()).getBitmap()), new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getApplicationContext(), "添加成功");
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getApplicationContext(), "添加失败");
                    }
                });

            }
        });
        /*
         * 删除按钮响应函数
         */
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.sendHttpRequest("DeletePhoto?id=" + id, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        ShowToast(getApplicationContext(), "删除成功");
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        ShowToast(getApplicationContext(), "删除失败");
                    }
                });

            }
        });
        /*
         * 取消按钮响应函数
         */
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 1); // 打开相册
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            openAlbum();
        else
            Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 判断手机系统版本号
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4及以上系统使用这个方法处理图片
                handleImageOnKitKat(data);
            } else {
                // 4.4以下系统使用这个方法处理图片
                handleImageBeforeKitKat(data);
            }
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            img.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
