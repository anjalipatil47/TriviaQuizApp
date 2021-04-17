package com.tech.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SummaryActivity extends AppCompatActivity {
    Button next;
    String name1, question1, answer1, question2, answer2;
    TextView tv1, tv2, tv3, tv4, tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        next = findViewById(R.id.btn_next);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);


        Intent intent = getIntent();

        name1 = intent.getStringExtra("name1");
        question1 = intent.getStringExtra("question1");
        answer1 = intent.getStringExtra("answer1");
        question2 = intent.getStringExtra("question2");
        answer2 = intent.getStringExtra("answer2");


        tv1.setText("name:  "+name1);
        tv2.setText("Question1:  "+question1);
        tv3.setText("Answer1:  "+answer1);
        tv4.setText("Question2:  "+question2);
        tv5.setText("Answer2:  "+answer2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SummaryActivity.this, HistoryActivity.class));
            }
        });

    }
}