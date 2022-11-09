package com.example.gymbooker.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gymbooker.ChatList;
import com.example.gymbooker.GymAccess.GymClasses;
import com.example.gymbooker.Login;
import com.example.gymbooker.ManageGym;
import com.example.gymbooker.Profile;
import com.example.gymbooker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AdminHome extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();
    private DocumentReference dReference = fStore.collection("Accounts").document(UserID);

    TextView tvManageGym, tvUserBooking, tvTrainerBooking ,tvTotalGymClasses, tvTotalFloor, tvAdmin, tvProfile;
    ImageView ivChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        getSupportActionBar().hide();

        tvAdmin = findViewById(R.id.show_adminName);

        tvTotalGymClasses = findViewById(R.id.adminHome_gymClassBooking);
        tvTotalFloor = findViewById(R.id.adminHome_gymFloorBooking);
        tvTrainerBooking = findViewById(R.id.adminHome_gymFloorBookingTrainers);

        tvManageGym = findViewById(R.id.adminHome_manageGym);
        tvUserBooking = findViewById(R.id.adminHome_bookingHistory);
        ivChats = findViewById(R.id.image_adminChats);
        tvProfile = findViewById(R.id.adminEdit);


        dReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                // setText = Receives a String
                tvAdmin.setText(documentSnapshot.getString("Full Name"));
            }
        });

        ivChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChatList.class));
            }
        });

        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });

        tvManageGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManageGym.class));
            }
        });

        tvUserBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminGymHistory.class));
            }
        });

        statsStatistic();
        statsGymFloorBookings();
        statsTrainerBookings();
    }

    private void statsStatistic() {
        fStore.collection("Statistics")
                .document("Gym Classes")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String totalBooking = documentSnapshot.getString("Total Bookings");

                        tvTotalGymClasses.setText(String.valueOf(totalBooking));
                    }

                });
    }

    private void statsGymFloorBookings() {
        fStore.collection("Statistics")
                .document("Gym Floor")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String totalBookingGymFloor = documentSnapshot.getString("Total Bookings");

                        tvTotalFloor.setText(String.valueOf(totalBookingGymFloor));
                    }

                });
    }

    private void statsTrainerBookings() {

        fStore.collection("Statistics")
                .document("Gym Floor Trainers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String totalBookingGymFloor = documentSnapshot.getString("Total Bookings");

                        tvTrainerBooking.setText(String.valueOf(totalBookingGymFloor));
                    }

                });
    }

    public void logoutAdmin(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }



}
