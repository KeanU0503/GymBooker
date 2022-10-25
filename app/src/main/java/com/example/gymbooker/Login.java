package com.example.gymbooker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private String Email, Password;
    private EditText etEmail, etPassword;
    Button btLogin;
    TextView tvToRegister;
    ImageView ilDumBell;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ilDumBell = findViewById(R.id.log_dumBell);
        etEmail = findViewById(R.id.atyEmailLogin);
        etPassword = findViewById(R.id.atyPasswordLogin);
        tvToRegister = findViewById(R.id.atyToRegister);
        btLogin = findViewById(R.id.atyLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Email = etEmail.getText().toString().trim();
                Password = etPassword.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    etEmail.setError("Please enter your email.");
                    etEmail.requestFocus();
                    return;
                }

                if(Password.length() < 8){
                    etPassword.setError("At least 8 character are required.");
                    etPassword.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    etPassword.setError("Please enter your password.");
                    etPassword.requestFocus();
                    return;
                }

                fAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                        checkUserType(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Login is unsuccessful, email or password is incorrect.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }

    // Check user or admin
    private void checkUserType(String uid) {
        DocumentReference dReference = fStore.collection("Accounts").document(uid);
        dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","Login Type" + documentSnapshot.getData());

                if(documentSnapshot.getString("isAdmin") != null){
                    startActivity(new Intent(getApplicationContext(),AdminHome.class));
                    finish();
                }

                if(documentSnapshot.getString("isUser") != null){
                    startActivity(new Intent(getApplicationContext(),UserHome.class));
                    finish();
                }
            }
        });
    }

}