package com.example.gymbooker.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymbooker.Model.ModelGymClasses;
import com.example.gymbooker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterGymClasses extends RecyclerView.Adapter<AdapterGymClasses.GymClassesHolder> {

    ArrayList<ModelGymClasses> arrayInformationGymClasses;
    Context context;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String UserID = firebaseAuth.getCurrentUser().getUid();

    public AdapterGymClasses(ArrayList<ModelGymClasses> arrayInformationGymClasses, Context context) {
        this.arrayInformationGymClasses = arrayInformationGymClasses;
        this.context = context;
    }

    public void AdapterSearchList(ArrayList<ModelGymClasses> AdapterSearchList) {
        arrayInformationGymClasses = AdapterSearchList; //  To add our filtered list in our course array list.
        notifyDataSetChanged();//  Notify our adapter as change in recycler view data.
    }

//  Deals with the inflation of the card layout as an item for the RecyclerView.
    @NonNull
    @Override
    public AdapterGymClasses.GymClassesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_gym_classes,parent,false);
        return new AdapterGymClasses.GymClassesHolder(view);
    }


//  Deals with the setting of different data and methods related
//  to clicks on particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(@NonNull AdapterGymClasses.GymClassesHolder holder, int position) {

        //  Getting the particular item from the list
        ModelGymClasses gymClasses = arrayInformationGymClasses.get(position);

        String className = gymClasses.getInClassName();
        String classDuration = gymClasses.getInClassDuration();
        String classDate = gymClasses.getInClassDate();
        String classTime = gymClasses.getInClassTime();

        holder.tvClassName.setText(gymClasses.getInClassName());
        holder.tvClassDetails.setText(gymClasses.getInClassDetails());
        holder.tvClassTrainer.setText(gymClasses.getInClassTrainer());
        holder.tvClassDuration.setText(gymClasses.getInClassDuration());
        holder.tvClassDate.setText(gymClasses.getInClassDate());
        holder.tvClassTime.setText(gymClasses.getInClassTime());
        
        holder.btBookNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                
                Map<String, Object> GymClassBooking = new HashMap<>();
                GymClassBooking.put("GymType",className);
                GymClassBooking.put("Duration",classDuration);
                GymClassBooking.put("Date",classDate);
                GymClassBooking.put("Time",classTime);

                DocumentReference placeBooking = fStore.collection("Accounts").document(UserID).collection("Bookings").document();
                placeBooking.set(GymClassBooking);
                
                Toast.makeText(view.getContext(),"Booking Made", Toast.LENGTH_LONG).show();
            }
        });

    }

//  Returns the length of the RecyclerView.
    @Override
    public int getItemCount() { return arrayInformationGymClasses.size(); }


    public class GymClassesHolder extends RecyclerView.ViewHolder {
        private TextView tvClassName, tvClassDetails, tvClassTrainer, tvClassDuration, tvClassDate, tvClassTime, tvClassLimit;
        private Button btBookNow;

        public GymClassesHolder(@NonNull View itemView) {
            super(itemView);

            tvClassName = itemView.findViewById(R.id.gymClasses_Name);
            tvClassDetails = itemView.findViewById(R.id.gymClasses_Details);
            tvClassTrainer = itemView.findViewById(R.id.gymClasses_Trainer);
            tvClassDuration = itemView.findViewById(R.id.gymClasses_Duration);
            tvClassDate = itemView.findViewById(R.id.gymClasses_Date);
            tvClassTime = itemView.findViewById(R.id.gymClasses_Time);
            tvClassLimit = itemView.findViewById(R.id.gymClasses_ClassLimit);

            btBookNow = itemView.findViewById(R.id.gymClasses_bookNow);

        }
    }

}