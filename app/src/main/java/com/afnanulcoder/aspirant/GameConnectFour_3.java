package com.afnanulcoder.aspirant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class GameConnectFour_3 extends AppCompatActivity {

    LinearLayout mainLayout, starLayout;
    TextView playerName;

    ImageView star1, star2, star3;
    int[][] ring = new int[7][6];
    Button[][] btn = new Button[7][6];


    int playerNumber = 1;
    int[] stepCount = {0,0};
    boolean gameOn = true;


    String[] pName = new String[2];
    int c = 1;

    Random random = new Random();

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            computerPlay();
        }
    };

    //DatabaseReference databaseReferenceOfPlayer;


    private void setId() {
        btn[0][0] = findViewById(R.id.btn00);
        btn[0][1] = findViewById(R.id.btn01);
        btn[0][2] = findViewById(R.id.btn02);
        btn[0][3] = findViewById(R.id.btn03);
        btn[0][4] = findViewById(R.id.btn04);
        btn[0][5] = findViewById(R.id.btn05);
        btn[1][0] = findViewById(R.id.btn10);
        btn[1][1] = findViewById(R.id.btn11);
        btn[1][2] = findViewById(R.id.btn12);
        btn[1][3] = findViewById(R.id.btn13);
        btn[1][4] = findViewById(R.id.btn14);
        btn[1][5] = findViewById(R.id.btn15);
        btn[2][0] = findViewById(R.id.btn20);
        btn[2][1] = findViewById(R.id.btn21);
        btn[2][2] = findViewById(R.id.btn22);
        btn[2][3] = findViewById(R.id.btn23);
        btn[2][4] = findViewById(R.id.btn24);
        btn[2][5] = findViewById(R.id.btn25);
        btn[3][0] = findViewById(R.id.btn30);
        btn[3][1] = findViewById(R.id.btn31);
        btn[3][2] = findViewById(R.id.btn32);
        btn[3][3] = findViewById(R.id.btn33);
        btn[3][4] = findViewById(R.id.btn34);
        btn[3][5] = findViewById(R.id.btn35);
        btn[4][0] = findViewById(R.id.btn40);
        btn[4][1] = findViewById(R.id.btn41);
        btn[4][2] = findViewById(R.id.btn42);
        btn[4][3] = findViewById(R.id.btn43);
        btn[4][4] = findViewById(R.id.btn44);
        btn[4][5] = findViewById(R.id.btn45);
        btn[5][0] = findViewById(R.id.btn50);
        btn[5][1] = findViewById(R.id.btn51);
        btn[5][2] = findViewById(R.id.btn52);
        btn[5][3] = findViewById(R.id.btn53);
        btn[5][4] = findViewById(R.id.btn54);
        btn[5][5] = findViewById(R.id.btn55);
        btn[6][0] = findViewById(R.id.btn60);
        btn[6][1] = findViewById(R.id.btn61);
        btn[6][2] = findViewById(R.id.btn62);
        btn[6][3] = findViewById(R.id.btn63);
        btn[6][4] = findViewById(R.id.btn64);
        btn[6][5] = findViewById(R.id.btn65);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_connect_four_3);

        int i, j;

        setId();
        mainLayout = findViewById(R.id.gameActivityLL);
        playerName = findViewById(R.id.playerNameTV);

        starLayout = findViewById(R.id.starLayoutID);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);


        pName[0] = getIntent().getStringExtra("prothom");
        pName[1] = getIntent().getStringExtra("ditiyo");
        c = getIntent().getIntExtra("posondo", 1);


        playerName.setText(pName[0]);
        mainLayout.setBackground(getDrawable(R.drawable.player1));


        //databaseReferenceOfPlayer = FirebaseDatabase.getInstance().getReference("Players");


        for(i=0;i<7;i++)
            for(j=0;j<6;j++)
                ring[i][j] = 0;


    }



    @SuppressLint("SetTextI18n")
    public void pressed(View view)
    {
        Button pressedButton = (Button) view;
        int pressedValue = Integer.parseInt(pressedButton.getText().toString());
        int colNum = pressedValue/10;

        int ringPosition = putRing(colNum);

        if(playerNumber == 1 && ringPosition >= 0 && gameOn)
        {
            btn[colNum][ringPosition].setBackground(getDrawable(R.drawable.player1round));
            ring[colNum][ringPosition] = 1;
            stepCount[0]++;
            mainLayout.setBackground(getDrawable(R.drawable.player2));
            playerNumber = 2;
            playerName.setText(pName[1]);
            isIt4(colNum, ringPosition, 1);
            if(c == 1)
                handler.postDelayed(runnable, 2500);
        }
        else if(playerNumber == 2 && ringPosition >= 0 && gameOn)
        {

            btn[colNum][ringPosition].setBackground(getDrawable(R.drawable.player2round));
            ring[colNum][ringPosition] = 2;
            stepCount[1]++;
            mainLayout.setBackground(getDrawable(R.drawable.player1));
            playerNumber = 1;
            playerName.setText(pName[0]);
            isIt4(colNum, ringPosition, 2);
        }

    }

    public void computerPlay()
    {
        int cl = random.nextInt(7);
        int ro = putRing(cl);

        if(gameOn)
        {
            if(ro >= 0)
            {

                btn[cl][ro].setBackground(getDrawable(R.drawable.player2round));
                ring[cl][ro] = 2;
                stepCount[1]++;
                mainLayout.setBackground(getDrawable(R.drawable.player1));
                playerNumber = 1;
                playerName.setText(pName[0]);
                isIt4(cl, ro, 2);

            }
            else
            {
                computerPlay();
            }
        }

    }


    public void isIt4(int colN, int ringN, int playerN)
    {
        //Part 1

        try {
            if(ring[colN][ringN] == playerN && ring[colN][ringN-1] == playerN && ring[colN][ringN-2] == playerN && ring[colN][ringN-3] == playerN)
            {
                winningMethod(btn[colN][ringN], btn[colN][ringN-1], btn[colN][ringN-2], btn[colN][ringN-3], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN][ringN] == playerN && ring[colN][ringN+1] == playerN && ring[colN][ringN+2] == playerN && ring[colN][ringN+3] == playerN)
            {
                winningMethod(btn[colN][ringN], btn[colN][ringN+1], btn[colN][ringN+2], btn[colN][ringN+3], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN][ringN-2] == playerN && ring[colN][ringN-1] == playerN && ring[colN][ringN] == playerN && ring[colN][ringN+1] == playerN)
            {
                winningMethod(btn[colN][ringN-2], btn[colN][ringN-1], btn[colN][ringN], btn[colN][ringN+1], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN][ringN-1] == playerN && ring[colN][ringN] == playerN && ring[colN][ringN+1] == playerN && ring[colN][ringN+2] == playerN)
            {
                winningMethod(btn[colN][ringN-1], btn[colN][ringN], btn[colN][ringN+1], btn[colN][ringN+2], playerN);
            }
        } catch (Exception ignored) {

        }


        //Part 2

        try {
            if(ring[colN][ringN] == playerN && ring[colN-1][ringN] == playerN && ring[colN-2][ringN] == playerN && ring[colN-3][ringN] == playerN)
            {
                winningMethod(btn[colN][ringN], btn[colN-1][ringN], btn[colN-2][ringN], btn[colN-3][ringN], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN][ringN] == playerN && ring[colN+1][ringN] == playerN && ring[colN+2][ringN] == playerN && ring[colN+3][ringN] == playerN)
            {
                winningMethod(btn[colN][ringN], btn[colN+1][ringN], btn[colN+2][ringN], btn[colN+3][ringN], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN-2][ringN] == playerN && ring[colN-1][ringN] == playerN && ring[colN][ringN] == playerN && ring[colN+1][ringN] == playerN)
            {
                winningMethod(btn[colN-2][ringN], btn[colN-1][ringN], btn[colN][ringN], btn[colN+1][ringN], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN-1][ringN] == playerN && ring[colN][ringN] == playerN && ring[colN+1][ringN] == playerN && ring[colN+2][ringN] == playerN)
            {
                winningMethod(btn[colN-1][ringN], btn[colN][ringN], btn[colN+1][ringN], btn[colN+2][ringN], playerN);
            }
        } catch (Exception ignored) {

        }

        //Part 3

        try {
            if(ring[colN][ringN] == playerN && ring[colN-1][ringN-1] == playerN && ring[colN-2][ringN-2] == playerN && ring[colN-3][ringN-3] == playerN)
            {
                winningMethod(btn[colN][ringN], btn[colN-1][ringN-1], btn[colN-2][ringN-2], btn[colN-3][ringN-3], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN][ringN] == playerN && ring[colN+1][ringN+1] == playerN && ring[colN+2][ringN+2] == playerN && ring[colN+3][ringN+3] == playerN)
            {
                winningMethod(btn[colN][ringN], btn[colN+1][ringN+1], btn[colN+2][ringN+2], btn[colN+3][ringN+3], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN-2][ringN-2] == playerN && ring[colN-1][ringN-1] == playerN && ring[colN][ringN] == playerN && ring[colN+1][ringN+1] == playerN)
            {
                winningMethod(btn[colN-2][ringN-2], btn[colN-1][ringN-1], btn[colN][ringN], btn[colN+1][ringN+1], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN-1][ringN-1] == playerN && ring[colN][ringN] == playerN && ring[colN+1][ringN+1] == playerN && ring[colN+2][ringN+2] == playerN)
            {
                winningMethod(btn[colN-1][ringN-1], btn[colN][ringN], btn[colN+1][ringN+1], btn[colN+2][ringN+2], playerN);
            }
        } catch (Exception ignored) {

        }

        //Part 4

        try {
            if(ring[colN][ringN] == playerN && ring[colN+1][ringN-1] == playerN && ring[colN+2][ringN-2] == playerN && ring[colN+3][ringN-3] == playerN)
            {
                winningMethod(btn[colN][ringN], btn[colN+1][ringN-1], btn[colN+2][ringN-2], btn[colN+3][ringN-3], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN][ringN] == playerN && ring[colN-1][ringN+1] == playerN && ring[colN-2][ringN+2] == playerN && ring[colN-3][ringN+3] == playerN)
            {
                winningMethod(btn[colN][ringN], btn[colN-1][ringN+1], btn[colN-2][ringN+2], btn[colN-3][ringN+3], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN+2][ringN-2] == playerN && ring[colN+1][ringN-1] == playerN && ring[colN][ringN] == playerN && ring[colN-1][ringN+1] == playerN)
            {
                winningMethod(btn[colN+2][ringN-2], btn[colN+1][ringN-1], btn[colN][ringN], btn[colN-1][ringN+1], playerN);
            }
        } catch (Exception ignored) {

        }

        try {
            if(ring[colN+1][ringN-1] == playerN && ring[colN][ringN] == playerN && ring[colN-1][ringN+1] == playerN && ring[colN-2][ringN+2] == playerN)
            {
                winningMethod(btn[colN+1][ringN-1], btn[colN][ringN], btn[colN-1][ringN+1], btn[colN-2][ringN+2], playerN);
            }
        } catch (Exception ignored) {

        }
    }


    public int putRing(int col)
    {
        int i;

        for(i = 0; i <6; i++)
        {
            if(ring[col][i] > 0)
            {
                return (i-1);
            }
        }
        return (i-1);
    }


    @SuppressLint("SetTextI18n")
    public void winningMethod(Button bt1, Button bt2, Button bt3, Button bt4, int playerN)
    {
        //int starCount = showStar(playerN);

        showStar(playerN);

        if(playerN == 1)
        {
            playerName.setText(pName[0]+" Won");
            mainLayout.setBackground(getDrawable(R.drawable.player1));
            bt1.setBackground(getDrawable(R.drawable.winner1));
            bt2.setBackground(getDrawable(R.drawable.winner1));
            bt3.setBackground(getDrawable(R.drawable.winner1));
            bt4.setBackground(getDrawable(R.drawable.winner1));
            gameOn = false;
            //////////insertToDatabase(pName[0], starCount);
        }
        else
        {
            playerName.setText(pName[1]+" Won");
            mainLayout.setBackground(getDrawable(R.drawable.player2));
            bt1.setBackground(getDrawable(R.drawable.winner2));
            bt2.setBackground(getDrawable(R.drawable.winner2));
            bt3.setBackground(getDrawable(R.drawable.winner2));
            bt4.setBackground(getDrawable(R.drawable.winner2));
            gameOn = false;
            ///////////insertToDatabase(pName[1], starCount);
        }
    }


  /*  public void insertToDatabase(String name, int starCount)
    {
        databaseReferenceOfPlayer.setValue("ALLAH");
        Toast.makeText(this, "Score Saved", Toast.LENGTH_SHORT).show();
    }

   */

    public void showStar(int playerN)
    {
        starLayout.setVisibility(View.VISIBLE);
        if(stepCount[playerN-1] >=0 && stepCount[playerN-1] <= 7)
        {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            //return 3;
        }
        else if(stepCount[playerN-1] >=8 && stepCount[playerN-1] <= 14)
        {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            //return 2;
        }
        else
        {
            star2.setVisibility(View.VISIBLE);
            //return 1;
        }
    }


    public void restartGame(View view)
    {

        Intent intent = new Intent(this, GameConnectFour_2.class);
        //intent.putExtra("posondo", choice);
        intent.putExtra("prothom", pName[0]);
        intent.putExtra("ditiyo", pName[1]);
        intent.putExtra("posondo", c);
        startActivity(intent);

    }

    public void backToHome(View view)
    {
        Intent intent = new Intent(this, GameConnectFour_1.class);
        startActivity(intent);
    }
}
