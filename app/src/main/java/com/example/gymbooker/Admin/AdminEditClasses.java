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

public class AdminEditClasses extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth =  FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    EditText etNewTrainer, etNewDuration, etNewTime, etNewDay, etDetails, etLimit, etMaxLimit;
    Button btEditClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_classes);
        getSupportActionBar().hide();

        etNewTrainer = findViewById(R.id.upload_TrainerName);
        etNewDuration = findViewById(R.id.upload_Duration);
        etNewTime = findViewById(R.id.upload_Time);
        etNewDay = findViewById(R.id.upload_Day);
        etDetails = findViewById(R.id.upload_Details);
        etLimit = findViewById(R.id.upload_Limit);
        etMaxLimit = findViewById(R.id.upload_MaxLimit);

        btEditClass = findViewById(R.id.upload_Button);

        Intent i = getIntent();

        String classTrainer = i.getStringExtra(("Class Trainer"));
        String classDetails = i.getStringExtra(("Class Details"));
        String classDay = i.getStringExtra(("Class Day"));
        String classTime= i.getStringExtra(("Class Time"));
        String classDuration = i.getStringExtra(("Class Duration"));
        String classLimit = i.getStringExtra(("Class Limit"));
        String classCategory = i.getStringExtra(("Class Category"));
        String classMaxLimit = i.getStringExtra(("Class Max Limit"));

        etNewTrainer.setText(classTrainer);
        etDetails.setText(classDetails);
        etNewDay.setText(classDay);
        etNewTime.setText(classTime);
        etNewDuration.setText(classDuration);
        etLimit.setText(classLimit);
        etMaxLimit.setText(classMaxLimit);

        btEditClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Trainer = etNewTrainer.getText().toString().trim();
                String Duration = etNewDuration.getText().toString().trim();
                String Time = etNewTime.getText().toString().trim();
                String Day = etNewDay.getText().toString().trim();
                String Details = etDetails.getText().toString().trim();
                String Limit = etLimit.getText().toString().trim();
                String MaxLimit = etMaxLimit.getText().toString().trim();

                if(TextUtils.isEmpty(Trainer)){
                    etNewTrainer.setError("This field cannot be empty.");
                    etNewTrainer.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Duration)){
                    etNewDuration.setError("This field cannot be empty.");
                    etNewDuration.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Time)){
                    etNewTime.setError("This field cannot be empty.");
                    etNewTime.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Day)){
                    etNewDay.setError("This field cannot be empty.");
                    etNewDay.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Details)){
                    etDetails.setError("This field cannot be empty.");
                    etDetails.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Limit)){
                    etLimit.setError("This field cannot be empty.");
                    etLimit.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(MaxLimit)){
                    etMaxLimit.setError("This field cannot be empty.");
                    etMaxLimit.requestFocus();
                    return;
                }

                DocumentReference documentReference = fStore.collection("Gym Classes").document(classCategory);

                documentReference.update("Class Trainer", Trainer);
                documentReference.update("Class Duration", Duration);
                documentReference.update("Class Day", Day);
                documentReference.update("Class Time", Time);
                documentReference.update("Class Details", Details);
                documentReference.update("Class Limit", Limit);
                documentReference.update("Class Max Limit", MaxLimit);

                finish();
            }
        });


    }
}