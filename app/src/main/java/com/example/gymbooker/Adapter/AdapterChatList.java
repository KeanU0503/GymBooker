package com.example.gymbooker.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.gymbooker.Model.ModelChatList;
import com.example.gymbooker.R;
import com.example.gymbooker.ViewChatMessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterChatList extends RecyclerView.Adapter<AdapterChatList.ChatListHolder> {

    ArrayList<ModelChatList> arrayModelChatList;
    Context context;

    private FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    String userID;

    public AdapterChatList(ArrayList<ModelChatList> arrayModelChatList, Context context) {
        this.arrayModelChatList = arrayModelChatList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterChatList.ChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_chat_list,parent,false);
        return new AdapterChatList.ChatListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatList.ChatListHolder holder, int position) {

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID=firebaseAuth.getCurrentUser().getUid();

        final ModelChatList users = arrayModelChatList.get(position);
        final String names = users.getInName();

        holder.tvDisplayUser.setText(arrayModelChatList.get(position).getInName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // context - homeActivity
                Intent intent = new Intent(context, ViewChatMessages.class);
                intent.putExtra("Name",users.getInName());
                intent.putExtra("Uid",users.getInUid());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayModelChatList.size();
    }

    public class ChatListHolder extends RecyclerView.ViewHolder {

        private TextView tvDisplayUser;

        public ChatListHolder(@NonNull View itemView) {
            super(itemView);

            tvDisplayUser = itemView.findViewById(R.id.chatList_displayUser);
        }
    }
}