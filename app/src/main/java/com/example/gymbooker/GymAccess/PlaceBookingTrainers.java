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

public class PlaceBookingTrainers extends AppCompatActivity {

    TextView tvVerDay, tvVerTime, tvVerDuration, tvFloorName, tvFloorTrainer;
    Button btPlaceBooking;
    String floorTotal, stats;

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
        setContentView(R.layout.place_booking_trainers);

        tvFloorName = findViewById(R.id.verify_gymFloorType);
        tvFloorTrainer = findViewById(R.id.gymClasses_floorTrainer);
        tvVerDay = findViewById(R.id.verify_trainerDay);
        tvVerTime= findViewById(R.id.verify_trainerTime);
        tvVerDuration = findViewById(R.id.verify_trainerDuration);

        btPlaceBooking = findViewById(R.id.placeFloorBooking);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyy hh:mm a");

        currentTime = simpleDateFormat.format(calendar.getTime());

        Intent intent = getIntent();

        String selectedGymType = intent.getStringExtra("Gym Type");
        String floorTrainer = intent.getStringExtra("Name");
        String selectedDay = intent.getStringExtra("Day");
        String selectedTime = intent.getStringExtra("Time");
        String selectedDuration = intent.getStringExtra("Duration");
        String selectedLimit = intent.getStringExtra("Limit");
        String clientName = intent.getStringExtra("Client Name");
        String clientContactNumber = intent.getStringExtra("Client Contact Number");

        String selectedCategory = intent.getStringExtra("Trainer Category");

        tvFloorName.setText(selectedGymType);
        tvFloorTrainer.setText(floorTrainer);
        tvVerDay.setText(selectedDay);
        tvVerTime.setText(selectedTime);
        tvVerDuration.setText(selectedDuration);

        DocumentReference dReference;

        dReference = fStore.collection("Gym Floor Trainers").document(selectedCategory);
        dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                floorTotal = documentSnapshot.getString("Limit");

                limit = Integer.parseInt(floorTotal);
                if(limit == 1) {
                    btPlaceBooking.setEnabled(false);

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

        btPlaceBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference dReference1 = fStore.collection("Gym Floor Trainers").document(selectedCategory);
                DocumentReference dReference2 = fStore.collection("Statistics").document("Gym Floor Trainers");

                dReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        limit++;
                        dReference1.update("Limit", limit + "");
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
                GymClassBooking.put("Gym Type",selectedGymType);
                GymClassBooking.put("Duration",selectedDuration);
                GymClassBooking.put("Day",selectedDay);
                GymClassBooking.put("Time",selectedTime);
                GymClassBooking.put("Gym Trainer",floorTrainer + " [Gym Floor Trainer]");
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