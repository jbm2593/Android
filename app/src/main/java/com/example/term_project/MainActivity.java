package com.example.term_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InitializeView();
        this.SetListener();
    }

    public void InitializeView() {
        button1 = (Button) findViewById(R.id.btn_1);
        button2 = (Button) findViewById(R.id.btn_2);
        button3 = (Button) findViewById(R.id.btn_3);
    }

    public void SetListener() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity1.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity2.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity3.class);
                startActivity(intent);
            }
        });
    }
}

//        button1.setOnClickListener() {
//            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//            startActivity(intent);
//        };

