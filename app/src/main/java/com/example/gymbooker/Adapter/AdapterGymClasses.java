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
import com.example.gymbooker.GymAccess.GymAccess;
import com.example.gymbooker.GymAccess.GymFloorTrainers;
import com.example.gymbooker.Model.ModelGymClasses;
import com.example.gymbooker.R;
import com.example.gymbooker.UserHome;
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

public class AdapterGymClasses extends RecyclerView.Adapter<AdapterGymClasses.GymClassesHolder> {

    ArrayList<ModelGymClasses> ModelGymClasses;
    Context context;
    String classTotal, userType, adminType, clientName, stats;
    int limit;
    int statistic;

    Calendar calendar;

    String currentTime;
    SimpleDateFormat simpleDateFormat;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String UserID = firebaseAuth.getCurrentUser().getUid();

    public AdapterGymClasses(ArrayList<ModelGymClasses> ModelGymClasses, Context context) {
        this.ModelGymClasses = ModelGymClasses;
        this.context = context;
    }

    public void AdapterSearchList(ArrayList<ModelGymClasses> AdapterSearchList) {
        ModelGymClasses = AdapterSearchList; //  To add our filtered list in our course array list.
        notifyDataSetChanged();//  Notify our adapter as change in recycler view data.
    }

//  Deals with the inflation of the card layout as an item for the RecyclerView.
    @NonNull
    @Override
    public AdapterGymClasses.GymClassesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gym_classes_adapter,parent,false);
        return new AdapterGymClasses.GymClassesHolder(view);
    }


//  Deals with the setting of different data and methods related
//  to clicks on particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(@NonNull AdapterGymClasses.GymClassesHolder holder, int position) {

        DocumentReference dReference;

        ModelGymClasses gymClasses = ModelGymClasses.get(position);

        holder.tvClassName.setText(gymClasses.getInClassName());
        holder.tvClassDetails.setText(gymClasses.getInClassDetails());
        holder.tvClassTrainer.setText(gymClasses.getInClassTrainer());
        holder.tvClassDuration.setText(gymClasses.getInClassDuration());
        holder.tvClassDay.setText(gymClasses.getInClassDay());
        holder.tvClassTime.setText(gymClasses.getInClassTime());
        holder.tvClassTrainer.setText(gymClasses.getInClassTrainer());
        holder.tvClassLimit.setText("Currently: " + gymClasses.getInClassLimit());
        holder.tvMaxLimit.setText("Max Limit: " + gymClasses.getInMaxLimit());

        String className = gymClasses.getInClassName();
        String classDuration = gymClasses.getInClassDuration();
        String classDay = gymClasses.getInClassDay();
        String classTime = gymClasses.getInClassTime();
        String classTrainer = gymClasses.getInClassTrainer();
        String classLimit = gymClasses.getInClassLimit();
        String classCategory = gymClasses.getInClassCategory();
        String classDetails = gymClasses.getInClassDetails();
        String classMaxLimit = gymClasses.getInMaxLimit();

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyy hh:mm a");

        currentTime = simpleDateFormat.format(calendar.getTime());

        dReference = fStore.collection("Gym Classes").document(gymClasses.getInClassCategory());
        dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                classTotal = documentSnapshot.getString("Class Limit");

                limit = Integer.parseInt(classTotal);
                if(limit == 2){
                    holder.btBookNow.setEnabled(false);
                }

            }
        });

        dReference = fStore.collection("Statistics").document("Gym Classes");
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

                Intent i = new Intent(view.getContext(), AdminEditClasses.class);
                i.putExtra("Class Name", className);
                i.putExtra("Class Trainer", classTrainer);
                i.putExtra("Class Details", classDetails);
                i.putExtra("Class Day", classDay);
                i.putExtra("Class Time", classTime);
                i.putExtra("Class Duration", classDuration);
                i.putExtra("Class Limit", classLimit);
                i.putExtra("Class Category",classCategory);
                i.putExtra("Class Max Limit", classMaxLimit);

                view.getContext().startActivity(i);
            }
        });

        holder.btBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference dReference1 = fStore.collection("Gym Classes").document(gymClasses.getInClassCategory());
                DocumentReference dReference2 = fStore.collection("Statistics").document("Gym Classes");

                dReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        limit++;
                        dReference1.update("Class Limit", limit + "");

                    }
                });

                dReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        statistic++;
                        dReference2.update("Total Bookings", statistic + "");
                    }
                });

                Map<String, Object> GymClassBooking = new HashMap<>();
                GymClassBooking.put("Gym Type",className);
                GymClassBooking.put("Duration",classDuration);
                GymClassBooking.put("Day",classDay);
                GymClassBooking.put("Time",classTime);
                GymClassBooking.put("Gym Trainer",classTrainer);
                GymClassBooking.put("User ID", UserID);
                GymClassBooking.put("Client Name", clientName);
                GymClassBooking.put("Booked Time",currentTime);

                DocumentReference placeBooking = fStore.collection("Gym Bookings").document();
                placeBooking.set(GymClassBooking);

                Toast.makeText(view.getContext(),"Booking Made", Toast.LENGTH_LONG).show();

                Intent i = new Intent(view.getContext(), UserHome.class);
                view.getContext().startActivity(i);
            }
        });

    }

//  Returns the length of the RecyclerView.
    @Override
    public int getItemCount() { return ModelGymClasses.size(); }


    public class GymClassesHolder extends RecyclerView.ViewHolder {
        private TextView tvClassName, tvClassDetails, tvClassTrainer, tvClassDuration, tvClassDay, tvClassTime, tvClassLimit, tvMaxLimit;
        private Button btBookNow,btEdit;

        public GymClassesHolder(@NonNull View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.gymClasses_Name);
            tvClassDetails = itemView.findViewById(R.id.gymClasses_Details);
            tvClassTrainer = itemView.findViewById(R.id.gymClasses_Trainer);
            tvClassDuration = itemView.findViewById(R.id.gymClasses_Duration);
            tvClassDay= itemView.findViewById(R.id.gymClasses_Day);
            tvClassTime = itemView.findViewById(R.id.gymClasses_Time);
            tvClassLimit = itemView.findViewById(R.id.gymClasses_ClassLimit);
            tvMaxLimit = itemView.findViewById(R.id.gymClasses_MaxLimit);

            btBookNow = itemView.findViewById(R.id.gymClasses_bookNow);
            btEdit = itemView.findViewById(R.id.gymClasses_editClass);

        }
    }

}