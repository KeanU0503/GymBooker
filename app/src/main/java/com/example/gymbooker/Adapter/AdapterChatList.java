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

    ArrayList<ModelChatList> ModelChatList;
    Context context;

    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private String userID;

    public AdapterChatList(ArrayList<ModelChatList> ModelChatList, Context context) {
        this.ModelChatList = ModelChatList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterChatList.ChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_adapter,parent,false);
        return new AdapterChatList.ChatListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatList.ChatListHolder holder, int position) {

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        ModelChatList users = ModelChatList.get(position);
        String names = users.getInName();

        holder.tvDisplayUser.setText(ModelChatList.get(position).getInName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewChatMessages.class);
                intent.putExtra("Full Name",users.getInName());
                intent.putExtra("User ID",users.getInUid());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ModelChatList.size();
    }

    public class ChatListHolder extends RecyclerView.ViewHolder {

        private TextView tvDisplayUser;

        public ChatListHolder(@NonNull View itemView) {
            super(itemView);

            tvDisplayUser = itemView.findViewById(R.id.chatList_displayUser);
        }
    }
}