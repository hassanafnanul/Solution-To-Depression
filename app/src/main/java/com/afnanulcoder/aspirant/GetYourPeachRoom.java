package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetYourPeachRoom extends AppCompatActivity {

    Button singUpButton;
    ListView peachRoomListView;

    String theType;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;
    //------------------------------------


    private List<PeachRoomInfo> peachRoomList;
    private CustomAdapter customAdapter;
    PeachRoomInfo peachRoomInfo;


    int selectedPeachRoomMemberNumber = 0;


    String nName, nAge, nEmail, nSomething, nGender, nPassword, nSelectedPeachRoom;
    int nLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_your_peach_room);

        mAuth = FirebaseAuth.getInstance();

        nName = getIntent().getStringExtra("nName");
        nAge = getIntent().getStringExtra("nAge");
        nEmail = getIntent().getStringExtra("nEmail");
        nPassword = getIntent().getStringExtra("nPassword");
        nSomething = getIntent().getStringExtra("nSomething");
        nGender = getIntent().getStringExtra("nGender");
        nLevel = getIntent().getIntExtra("nLevel", 1);


        //Toast.makeText(this, nEmail+"."+nPassword+"."+nName+"."+nAge+"."+nSomething+"."+nGender+"."+nLevel, Toast.LENGTH_SHORT).show();


        peachRoomInfo = new PeachRoomInfo();
        peachRoomListView = findViewById(R.id.peachRoomListViewID);

        databaseReference1 = FirebaseDatabase.getInstance().getReference("PeachRoomList");
        //   .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PeachRoom");
        peachRoomList = new ArrayList<>();
        customAdapter = new CustomAdapter(GetYourPeachRoom.this, peachRoomList);



        singUpButton = findViewById(R.id.finalNeedSignUpButtonID);

        peachRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                singUpButton.setVisibility(View.VISIBLE);
                nSelectedPeachRoom = peachRoomList.get(position).getPrName();
            }
        });
    }



    @Override
    protected void onStart() {


        //-----------------------------Showing PeachRoom Part-------------- (It Is Causing Crash...)
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                peachRoomList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    /*String prName = dataSnapshot1.child("prName").getValue().toString();
                    String prCategory = dataSnapshot1.child("prCategory").getValue().toString();
                    String prSDescription = dataSnapshot1.child("prSDescription").getValue().toString();
                    int prMember = Integer.parseInt(dataSnapshot1.child("prMember").getValue().toString());
                    String prAdmin = dataSnapshot1.child("prAdmin").getValue().toString();


                    PeachRoomInfo peachRoomInfo = new PeachRoomInfo(prName, prCategory, prSDescription, prMember, prAdmin);


                    Toast.makeText(WantToHome.this, peachRoomInfo.getPrName()+"."+peachRoomInfo.getPrCategory()+"."+peachRoomInfo.getPrSDescription()+"."+peachRoomInfo.getPrMember(), Toast.LENGTH_SHORT).show();
                     */


                    PeachRoomInfo peachRoomInfo = dataSnapshot1.getValue(PeachRoomInfo.class);
                    if(peachRoomInfo.getPrLevel() == nLevel)
                        peachRoomList.add(peachRoomInfo);

                }

                peachRoomListView.setAdapter(customAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }


    public void NeedHelpFinalSignUp(View view)
    {
        Toast.makeText(this, "Processing\nPlease Wait", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Processing\nPlease Wait", Toast.LENGTH_LONG).show();

        mAuth.createUserWithEmailAndPassword(nEmail, nPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    UserInformations userInformations = new UserInformations("Need", nName, nAge, nEmail, nSomething, nGender, nSelectedPeachRoom, nLevel);

                    FirebaseDatabase.getInstance().getReference("UserList")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userInformations).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(GetYourPeachRoom.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(GetYourPeachRoom.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    //--------------Set Name in Member list of peachRoom-----
                    FirebaseDatabase.getInstance().getReference("PeachRoomList")
                            .child(nSelectedPeachRoom).child("Members").child(mAuth.getCurrentUser().getUid()).setValue(nName);

                    //-----------------Increase Member Number------------
                    FirebaseDatabase.getInstance().getReference("PeachRoomList")
                            .child(nSelectedPeachRoom).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            selectedPeachRoomMemberNumber = Integer.parseInt(dataSnapshot.child("prMember").getValue().toString());
                            //Toast.makeText(GetYourPeachRoom.this, dataSnapshot.child("prMember").getValue(Integer.class), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    selectedPeachRoomMemberNumber++;
                                        /*
                    FirebaseDatabase.getInstance().getReference("PeachRoomList")
                            .child(nSelectedPeachRoom).child("prMember").setValue(selectedPeachRoomMemberNumber);

                     */

                }
                else {

                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(GetYourPeachRoom.this, "User Already Registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(GetYourPeachRoom.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
