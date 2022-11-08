package com.example.gymbooker.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gymbooker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;

public class AdminEditGymFloor extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth =  FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    EditText etEditTrainer, etEditDuration, etEditDay, etEditDetails, etEditLimit,etEditTime;
    Button btEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_gym_floor);
        getSupportActionBar().hide();

        etEditTrainer = findViewById(R.id.edit_TrainerName);
        etEditDuration = findViewById(R.id.edit_Duration);
        etEditDay = findViewById(R.id.edit_Day);
        etEditDetails = findViewById(R.id.edit_Details);
        etEditLimit = findViewById(R.id.edit_Limit);
        etEditTime = findViewById(R.id.edit_Time);

        btEdit = findViewById(R.id.gymFloorTrainer_edit);

        Intent i = getIntent();

        String gymTrainer = i.getStringExtra(("Trainer Name"));
        String day = i.getStringExtra(("Day"));
        String duration = i.getStringExtra(("Duration"));
        String details = i.getStringExtra(("Details"));
        String slotLimit = i.getStringExtra(("Limit"));
        String trainerCategory = i.getStringExtra(("Trainer Category")); // Only for reference document
        String time = i.getStringExtra(("Time"));

        etEditTrainer.setText(gymTrainer);
        etEditDuration.setText(duration);
        etEditDay.setText(day);
        etEditDetails.setText(details);
        etEditLimit.setText(slotLimit);
        etEditTime.setText(time);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = etEditTrainer.getText().toString().trim();
                String Duration = etEditDuration.getText().toString().trim();
                String Day = etEditDay.getText().toString().trim();
                String Details = etEditDetails.getText().toString().trim();
                String Limit = etEditLimit.getText().toString().trim();
                String Time = etEditTime.getText().toString().trim();

                if(TextUtils.isEmpty(Name)){
                    etEditTrainer.setError("This field cannot be empty.");
                    etEditTrainer.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Duration)){
                    etEditDuration.setError("This field cannot be empty.");
                    etEditDuration.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Day)){
                    etEditDay.setError("This field cannot be empty.");
                    etEditDay.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Time)){
                    etEditTime.setError("This field cannot be empty.");
                    etEditTime.requestFocus();
                    return;
                }


                if(TextUtils.isEmpty(Details)){
                    etEditDetails.setError("This field cannot be empty.");
                    etEditDetails.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Limit)){
                    etEditLimit.setError("This field cannot be empty.");
                    etEditLimit.requestFocus();
                    return;
                }

                DocumentReference documentReference = fStore.collection("Gym Trainers").document(trainerCategory);

                documentReference.update("Gym Trainer", Name);
                documentReference.update("Duration", Duration);
                documentReference.update("Day", Day);
                documentReference.update("Time", Time);
                documentReference.update("Details", Details);
                documentReference.update("Limit", Limit);

                finish();
            }
        });
    }
}