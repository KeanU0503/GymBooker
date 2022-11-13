package com.example.gymbooker.GymAccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymbooker.R;
import com.example.gymbooker.UserHome;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PlaceBookingClasses extends AppCompatActivity {

    TextView tvVerDay, tvVerTime, tvVerDuration, tvLimit, tvMaxLimit, tvClassName, tvClassTrainer;
    Button btPlaceBooking;
    String classTotal, stats;

    int limit;
    int statistic;

    Calendar calendar;

    String currentTime;
    SimpleDateFormat simpleDateFormat;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_booking_classes);

        tvClassName = findViewById(R.id.verify_className);
        tvClassTrainer = findViewById(R.id.gymClasses_classTrainer);
        tvVerDay = findViewById(R.id.verify_classDay);
        tvVerTime= findViewById(R.id.verify_classTime);
        tvVerDuration = findViewById(R.id.verify_classDuration);
        tvMaxLimit =findViewById(R.id.gymClasses_classMaxLimit);
        tvLimit = findViewById(R.id.gymClasses_classLimit);

        btPlaceBooking = findViewById(R.id.placeClassBooking);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyy hh:mm a");

        currentTime = simpleDateFormat.format(calendar.getTime());

        Intent intent = getIntent();

        String className = intent.getStringExtra("Class Name");
        String classTrainer = intent.getStringExtra("Class Trainer");
        String selectedDay = intent.getStringExtra("Class Day");
        String selectedTime = intent.getStringExtra("Class Time");
        String selectedDuration = intent.getStringExtra("Class Duration");
        String selectedMaxLimit = intent.getStringExtra("Class Max Limit");
        String selectedLimit = intent.getStringExtra("Class Limit");
        String clientName = intent.getStringExtra("Client Name");
        String selectedCategory = intent.getStringExtra("Class Category");
        String clientContactNumber = intent.getStringExtra("Client Contact Number");

        tvClassName.setText(className);
        tvClassTrainer.setText(classTrainer);
        tvVerDay.setText(selectedDay);
        tvVerTime.setText(selectedTime);
        tvVerDuration.setText(selectedDuration);
        tvMaxLimit.setText("Max Limit: " + selectedMaxLimit);
        tvLimit.setText("Currently: " + selectedLimit);

        DocumentReference dReference;

        dReference = fStore.collection("Gym Classes").document(selectedCategory);
        dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                classTotal = documentSnapshot.getString("Class Limit");

                limit = Integer.parseInt(classTotal);
                if(limit == 2) {
                    btPlaceBooking.setEnabled(false);
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

        btPlaceBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference dReference1 = fStore.collection("Gym Classes").document(selectedCategory);
                DocumentReference dReference2 = fStore.collection("Statistics").document("Gym Classes");

                dReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        limit++;
                        dReference1.update("Class Limit", limit + "");
                        dReference1.update("Overall Bookings", limit + "");

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
                GymClassBooking.put("Duration",selectedDuration);
                GymClassBooking.put("Day",selectedDay);
                GymClassBooking.put("Time",selectedTime);
                GymClassBooking.put("Gym Trainer",classTrainer + " [Gym Class]");
                GymClassBooking.put("User ID", UserID);
                GymClassBooking.put("Client Name", clientName);
                GymClassBooking.put("Client Contact Number", clientContactNumber);
                GymClassBooking.put("Booked Time",currentTime);

                DocumentReference placeBooking = fStore.collection("Gym Bookings").document();
                placeBooking.set(GymClassBooking);

                Toast.makeText(view.getContext(),"Booking Made", Toast.LENGTH_LONG).show();

                Intent i = new Intent(view.getContext(), UserHome.class);
                view.getContext().startActivity(i);

            }
        });


    }
}