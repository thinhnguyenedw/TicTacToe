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

public class CheDovs5 extends AppCompatActivity {
    public static  int count_a=0;
    public static  int count_b=0;
Button btn_back;
    CardView undo_bt;
    private int grid_size5;
    TableLayout gameBoard5;
    int x = 8;
    TextView txt_turn5,txt_tiso;
    char[][] my_board5;
    char turn5;
    int size;
public static int count_x=0;
    public static int count_y=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_che_dovs5);
btn_back=(Button) findViewById(R.id.btn_back);
        grid_size5 = Integer.parseInt(getString(R.string.size_of_board5));
        my_board5 = new char[grid_size5][grid_size5];
        gameBoard5 = (TableLayout) findViewById(R.id.mainBoard5);
        txt_turn5 = (TextView) findViewById(R.id.turn);
        undo_bt = (CardView) findViewById(R.id.btnundo);
        txt_tiso= (TextView) findViewById(R.id.txtiso) ;
        resetBoard();
        txt_turn5.setText("Lượt bắt đầu: " + turn5);

        txt_tiso.setText("X: "+count_a+" - "+ " O: " +count_b);
        for (int i = 0; i < gameBoard5.getChildCount(); i++) {
            TableRow row = (TableRow) gameBoard5.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setText(R.string.none);
                tv.setOnClickListener(Move(i, j, tv,txt_tiso));

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
        undo_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < gameBoard5.getChildCount(); i++) {
                    TableRow row = (TableRow) gameBoard5.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {

                        if(i==count_x&&j==count_y){
                            my_board5[i][j] = turn5;
                            if(turn5=='X'){
                                turn5='O';
                            }else{
                                turn5='X';
                            }
                            TextView tv = (TextView) row.getChildAt(j);
                            tv.setText(R.string.none);
                            my_board5[i][j]=' ';
                            count_y=0;
                            count_x=0;




                        }



                    }
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(CheDovs5.this);
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
        turn5 = 'X';
        for (int i = 0; i < grid_size5; i++) {
            for (int j = 0; j < grid_size5; j++) {
                my_board5[i][j] = ' ';
            }
        }
    }
    //chuẩn

    protected int gameStatus() {

        //0 Continue
        //1 X Wins
        //2 O Wins
        //-1 Draw

        int rowX = 0, colX = 0, rowO = 0, colO = 0;
        for (int i = 0; i < grid_size5; i++) {
            if (check_Row_Equality(i, 'X')) {
                return 1;

            }

            if (check_Column_Equality(i, 'X')) {
                return 1;

            }

            if (check_Row_Equality(i, 'O')) {
                return 2;

            }

            if (check_Column_Equality(i, 'O')) {
                return 2;

            }

            if (check_Diagonal('X'))
                return 1;
            if (check_Diagonal('O'))
                return 2;
        }

        boolean boardFull = true;
        for (int i = 0; i < grid_size5; i++) {
            for (int j = 0; j < grid_size5; j++) {
                if (my_board5[i][j] == ' ')
                    boardFull = false;
            }
        }
        if (boardFull)
            return -1;
        else return 0;
    }

    protected boolean check_Diagonal(char player) {
        String player_Turn = Character.toString(player);
        String player_Win = player_Turn + player_Turn + player_Turn+ player_Turn+ player_Turn;
        boolean win1 = false;
        boolean win2 = false;
        boolean win3 = false;
        boolean win4 = false;

        for (int j = 0; j < 4; j++) {
            win1 = false;
            String diagonal = null;

            for (int i = 0; i < grid_size5 - j; i++) {
                diagonal += Character.toString(my_board5[i][i + j]);
                if (diagonal.contains(player_Win)) break;
            }
            if (diagonal.contains(player_Win)) {
                win1 = true;
                break;
            }

        }

        for (int j = 0; j < 4; j++) {
            win2 = false;
            String diagonal = null;
            for (int i = 0; i < grid_size5 - j; i++) {
                diagonal += Character.toString(my_board5[i + j][i]);
                if (diagonal.contains(player_Win)) break;
            }
            if (diagonal.contains(player_Win)) {
                win2 = true;
                break;
            }

        }

        for (int j = 0; j < 4; j++) {
            win3 = false;
            String diagonal = null;
            for (int i = 0; i < grid_size5 - j; i++) {
                diagonal += Character.toString(my_board5[grid_size5 - 1 - i - j][i]);
                if (diagonal.contains(player_Win)) break;
            }
            if (diagonal.contains(player_Win)) {
                win3 = true;
                break;
            }
        }

        for (int j = 0; j < 4; j++) {
            win4 = false;
            String diagonal = null;
            for (int i = 0; i < grid_size5 - j; i++) {
                diagonal += Character.toString(my_board5[grid_size5 - 1 - i][i + j]);
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

    protected boolean check_Row_Equality(int r, char player) {
        int count_Equal = 0;
        for (int i = 0; i < grid_size5; i++) {
            if (my_board5[r][i] == player)
                count_Equal++;

        }

        if (count_Equal == 5)
            return true;
        else
            return false;
    }

    protected boolean check_Column_Equality(int c, char player) {
        int count_Equal = 0;
        for (int i = 0; i < grid_size5; i++) {
            if (my_board5[i][c] == player)
                count_Equal++;

        }

        if (count_Equal == 5)
            return true;
        else
            return false;
    }

    protected boolean Cell_Set(int r, int c) {
        return !(my_board5[r][c] == ' ');
    }

    protected void stopMatch() {
        for (int i = 0; i < gameBoard5.getChildCount(); i++) {
            TableRow row = (TableRow) gameBoard5.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }
    //dừng

    View.OnClickListener Move(final int r, final int c, final TextView tv,final TextView tiso) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
int a=count_a,b=count_b;
                if (!Cell_Set(r, c)) {
                    my_board5[r][c] = turn5;

                    if (turn5 == 'X') {
                        tv.setText(R.string.X);
                        turn5 = 'O';
                        count_x=r;
                        count_y=c;


                    } else if (turn5 == 'O') {
                        tv.setText(R.string.O);
                        turn5 = 'X';
                        count_x=r;
                        count_y=c;
                    }
                    if (gameStatus() == 0) {
                        txt_turn5.setText("Lượt người chơi: " + turn5);
                    } else if (gameStatus() == -1) {
                        txt_turn5.setText("Hòa");
                        stopMatch();
                    } else {

                        if (turn5 == 'O') {
                            txt_turn5.setText("X Thắng!");

                            count_a++;
                            a++;
                            tiso.setText("X: "+a+" - "+" O: "+b);
                            stopMatch();

                        } else {
                            txt_turn5.setText("O Thắng!");
                            count_b++;
                            b++;
                            tiso.setText("X: "+a+" - "+" O: "+b);
                            stopMatch();
                        }

                    }
                } else {

                    txt_turn5.setText(txt_turn5.getText() + "\nChọn vào ô trống!");
                }
            }
        };
    }

    public void undo() {

//            my_board5[xUndo[Size - 1]][yUndo[Size - 1]].setText(" ");
//            b[xUndo[Size - 1]][yUndo[Size - 1]].setActionCommand(xUndo[Size - 1]+ " " + yUndo[Size - 1]);
//            b[xUndo[Size - 1]][yUndo[Size - 1]].setBackground(background_cl);
//            tick[xUndo[Size - 1]][yUndo[Size - 1]] = true;
//            count--;
//            if (count % 2 == 0) lb.setText("Lượt Của X");
//            else lb.setText("Lượt Của O");
//            Size--;

    }
}