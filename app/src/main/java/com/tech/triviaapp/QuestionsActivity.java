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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class QuestionsActivity extends AppCompatActivity {

    TextView tv;
    Button nextButton;
    RadioGroup radio_g;
    RadioButton rb1, rb2, rb3, rb4;
    String question1;
    String answer1;

    //list of questions
    String questions[] = {
            "Who is the best cricketer in the world?",
    };
    //list of options
    String opt[] = {
            "sachin Tendulkar", "virat kohli", "Adom gilchrist", "jaques callies"
    };
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        TextView textView = (TextView) findViewById(R.id.DispName);

        //got the name,which is sent from MainActivity
        Intent intent = getIntent();
        String name = intent.getStringExtra("myname");

        textView.setText("Hello \'" + name + "\' Welcome ");//user name will display on top of the activity

        nextButton = (Button) findViewById(R.id.button3);

        tv = (TextView) findViewById(R.id.tvque);

        radio_g = (RadioGroup) findViewById(R.id.answersgrp);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rb4 = (RadioButton) findViewById(R.id.radioButton4);

        tv.setText(questions[flag]);
        question1 = questions[flag];
        insertData(question1);

        rb1.setText(opt[0]);
        rb2.setText(opt[1]);
        rb3.setText(opt[2]);
        rb4.setText(opt[3]);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio_g.getCheckedRadioButtonId() == -1) {
                    Toasty.warning(QuestionsActivity.this, "please select answer..", Toast.LENGTH_SHORT, true).show();
                    return;
                }


                if (rb1.isChecked()) {
                    answer1 = opt[0];
                } else if (rb2.isChecked()) {
                    answer1 = opt[1];
                } else if (rb3.isChecked()) {
                    answer1 = opt[2];
                } else if (rb4.isChecked()) {
                    answer1 = opt[3];
                }
                Log.e("answer1", answer1);

                insertData(answer1);
                Intent in = new Intent(getApplicationContext(), Question1Activity.class);
                in.putExtra("name1", name);
                in.putExtra("question1", question1);
                in.putExtra("answer1", answer1);

                startActivity(in);

            }
        });
    }

    private void insertData(String question1) {
        //step 1 :create a database
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("MyDataBase", Context.MODE_PRIVATE, null);

        //create a table
        sqLiteDatabase.execSQL("create table if not exists Test(_id integer primary key  autoincrement,data varchar(200))");

        ContentValues cv = new ContentValues();

        cv.put("data", question1);

        long status = sqLiteDatabase.insert("Test", null, cv);

        if (status == -1) {
            Toasty.error(this, "unable to insert data", Toast.LENGTH_SHORT).show();

        } else {
            Toasty.success(this, "data inserted succesfully", Toast.LENGTH_SHORT).show();

        }
    }
}