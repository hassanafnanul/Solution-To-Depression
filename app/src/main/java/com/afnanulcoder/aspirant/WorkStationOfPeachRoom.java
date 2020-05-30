package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.List;

public class WorkStationOfPeachRoom extends AppCompatActivity {

    String theType, thePeachRoom, theName;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;
    //-----------------------------------------------


    private ListView postListView;
    private List<PostDetails> postList;
    private CustomAdapterForPosts customAdapterForPosts;
    PostDetails postDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_station_of_peach_room);

        //---------Get The type & PeachRoom from DataBase Part
        {
            thePeachRoom = getIntent().getStringExtra("thePeachRoom");

            mAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("UserList").child(mAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    theType = dataSnapshot.child("type").getValue().toString();
                    if(theType.equals("WantTo"))
                    {
                        theName = dataSnapshot.child("wName").getValue().toString();
                    }
                    else
                    {
                        theName = dataSnapshot.child("nName").getValue().toString();
                    }
                    //thePeachRoom = dataSnapshot.child("nPeachRoom").getValue().toString();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //-----------------------------Showing Post Part--------------
        {
            postDetails = new PostDetails();
            postListView = findViewById(R.id.postListViewID);

            databaseReference1 = FirebaseDatabase.getInstance().getReference("PeachRoomList")
                    .child(thePeachRoom).child("Posts");
            postList = new ArrayList<>();
            customAdapterForPosts = new CustomAdapterForPosts(WorkStationOfPeachRoom.this, postList);

        }


        //--------------------To Like The Post------------
        {
            // ---- Ekhono kisu hoy nai..... :'(
        }


        //-------------Setting PeachRoom Name------------
        {
            TextView peachRoomNameTV= findViewById(R.id.peachRoomNameID);
            peachRoomNameTV.setText(thePeachRoom);
        }



    }



    @Override
    protected void onStart() {

        //-----------------------------Showing PeachRoom Part-------------- (It Is Causing Crash...)
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                postList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    PostDetails postDetails = dataSnapshot1.getValue(PostDetails.class);
                    postList.add(postDetails);
                }

                postListView.setAdapter(customAdapterForPosts);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }





    public void UseHeader(View view)
    {
        if(view.getId() == R.id.headerProfileID)
        {
            startActivity(new Intent(this, Profile.class));
        }
        else if(view.getId() == R.id.headerAddID)
        {
            Toast.makeText(this, "Add Post", Toast.LENGTH_SHORT).show();
            //-----------------------------Add New PeachRoom Part-------------- (It Is Working Well)
            final AlertDialog.Builder alert = new AlertDialog.Builder(WorkStationOfPeachRoom.this);

            View myView = getLayoutInflater().inflate(R.layout.add_post_form, null);


            final EditText postET = myView.findViewById(R.id.postEtId);
            final Button submitPost = myView.findViewById(R.id.submitPostButtonID);

            alert.setView(myView);

            final AlertDialog alertDialog = alert.create();

            submitPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(postET.getText().equals(""))
                    {
                        Toast.makeText(WorkStationOfPeachRoom.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        PostDetails postDetails = new PostDetails(postET.getText().toString(), theName, 0);
                        FirebaseDatabase.getInstance().getReference("PeachRoomList")
                                .child(thePeachRoom).child("Posts").push()
                                .setValue(postDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(WorkStationOfPeachRoom.this, "Post Uploaded", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        });
                    }
                }
            });

            alertDialog.show();
        }
        else if(view.getId() == R.id.headerHomeID)
        {
            startActivity(new Intent(WorkStationOfPeachRoom.this, Options.class));

        }

    }



    public void GoToMemberDetailesLst(View view)
    {
        Intent intent = new Intent(this, MemberDetailesLst.class);
        intent.putExtra("thePeachRoom", thePeachRoom);
        startActivity(intent);
    }

    public void GoToGroupChatRoom(View view)
    {

        //Toast.makeText(this, theName+".."+thePeachRoom+".."+mAuth.getUid(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, GroupChatRoom.class);
        intent.putExtra("senderName", theName);
        intent.putExtra("thePeachRoom", thePeachRoom);
        intent.putExtra("senderID", mAuth.getUid());
        startActivity(intent);

    }
}
