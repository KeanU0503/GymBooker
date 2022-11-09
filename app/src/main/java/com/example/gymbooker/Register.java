package com.example.gymbooker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymbooker.Model.ModelChatList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    String FullName, Email, Password, ContactNumber;
    EditText etFullName, etEmail, etPassword, etContactNumber;
    Button btSignUp;
    TextView tvToLogin;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().hide();

        etFullName = findViewById(R.id.atyFullName);
        etEmail = findViewById(R.id.atyEmailRegister);
        etPassword = findViewById(R.id.atyPasswordRegister);
        etContactNumber = findViewById(R.id.atyContactNumber);
        tvToLogin = findViewById(R.id.atyToLogin);

        btSignUp = findViewById(R.id.atySignUp);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FullName = etFullName.getText().toString().trim();
                Email = etEmail.getText().toString().trim();
                Password = etPassword.getText().toString().trim();
                ContactNumber = etContactNumber.getText().toString().trim();

                if(TextUtils.isEmpty(FullName)){
                    etFullName.setError("Please enter your full name.");
                    etFullName.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Email)){
                    etEmail.setError("Please enter your email.");
                    etEmail.requestFocus();
                    return;
                }

                if(Password.length() < 8){
                    etPassword.setError("At least 8 characters are required.");
                    etPassword.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    etPassword.setError("Please enter a password.");
                    etPassword.requestFocus();
                    return;
                }

                if(ContactNumber.length() != 10){
                    etContactNumber.setError("Contact number is an invalid format.");
                    etContactNumber.requestFocus();
                    return;
                }


                fAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        FirebaseUser user = fAuth.getCurrentUser();
                        DatabaseReference reference = fDatabase.getReference().child("Users").child(user.getUid());

                        DocumentReference df = fStore.collection("Accounts").document(user.getUid());
                        Map<String,Object> userInfo = new HashMap<>();
                        userInfo.put("Full Name",etFullName.getText().toString());
                        userInfo.put("Email",etEmail.getText().toString());
                        userInfo.put("Password",etPassword.getText().toString());
                        userInfo.put("Contact Number",etContactNumber.getText().toString());
                        userInfo.put("User","1");
                        userInfo.put("Chat","1");

                        ModelChatList users = new ModelChatList(FullName, null);

                        reference.setValue(users);
                        df.set(userInfo);

                        Toast.makeText(Register.this, "Your account has been created", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),UserHome.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Register is unsuccessful, please try again.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }

}