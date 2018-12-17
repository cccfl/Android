package com.example.yyf.notebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by yyf on 2017/12/5.
 */

public class notebookDatabaseHelper extends SQLiteOpenHelper
{
    public static final String CREATE_note = "create table note ("
            + "id integer primary key autoincrement, "
            + "content text , "
            + "date text)";
    private Context mContext;
    public notebookDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
        mContext = context;
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_note);
        Toast.makeText(mContext, "创建成功", Toast.LENGTH_SHORT).show();
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {}

}
