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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterGymHistory extends RecyclerView.Adapter<AdapterGymHistory.GymHistoryHolder> {

    ArrayList<ModelGymHistory> ModelGymHistory;
    Context context; // pass background information / relation to / what has been going on / access & communicate
    String userType;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    public AdapterGymHistory(ArrayList<ModelGymHistory> ModelGymHistory, Context context) {
        this.ModelGymHistory = ModelGymHistory;
        this.context = context;
    }

    public void AdapterSearchList(ArrayList<ModelGymHistory> AdapterSearchList) {
        ModelGymHistory = AdapterSearchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterGymHistory.GymHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gym_history_adapter,parent,false);
        return new AdapterGymHistory.GymHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGymHistory.GymHistoryHolder holder, int position) {

        ModelGymHistory gymHistory = ModelGymHistory.get(position);

        holder.tvGymType.setText(gymHistory.getInGymType());
        holder.tvDate.setText(gymHistory.getInDate());

        holder.tvTime.setText(gymHistory.getInTime());
        holder.tvDuration.setText(gymHistory.getInDuration());
        holder.tvGymTrainer.setText(gymHistory.getInGymTrainer());
        holder.tvClientName.setText(gymHistory.getInClientName());
        holder.tvDay.setText(gymHistory.getInDay());
        holder.tvBookTime.setText(gymHistory.getInBookTime());
        holder.tvClientContactNumber.setText(gymHistory.getInContactNumber());

        DocumentReference dReference = fStore.collection("Accounts").document(UserID);
        dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userType = documentSnapshot.getString("User");

                if(Objects.equals(userType, "1")){
                    holder.tvTxtClientName.setVisibility(View.GONE);
                    holder.tvTxtClientContactNumber.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() { return ModelGymHistory.size(); }


    public class GymHistoryHolder extends RecyclerView.ViewHolder {
        private TextView tvDuration, tvDate, tvTime, tvGymType,tvGymTrainer, tvClientName, tvTxtClientName, tvDay, tvBookTime, tvClientContactNumber,tvTxtClientContactNumber;

        public GymHistoryHolder(@NonNull View itemView) {
            super(itemView);

            tvDuration = itemView.findViewById(R.id.adp_GymHistory_selectedDuration);
            tvDate = itemView.findViewById(R.id.adp_GymHistory_selectedDate);
            tvTime = itemView.findViewById(R.id.adp_GymHistory_selectedTime);
            tvGymType = itemView.findViewById(R.id.adp_gymHistory_gymType);
            tvGymTrainer = itemView.findViewById(R.id.adp_gymHistory_gymTrainer);
            tvDay = itemView.findViewById(R.id.adp_GymHistory_selectedDay);
            tvBookTime = itemView.findViewById(R.id.adp_GymHistory_bookTime);
            tvClientName = itemView.findViewById(R.id.adp_GymHistory_clientName);
            tvTxtClientName = itemView.findViewById(R.id.adp_GymHistory_txtClientName);
            tvClientContactNumber = itemView.findViewById(R.id.adp_GymHistory_clientContactNumber);
            tvTxtClientContactNumber = itemView.findViewById(R.id.adp_GymHistory_txtClientContactNumber);

        }
    }
}