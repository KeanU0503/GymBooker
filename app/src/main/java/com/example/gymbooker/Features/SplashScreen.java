package com.example.gymbooker.Features;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.gymbooker.Admin.AdminHome;
import com.example.gymbooker.Login;
import com.example.gymbooker.R;
import com.example.gymbooker.UserHome;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();

                if(FirebaseAuth.getInstance().getCurrentUser() != null) {

                    DocumentReference dReference = FirebaseFirestore.getInstance().collection("Accounts")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    dReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.getString("Admin") != null){
                                startActivity(new Intent(SplashScreen.this, AdminHome.class));
                                finish();
                            }

                            if(documentSnapshot.getString("User") != null){
                                startActivity(new Intent(SplashScreen.this, UserHome.class));
                                finish();
                            }
                        }
                    });
                }
            }
        },SPLASH_TIME_OUT);

    }

}