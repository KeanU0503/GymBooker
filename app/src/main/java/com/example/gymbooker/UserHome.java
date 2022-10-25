package com.example.gymbooker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserHome extends AppCompatActivity {

    TextView Welcome, User, GymAccess, GymHistory, GA_description, GH_description;
    Button GA_button, GH_button, GC_button;
    ImageView GA_image, GH_image;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID = fAuth.getCurrentUser().getUid();

    DocumentReference dReference = fStore.collection("Accounts").document(userID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Welcome = findViewById(R.id.user_Welcome);
        User = findViewById(R.id.actUser);
        GymAccess = findViewById(R.id.act_tv_gymAccess);
        GymHistory = findViewById(R.id.act_tv_gymHistory);
        GA_description = findViewById(R.id.act_gymAccess_des);
        GH_description = findViewById(R.id.act_gym_userHistory_des);

        GA_button = findViewById(R.id.act_user_bookNow);
        GH_button = findViewById(R.id.act_user_SeeAll);
        GC_button = findViewById(R.id.act_user_toChats);

        GA_image = findViewById(R.id.act_gymAccess_image);
        GH_image = findViewById(R.id.act_gymHistory_image);

        // Listen to data changes or retrieve in the database
        dReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                // setText = Receives a String
                User.setText(documentSnapshot.getString("FullName"));
            }
        });

        GA_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GymAccess.class));
            }
        });

        GH_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GymHistory.class));
            }
        });

        GC_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChatList.class));
            }
        });
    }

}

