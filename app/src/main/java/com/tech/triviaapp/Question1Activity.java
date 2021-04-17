package com.tech.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Question1Activity extends AppCompatActivity {
    TextView tv;
    Button nextButton;
    RadioGroup radio_g;
    CheckBox ch1, ch2, ch3, ch4;
    String name1, question1, answer1;
    String answer2 = "";
    String question2;


    //list of questions
    String questions[] = {
            "what are the colours in the indian national flag? select All?"
    };
    //list of options
    String opt[] = {"white", "yello", "orange", "green",};
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);
        TextView textView = (TextView) findViewById(R.id.DispName);

        //got the name,which is sent from MainActivity
        Intent intent = getIntent();
        name1 = intent.getStringExtra("name1");
        question1 = intent.getStringExtra("question1");
        answer1 = intent.getStringExtra("answer1");


        textView.setText("Hello \'" + name1 + "\' Welcome ");//user name will display on top of the activity

        nextButton = (Button) findViewById(R.id.button3);

        tv = (TextView) findViewById(R.id.tvque);

        radio_g = (RadioGroup) findViewById(R.id.answersgrp);
        ch1 = (CheckBox) findViewById(R.id.ch1);
        ch2 = (CheckBox) findViewById(R.id.ch2);
        ch3 = (CheckBox) findViewById(R.id.ch3);
        ch4 = (CheckBox) findViewById(R.id.ch4);

        tv.setText(questions[flag]);
        question2 = questions[flag];
        insertData(question2);

        ch1.setText(opt[0]);
        ch2.setText(opt[1]);
        ch3.setText(opt[2]);
        ch4.setText(opt[3]);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(ch1.isChecked() || ch2.isChecked() || ch3.isChecked() || ch4.isChecked())) {
                    Toasty.warning(Question1Activity.this, "please select your answers.", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                if (ch1.isChecked()) {
                    answer2 = opt[0];
                }
                if (ch2.isChecked()) {
                    if (answer2.length() > 0) {
                        answer2 = answer2 + "," + opt[1];
                    } else {
                        answer2 = opt[1];
                    }
                }
                if (ch3.isChecked()) {
                    if (answer2.length() > 0) {
                        answer2 = answer2 + "," + opt[2];
                    } else {
                        answer2 = opt[2];
                    }
                }
                if (ch4.isChecked()) {
                    if (answer2.length() > 0) {
                        answer2 = answer2 + "," + opt[3];
                    } else {
                        answer2 = opt[3];
                    }
                }

                if (answer2.startsWith(",")) {
                    answer2.substring(1);
                }
                Log.e("answer2", answer2);
                Log.e("answerlen", String.valueOf(answer2.length()));
                insertData(answer2);
                Intent in = new Intent(getApplicationContext(), SummaryActivity.class);
                in.putExtra("name1", name1);
                in.putExtra("question1", question1);
                in.putExtra("answer1", answer1);
                in.putExtra("question2", question2);
                in.putExtra("answer2", answer2);
                startActivity(in);

            }
        });
    }

    private void insertData(String question2) {
        //step 1 :create a database
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("MyDataBase", Context.MODE_PRIVATE, null);

        //create a table
        sqLiteDatabase.execSQL("create table if not exists Test(_id integer primary key  autoincrement,data varchar(200))");

        ContentValues cv = new ContentValues();

        cv.put("data", String.valueOf(question2));

        long status = sqLiteDatabase.insert("Test", null, cv);

        if (status == -1) {
            Toasty.error(this, "unable to insert data", Toast.LENGTH_SHORT).show();

        } else {
            Toasty.success(this, "data inserted succesfully", Toast.LENGTH_SHORT).show();

        }
    }
}