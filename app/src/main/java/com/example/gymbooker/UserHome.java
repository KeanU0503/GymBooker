package com.example.gymbooker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gymbooker.GymAccess.GymAccess;
import com.example.gymbooker.GymAccess.GymFloorBooking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserHome extends AppCompatActivity {

    TextView tvUser;
    Button GA_button, GH_button, GC_button, btProfile;
    ImageView GA_image, GH_image;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String UserID = fAuth.getCurrentUser().getUid();

    DocumentReference dReference = fStore.collection("Accounts").document(UserID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);
        getSupportActionBar().hide();

        tvUser = findViewById(R.id.show_userName);

        GA_button = findViewById(R.id.user_bookNow);
        GH_button = findViewById(R.id.user_SeeAll);
        GC_button = findViewById(R.id.user_toChats);
        btProfile = findViewById(R.id.user_toProfile);

        GA_image = findViewById(R.id.act_gymAccess_image);
        GH_image = findViewById(R.id.act_gymHistory_image);

        dReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                // setText = Receives a String
                tvUser.setText(documentSnapshot.getString("Full Name"));
            }
        });

        GA_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String passUserName = tvUser.getText().toString();

                Intent toVerify = new Intent(view.getContext(), GymFloorBooking.class);
                toVerify.putExtra("Client Name", passUserName);

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

        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });


    }

    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}

