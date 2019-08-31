package lele.e_learning.activity.activity.Vote;

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
import lele.e_learning.activity.activity.BBS.ChildBBSPage;
import lele.e_learning.activity.entity.ChildBBS;
import lele.e_learning.activity.entity.RankRecord;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

import static lele.e_learning.activity.tools.Pack.ShowToast;

public class VoteRank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_rank);
        HttpUtil.sendHttpRequest("GetVoteRank", new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<RankRecord> rankRecords = new ArrayList<>();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                rankRecords.add(new RankRecord(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("workName"),
                                        jsonObject.getString("userName"),
                                        jsonObject.getString("point")
                                ));
                            }

                            RecyclerView recyclerView = findViewById(R.id.rv);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(linearLayoutManager);

                            RankRecordAdapter rankRecordAdapter = new RankRecordAdapter(rankRecords);
                            recyclerView.setAdapter(rankRecordAdapter);


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
    }

    class RankRecordAdapter extends RecyclerView.Adapter<RankRecordAdapter.ViewHolder> {
        private List<RankRecord> recordList;
        class ViewHolder extends RecyclerView.ViewHolder {
            View view;//整个控件组
            TextView tvRank;
            TextView tvWorkName;
            TextView tvUserName;
            TextView tvPoint;
            ViewHolder(View view) {
                super(view);
                this.view = view;
                tvRank = view.findViewById(R.id.tvRank);
                tvWorkName = view.findViewById(R.id.tvWorkName);
                tvUserName = view.findViewById(R.id.tvUserName);
                tvPoint = view.findViewById(R.id.tvPoint);

            }
        }

        RankRecordAdapter(ArrayList<RankRecord> recordList) {
            this.recordList = recordList;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
            RankRecordAdapter.ViewHolder holder = new RankRecordAdapter.ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RankRecordAdapter.ViewHolder holder, int position) {
            RankRecord rankRecord = recordList.get(position);
            holder.tvRank.setText(String.valueOf(position+1));
            holder.tvWorkName.setText(rankRecord.getWorkName());
            holder.tvUserName.setText(rankRecord.getUserName());
            holder.tvPoint.setText(rankRecord.getPoint()+"票");
        }

        @Override
        public int getItemCount() {
            return recordList.size();
        }
    }
}
