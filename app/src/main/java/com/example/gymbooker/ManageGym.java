package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gymbooker.GymAccess.GymClasses;
import com.example.gymbooker.GymAccess.GymFloorTrainers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManageGym extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String UserID = firebaseAuth.getCurrentUser().getUid();

    TextView tvGymFloor, tvGymClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_gym_admin);
        getSupportActionBar().hide();

        tvGymFloor = findViewById(R.id.edit_gymFloor);
        tvGymClasses = findViewById(R.id.edit_gymClasses);

        tvGymFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GymFloorTrainers.class));
            }
        });

        tvGymClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GymClasses.class));
            }
        });

    }

}
