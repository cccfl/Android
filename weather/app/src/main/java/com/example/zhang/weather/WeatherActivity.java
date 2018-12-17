package com.example.zhang.weather;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.http.HttpResponseCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.URLEncoder;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    Button btback;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    Cursor cursor;
    SQLiteDatabase db;
    Intent intent;
    private String url = "http://www.weather.com.cn/data/cityinfo/101121305.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        btback = (Button)findViewById(R.id.back);
        textView1 = (TextView)findViewById(R.id.chengshi2);
        textView2 = (TextView)findViewById(R.id.qingkuang2);
        textView3 = (TextView)findViewById(R.id.zuigao2);
        textView4 = (TextView)findViewById(R.id.zuidi2);
        textView5 = (TextView)findViewById(R.id.shijian2);
        db = openOrCreateDatabase("weather.db", Context.MODE_PRIVATE, null);
        intent = getIntent();
        url = "http://www.weather.com.cn/data/cityinfo/"+intent.getStringExtra("citycode")+".html";
        cursor = db.rawQuery("select * from weather where cnumber = '"+intent.getStringExtra("citycode")+"'",null);
        if(cursor.moveToFirst())
        {
            textView1.setText(cursor.getString(1));
            textView2.setText(cursor.getString(2));
            textView3.setText(cursor.getString(3));
            textView4.setText(cursor.getString(4));
            textView5.setText(cursor.getString(5));
        }
        cursor.close();
        btback.setOnClickListener(this);
        try
        {
            UpdateTextTask updatetext = new UpdateTextTask(this, url , textView1,textView2,textView3,textView4,textView5);
            updatetext.execute();
        }catch(Exception e){
            textView1.setText("error");
        }

    }

    @Override
    public void onClick(View v) {
        String city     = textView1.getText().toString();
        String temp1    = textView2.getText().toString();
        String temp2    = textView3.getText().toString();
        String weather  = textView4.getText().toString();
        String ptime    = textView5.getText().toString();
        boolean isE = false;
        cursor = db.rawQuery("select * from weather where cnumber = '"+intent.getStringExtra("citycode")+"'",null);
        if(cursor.moveToFirst()) {
            do {
                if (intent.getStringExtra("citycode").equals(cursor.getString(0)))
                {
                    isE = true;
                }
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        if(isE)
        {
            ContentValues values = new ContentValues();
            values.put("city",city);
            values.put("temp1",temp1);
            values.put("temp2",temp2);
            values.put("weather",weather);
            values.put("ptime",ptime);
            String[] args = {intent.getStringExtra("citycode")};
            db.update("weather",values,"cnumber=?",args);
            db.close();
        }
        else
        {
            ContentValues cValue = new ContentValues();
            cValue.put("cnumber",intent.getStringExtra("citycode"));
            cValue.put("city",city);
            cValue.put("temp1",temp1);
            cValue.put("temp2",temp2);
            cValue.put("weather",weather);
            cValue.put("ptime",ptime);
            db.insert("weather",null,cValue);
            db.close();
        }
        finish();
    }
}
