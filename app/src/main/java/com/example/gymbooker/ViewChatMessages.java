package com.example.gymbooker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymbooker.Adapter.AdapterViewChatMessages;
import com.example.gymbooker.Model.ModelViewChatMessages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;

public class ViewChatMessages extends AppCompatActivity {

    FirebaseDatabase fDatabase = FirebaseDatabase.getInstance("https://gymbooker-4c510-default-rtdb.asia-southeast1.firebasedatabase.app/");
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    DatabaseReference dReference;

    ArrayList<ModelViewChatMessages> ModelViewChatMessages;

    TextView tvTopName;
    EditText etMessage;
    ImageView ivSend;

    private String ReceiverName, ReceiverUserID, SenderUserID;
    private String SenderRoom, ReceiverRoom, UserID;

    RecyclerView RecMessages;
    AdapterViewChatMessages AdapterViewChatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chat_messages);

        tvTopName = findViewById(R.id.viewChatMessages_topName);
        etMessage = findViewById(R.id.viewChatMessages_typeMessages);
        ivSend = findViewById(R.id.viewChatMessages_sendMessages);
        RecMessages = findViewById(R.id.RV_ViewChatMessages);

        UserID = fAuth.getCurrentUser().getUid();
        SenderUserID = fAuth.getUid();

        ReceiverName = getIntent().getStringExtra("Name");
        ReceiverUserID = getIntent().getStringExtra("Uid");

        tvTopName.setText("" + ReceiverName);

        ModelViewChatMessages = new ArrayList<>();

        SenderRoom = SenderUserID + ReceiverUserID;
        ReceiverRoom = ReceiverUserID + SenderUserID;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        RecMessages.setLayoutManager(linearLayoutManager); // Set recyclerView to linearlayout Manager
        AdapterViewChatMessages = new AdapterViewChatMessages(ViewChatMessages.this, ModelViewChatMessages); // Pass context and arraylist
        RecMessages.setAdapter(AdapterViewChatMessages);

        // Fetch the data from RT-Database and store the messages in the arraylist
        dReference = fDatabase.getReference().child("Chats").child(SenderRoom).child("Messages");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ModelViewChatMessages.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) // Gives access to all of the immediate children of this snapshot. // Take Message 1 by 1
                {
                    // Pass it to our arraylist
                    ModelViewChatMessages messages = dataSnapshot.getValue(ModelViewChatMessages.class);
                    ModelViewChatMessages.add(messages);
                }

                AdapterViewChatMessages.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dReference = fDatabase.getReference().child("Users").child(fAuth.getUid());
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();

                if(message.isEmpty())
                {
                    Toast.makeText(ViewChatMessages.this, "Pls enter a message", Toast.LENGTH_SHORT).show();
                    return;
                }
                etMessage.setText("");

                Date date = new Date();
                ModelViewChatMessages messages = new ModelViewChatMessages(message,SenderUserID,date.getTime()); // Object of the model

                fDatabase = FirebaseDatabase.getInstance();
                fDatabase.getReference().child("Chats")
                        .child(SenderRoom)
                        .child("Messages")
                        .push() // Messages contain all four things of the model
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) { // If send successfully send to receiver room
                                fDatabase.getReference().child("Chats")
                                        .child(ReceiverRoom)
                                        .child("Messages")
                                        .push()
                                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // Display toast by choice
                                            }
                                        });
                            }
                        });
            }
        });

    }
}