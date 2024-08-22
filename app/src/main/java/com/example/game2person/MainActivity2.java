package com.example.game2person;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
Button bt1,bt2,bt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt1 =(Button) findViewById(R.id.btn1);
        bt2=(Button) findViewById(R.id.btn2);
        bt3=(Button) findViewById(R.id.btn3);
btnchoi();
btnHD();
    }
    public void btnchoi(){
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(intent);
            }
        });
    }
    public void btnHD(){
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity2.this,HuongDan.class);
                startActivity(i);
            }
        });
    }
}
