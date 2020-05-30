package com.afnanulcoder.aspirant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void goToBack(View view)
    {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }
}
