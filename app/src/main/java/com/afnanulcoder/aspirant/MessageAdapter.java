package com.afnanulcoder.aspirant;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context myContext;
    private List<ChatDetails> chatList;


    FirebaseUser theCurrentUser;

    public MessageAdapter(Context myContext, List<ChatDetails> chatList) {
        this.myContext = myContext;
        this.chatList = chatList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(myContext).inflate(R.layout.sender_sample_view, parent, false);
            return new ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(myContext).inflate(R.layout.receiver_sample_view, parent, false);
            return new ViewHolder(view);
        }


    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChatDetails chatDetails = chatList.get(position);

        holder.showMessage.setText(chatDetails.getTheMessage());
        holder.senderNameForReceiver.setText(chatDetails.getSenderName());

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView showMessage;
        public TextView senderNameForReceiver;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.showMessageID);
            senderNameForReceiver = itemView.findViewById(R.id.senderNameForReceiverID);

        }
    }


    @Override
    public int getItemViewType(int position) {
        theCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(chatList.get(position).getSenderID().equals(theCurrentUser.getUid()))
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;
    }
}