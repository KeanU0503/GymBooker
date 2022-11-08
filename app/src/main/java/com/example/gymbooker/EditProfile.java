package com.example.gymbooker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gymbooker.Admin.AdminHome;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class EditProfile extends AppCompatActivity {

    TextView tvProfileEmail;
    EditText etProfileName, etProfileContactNumber;
    Button btSave;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private String UserID = fAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        getSupportActionBar().hide();

        etProfileName = findViewById(R.id.editProfileName);
        etProfileContactNumber = findViewById(R.id.editProfileContactNumber);
        tvProfileEmail = findViewById(R.id.editProfileEmail);

        btSave = findViewById(R.id.editProfileSave);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileName = etProfileName.getText().toString();
                String profileContactNumber = etProfileContactNumber.getText().toString();

                if(TextUtils.isEmpty(profileName)){
                    etProfileName.setError("Please Enter Your Name");
                    etProfileName.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(profileContactNumber)){
                    etProfileContactNumber.setError("Please Enter Your Contact Number");
                    etProfileContactNumber.requestFocus();
                    return;
                }
                else {

                    DocumentReference dReference = fStore.collection("Accounts").document(UserID);
                    dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            dReference.update("Full Name", profileName);
                            dReference.update("Contact Number", profileContactNumber);

                            if(Objects.equals(documentSnapshot.getString("Admin"), "1")){
                                startActivity(new Intent(getApplicationContext(), AdminHome.class));
                                finish();

                            } else if (Objects.equals(documentSnapshot.getString("User"), "1")){
                                startActivity(new Intent(getApplicationContext(),UserHome.class));
                                finish();
                            }
                        }
                    });
                }
            }
        });

        displayUserDetailsDB();
    }

    private void displayUserDetailsDB() {
        fStore.collection("Accounts").document(UserID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String profileEmail = documentSnapshot.getString("Email");
                String profileName = documentSnapshot.getString("Full Name");
                String profilePhoneNumber = documentSnapshot.getString("Contact Number");

                etProfileName.setText(String.valueOf(profileName));
                etProfileContactNumber.setText(String.valueOf(profilePhoneNumber));
                tvProfileEmail.setText(String.valueOf(profileEmail));
            }
        });
    }

}
