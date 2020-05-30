package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Home_NeedHelp extends AppCompatActivity {

    String theType, thePeachRoom;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__need_help);

        //---------Get The type & PeachRoom from DataBase Part
        {
            mAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("UserList").child(mAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //theType = dataSnapshot.child("type").getValue().toString();
                    thePeachRoom = dataSnapshot.child("nPeachRoom").getValue().toString();

                    Intent intent = new Intent(Home_NeedHelp.this, WorkStationOfPeachRoom.class);
                    intent.putExtra("thePeachRoom", thePeachRoom);
                    startActivity(intent);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }



    public void UseHeader(View view)
    {
        if(view.getId() == R.id.headerProfileID)
        {
            startActivity(new Intent(this, Profile.class));
        }
        else if(view.getId() == R.id.headerAddID)
        {

        }
        else if(view.getId() == R.id.headerHomeID)
        {
            startActivity(new Intent(Home_NeedHelp.this, Options.class));
        }

    }


}
