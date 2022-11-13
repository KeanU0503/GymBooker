package com.example.gymbooker.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gymbooker.Charts.ClassChart;
import com.example.gymbooker.Charts.BookingChart;
import com.example.gymbooker.ChatList;
import com.example.gymbooker.Login;
import com.example.gymbooker.ManageGym;
import com.example.gymbooker.Profile;
import com.example.gymbooker.R;
import com.example.gymbooker.Charts.TrainerChart;
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

    TextView tvManageGym, tvUserBooking, tvTrainerBooking ,tvTotalGymClasses, tvTotalFloor, tvAdmin, tvProfile,
             tvTotalBookingStats, tvClassBookingStats, tvTrainerBookingStats;

    ImageView ivChats;

    String totalBooking, totalBookingGymFloor, totalBookingGymFloorTrainer;

    String cardio, cycling, hilt, mind, strength;
    String cardioT, cyclingT, hiltT, mindT, strengthT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_admin);
        getSupportActionBar().hide();

        tvAdmin = findViewById(R.id.show_adminName);

        tvTotalGymClasses = findViewById(R.id.adminHome_gymClassBooking);
        tvTotalFloor = findViewById(R.id.adminHome_gymFloorBooking);
        tvTrainerBooking = findViewById(R.id.adminHome_gymFloorBookingTrainers);
        tvTotalBookingStats = findViewById(R.id.stats_totalBookings);
        tvClassBookingStats = findViewById(R.id.stats_totalClassBookings);
        tvTrainerBookingStats = findViewById(R.id.stats_totalTrainerBookings);

        tvManageGym = findViewById(R.id.adminHome_manageGym);
        tvUserBooking = findViewById(R.id.adminHome_bookingHistory);
        ivChats = findViewById(R.id.image_adminChats);
        tvProfile = findViewById(R.id.adminEdit);

        dReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

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

        tvTotalBookingStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), BookingChart.class);
                i.putExtra("Class", totalBooking);
                i.putExtra("Floor", totalBookingGymFloor);
                i.putExtra("Trainer", totalBookingGymFloorTrainer);
                view.getContext().startActivity(i);

            }
        });

        tvClassBookingStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), ClassChart.class);
                i.putExtra("Cardio", cardio);
                i.putExtra("Cycling", cycling);
                i.putExtra("Hilt", hilt);
                i.putExtra("Mind", mind);
                i.putExtra("Strength", strength);
                view.getContext().startActivity(i);
            }
        });

        tvTrainerBookingStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), TrainerChart.class);
                i.putExtra("Cardio", cardioT);
                i.putExtra("Cycling", cyclingT);
                i.putExtra("Hilt", hiltT);
                i.putExtra("Mind", mindT);
                i.putExtra("Strength", strengthT);
                view.getContext().startActivity(i);
            }
        });

        statsStatistic();
        statsGymFloorBookings();
        statsTrainerBookings();

        statsClassCardio();
        statsClassCycling();
        statsClassHilt();
        statsClassMind();
        statsClassStrength();

        statsTrainerCardio();
        statsTrainerCycling();
        statsTrainerHilt();
        statsTrainerMind();
        statsTrainerStrength();
    }

// Overall Bookings

    private void statsStatistic() {
        fStore.collection("Statistics")
                .document("Gym Classes")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        totalBooking = documentSnapshot.getString("Total Bookings");

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
                        totalBookingGymFloor = documentSnapshot.getString("Total Bookings");

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
                        totalBookingGymFloorTrainer = documentSnapshot.getString("Total Bookings");

                        tvTrainerBooking.setText(String.valueOf(totalBookingGymFloorTrainer));
                    }

                });
    }

// Gym Classes

    private void statsClassCardio() {

        fStore.collection("Gym Classes")
                .document("Cardio Steps")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        cardio = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }

    private void statsClassCycling() {

        fStore.collection("Gym Classes")
                .document("Cycling Ace")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        cycling = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }

    private void statsClassHilt() {

        fStore.collection("Gym Classes")
                .document("Hilt Box")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        hilt = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }

    private void statsClassMind() {

        fStore.collection("Gym Classes")
                .document("Mind Yoga")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                          mind = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }

    private void statsClassStrength() {

        fStore.collection("Gym Classes")
                .document("Strength Hardcore")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        strength = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }


// Gym Floor Trainer

    private void statsTrainerCardio() {

        fStore.collection("Gym Floor Trainers")
                .document("Cardio Steps")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        cardioT = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }

    private void statsTrainerCycling() {

        fStore.collection("Gym Floor Trainers")
                .document("Cycling Ace")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        cyclingT = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }

    private void statsTrainerHilt() {

        fStore.collection("Gym Floor Trainers")
                .document("Hilt Box")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        hiltT = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }

    private void statsTrainerMind() {

        fStore.collection("Gym Floor Trainers")
                .document("Mind Yoga")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        mindT = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }

    private void statsTrainerStrength() {

        fStore.collection("Gym Floor Trainers")
                .document("Strength Hardcore")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        strengthT = documentSnapshot.getString("Overall Bookings");

                    }
                });
    }


    public void logoutAdmin(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }



}
