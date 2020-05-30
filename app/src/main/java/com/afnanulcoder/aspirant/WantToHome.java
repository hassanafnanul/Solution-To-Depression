package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class WantToHome extends AppCompatActivity {

    String theType, theName;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;
    //------------------------------------


    private ListView peachRoomListView;
    private List<PeachRoomInfo> peachRoomList;
    private CustomAdapter customAdapter;
    PeachRoomInfo peachRoomInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_home);

        //---------Get The type & PeachRoom from DataBase Part
        {
            mAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("UserList").child(mAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    theType = dataSnapshot.child("type").getValue().toString();
                    theName = dataSnapshot.child("wName").getValue().toString();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //-----------------------------Showing PeachRoom Part-------------- (It Is Causing Crash...)
        peachRoomInfo = new PeachRoomInfo();
        peachRoomListView = findViewById(R.id.peachRoomListViewID);

        databaseReference1 = FirebaseDatabase.getInstance().getReference("PeachRoomList");
        peachRoomList = new ArrayList<>();
        customAdapter = new CustomAdapter(WantToHome.this, peachRoomList);

        peachRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPrName = peachRoomList.get(position).getPrName();

                Toast.makeText(WantToHome.this, selectedPrName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(WantToHome.this, WorkStationOfPeachRoom.class);
                intent.putExtra("thePeachRoom", selectedPrName);
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


                    if(dataSnapshot1.child("prAdmin").getValue().toString().equals(mAuth.getUid()))
                    {
                        PeachRoomInfo peachRoomInfo = dataSnapshot1.getValue(PeachRoomInfo.class);
                        peachRoomList.add(peachRoomInfo);
                    }


                }

                peachRoomListView.setAdapter(customAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }


    public void AddPeachRoom(View view)
    {
        //-----------------------------Add New PeachRoom Part-------------- (It Is Working Well)
        final AlertDialog.Builder alert = new AlertDialog.Builder(WantToHome.this);

        View myView = getLayoutInflater().inflate(R.layout.add_peach_room_form, null);

        final EditText prNameET = myView.findViewById(R.id.peachRoomNameID);
        final EditText prCategoryET = myView.findViewById(R.id.peachRoomCategoryID);
        final EditText prSDescriptionET = myView.findViewById(R.id.peachRoomSDescriptionID);
        final EditText prLevelET = myView.findViewById(R.id.peachRoomLevelID);
        final Button prButton = myView.findViewById(R.id.peachRoomButtonID);

        alert.setView(myView);

        final AlertDialog alertDialog = alert.create();

        prButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String prName = prNameET.getText().toString();
                if(prName.equals("") || prCategoryET.getText().toString().equals("") || prSDescriptionET.getText().toString().equals("") || prLevelET.getText().toString().equals(""))
                {
                    Toast.makeText(WantToHome.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(prLevelET.getText().toString())>3 && Integer.parseInt(prLevelET.getText().toString())<0)
                {
                    Toast.makeText(WantToHome.this, "Please Enter From 0 to 3\notherwise it will be unused", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    PeachRoomInfo peachRoom = new PeachRoomInfo(prNameET.getText().toString(), prCategoryET.getText().toString(), prSDescriptionET.getText().toString(), 1, mAuth.getUid(), Integer.parseInt(prLevelET.getText().toString()));

                    FirebaseDatabase.getInstance().getReference("PeachRoomList")
                            .child(prNameET.getText().toString())
                            .setValue(peachRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(WantToHome.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(WantToHome.this, WantToHome.class));
                        }
                    });

                    FirebaseDatabase.getInstance().getReference("PeachRoomList")
                            .child(prName).child("Members").child(mAuth.getCurrentUser().getUid()).setValue(theName);
                }
            }
        });

        alertDialog.show();

    }
}
