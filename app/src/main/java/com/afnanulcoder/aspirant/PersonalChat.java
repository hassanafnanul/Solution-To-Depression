package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonalChat extends AppCompatActivity {


    String theSender, theReceiver, theSenderID, theRecieverID;
    TextView receiverName;
    EditText theMessageTaker;

    //--------------------------------------

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;
    //-----------------------------------------------

    //private ListView chatDetailsListView;
    private List<ChatDetails> chatList;
    private MessageAdapter messageAdapter;
    RecyclerView recyclerView;

    //ChatDetails chatDetails;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);

        theSender = getIntent().getStringExtra("senderName");
        theReceiver = getIntent().getStringExtra("ReceiverName");
        theSenderID = getIntent().getStringExtra("senderID");
        theRecieverID = getIntent().getStringExtra("ReceiverID");

        //Toast.makeText(this, getIntent().getStringExtra("senderID")+".."+getIntent().getStringExtra("senderName")+".."+getIntent().getStringExtra("ReceiverID")+".."+getIntent().getStringExtra("ReceiverName"), Toast.LENGTH_SHORT).show();

        theMessageTaker = findViewById(R.id.theMessageTakerID);
        receiverName = findViewById(R.id.receiverNameID);
        receiverName.setText(theReceiver);



        //---------------Showing MEssage With Recycle View---------
        {
            recyclerView = findViewById(R.id.personalRecycleViewID);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);


            readMessage(theSenderID, theRecieverID);


        }




        /*
        //-----------------------------Showing Message Part--------------
        {
            chatDetails = new ChatDetails();
            chatDetailsListView = findViewById(R.id.chatListViewID);

            databaseReference1 = FirebaseDatabase.getInstance().getReference("chats");
            chatList = new ArrayList<>();

            messageAdapter = new MessageAdapter(PersonalChat.this, R.layout.sender_sample_view, chatList);

        }

         */

    }
/*
    @Override
    protected void onStart() {

        //-----------------------------Showing PeachRoom Part-------------- (It Is Causing Crash...)
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                chatList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    ChatDetails chatDetails = dataSnapshot1.getValue(ChatDetails.class);
                    chatList.add(chatDetails);
                }

                chatDetailsListView.setAdapter(messageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }

 */


    public void SendMessageButton(View view)
    {
        String theMessage = theMessageTaker.getText().toString();

        if(theMessage.isEmpty())
        {
            Toast.makeText(this, "Please Write Something To Send", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ChatDetails chatDetails = new ChatDetails(theSenderID, theSender, theRecieverID, theReceiver, theMessage);

            FirebaseDatabase.getInstance().getReference()
                    .child("chats").push()
                    .setValue(chatDetails);

            theMessageTaker.setText("");

        }
    }

    private void readMessage(final String myID, final String userID)
    {
        chatList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    ChatDetails chatDetails = dataSnapshot1.getValue(ChatDetails.class);
                    if(chatDetails.getSenderID().equals(myID) && chatDetails.getRecieverID().equals(userID) || chatDetails.getSenderID().equals(userID) && chatDetails.getRecieverID().equals(myID))
                    {
                        chatList.add(chatDetails);
                    }

                    messageAdapter = new MessageAdapter(PersonalChat.this, chatList);
                    recyclerView.setAdapter(messageAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
