package com.tech.triviaapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startbutton = (Button) findViewById(R.id.button);

        final EditText nametext = (EditText) findViewById(R.id.editName);

        //calling to Questoins Activity using Intent.
        //sending name to next activity using putExtra.
        startbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String name = nametext.getText().toString();
                Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);

//                java.util.Date date = new java.util.Date();
//                System.out.println(date);

                String d= LocalDate.now().toString();
                insertData(d);//inserted date here.

                //user must enter his/her name
                if (TextUtils.isEmpty(name)) {
                    nametext.setError("Please enter your name here!");
                    return;
                } else {
                    intent.putExtra("myname", name);
                    insertData(name);
                }
                startActivity(intent);


            }
        });

    }

    private void insertData(String s) {
        //step 1 :create a database
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("MyDataBase", Context.MODE_PRIVATE, null);

        //create a table
        sqLiteDatabase.execSQL("create table if not exists Test(_id integer primary key  autoincrement,data varchar(200))");

        ContentValues cv = new ContentValues();

        cv.put("data", s);

        long status = sqLiteDatabase.insert("Test", null, cv);

        if (status == -1) {
            Toasty.error(this, "unable to insert data", Toast.LENGTH_SHORT).show();

        } else {
             Toasty.success(this, "data inserted succesfully", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(" Alert!");
        builder.setMessage("Do you want to exit Trivia Quiz?").setCancelable(false);
        builder.setIcon(R.drawable.ic_baseline_exit_to_app_24);


        AlertDialog.OnClickListener listener = new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == dialog.BUTTON_POSITIVE) {
                    MainActivity.super.onBackPressed();

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                    startActivity(intent);
                    finish();

                    System.exit(0);
                    dialog.dismiss();

                } else if (which == dialog.BUTTON_NEGATIVE) {
                    dialog.dismiss();
                }
            }
        };
        builder.setPositiveButton("Yes", listener);
        builder.setNegativeButton("No", listener);
        builder.show();

    }
}