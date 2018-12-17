package com.example.zhang.weather;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase db;
    Cursor cursor,tcursor;

    List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
    int step = 0;
    int num1=0,num2=0,num3=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        listView = (ListView)findViewById(R.id.listview);
        db = openOrCreateDatabase("weather.db", Context.MODE_PRIVATE, null);
        cursor = db.rawQuery("select * from citycode where wnumber like '%0100' or wnumber like '%0101'" ,null);
        String[] pro = new String[cursor.getCount()];
        if(cursor.moveToFirst())
        {
            int i = 0;
            do
            {
                pro[i++] = cursor.getString(1);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        for (int i = 0; i < pro.length; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("name", pro[i]);
            listems.add(listem);
        }
        final SimpleAdapter simplead = new SimpleAdapter(this, listems,
                R.layout.item,
                new String[] { "name" },
                new int[] {R.id.listname});
        listView.setAdapter(simplead);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (step)
                {
                    case 0:
                    {
                        num1 = 10100 + position + 1;
                        String pnumber = String.valueOf(num1);
                        cursor.close();
                        if(position<4)
                            cursor = db.rawQuery("select * from citycode where wnumber like '" + pnumber + "%00'",  null);
                        else
                            cursor = db.rawQuery("select * from citycode where wnumber like '" + pnumber + "%01'", null);
                        String[] city = new String[cursor.getCount()];
                        if(cursor.moveToFirst())
                        {
                            int i = 0;
                            do
                            {
                                city[i++] = cursor.getString(2);
                                cursor.moveToNext();
                            } while (!cursor.isAfterLast());
                        }
                        listems.clear();
                        for (int i = 0; i < city.length; i++) {
                            Map<String, Object> listem = new HashMap<String, Object>();
                            listem.put("name", city[i]);
                            listems.add(listem);
                        }
                        simplead.notifyDataSetChanged();
                        listView.setAdapter(simplead);
                        step++;
                        break;
                    }
                    case 1:
                    {
                        num2 = num1*100 + position + 1;
                        String cnumber = String.valueOf(num2);
                        cursor.close();
                        cursor = db.rawQuery("select * from citycode where wnumber like '" + cnumber + "%'", null);
                        String[] qu = new String[cursor.getCount()];
                        if(cursor.moveToFirst())
                        {
                            int i = 0;
                            do
                            {
                                qu[i++] = cursor.getString(3);
                                cursor.moveToNext();
                            } while (!cursor.isAfterLast());
                        }
                        listems.clear();
                        for (int i = 0; i < qu.length; i++) {
                            Map<String, Object> listem = new HashMap<String, Object>();
                            listem.put("name", qu[i]);
                            listems.add(listem);
                        }
                        simplead.notifyDataSetChanged();
                        listView.setAdapter(simplead);
                        step++;
                        break;
                    }
                    case 2:
                    {
                         boolean isE = false;
                        Intent intent = getIntent();
                        if(num1 < 10105)
                            num3 = num2*100 + position ;
                        else
                            num3 = num2*100 + position + 1;
                        String wnumber = String.valueOf(num3);
                        tcursor = db.rawQuery("select * from userlike where username = '"+intent.getStringExtra("username")+"'", null);
                        if(tcursor.moveToFirst()) {
                            do {
                                if (wnumber.equals(tcursor.getString(2))) {
                                    Toast.makeText(getApplicationContext(), "HAVE EXSIST", Toast.LENGTH_SHORT).show();
                                    isE = true;
                                }
                                tcursor.moveToNext();
                            } while (!tcursor.isAfterLast());
                        }
                        if(isE) {
                            finish();
                            break;
                        }
                        cursor.close();
                        cursor = db.rawQuery("select * from citycode where wnumber = '"+wnumber+"'", null);
                        cursor.moveToFirst();
                        String str = cursor.getString(3);
                        ContentValues cValue = new ContentValues();
                        cValue.put("username",intent.getStringExtra("username"));
                        cValue.put("cname",str);
                        cValue.put("cnumber",wnumber);
                        db.insert("userlike",null,cValue);
                        db.close();
                        setResult(2, intent);
                        finish();
                        break;
                    }
                    default:
                    {
                        Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

            }
        });

    }
}
