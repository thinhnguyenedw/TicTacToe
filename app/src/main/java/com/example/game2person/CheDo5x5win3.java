package com.example.game2person;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CheDo5x5win3 extends AppCompatActivity {
    public static int count_a = 0;
    public static int count_b = 0;
    private int grid_size;
    TableLayout gameBoard;
    TextView txt_turn;
    char[][] my_board;
    char turn;
    TextView txt_tiso;
    CardView undo_bt;
    Button btnback;
    public static int count_x=0;
    public static int count_y=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_che_do5x5win3);

        grid_size = Integer.parseInt(getString(R.string.size_of_board4));
        my_board = new char[grid_size][grid_size];
        gameBoard = (TableLayout) findViewById(R.id.mainBoard5);
        txt_turn = (TextView) findViewById(R.id.turn5);
        undo_bt = (CardView) findViewById(R.id.btnunodo) ;
        txt_tiso = (TextView) findViewById(R.id.txtiso5);
btnback=(Button) findViewById(R.id.btn_back) ;
        resetBoard();
        txt_turn.setText("Lượt bắt đầu: " + turn);

        txt_tiso.setText("X: " + count_a + " - " + " O: " + count_b);
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);
                tv.setOnClickListener(Move(i, j, tv, txt_tiso));
            }
        }

        Button reset_btn = (Button) findViewById(R.id.reset5);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent current = getIntent();
                finish();
                startActivity(current);
            }
        });
        undo_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < gameBoard.getChildCount(); i++) {
                    TableRow row = (TableRow) gameBoard.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        if(i==count_x&&j==count_y){
                            my_board[i][j] = turn;
                            if(turn=='X'){
                                turn='O';
                            }else{
                                turn='X';
                            }
                            TextView tv = (TextView) row.getChildAt(j);
                            tv.setText(R.string.none);
                            my_board[i][j]=' ';
                            count_y=0;
                            count_x=0;




                        }
                    }
                }
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(CheDo5x5win3.this);
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


    protected void resetBoard() {
        turn = 'X';
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                my_board[i][j] = ' ';
            }
        }
    }

    protected int gameStatus() {

        //0 Continue
        //1 X Thang
        //2 O Thang
        //-1 Draw

        int rowX = 0, colX = 0, rowO = 0, colO = 0;
        for (int i = 0; i < 5; i++) {
            if (check_Row_Equality(i, 'X'))
                return 1;
            if (check_Column_Equality(i, 'X'))
                return 1;
            if (check_Row_Equality(i, 'O'))
                return 2;
            if (check_Column_Equality(i, 'O'))
                return 2;
            if (check_Diagonal('X'))
                return 1;
            if (check_Diagonal('O'))
                return 2;
        }

        boolean boardFull = true;
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                if (my_board[i][j] == ' ')
                    boardFull = false;
            }
        }
        if (boardFull)
            return -1;
        else return 0;
    }

    protected boolean check_Diagonal(char player) {
        String player_Turn = Character.toString(player);
        String player_Win = player_Turn + player_Turn + player_Turn;
        boolean win1 = false;
        boolean win2 = false;
        boolean win3 = false;
        boolean win4 = false;

        for (int j = 0; j < 3; j++) {
            win1 = false;
            String diagonal = null;

            for (int i = 0; i < grid_size - j; i++) {
                diagonal += Character.toString(my_board[i][i + j]);
                if (diagonal.contains(player_Win)) break;
            }
            if (diagonal.contains(player_Win)) {
                win1 = true;
                break;
            }

        }

        for (int j = 0; j < 3; j++) {
            win2 = false;
            String diagonal = null;
            for (int i = 0; i < grid_size - j; i++) {
                diagonal += Character.toString(my_board[i + j][i]);
                if (diagonal.contains(player_Win)) break;
            }
            if (diagonal.contains(player_Win)) {
                win2 = true;
                break;
            }

        }

        for (int j = 0; j < 3; j++) {
            win3 = false;
            String diagonal = null;
            for (int i = 0; i < grid_size - j; i++) {
                diagonal += Character.toString(my_board[grid_size - 1 - i - j][i]);
                if (diagonal.contains(player_Win)) break;
            }
            if (diagonal.contains(player_Win)) {
                win3 = true;
                break;
            }
        }

        for (int j = 0; j < 3; j++) {
            win4 = false;
            String diagonal = null;
            for (int i = 0; i < grid_size - j; i++) {
                diagonal += Character.toString(my_board[grid_size - 1 - i][i + j]);
                if (diagonal.contains(player_Win)) break;
            }
            if (diagonal.contains(player_Win)) {
                win4 = true;
                break;
            }

        }
        if (win1 == true || win2 == true || win3 == true || win4 == true)
            return true;
        else return false;
    }
/*        for(int i = 0; i< grid_size; i++) {
            diagonal += Character.toString(my_board[i][i]);
            if(diagonal.contains(player_Win)) break;
        }
        for(int i=0; i< grid_size-1; i++){
            diagonal += Character.toString(my_board[i][i+1]);
            if(diagonal.contains(player_Win)) break;
        }
        for(int i=0; i< grid_size-2; i++){
            diagonal += Character.toString(my_board[i][i+2]);
            if(diagonal.contains(player_Win)) break;
        }*/
/*        for(int i = 0; i< grid_size; i++) {
            diagonal += Character.toString(my_board[i][grid_size - 1 - i]);
            if(diagonal.contains(player_Win)) break;
        }
        for(int i = 0; i< grid_size-1; i++) {
            diagonal += Character.toString(my_board[i][grid_size - 1 - i-1]);
            if(diagonal.contains(player_Win)) break;
        }
        for(int i = 0; i< grid_size-2; i++) {
            diagonal += Character.toString(my_board[i][grid_size - 1 - i-2]);
            if(diagonal.contains(player_Win)) break;
        }*/


    protected boolean check_Row_Equality(int r, char player) {
        String player_Turn = Character.toString(player);
        String player_Win = player_Turn + player_Turn + player_Turn;
        String row = null;
        for (int i = 0; i < grid_size; i++) {
            row += Character.toString(my_board[r][i]);
            if (row.contains(player_Win)) break;
        }

        if (row.contains(player_Win))
            return true;
        else
            return false;
    }


    protected boolean check_Column_Equality(int c, char player) {
        String player_Turn = Character.toString(player);
        String player_Win = player_Turn + player_Turn + player_Turn;
        String column = null;
        for (int i = 0; i < grid_size; i++) {
            column += Character.toString(my_board[i][c]);
            if (column.contains(player_Win)) break;
        }

        if (column.contains(player_Win))
            return true;
        else
            return false;
    }

    protected boolean Cell_Set(int r, int c) {
        return !(my_board[r][c] == ' ');
    }

    protected void stopMatch() {
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }

    View.OnClickListener Move(final int r, final int c, final TextView tv, final TextView tiso) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = count_a, b = count_b;
                if (!Cell_Set(r, c)) {
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
                    } else if (gameStatus() == -1) {
                        txt_turn.setText("Hòa");
                        stopMatch();
                    } else {

                        if (turn == 'O') {
                            txt_turn.setText("X Thắng!");

                            count_a++;
                            a++;

                            tiso.setText("X: " + a + " - " + " O: " + b);
                            stopMatch();

                        } else {
                            txt_turn.setText("O Thắng!");
                            count_b++;
                            b++;

                            tiso.setText("X: " + a + " - " + " O: " + b);
                            stopMatch();
                        }

                    }
                } else {

                    txt_turn.setText(txt_turn.getText() + "\nChọn vào ô trống!");
                }
            }
        };
    }
}