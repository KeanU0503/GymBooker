package com.example.gymbooker.GymAccess;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.gymbooker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class GymAccess extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String UserID = firebaseAuth.getCurrentUser().getUid();

    TextView tvGymFloor, tvGymClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_access);
        getSupportActionBar().hide();

        Intent intent = getIntent();

        String userName = intent.getStringExtra("Client Name");


        tvGymFloor = findViewById(R.id.book_gymFloor);
        tvGymClasses = findViewById(R.id.book_gymClasses);

        tvGymFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(GymAccess.this);
                AlertDialog.setTitle("Would you like a trainer ?");

                AlertDialog.setPositiveButton("Yes please", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(GymAccess.this, GymFloorTrainers.class);
                            startActivity(intent);
                    }
                });

                AlertDialog.setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(GymAccess.this, GymFloor.class);
                        startActivity(intent);
                    }
                });

                AlertDialog.create().show();
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