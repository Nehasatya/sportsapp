package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn_callroom);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                callroompage(v);
            }
        });

        btn2 = (Button) findViewById(R.id.btn_track);
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                trackpage(v);
            }
        });

        btn3 = (Button) findViewById(R.id.btn_throw);
        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                throwpage(v);
            }
        });

        btn4 = (Button) findViewById(R.id.btn_jump);
        btn4.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                 jumppage(v);
            }
        });
    }

    public void callroompage(View v){
        Intent i = new Intent(this, CallRoomActivity.class);
        startActivity(i);
    }

    public void trackpage(View v){
        Intent i = new Intent(this, TrackActivity.class);
        startActivity(i);
    }

    public void throwpage(View v){
        Intent i = new Intent(this, ThrowActivity.class);
        startActivity(i);
    }

    public void jumppage(View v){
        Intent i = new Intent(this, JumpActivity.class);
        startActivity(i);
    }

}
