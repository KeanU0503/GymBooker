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

public class PlaceBookingFloors extends AppCompatActivity {

    TextView tvVerDate, tvVerTime, tvVerDuration, tvGymFloorTitle;
    Button btPlaceBooking;
    int statistic;
    String profileName, clientNumber;

    Calendar calendar;

    String currentTime;
    SimpleDateFormat simpleDateFormat;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_booking_floors);
        getSupportActionBar().hide();

        tvVerDate = findViewById(R.id.verify_selectedDate);
        tvVerTime= findViewById(R.id.verify_selectedTime);
        tvVerDuration = findViewById(R.id.verify_selectedDuration);
        tvGymFloorTitle = findViewById(R.id.verify_gymFloor_title);
        btPlaceBooking = findViewById(R.id.placeBooking);

        Intent intent = getIntent();

        String passGymFloorTitle = intent.getStringExtra("Gym Floor");
        String selectedDuration = intent.getStringExtra("Duration");
        String selectedDate = intent.getStringExtra("Date");
        String selectedTime = intent.getStringExtra("Time");

        tvGymFloorTitle.setText(passGymFloorTitle);
        tvVerDuration.setText(selectedDuration);
        tvVerDate.setText(selectedDate);
        tvVerTime.setText(selectedTime);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyy hh:mm a");

        currentTime = simpleDateFormat.format(calendar.getTime());

        fStore.collection("Accounts").document(UserID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                profileName = documentSnapshot.getString("Full Name");
                clientNumber = documentSnapshot.getString("Contact Number");

            }
        });

        btPlaceBooking.setOnClickListener(new View.OnClickListener() {
            DocumentReference dReference1 = fStore.collection("Statistics").document("Gym Floor");
            @Override
            public void onClick(View view) {

                String saveGymFloorTitle = tvGymFloorTitle.getText().toString();
                String saveDuration = tvVerDuration.getText().toString();
                String saveDate = tvVerDate.getText().toString();
                String saveTime = tvVerTime.getText().toString();

                Map<String, Object> GymFloorBookings = new HashMap<>();
                GymFloorBookings.put("Gym Type",saveGymFloorTitle);
                GymFloorBookings.put("Duration",saveDuration);
                GymFloorBookings.put("Date",saveDate);
                GymFloorBookings.put("Time", saveTime);
                GymFloorBookings.put("User ID", UserID);
                GymFloorBookings.put("Client Name", profileName);
                GymFloorBookings.put("Booked Time", currentTime);
                GymFloorBookings.put("Client Contact Number", clientNumber);

                DocumentReference placeBooking = fStore.collection("Gym Bookings").document();
                placeBooking.set(GymFloorBookings);

                dReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        statistic++;
                        dReference1.update("Total Bookings", statistic + "");
                    }
                });

                Toast.makeText(view.getContext(),"Booking Made", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), UserHome.class));
                finish();
            }
        });

        statisticGymClasses();

    }

    private void statisticGymClasses(){
        fStore.collection("Statistics")
                .document("Gym Floor")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String statistics = documentSnapshot.getString("Total Bookings");
                        statistic = Integer.parseInt(statistics);
                    }
                });
    }

}