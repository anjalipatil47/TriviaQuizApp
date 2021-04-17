package com.tech.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class HistoryActivity extends AppCompatActivity {
    Button btn_finish, btn_history;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toasty.info(this,"please click on history button to see history",Toasty.LENGTH_LONG).show();
        listView = findViewById(R.id.list_view);

        btn_finish = findViewById(R.id.btn_finish);
        btn_history = findViewById(R.id.btn_history);


        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();

            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
                Toasty.success(HistoryActivity.this, "Started again!", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void readData() {

        //step 1 :create a database
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("MyDataBase", Context.MODE_PRIVATE, null);

        //create a table
        sqLiteDatabase.execSQL("create table if not exists Test(_id integer primary key  autoincrement,data varchar(200))");


        Cursor cursor = sqLiteDatabase.query("Test", null, null, null, null, null, null);

        String[] fromArray = new String[]{"data"};
        int[] intArray = new int[]{R.id.tv1};

        SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(
                HistoryActivity.this, R.layout.show_history, cursor,
                fromArray, intArray);

        listView.setAdapter(myAdapter);


    }
}