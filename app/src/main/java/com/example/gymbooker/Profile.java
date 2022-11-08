package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    TextView tvProfileName, tvProfileEmail, tvProfileContactNumber;
    Button btEdit;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().hide();

        tvProfileName = findViewById(R.id.profileName);
        tvProfileEmail = findViewById(R.id.profileEmail);
        tvProfileContactNumber = findViewById(R.id.profileContactNumber);
        btEdit = findViewById(R.id.profileEdit);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        displayUserInformation();
    }

    private void displayUserInformation() {
        fStore.collection("Accounts").document(UserID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String profileName = documentSnapshot.getString("Full Name");
                String profilePhoneNumber = documentSnapshot.getString("Contact Number");
                String profileEmail = documentSnapshot.getString("Email");

                tvProfileName.setText(String.valueOf(profileName));
                tvProfileEmail.setText(String.valueOf(profileEmail));
                tvProfileContactNumber.setText(String.valueOf(profilePhoneNumber));
            }
        });
    }
}