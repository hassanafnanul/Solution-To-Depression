package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String theType = "Please Wait and Try Again";
    boolean isLogInSuccessfull = false;

    LinearLayout logIn;
    RelativeLayout buttonLayout;

    Button logInButton;
    EditText userNameET, passwordET;

    String uName, password;

    ProgressBar progressBar;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            logIn.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        logIn = findViewById(R.id.logIn);
        buttonLayout = findViewById(R.id.buttonLayout);

        logInButton = findViewById(R.id.logInButtonID);
        userNameET = findViewById(R.id.UNameID);
        passwordET = findViewById(R.id.PassID);

        progressBar = findViewById(R.id.progressBarID);

        handler.postDelayed(runnable, 2000);

    }

    public void LogInButton(View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        uName = userNameET.getText().toString().trim();
        password = passwordET.getText().toString().trim();



        if(!Patterns.EMAIL_ADDRESS.matcher(uName).matches())
        {
            userNameET.setError("Enter Valid Email");
            userNameET.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            passwordET.setError("Password Must Be More Than 6 Character");
            passwordET.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(uName, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    progressBar.setVisibility(View.INVISIBLE);
                    userNameET.setText("");
                    passwordET.setText("");
                    Intent intent = new Intent(MainActivity.this, Options.class);
                    intent.addFlags(    Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Log In Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void SignUpButton(View view)
    {
        Intent intent = new Intent(this, SignUpForm.class);
        startActivity(intent);
    }
}
