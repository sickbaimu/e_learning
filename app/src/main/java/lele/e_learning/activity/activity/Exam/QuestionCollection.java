package lele.e_learning.activity.activity.Exam;

import android.icu.text.IDNA;
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
import lele.e_learning.activity.entity.Question;
import lele.e_learning.activity.tools.ClientUser;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class QuestionCollection extends AppCompatActivity {


    ArrayList<Question> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_collection);
        questions = new ArrayList<>();
        HttpUtil.sendHttpRequest("GetQuestionCollection?userID=" + ClientUser.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                questions.add(new Question(
                                        jsonObject.getString("id"),
                                        jsonObject.getInt("order"),
                                        jsonObject.getString("type"),
                                        jsonObject.getString("question"),
                                        jsonObject.getString("answer")
                                ));
                            }
                            RecyclerView recyclerView =  findViewById(R.id.recycler_view);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            QuestionAdapter roomAdapter = new QuestionAdapter(questions);
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


    class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
        private List<Question> questionList;
        class ViewHolder extends RecyclerView.ViewHolder {
            View view;//整个控件组
            TextView tv_content;
            TextView tv_info;
            ViewHolder(View view) {
                super(view);
                this.view = view;
                tv_content = view.findViewById(R.id.content);
                tv_info = view.findViewById(R.id.info);
            }
        }

        QuestionAdapter(List<Question> questionList) {
            this.questionList = questionList;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_bbs, parent, false);
            QuestionAdapter.ViewHolder holder = new QuestionAdapter.ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
            Question question = questionList.get(position);
            holder.tv_content.setText(question.getQuestion().split("@")[0]);
            String info = "";
            switch (question.getType()){
                case "Radio":info = "单选题 ";break;
                case "Check":info = "多选题 ";break;
                case "Fill":info = "填空题 ";break;
                default:break;
            }
            info += "\n";

            if(!question.getType().equals("Fill")){
                    info += "A. " + question.getQuestion().split("@")[1]+"   ";
                    info += "B. " + question.getQuestion().split("@")[2]+"   ";
                    info += "C. " + question.getQuestion().split("@")[3]+"   ";
                    info += "D. " + question.getQuestion().split("@")[4]+"   ";
            }
            info += "\n";
            info += "正确答案"+question.getAnswer();
            holder.tv_info.setText(info);
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }
    }

}
