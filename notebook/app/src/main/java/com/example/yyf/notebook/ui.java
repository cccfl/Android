package com.example.yyf.notebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ui extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private SharedPreferences pref;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox remenberPass;
    private Button login;
    private Button change;
    private Button register;
    private SharedPreferences.Editor editor;
    private String admin;
    private String pas;
    private int i =0;
    //   public String admin = "yyf";
    //   public String pas = "123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        dbHelper = new MyDatabaseHelper(this, "YYFdb.db", null, 1);
        dbHelper.getWritableDatabase();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        login = (Button) findViewById(R.id.login);
        change = (Button) findViewById(R.id.change);
        register = (Button) findViewById(R.id.register);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        remenberPass = (CheckBox) findViewById(R.id.remember);
        boolean isRemenber = pref.getBoolean("remember", false);
        if (isRemenber) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            remenberPass.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("YYFdb", null, null, null, null, null, null);

                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (cursor.moveToFirst()) {
                    while (cursor.moveToNext()) {
                        admin = cursor.getString(cursor.getColumnIndex("admin"));
                        pas = cursor.getString(cursor.getColumnIndex("pas"));
                        if (account.equals(admin)){

                            if(password.equals(pas)) {
                                editor = pref.edit();
                                if (remenberPass.isChecked()) {
                                    editor.putBoolean("remember", true);
                                    editor.putString("password", password);
                                    editor.putString("account", account);

                                } else {
                                    editor.clear();
                                }
                                editor.apply();
                                Intent intent = new Intent(ui.this, main.class);
                                i = 1;
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ui.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }


                    }


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(ui.this,register.class);
                startActivity(intent1);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(ui.this,change.class);
                startActivity(intent2);
            }
        });
    }
}