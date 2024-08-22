package com.example.game2person;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
public static int count_x=0;
    public static int count_y=0;
    public static int count_a=0;
    public static int count_b=0;
CardView undo;
Button btn_Back;
    private int grid_size;
    TableLayout gameBoard;
    TextView txt_turn;
    char [][] my_board;
    char turn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        grid_size = Integer.parseInt(getString(R.string.size_of_board));
        my_board = new char [grid_size][grid_size];
        gameBoard = (TableLayout) findViewById(R.id.mainBoard);
        txt_turn = (TextView) findViewById(R.id.turn);
        undo=(CardView) findViewById(R.id.btnundo) ;
        btn_Back=(Button) findViewById(R.id.btnback);

        resetBoard();
        txt_turn.setText("Lượt bắt đầu: "+turn);

        for(int i = 0; i< gameBoard.getChildCount(); i++){
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for(int j = 0; j<row.getChildCount(); j++){
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);
                tv.setOnClickListener(Move(i, j, tv));
            }
        }

        Button reset_btn = (Button) findViewById(R.id.reset);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent current = getIntent();
                finish();
                startActivity(current);
            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < gameBoard.getChildCount(); i++) {
                    TableRow row = (TableRow) gameBoard.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        if (i == count_x && j == count_y) {
                            my_board[i][j] = turn;
                            if (turn == 'X') {
                                turn = 'O';
                            } else {
                                turn = 'X';
                            }
                            TextView tv = (TextView) row.getChildAt(j);
                            tv.setText(R.string.none);
                            my_board[i][j] = ' ';
                            count_y = 0;
                            count_x = 0;
                        }
                    }
                }
            }
        });
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(MainActivity.this);
                mydialog.setTitle("Game Caro ");
                mydialog.setMessage("Bạn có chắc chắn muốn thoát không ?");
                mydialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                mydialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mydialog.create().show();
            }
        });
            }




    protected void resetBoard(){
        turn = 'X';
        for(int i = 0; i< grid_size; i++){
            for(int j = 0; j< grid_size; j++){
                my_board[i][j] = ' ';
            }
        }
    }

    protected int gameStatus(){

        //0 Continue
        //1 X Wins
        //2 O Wins
        //-1 Draw

        int rowX = 0, colX = 0, rowO = 0, colO = 0;
        for(int i = 0; i< grid_size; i++){
            if(check_Row_Equality(i,'X'))
                return 1;
            if(check_Column_Equality(i, 'X'))
                return 1;
            if(check_Row_Equality(i,'O'))
                return 2;
            if(check_Column_Equality(i,'O'))
                return 2;
            if(check_Diagonal('X'))
                return 1;
            if(check_Diagonal('O'))
                return 2;
        }

        boolean boardFull = true;
        for(int i = 0; i< grid_size; i++){
            for(int j = 0; j< grid_size; j++){
                if(my_board[i][j]==' ')
                    boardFull = false;
            }
        }
        if(boardFull)
            return -1;
        else return 0;
    }

    protected boolean check_Diagonal(char player){
        int count_Equal1 = 0,count_Equal2 = 0;
        for(int i = 0; i< grid_size; i++)
            if(my_board[i][i]==player)
                count_Equal1++;
        for(int i = 0; i< grid_size; i++)
            if(my_board[i][grid_size -1-i]==player)
                count_Equal2++;
        if(count_Equal1== grid_size || count_Equal2== grid_size)
            return true;
        else return false;
    }

    protected boolean check_Row_Equality(int r, char player){
        int count_Equal=0;
        for(int i = 0; i< grid_size; i++){
            if(my_board[r][i]==player)
                count_Equal++;
        }

        if(count_Equal== grid_size)
            return true;
        else
            return false;
    }

    protected boolean check_Column_Equality(int c, char player){
        int count_Equal=0;
        for(int i = 0; i< grid_size; i++){
            if(my_board[i][c]==player)
                count_Equal++;
        }

        if(count_Equal== grid_size)
            return true;
        else
            return false;
    }

    protected boolean Cell_Set(int r, int c){
        return !(my_board[r][c]==' ');
    }

    protected void stopMatch(){
        for(int i = 0; i< gameBoard.getChildCount(); i++){
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for(int j = 0; j<row.getChildCount(); j++){
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }

    View.OnClickListener Move(final int r, final int c, final TextView tv){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
int a=count_a,b=count_b;
                if(!Cell_Set(r,c)) {
                    my_board[r][c] = turn;
                    if (turn == 'X') {
                        tv.setText(R.string.X);
                        turn = 'O';
                        count_x=r;
                        count_y=c;
                    } else if (turn == 'O') {
                        tv.setText(R.string.O);
                        turn = 'X';
                        count_x=r;
                        count_y=c;
                    }
                    if (gameStatus() == 0) {
                        txt_turn.setText("Lượt người chơi: " + turn);
                    }
                    else if(gameStatus() == -1){
                        txt_turn.setText("Hòa");
                        stopMatch();
                    }
                    else{
                        if(turn=='O'){
                            txt_turn.setText("X Thắng!");
                            count_a++;
                            a++;

                        }else{
                        txt_turn.setText("O Thắng!");
                        count_b++;
                        b++;}
                        stopMatch();
                    }
                }
                else{
                    txt_turn.setText(txt_turn.getText()+"\nChọn vào ô trống!");
                }
            }
        };
    }
}