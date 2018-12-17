package com.example.zhang.weather;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.widget.Toast;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    Button btBack;
    Button btSignup;
    EditText edUsername;
    EditText edPssword1;
    EditText edPssword2;
    TextView tvUsername;
    TextView tvPassword;
    SQLiteDatabase db;
    Cursor cursor;
    boolean isUsernameExsist;
    boolean isPasswordused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btBack      = (Button)findViewById(R.id.back);
        btSignup    = (Button)findViewById(R.id.Signup);
        edUsername  = (EditText)findViewById(R.id.username);
        edPssword1  = (EditText)findViewById(R.id.password1);
        edPssword2  = (EditText)findViewById(R.id.password2);
        tvUsername  = (TextView)findViewById(R.id.usernametext);
        tvPassword  = (TextView)findViewById(R.id.passwordtext);

        isUsernameExsist = false;
        isPasswordused = false;
        db = openOrCreateDatabase("weather.db", Context.MODE_PRIVATE, null);

        btBack.setOnClickListener(this);
        btSignup.setOnClickListener(this);
        edUsername.addTextChangedListener(watcher);
        edPssword1.addTextChangedListener(passwordwatch);
        edPssword2.addTextChangedListener(passwordwatch);


    }

    private TextWatcher watcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            cursor = db.query ("user",null,null,null,null,null,null);
            isUsernameExsist = false;
            if(cursor.moveToFirst())
            {
                while(!cursor.isAfterLast())
                {
                    if(edUsername.getText().toString().equals(cursor.getString(0)))
                    {
                        isUsernameExsist = true;
                        break;
                    }
                    cursor.moveToNext();
                }
                if(isUsernameExsist)
                {
                    tvUsername.setTextColor(Color.rgb(255,0,0));
                    tvUsername.setText("username already exsist !");
                }
                else
                {
                    tvUsername.setTextColor(Color.rgb(0,201,87));
                    tvUsername.setText("username can be used !");

                }
            }
        }
    };
    private TextWatcher passwordwatch = new TextWatcher()
    {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edPssword1.getText().toString().equals(edPssword2.getText().toString()))
                {
                    tvPassword.setTextColor(Color.rgb(0,201,87));
                    tvPassword.setText("password can be used !");
                    isPasswordused = true;
                }
                else
                {
                    tvPassword.setTextColor(Color.rgb(255,0,0));
                    tvPassword.setText("Passwords are inconsistent !");
                    isPasswordused = false;
                }
            }

    };

        public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
            {
                finish();
            }
            case R.id.Signup:
            {
               if(!isUsernameExsist)
               {
                   if(isPasswordused)
                   {
                       ContentValues cValue = new ContentValues();
                       cValue.put("username",edUsername.getText().toString());
                       cValue.put("password",edPssword1.getText().toString());
                       db.insert("user",null,cValue);
                       db.close();
                       Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                       finish();
                   }
               }


            }
        }


    }
}
