package com.afnanulcoder.aspirant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Games extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
    }

    public void GoToConnectFour(View view) {
        Intent intent = new Intent(this, GameConnectFour_1.class);
        startActivity(intent);
    }

    public void GoToTickTacToe(View view) {
        Toast.makeText(this, "Please Find at Next update", Toast.LENGTH_SHORT).show();
    }

    public void GoToSudoku(View view) {
        Toast.makeText(this, "Please Find at Next update", Toast.LENGTH_SHORT).show();
    }

    public void GoToGuessNumber(View view) {
        Toast.makeText(this, "Please Find at Next update", Toast.LENGTH_SHORT).show();
    }
}
