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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewChatMessages extends AppCompatActivity {

    private FirebaseDatabase fDatabase = FirebaseDatabase.getInstance("https://gymbooker-4c510-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private DatabaseReference dReference;

    ArrayList<ModelViewChatMessages> ModelViewChatMessages;

    TextView tvTopName;
    EditText etMessage;
    ImageView ivSend;

    private String ReceiverName, ReceiverUID, SenderUID;
    private String SenderRoom, ReceiverRoom, UserID;

    RecyclerView RecMessages;
    AdapterViewChatMessages AdapterViewChatMessages;

    Calendar calendar;

    String currentTime;
    SimpleDateFormat simpleDateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_chat_messages);
        getSupportActionBar().hide();

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd MMM yyy hh:mm a");

        tvTopName = findViewById(R.id.viewChatMessages_topName);
        etMessage = findViewById(R.id.viewChatMessages_typeMessages);
        ivSend = findViewById(R.id.viewChatMessages_sendMessages);
        RecMessages = findViewById(R.id.RV_ViewChatMessages);

        UserID = fAuth.getCurrentUser().getUid();
        SenderUID = fAuth.getUid();

        ReceiverName = getIntent().getStringExtra("Full Name");
        ReceiverUID = getIntent().getStringExtra("User ID");

        tvTopName.setText("" + ReceiverName);

        ModelViewChatMessages = new ArrayList<>();

        SenderRoom = SenderUID + ReceiverUID;
        ReceiverRoom = ReceiverUID + SenderUID;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        RecMessages.setLayoutManager(linearLayoutManager);
        AdapterViewChatMessages = new AdapterViewChatMessages(ViewChatMessages.this, ModelViewChatMessages); // Pass context and arraylist
        RecMessages.setAdapter(AdapterViewChatMessages);


        dReference = fDatabase.getReference().child("Chats").child(SenderRoom).child("Messages");
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ModelViewChatMessages.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ModelViewChatMessages messages = dataSnapshot.getValue(ModelViewChatMessages.class);
                    ModelViewChatMessages.add(messages);
                }

                AdapterViewChatMessages.notifyDataSetChanged();
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
                    Toast.makeText(ViewChatMessages.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                    return;
                }
                etMessage.setText("");

                Date date = new Date();
                currentTime = simpleDateFormat.format(calendar.getTime());
                ModelViewChatMessages messages = new ModelViewChatMessages(message,SenderUID,date.getTime(),currentTime);

                fDatabase = FirebaseDatabase.getInstance();
                fDatabase.getReference().child("Chats")
                        .child(SenderRoom)
                        .child("Messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                fDatabase.getReference().child("Chats")
                                        .child(ReceiverRoom)
                                        .child("Messages")
                                        .push()
                                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
            }
        });

    }
}