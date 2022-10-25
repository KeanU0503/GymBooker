package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class GymFloorBooking extends AppCompatActivity {

    TextView tvVerDate, tvVerTime, tvVerDuration, tvGymFloorTitle;
    Button btPlaceBooking;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String UserID = fAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_floor_booking);

        tvVerDate = findViewById(R.id.verify_selectedDate);
        tvVerTime= findViewById(R.id.verify_selectedTime);
        tvVerDuration = findViewById(R.id.verify_selectedDuration);
        tvGymFloorTitle = findViewById(R.id.verify_gymFloor_title);
        btPlaceBooking = findViewById(R.id.placeBooking);

        // Data from GymFloor
        Intent intent = getIntent();

        String passGymFloorTitle = intent.getStringExtra("GymFloor");
        String selectedDuration = intent.getStringExtra("Duration");
        String selectedDate = intent.getStringExtra("Date");
        String selectedTime = intent.getStringExtra("Time");

        // Display it here
        tvGymFloorTitle.setText(passGymFloorTitle);
        tvVerDuration.setText(selectedDuration);
        tvVerDate.setText(selectedDate);
        tvVerTime.setText(selectedTime);

        btPlaceBooking.setOnClickListener(new View.OnClickListener() {
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

                DocumentReference placeBooking = fStore.collection("Accounts").document(UserID).collection("Bookings").document();
                placeBooking.set(GymFloorBookings);

                Toast.makeText(view.getContext(),"Booking Made", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), UserHome.class));
                finish();
            }
        });
    }
}