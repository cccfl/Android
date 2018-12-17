package com.example.zhang.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.String;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btLogin;
    Button btSignup;
    EditText tvUsername;
    EditText tvPassword;
    TextView textView;
    String username;
    SQLiteDatabase db;
    CheckBox checkBox;
    boolean isRight = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin     = (Button)findViewById(R.id.Login);
        btSignup    = (Button)findViewById(R.id.Signup);
        tvUsername  = (EditText) findViewById(R.id.username);
        tvPassword  = (EditText) findViewById(R.id.password);
        textView    = (TextView) findViewById(R.id.textView);
        checkBox    = (CheckBox) findViewById(R.id.checkBox);
        btLogin.setOnClickListener(this);
        btSignup.setOnClickListener(this);
        SharedPreferences sp =getSharedPreferences("user", MODE_PRIVATE);
        tvUsername.setText(sp.getString("username",""));
        tvPassword.setText(sp.getString("password",""));
        if(sp.getString("checkbox","2").equals("1"))
        {
            checkBox.setChecked(true);
        }
        else
        {
            checkBox.setChecked(false);
        }
        db = openOrCreateDatabase("weather.db", Context.MODE_PRIVATE, null);
        textView.setTextColor(Color.rgb(255,0,0));

    }

    @Override
    public void onClick(View v) {
        isRight = false;
        textView.setText("");
        switch (v.getId())
        {
            case R.id.Login:
            {
                Cursor cursor = db.query ("user",null,null,null,null,null,null);
                if(cursor.moveToFirst())
                {
                    boolean isName = false;
                    while(!cursor.isAfterLast())
                    {
                        String username = cursor.getString(0).toString();
                        String password = cursor.getString(1).toString();
                        String inputusername = tvUsername.getText().toString();
                        String inputpassword = tvPassword.getText().toString();
                        if (username.equals(inputusername))
                        {
                            isName = true;
                            if (password.equals(inputpassword))
                            {
                                isRight = true;
                            }
                        }
                        cursor.moveToNext();
                    }
                    if(isName == false)
                    {
                        textView.setText("username not exsist !");
                    }
                    else if(isRight == false)
                    {
                        textView.setText("wrong username or password !");
                    }
                }

                if(isRight)
                {
                    SharedPreferences sp =getSharedPreferences("user", MODE_PRIVATE);
                    Editor editor= sp.edit();
                    if(checkBox.isChecked())
                    {
                        editor.putString("username", tvUsername.getText().toString());
                        editor.putString("password", tvPassword.getText().toString());
                        editor.putString("checkbox","1");
                    }
                    else
                    {
                        editor.putString("username", tvUsername.getText().toString());
                        editor.putString("password", "");
                        editor.putString("checkbox","0");
                    }
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    username = tvUsername.getText().toString();
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
                cursor.close();
                break;
            }
            case R.id.Signup:
            {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                break;
            }
        }




    }
}
