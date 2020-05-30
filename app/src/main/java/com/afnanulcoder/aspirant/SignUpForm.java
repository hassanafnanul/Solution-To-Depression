package com.afnanulcoder.aspirant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpForm extends AppCompatActivity {

    Button needHelpButton, wantToHelpButton;
    ScrollView needHelpLayout, wantToHelpLayout;

    private FirebaseAuth mAuth;



    //---------------------------------------Need Help Views
    EditText nNameET, nAgeET, nEmailET, nPasswordET, nConfirmPasswordET, nSomethingET;
    RadioButton nMaleRB, nFemaleRB;
    ProgressBar nProgressBar;

    String nName, nAge, nEmail, nPassword, nSomething, nGender;
    int nLevel = 1;
    Button nextButton, signUpButton;

    int countOfYes = 0;

    //------------------------------------------------------

    //------------------------------------Want To Help Views
    EditText wNameET, wAgeET, wEmailET, wPasswordET, wConfirmPasswordET;
    RadioButton wMaleRB, wFemaleRB;
    ProgressBar wProgressBar;

    String wName, wAge, wEmail, wPassword, wGender;

    //------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);


        mAuth = FirebaseAuth.getInstance();


        //Main Button and Layout Identification
        needHelpButton = findViewById(R.id.needHelpButtonID);
        wantToHelpButton = findViewById(R.id.wantToHelpButtonID);

        needHelpLayout = findViewById(R.id.needHelpLayoutId);
        wantToHelpLayout = findViewById(R.id.wantToHelpLayoutId);


        // Need Help Views Identification

        nNameET = findViewById(R.id.needHelpFnameID);
        nAgeET = findViewById(R.id.needHelpAgeID);
        nEmailET = findViewById(R.id.needHelpeMailID);
        nPasswordET = findViewById(R.id.needHelpPasswordID);
        nConfirmPasswordET = findViewById(R.id.needHelpConfirmPasswordID);
        nSomethingET = findViewById(R.id.needHelpAlineID);
        nMaleRB = findViewById(R.id.NeedHelpMaleID);
        nFemaleRB = findViewById(R.id.NeedHelpFemaleID);

        nProgressBar = findViewById(R.id.needHelpProgressBarID);

        nextButton = findViewById(R.id.needHelpNextButtonID);
        signUpButton = findViewById(R.id.needHelpRegistrationButtonID);

        //Want To Help Views Identification

        wNameET = findViewById(R.id.wantToFnameID);
        wAgeET = findViewById(R.id.wantToAgeID);
        wEmailET = findViewById(R.id.wantToeMailID);
        wPasswordET = findViewById(R.id.wantToPasswordID);
        wConfirmPasswordET = findViewById(R.id.wantToConfirmPassID);

        wMaleRB = findViewById(R.id.wantToMailID);
        wFemaleRB = findViewById(R.id.wantToFemailID);

        wProgressBar = findViewById(R.id.wantToProgressBarID);
    }



    public void WantToHelpSignUpNow(View view)
    {

        wName = wNameET.getText().toString().trim();
        wAge = wAgeET.getText().toString().trim();
        wEmail = wEmailET.getText().toString().trim();
        wPassword = wPasswordET.getText().toString().trim();
        if(!wConfirmPasswordET.getText().toString().trim().equals(wPassword))
        {
            wConfirmPasswordET.setError("Password Did Not matched");
            wConfirmPasswordET.requestFocus();
            return;
        }

        if(wMaleRB.isChecked())
            wGender = "Male";
        else if(wFemaleRB.isChecked())
            wGender = "Female";
        else
            wGender = "";

        if(wName.equals("") || wAge.equals("") || wEmail.equals("") || wPassword.equals("") || wGender.equals(""))
        {
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(wEmail).matches())
        {
            wEmailET.setError("Enter Valid Email");
            wEmailET.requestFocus();
            return;
        }

        if(wPassword.length()<6)
        {
            wPasswordET.setError("Password Must Be More Than 6 Character");
            wPasswordET.requestFocus();
            return;
        }

        wProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(wEmail, wPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                wProgressBar.setVisibility(View.INVISIBLE);

                if (task.isSuccessful()) {

                    UserInformations userInformations = new UserInformations("WantTo", wName+"*", wAge, wEmail, wGender);

                    FirebaseDatabase.getInstance().getReference("UserList")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userInformations).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SignUpForm.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpForm.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {

                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(SignUpForm.this, "User Already Registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignUpForm.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void NeedHelpSignUpNow(View view)
    {

        nName = nNameET.getText().toString().trim();
        nAge = nAgeET.getText().toString().trim();
        nEmail = nEmailET.getText().toString().trim();
        nPassword = nPasswordET.getText().toString().trim();
        if(!nConfirmPasswordET.getText().toString().trim().equals(nPassword))
        {
            nConfirmPasswordET.setError("Password Did Not matched");
            nConfirmPasswordET.requestFocus();
            return;
        }

        nSomething = nSomethingET.getText().toString().trim();

        if(nMaleRB.isChecked())
            nGender = "Male";
        else if(nFemaleRB.isChecked())
            nGender = "Female";
        else
            nGender = "";

        if(nName.equals("") || nAge.equals("") || nEmail.equals("") || nPassword.equals("") || nGender.equals("") || nSomething.equals(""))
        {
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(nEmail).matches())
        {
            nEmailET.setError("Enter Valid Email");
            nEmailET.requestFocus();
            return;
        }

        if(nPassword.length()<6)
        {
            nPasswordET.setError("Password Must Be More Than 6 Character");
            nPasswordET.requestFocus();
            return;
        }

        Intent intent = new Intent(this, GetYourPeachRoom.class);
        intent.putExtra("nName", nName);
        intent.putExtra("nAge", nAge);
        intent.putExtra("nEmail", nEmail);
        intent.putExtra("nPassword", nPassword);
        intent.putExtra("nSomething", nSomething);
        intent.putExtra("nGender", nGender);
        intent.putExtra("nLevel", nLevel);
        startActivity(intent);

    }

    public void NeedHelpNext(View view)
    {

        final AlertDialog.Builder alert = new AlertDialog.Builder(SignUpForm.this);

        View myView = getLayoutInflater().inflate(R.layout.questions_for_leveling, null);


        final RadioButton yes1 = myView.findViewById(R.id.yes1ID);
        final RadioButton yes2 = myView.findViewById(R.id.yes2ID);
        final RadioButton yes3 = myView.findViewById(R.id.yes3ID);
        final RadioButton yes4 = myView.findViewById(R.id.yes4ID);
        final RadioButton yes5 = myView.findViewById(R.id.yes5ID);
        final RadioButton yes6 = myView.findViewById(R.id.yes6ID);
        final RadioButton yes7 = myView.findViewById(R.id.yes7ID);
        final RadioButton yes8 = myView.findViewById(R.id.yes8ID);
        final RadioButton yes9 = myView.findViewById(R.id.yes9ID);
        final Button submitButton = myView.findViewById(R.id.answerSubmitButtonID);

        alert.setView(myView);

        final AlertDialog alertDialog = alert.create();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(yes1.isChecked())
                {
                    countOfYes++;
                }
                if(yes2.isChecked())
                {
                    countOfYes++;
                }
                if(yes3.isChecked())
                {
                    countOfYes++;
                }
                if(yes4.isChecked())
                {
                    countOfYes++;
                }
                if(yes5.isChecked())
                {
                    countOfYes++;
                }
                if(yes6.isChecked())
                {
                    countOfYes++;
                }
                if(yes7.isChecked())
                {
                    countOfYes++;
                }
                if(yes8.isChecked())
                {
                    countOfYes++;
                }
                if(yes9.isChecked())
                {
                    countOfYes++;
                }
                else {

                }

                if(countOfYes<=3)
                    nLevel = 1;
                else if(countOfYes<=6)
                    nLevel = 2;
                else if(countOfYes<=9)
                    nLevel = 3;

                Toast.makeText(SignUpForm.this, "You are now Level: "+nLevel, Toast.LENGTH_SHORT).show();

                signUpButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.INVISIBLE);

                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

    public void NeedHelpButton(View view)
    {
        needHelpLayout.setVisibility(View.VISIBLE);
        wantToHelpButton.setVisibility(View.INVISIBLE);
    }

    public void WantToHelpButton(View view)
    {
        needHelpButton.setVisibility(View.INVISIBLE);
        wantToHelpLayout.setVisibility(View.VISIBLE);
    }


}
