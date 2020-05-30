package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.Objects;

public class MemberDetailesLst extends AppCompatActivity {

    String theType, thePeachRoom, theName;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;
    //---------------------------------------------


    private ListView membersListView;
    private List<UserInformations> memberList;
    private CustomAdapterForMemberList customAdapterForMemberList;
    UserInformations userInformations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detailes_lst);


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
            userInformations = new UserInformations();
            membersListView = findViewById(R.id.memberDetailsListViewID);

            databaseReference1 = FirebaseDatabase.getInstance().getReference("PeachRoomList")
                    .child(thePeachRoom).child("Members");

            memberList = new ArrayList<UserInformations>();
            customAdapterForMemberList = new CustomAdapterForMemberList(MemberDetailesLst.this, memberList);

        }

        membersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemberDetailesLst.this, OnnoderProfile.class);
                intent.putExtra("selectedMemberID", memberList.get(position).getMemberKey());
                intent.putExtra("currentUserType", theType);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {

        //-----------------------------Showing PeachRoom Part-------------- (It Is Causing Crash...)
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                memberList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {

                    UserInformations userInformations = new UserInformations(dataSnapshot1.getValue().toString(), dataSnapshot1.getKey().toString());

                    //UserInformations userInformations = dataSnapshot1.getValue(UserInformations.class);
                    memberList.add(userInformations);
                }

                membersListView.setAdapter(customAdapterForMemberList);

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
            final AlertDialog.Builder alert = new AlertDialog.Builder(MemberDetailesLst.this);

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
                        Toast.makeText(MemberDetailesLst.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        PostDetails postDetails = new PostDetails(postET.getText().toString(), theName, 0);
                        FirebaseDatabase.getInstance().getReference("PeachRoomList")
                                .child(thePeachRoom).child("Posts").push()
                                .setValue(postDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MemberDetailesLst.this, "Post Uploaded", Toast.LENGTH_SHORT).show();

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
            startActivity(new Intent(MemberDetailesLst.this, Options.class));

        }

    }



    public void GoToPersonalChat(View view)
    {
        UserInformations userInformations = (UserInformations) view.getTag();

        if(mAuth.getUid().equals(userInformations.getMemberKey()))
        {
            Toast.makeText(this, "You Can Not Send Message To Yourself", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this, PersonalChat.class);
            intent.putExtra("senderID", mAuth.getUid());
            intent.putExtra("senderName", theName);
            intent.putExtra("ReceiverID", userInformations.getMemberKey());
            intent.putExtra("ReceiverName", userInformations.getMemberName());
            startActivity(intent);
        }



    }
}
