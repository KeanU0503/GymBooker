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

import com.example.gymbooker.Admin.AdminEditClasses;
import com.example.gymbooker.GymAccess.GymClassBooking;
import com.example.gymbooker.Model.ModelGymClasses;
import com.example.gymbooker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterGymClasses extends RecyclerView.Adapter<AdapterGymClasses.GymClassesHolder> {

    ArrayList<ModelGymClasses> ModelGymClasses;
    Context context;
    String userType, adminType, clientName, clientContactNumber;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    public AdapterGymClasses(ArrayList<ModelGymClasses> ModelGymClasses, Context context) {
        this.ModelGymClasses = ModelGymClasses;
        this.context = context;
    }

    public void AdapterSearchList(ArrayList<ModelGymClasses> AdapterSearchList) {
        ModelGymClasses = AdapterSearchList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AdapterGymClasses.GymClassesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gym_classes_adapter,parent,false);
        return new AdapterGymClasses.GymClassesHolder(view);
    }


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

        String className = gymClasses.getInClassName();
        String classDuration = gymClasses.getInClassDuration();
        String classDay = gymClasses.getInClassDay();
        String classTime = gymClasses.getInClassTime();
        String classTrainer = gymClasses.getInClassTrainer();
        String classLimit = gymClasses.getInClassLimit();
        String classCategory = gymClasses.getInClassCategory();
        String classDetails = gymClasses.getInClassDetails();
        String classMaxLimit = gymClasses.getInMaxLimit();

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

                Intent i = new Intent(view.getContext(), AdminEditClasses.class);
                i.putExtra("Class Name", className);
                i.putExtra("Class Trainer", classTrainer);
                i.putExtra("Class Details", classDetails);
                i.putExtra("Class Day", classDay);
                i.putExtra("Class Time", classTime);
                i.putExtra("Class Duration", classDuration);
                i.putExtra("Class Max Limit", classMaxLimit);
                i.putExtra("Class Limit", classLimit);
                i.putExtra("Class Category",classCategory);

                view.getContext().startActivity(i);
            }
        });

        holder.btBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), GymClassBooking.class);
                i.putExtra("Class Name", className);
                i.putExtra("Class Trainer", classTrainer);
                i.putExtra("Class Day", classDay);
                i.putExtra("Class Time", classTime);
                i.putExtra("Class Duration", classDuration);
                i.putExtra("Class Max Limit", classMaxLimit);
                i.putExtra("Class Limit", classLimit);
                i.putExtra("Client Name", clientName);
                i.putExtra("Client Contact Number", clientContactNumber);
                i.putExtra("Class Category", classCategory);

                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() { return ModelGymClasses.size(); }


    public class GymClassesHolder extends RecyclerView.ViewHolder {
        private TextView tvClassName, tvClassDetails, tvClassTrainer, tvClassDuration, tvClassDay, tvClassTime;
        private Button btBookNow,btEdit;

        public GymClassesHolder(@NonNull View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.gymClasses_Name);
            tvClassDetails = itemView.findViewById(R.id.gymClasses_Details);
            tvClassTrainer = itemView.findViewById(R.id.gymClasses_Trainer);
            tvClassDuration = itemView.findViewById(R.id.gymClasses_Duration);
            tvClassDay= itemView.findViewById(R.id.gymClasses_Day);
            tvClassTime = itemView.findViewById(R.id.gymClasses_Time);
            btBookNow = itemView.findViewById(R.id.gymClasses_bookNow);
            btEdit = itemView.findViewById(R.id.gymClasses_editClass);

        }
    }

}