package com.example.zhang.weather;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Object> {

    String name;
    List<Map<String, Object>> listems;
    TextView textView1;
    SQLiteDatabase db;
    Button btaddlike;
    Cursor cursor;
    ListView listView;
    String username;
    SimpleAdapter simplead;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        btaddlike   = (Button)findViewById(R.id.addlike);
        textView1   = (TextView)findViewById(R.id.textView);
        listView    = (ListView)findViewById(R.id.listview);
        db = openOrCreateDatabase("weather.db", Context.MODE_PRIVATE, null);
        btaddlike.setOnClickListener(this);
        username = intent.getStringExtra("username");
        cursor = db.rawQuery("select * from userlike where username = '"+ username +"'" ,null);
        String[] userlikecity = new String[cursor.getCount()];

        if(cursor.moveToFirst())
        {
            int i = 0;
            do
            {
                userlikecity[i++] = cursor.getString(1);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < userlikecity.length; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("userlikecity", userlikecity[i]);
            listems.add(listem);
        }
        simplead = new SimpleAdapter(this, listems,
                R.layout.item,
                new String[] { "userlikecity" },
                new int[] {R.id.listname});
        listView.setAdapter(simplead);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String citycode = cursor.getString(2);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,WeatherActivity.class);
                intent.putExtra("citycode",citycode);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.addlike:{
                Intent intent = new Intent();
                intent.setClass(this,SelectActivity.class);
                intent.putExtra("username",username);
                startActivityForResult(intent, 1);
                //startActivity(intent);
                String a = "aaa";
                break;
            }

        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cursor.close();
        cursor = db.rawQuery("select * from userlike where username = '"+ username +"'" ,null);
        String[] userlikecity = new String[cursor.getCount()];
        if(cursor.moveToFirst())
        {
            int i = 0;
            do
            {
                userlikecity[i++] = cursor.getString(1);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        listems.clear();
        for (int i = 0; i < userlikecity.length; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("userlikecity", userlikecity[i]);
            listems.add(listem);
        }

        simplead.notifyDataSetChanged();
     }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}
