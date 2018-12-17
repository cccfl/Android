package com.example.yyf.notebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class change extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private EditText accountEditc;
    private EditText passwordEditc;
    private EditText newpasswordEditc;
    private Button change;
    private String admin;
    private String pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });



    }
}
