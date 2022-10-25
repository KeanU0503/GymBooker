package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GymAccess extends AppCompatActivity {

    TextView tvGymFloor, tvGymClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_access);

        tvGymFloor = findViewById(R.id.book_gymFloor);
        tvGymClasses = findViewById(R.id.book_gymClasses);

        tvGymFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GymFloor.class));
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