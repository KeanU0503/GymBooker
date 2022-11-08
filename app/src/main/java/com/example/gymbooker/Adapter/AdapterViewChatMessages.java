package com.example.gymbooker.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.gymbooker.Model.ModelViewChatMessages;
import com.example.gymbooker.R;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class AdapterViewChatMessages extends RecyclerView.Adapter {

    Context context;
    ArrayList<ModelViewChatMessages> ModelViewChatMessages;

    int viewSender = 1;
    int viewReceiver = 2;

    public AdapterViewChatMessages(Context context, ArrayList<ModelViewChatMessages> ModelViewChatMessages) {
        this.context = context;
        this.ModelViewChatMessages = ModelViewChatMessages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == viewSender)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelViewChatMessages messages = ModelViewChatMessages.get(position);

        if(holder.getClass() == SenderViewHolder.class)
        {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.tvMessages.setText(messages.getMessage());
            viewHolder.tvTime.setText(messages.getCurrentTime());

        }else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.tvMessages.setText(messages.getMessage());
            viewHolder.tvTime.setText(messages.getCurrentTime());
        }
    }

    @Override
    public int getItemCount() {
        return ModelViewChatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        ModelViewChatMessages messages = ModelViewChatMessages.get(position);

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId()))
        {
            return viewSender;
        }else {
            return viewReceiver;
        }
    }

    // 2 view holder because we have 2 layout outs
    class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessages, tvTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessages = itemView.findViewById(R.id.text_sendMessages);
            tvTime = itemView.findViewById(R.id.display_senderTime);
        }
    }

     class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessages, tvTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessages = itemView.findViewById(R.id.text_receiveMessages);
            tvTime = itemView.findViewById(R.id.display_receiverTime);
        }
    }

}