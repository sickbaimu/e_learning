package lele.e_learning.activity.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lele.e_learning.R;
import lele.e_learning.activity.activity.Exam.QuestionCollection;
import lele.e_learning.activity.activity.Learn.PhotoLearnPage;
import lele.e_learning.activity.entity.Note;
import lele.e_learning.activity.entity.Question;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

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
                        Intent intent = new Intent(getApplicationContext(), PhotoLearnPage.class);
                        intent.putExtra("head",holder.tvAddress.getText().toString());
                        startActivity(intent);
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
    }
}
