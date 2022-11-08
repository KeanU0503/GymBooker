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
import android.widget.Toast;

import com.example.gymbooker.Admin.AdminEditClasses;
import com.example.gymbooker.Admin.AdminEditGymFloor;
import com.example.gymbooker.Model.ModelGymTrainers;
import com.example.gymbooker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdapterGymFloorTrainers extends RecyclerView.Adapter<AdapterGymFloorTrainers.GymTrainersHolder> {

    ArrayList<ModelGymTrainers> ModelGymTrainers;
    Context context;
    String timeSlot, userType, adminType, clientName, stats, clientContactNumber;
    int limit, statistic;

    Calendar calendar;

    String currentTime;
    SimpleDateFormat simpleDateFormat;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String UserID = fAuth.getCurrentUser().getUid();

    public AdapterGymFloorTrainers(ArrayList<ModelGymTrainers> modelGymTrainers, Context context) {
        this.ModelGymTrainers = modelGymTrainers;
        this.context = context;
    }

    public void AdapterSearchList(ArrayList<ModelGymTrainers> AdapterSearchList) {
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

        ModelGymTrainers gymTrainers = ModelGymTrainers.get(position);

        String trainerName = gymTrainers.getInTrainerName();
        String gymType = gymTrainers.getInGymType();
        String trainerWeekly = gymTrainers.getInTrainerWeekly();
        String trainerDuration = gymTrainers.getInTrainerDuration();
        String trainerLimit = gymTrainers.getInLimit();
        String trainerCategory = gymTrainers.getInTrainerCategory(); // Reference
        String trainerDetails = gymTrainers.getInDetails();
        String trainerTime = gymTrainers.getInTime();

        holder.tvTrainersName.setText(gymTrainers.getInTrainerName());
        holder.tvTrainerType.setText(gymTrainers.getInGymType());
        holder.tvTrainerWeekly.setText(gymTrainers.getInTrainerWeekly());
        holder.tvTrainerDuration.setText(gymTrainers.getInTrainerDuration());
        holder.tvTrainerDetails.setText(gymTrainers.getInDetails());
        holder.tvTrainerTime.setText(gymTrainers.getInTime());

//      Check limit
        dReference = fStore.collection("Gym Trainers").document(gymTrainers.getInTrainerCategory());
        dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                timeSlot = documentSnapshot.getString("Limit");

                limit = Integer.parseInt(timeSlot);
                if(limit == 5){
                    holder.btBookNow.setEnabled(false);
                }
            }
        });

        dReference = fStore.collection("Statistics").document("Gym Floor Trainers");
        dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                stats = documentSnapshot.getString("Total Bookings");

                statistic = Integer.parseInt(stats);
            }
        });

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

                Intent i = new Intent(view.getContext(), AdminEditGymFloor.class);
                i.putExtra("Trainer Name", trainerName);
                i.putExtra("Day", trainerWeekly);
                i.putExtra("Duration", trainerDuration);
                i.putExtra("Limit", trainerLimit);
                i.putExtra("Trainer Category", trainerCategory); // Only for reference document
                i.putExtra("Details", trainerDetails);
                i.putExtra("Time", trainerTime);

                view.getContext().startActivity(i);
            }
        });

        holder.btBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference dReference1 = fStore.collection("Gym Trainers").document(gymTrainers.getInTrainerCategory());
                DocumentReference dReference2 = fStore.collection("Statistics").document("Gym Floor Trainers");

                dReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                      Increase limit
                        limit++;
                        dReference1.update("Limit", limit + "");
                        
                    }
                });

                dReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        statistic++;
                        dReference2.update("Total Bookings", statistic + "");
                    }
                });

                Map<String, Object> GymFloorBooking = new HashMap<>();
                GymFloorBooking.put("Gym Trainer", trainerName);
                GymFloorBooking.put("Gym Type", gymType);
                GymFloorBooking.put("Day", trainerWeekly);
                GymFloorBooking.put("Duration", trainerDuration);
                GymFloorBooking.put("Client Name", clientName);
                GymFloorBooking.put("User ID", UserID);
                GymFloorBooking.put("Booked Time", currentTime);
                GymFloorBooking.put("Time", trainerTime);
                GymFloorBooking.put("Client Contact Number", clientContactNumber);

                DocumentReference placeBooking = fStore.collection("Gym Bookings").document();
                placeBooking.set(GymFloorBooking);

                Toast.makeText(view.getContext(),"Booking Made", Toast.LENGTH_LONG).show();

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