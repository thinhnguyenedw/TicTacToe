package com.example.game2person;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity3 extends AppCompatActivity {
private Button button1,button2,btn5x5;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button1=(Button) findViewById(R.id.vs3);
        button2=(Button) findViewById(R.id.vs5);
        btn5x5=(Button) findViewById(R.id.btn5x5) ;
        khoichayvs3();
        khoichay5x5();
        khoichayvs5();
    }

    public void khoichayvs3(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void khoichayvs5(){
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity3.this,CheDovs5.class);
                startActivity(intent);
            }
        });
    }
    public void khoichay5x5(){
        btn5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity3.this,CheDo5x5win3.class);
                startActivity(intent);
            }
        });
    }
}