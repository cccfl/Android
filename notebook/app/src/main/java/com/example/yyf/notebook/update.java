package com.example.yyf.notebook;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class update extends AppCompatActivity {
    private EditText nameEdit;
    private EditText numberEdit;
    private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nameEdit = (EditText) findViewById(R.id.nameu);
        numberEdit = (EditText) findViewById(R.id.numberu);
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String number = numberEdit.getText().toString();
                if(!name.isEmpty()) {
                    if(!number.isEmpty()) {
                        Uri uri = Data.CONTENT_URI;//对data表的所有数据操作
                        ContentValues values = new ContentValues();
                        values.put(Data.DATA1, number);
                        int result = getContentResolver().update(uri, values, Data.MIMETYPE + "=? and " + ContactsContract.PhoneLookup.DISPLAY_NAME + "=?",
                                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, name});
                        if (result > 0) {
                            Toast.makeText(update.this, "更新成功", LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(update.this, "请输入联系方式", LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(update.this, "请输入账户名", LENGTH_SHORT).show();
                }

            }


        });
    }

}
