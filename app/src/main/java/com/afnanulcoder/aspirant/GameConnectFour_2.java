package com.afnanulcoder.aspirant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameConnectFour_2 extends AppCompatActivity {

    int choice, i;

    LinearLayout playerNameLayout;
    EditText[] playerNameET = new EditText[2];
    String[] playerName = {"Human", "Computer"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_connect_four_2);

        choice = getIntent().getIntExtra("choice", 1);

        playerNameLayout = findViewById(R.id.playerNameLayoutID);

        for(i = 1; i<=choice; i++)
        {
            makeTextView("Player "+i+" Name");
            makeEditText(i-1 , InputType.TYPE_CLASS_TEXT);
        }

    }

    void makeTextView(String text)
    {
        TextView textView1 = new TextView(this);
        textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView1.setText(text);
        playerNameLayout.addView(textView1);


        textView1.setPadding(0, 21, 0, 11);
    }

    void makeEditText(int i, int a)
    {
        playerNameET[i] = new EditText(this);
        playerNameET[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        playerNameLayout.addView(playerNameET[i]);


        //editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
        playerNameET[i].setBackground(getDrawable(R.drawable.round_corner_shape));

        playerNameET[i].setInputType(a);
        playerNameET[i].setPadding(29, 19, 29, 19);
        // View v = editText1.getRootView();
    }


    public void startGame(View view)
    {
        if (choice == 1)
        {
            playerName[0] = playerNameET[0].getText().toString();
            playerName[1] = "Computer";
        }
        else
        {
            playerName[0] = playerNameET[0].getText().toString();
            playerName[1] = playerNameET[1].getText().toString();
        }

        if(playerName[0].equals("") || playerName[1].equals(""))
        {
            Toast.makeText(this, "Please Fill Up The Box", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this, GameConnectFour_3.class);
            intent.putExtra("posondo", choice);
            intent.putExtra("prothom", playerName[0]);
            intent.putExtra("ditiyo", playerName[1]);
            startActivity(intent);
        }


    }
}
