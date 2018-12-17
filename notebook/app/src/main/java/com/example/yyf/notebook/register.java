package com.example.yyf.notebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class register extends AppCompatActivity {
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button register;
    private Button check;
    private MyDatabaseHelper dbHelper;
    private String admin;
    private boolean i = false;
    private int p = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new MyDatabaseHelper(this,"YYFdb.db",null,1);
        accountEdit = (EditText) findViewById(R.id.accountr);
        passwordEdit = (EditText) findViewById(R.id.passwordr);
        register = (Button) findViewById(R.id.registerr);
        check = (Button) findViewById(R.id.check);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    String account = accountEdit.getText().toString();
                    String password = passwordEdit.getText().toString();
                if(!i)
                {
                    Toast.makeText(register.this, "请验证用户名是否重复", LENGTH_SHORT).show();
                }
                    if(!account.isEmpty()&& i)
                    {
                        if (!password.isEmpty())
                    {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("admin", account);
                        values.put("pas", password);
                        db.insert("YYFdb", null, values);
                        values.clear();
                        Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                        i = false;
                    }
                        else
                            Toast.makeText(register.this, "用户名或密码为空", LENGTH_SHORT).show();

                    }
                else if(i){
                    Toast.makeText(register.this, "用户名或密码为空", LENGTH_SHORT).show();
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("YYFdb", null, null, null, null, null, null);

                String account = accountEdit.getText().toString();
                if (cursor.moveToFirst()) {
                    while (cursor.moveToNext()) {
                        admin = cursor.getString(cursor.getColumnIndex("admin"));

                        if (account.equals(admin)) {
                            Toast.makeText(register.this, "用户名重复", LENGTH_SHORT).show();
                            i = false;
                            p++;
                            break;
                        }
                        if (i && !account.isEmpty()) {
                            Toast.makeText(register.this, "用户名可用", LENGTH_SHORT).show();
                            break;
                        }
                        if (account.isEmpty()) {
                            Toast.makeText(register.this, "请输入用户名", LENGTH_SHORT).show();
                            p++;
                            break;
                        }
                    }
                            if(p==0)
                            {
                                i = true;
                                Toast.makeText(register.this, "用户名可用", LENGTH_SHORT).show();
                            }
                    p = 0;

                }
            }
        });
        }
}
