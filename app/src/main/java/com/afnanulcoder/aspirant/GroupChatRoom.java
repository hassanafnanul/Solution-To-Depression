package com.afnanulcoder.aspirant;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupChatRoom extends AppCompatActivity {

    String theSender, thePeachRoom, theSenderID;
    TextView peachRoomName;
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
        setContentView(R.layout.activity_group_chat_room);

        theSender = getIntent().getStringExtra("senderName");
        thePeachRoom = getIntent().getStringExtra("thePeachRoom");
        theSenderID = getIntent().getStringExtra("senderID");

        //Toast.makeText(this, getIntent().getStringExtra("senderID")+".."+getIntent().getStringExtra("senderName")+".."+getIntent().getStringExtra("ReceiverID")+".."+getIntent().getStringExtra("ReceiverName"), Toast.LENGTH_SHORT).show();

        theMessageTaker = findViewById(R.id.theMessageTakerID);
        peachRoomName = findViewById(R.id.peachRoomNameID);
        peachRoomName.setText(thePeachRoom);


        //---------------Showing MEssage With Recycle View---------
        {
            recyclerView = findViewById(R.id.personalRecycleViewID);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);


            readMessage(theSenderID, thePeachRoom);


        }



    }


    public void SendMessageButton(View view)
    {
        String theMessage = theMessageTaker.getText().toString();

        if(theMessage.isEmpty())
        {
            Toast.makeText(this, "Please Write Something To Send", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ChatDetails chatDetails = new ChatDetails(theSenderID, theSender, thePeachRoom, theMessage);

            FirebaseDatabase.getInstance().getReference()
                    .child("GroupChats").push()
                    .setValue(chatDetails);

            theMessageTaker.setText("");

        }
    }


    private void readMessage(final String myID, final String peachRoomName)
    {
        chatList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("GroupChats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    ChatDetails chatDetails = dataSnapshot1.getValue(ChatDetails.class);
                    if(chatDetails.getThePeachRoom().equals(peachRoomName))
                    {
                        chatList.add(chatDetails);
                    }

                    messageAdapter = new MessageAdapter(GroupChatRoom.this, chatList);
                    recyclerView.setAdapter(messageAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
