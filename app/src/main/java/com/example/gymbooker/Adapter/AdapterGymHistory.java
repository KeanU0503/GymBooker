package com.example.gymbooker.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gymbooker.Model.ModelGymHistory;
import com.example.gymbooker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterGymHistory extends RecyclerView.Adapter<AdapterGymHistory.GymHistoryHolder> {

    ArrayList<ModelGymHistory> arrayInformationGymHistory;
    Context context; // pass background information / relation to / what has been going on / access & communicate

    private FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    String userID;

    public AdapterGymHistory(ArrayList<ModelGymHistory> arrayInformationGymHistory, Context context) {
        this.arrayInformationGymHistory = arrayInformationGymHistory;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterGymHistory.GymHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_gym_history,parent,false);
        return new AdapterGymHistory.GymHistoryHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterGymHistory.GymHistoryHolder holder, int position) {

        holder.tvDuration.setText(arrayInformationGymHistory.get(position).getInDuration());
        holder.tvDate.setText(arrayInformationGymHistory.get(position).getInDate());
        holder.tvTime.setText(arrayInformationGymHistory.get(position).getInTime());
        holder.tvGymType.setText(arrayInformationGymHistory.get(position).getInGymType());

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID=firebaseAuth.getCurrentUser().getUid();
    }


    @Override
    public int getItemCount() { return arrayInformationGymHistory.size(); }


    public class GymHistoryHolder extends RecyclerView.ViewHolder {
        private TextView tvDuration, tvDate, tvTime, tvGymType;

        public GymHistoryHolder(@NonNull View itemView) {
            super(itemView);
            tvDuration = itemView.findViewById(R.id.adp_GymHistory_selectedDuration);
            tvDate = itemView.findViewById(R.id.adp_GymHistory_selectedDate);
            tvTime = itemView.findViewById(R.id.adp_GymHistory_selectedTime);
            tvGymType = itemView.findViewById(R.id.adp_gymHistory_gymType);


        }
    }
}