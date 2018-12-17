package com.example.zhang.weather;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class UpdateTextTask extends AsyncTask<Void,Integer,String>{

    private Context context;
    private String url;
    private TextView txCity,txTemp1,txTemp2,txClimate,txTime;
    UpdateTextTask(Context context , String string  , TextView txCity,TextView txTemp1,TextView txTemp2,TextView txClimate,TextView txTime)
    {
        this.context = context;
        this.url = string;
        this.txCity = txCity;
        this.txTemp1 = txTemp1;
        this.txTemp2 = txTemp2;
        this.txClimate = txClimate;
        this.txTime = txTime;
    }

    protected void onPreExecute()
    {

    }
    protected String doInBackground(Void... params) {




        String result = Common.postGetJson(url);
        //第三个参数为String 所以此处return一个String类型的数据
        return result;
    }
    protected void onPostExecute(String i) {
        String city     = null;
        String temp1    = null;
        String temp2    = null;
        String climate  = null;
        String time     = null;
        try {
            JSONObject jsonObject = new JSONObject(i).getJSONObject("weatherinfo");
            city    = jsonObject.getString("city");
            temp1   = jsonObject.getString("temp1");
            temp2   = jsonObject.getString("temp2");
            climate = jsonObject.getString("weather");
            time    = jsonObject.getString("ptime");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        txCity.setText(city);
        txTemp2.setText(temp2);
        txTemp1.setText(temp1);
        txClimate.setText(climate);
        txTime.setText(time);

    }

}
