package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Options extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String theType = "Please Wait for a while and Try Again";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UserList").child(mAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                theType = dataSnapshot.child("type").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void GoToHome(View view)
    {

        Toast.makeText(Options.this, theType, Toast.LENGTH_SHORT).show();

        if(theType.equals("Need"))
        {
            Intent intent = new Intent(Options.this, Home_NeedHelp.class);
            intent.addFlags(    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(theType.equals("WantTo"))
        {
            Intent intent = new Intent(Options.this, WantToHome.class);
            //Intent intent = new Intent(Options.this, WantToTestHome.class);
            intent.addFlags(    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
            Toast.makeText(Options.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToAbout(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void GoToGames(View view) {
        Intent intent = new Intent(this, Games.class);
        startActivity(intent);
    }

    public void GoToProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}
