package lele.e_learning.activity.activity.Learn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lele.e_learning.R;
import lele.e_learning.activity.activity.Exam.QuestionCollection;
import lele.e_learning.activity.activity.Learn.PhotoLearnPage;
import lele.e_learning.activity.activity.Learn.TextLearnPage;
import lele.e_learning.activity.entity.Note;
import lele.e_learning.activity.entity.Question;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.HttpUtil.getIp_address;
import static lele.e_learning.activity.tools.Pack.ShowToast;

public class CheckNote extends AppCompatActivity {

    ArrayList<Note> noteArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_note);
        noteArrayList = new ArrayList<>();
        HttpUtil.sendHttpRequest("GetNotes?userID=" + ClientUser.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                noteArrayList.add(new Note(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("userID"),
                                        jsonObject.getString("shape"),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("tag"),
                                        jsonObject.getString("content")

                                ));
                            }
                            RecyclerView recyclerView =  findViewById(R.id.recycler_view);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            NoteAdapter roomAdapter = new NoteAdapter(noteArrayList);
                            recyclerView.setAdapter(roomAdapter);

                        }catch (JSONException e) {
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


    class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
        private List<Note> noteList;
        class ViewHolder extends RecyclerView.ViewHolder {
            View view;//整个控件组
            TextView tvContent,tvShape,tvTAG,tvAddress;
            ViewHolder(View view) {
                super(view);
                this.view = view;
                tvContent = view.findViewById(R.id.tvContent);
                tvAddress = view.findViewById(R.id.tvAddress);
                tvShape = view.findViewById(R.id.tvShape);
                tvTAG = view.findViewById(R.id.tvTAG);
            }
        }

        NoteAdapter(List<Note> noteList) {
            this.noteList = noteList;
        }

        @Override
        @NonNull
        public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
            final NoteAdapter.ViewHolder holder = new NoteAdapter.ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.tvShape.getText().toString().equals("photo")){
                        toPhoto("photo/"+holder.tvAddress.getText().toString()+".jpg",holder);
                    }else if(holder.tvShape.getText().toString().equals("text")) {
                        //ShowToast(getApplicationContext(),holder.tvAddress.getText().toString());
                        toText(holder.tvAddress.getText().toString());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
            Note note = noteList.get(position);
            holder.tvContent.setText(note.getContent());
            holder.tvTAG.setText(note.getTag());
            holder.tvShape.setText(note.getShape());
            holder.tvAddress.setText(note.getTitle());
        }

        @Override
        public int getItemCount() {
            return noteList.size();
        }

        public void toText(String address){

            Intent intent = new Intent(getApplicationContext(), TextLearnPage.class);
            intent.putExtra("chapter_id",address.split("\\.")[0]);
            intent.putExtra("section_order",address.split("\\.")[1]);
            startActivity(intent);

        }
        public void toPhoto(final String path,final NoteAdapter.ViewHolder holder) {
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
                            Bitmap bitmap =  BitmapFactory.decodeStream(inputStream);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] bytes=baos.toByteArray();
                            Bundle b = new Bundle();
                            b.putByteArray("bitmap", bytes);
                            Intent intent = new Intent(getApplicationContext(), PhotoLearnPage.class);
                            intent.putExtra("head",holder.tvAddress.getText().toString());
                            intent.putExtras(b);

                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }





}
