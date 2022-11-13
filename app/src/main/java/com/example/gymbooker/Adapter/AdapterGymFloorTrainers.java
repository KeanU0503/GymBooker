package com.example.gymbooker.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gymbooker.Admin.AdminEditGymFloorTrainers;
import com.example.gymbooker.GymAccess.PlaceBookingTrainers;
import com.example.gymbooker.Model.ModelGymFloorTrainers;
import com.example.gymbooker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AdapterGymFloorTrainers extends RecyclerView.Adapter<AdapterGymFloorTrainers.GymTrainersHolder> {

    ArrayList<ModelGymFloorTrainers> ModelGymTrainers;
    Context context;
    String userType, adminType, clientName, clientContactNumber;

    Calendar calendar;

    String currentTime;
    SimpleDateFormat simpleDateFormat;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    public AdapterGymFloorTrainers(ArrayList<ModelGymFloorTrainers> modelGymTrainers, Context context) {
        this.ModelGymTrainers = modelGymTrainers;
        this.context = context;
    }

    public void AdapterSearchList(ArrayList<ModelGymFloorTrainers> AdapterSearchList) {
        ModelGymTrainers = AdapterSearchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GymTrainersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gym_floor_trainers_adapter,parent,false);
        return new AdapterGymFloorTrainers.GymTrainersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GymTrainersHolder holder, int position) {

        DocumentReference dReference;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyy hh:mm a");

        currentTime = simpleDateFormat.format(calendar.getTime());

        ModelGymFloorTrainers gymTrainers = ModelGymTrainers.get(position);

        String trainerName = gymTrainers.getInTrainerName();
        String gymType = gymTrainers.getInGymType();
        String trainerDay = gymTrainers.getInTrainerDay();
        String trainerDuration = gymTrainers.getInTrainerDuration();
        String trainerLimit = gymTrainers.getInLimit();
        String trainerCategory = gymTrainers.getInTrainerCategory();
        String trainerDetails = gymTrainers.getInDetails();
        String trainerTime = gymTrainers.getInTime();

        holder.tvTrainersName.setText(gymTrainers.getInTrainerName());
        holder.tvTrainerType.setText(gymTrainers.getInGymType());
        holder.tvTrainerWeekly.setText(gymTrainers.getInTrainerDay());
        holder.tvTrainerDuration.setText(gymTrainers.getInTrainerDuration());
        holder.tvTrainerDetails.setText(gymTrainers.getInDetails());
        holder.tvTrainerTime.setText(gymTrainers.getInTime());


        dReference = fStore.collection("Accounts").document(UserID);
        dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userType = documentSnapshot.getString("User");
                adminType = documentSnapshot.getString("Admin");
                clientName = documentSnapshot.getString("Full Name");
                clientContactNumber = documentSnapshot.getString("Contact Number");

                if(Objects.equals(userType, "1")){
                    holder.btEdit.setVisibility(View.GONE);
                }

                if(Objects.equals(adminType, "1")){
                    holder.btBookNow.setVisibility(View.GONE);
                }
            }
        });

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), AdminEditGymFloorTrainers.class);
                i.putExtra("Trainer Name", trainerName);
                i.putExtra("Day", trainerDay);
                i.putExtra("Duration", trainerDuration);
                i.putExtra("Limit", trainerLimit);
                i.putExtra("Trainer Category", trainerCategory);
                i.putExtra("Details", trainerDetails);
                i.putExtra("Time", trainerTime);

                view.getContext().startActivity(i);
            }
        });


        holder.btBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), PlaceBookingTrainers.class);
                i.putExtra("Gym Type", gymType);
                i.putExtra("Name", trainerName);
                i.putExtra("Day", trainerDay);
                i.putExtra("Time", trainerTime);
                i.putExtra("Duration", trainerDuration);
                i.putExtra("Limit", trainerLimit);
                i.putExtra("Client Name", clientName);
                i.putExtra("Client Contact Number", clientContactNumber);
                i.putExtra("Trainer Category", trainerCategory);

                view.getContext().startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ModelGymTrainers.size();
    }

    public class GymTrainersHolder extends RecyclerView.ViewHolder {

        TextView tvTrainersName, tvTrainerType, tvTrainerWeekly, tvTrainerDuration, tvTrainerDetails, tvTrainerTime;
        Button btBookNow, btEdit;

        public GymTrainersHolder(@NonNull View itemView) {
            super(itemView);

            tvTrainersName = itemView.findViewById(R.id.gymTrainer_Name);
            tvTrainerType = itemView.findViewById(R.id.gymTrainer_Type);
            tvTrainerWeekly = itemView.findViewById(R.id.gymTrainer_Day);
            tvTrainerDuration = itemView.findViewById(R.id.gymTrainer_Duration);
            tvTrainerDetails = itemView.findViewById(R.id.gymTrainer_Details);
            tvTrainerTime = itemView.findViewById(R.id.gymTrainer_Time);

            btBookNow = itemView.findViewById(R.id.gymTrainer_Book);
            btEdit = itemView.findViewById(R.id.gymTrainer_Edit);
        }
    }
}