package lele.e_learning.activity.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Collection;
import java.util.List;

import lele.e_learning.R;
import lele.e_learning.activity.entity.RankRecord;
import lele.e_learning.activity.tools.HttpCallbackListener;
import lele.e_learning.activity.tools.HttpUtil;

public class PointRank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_rank);
        HttpUtil.sendHttpRequest("GetPointRank", new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> rank = new ArrayList<>();
                        for(String item:response.split("\\.")){
                            rank.add(item);
                        }
                        RecyclerView recyclerView = findViewById(R.id.rv);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        PointAdapter rankRecordAdapter = new PointAdapter(rank);
                        recyclerView.setAdapter(rankRecordAdapter);
                    }
                });

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> {
        private List<String> List;
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

        PointAdapter(ArrayList<String> List) {
            this.List = List;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
            PointAdapter.ViewHolder holder = new PointAdapter.ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull PointAdapter.ViewHolder holder, int position) {
            String point = List.get(position);
            holder.tvRank.setText(String.valueOf(position+1));
            holder.tvWorkName.setText(point.split(" ")[0]);
            holder.tvUserName.setText(point.split(" ")[1].concat("分"));
            holder.tvPoint.setText("");
        }

        @Override
        public int getItemCount() {
            return List.size();
        }
    }
}
