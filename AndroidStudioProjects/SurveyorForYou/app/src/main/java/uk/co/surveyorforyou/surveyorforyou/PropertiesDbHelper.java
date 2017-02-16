package uk.co.surveyorforyou.surveyorforyou;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ruwan on 16/02/2017.
 */

public class PropertiesDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="";
    public static final int DB_VERSION = 1;
    public static final String DROP_QUERY = "drop table if exists"+PropertiesContract.TABLE_NAME+";";
    public static final String CREATE_QUERY = "create table"+PropertiesContract.TABLE_NAME+"("+PropertiesContract.REFERENCE_NO+" text,"+PropertiesContract.CLIENT_NAME+" text,"+PropertiesContract.CLIENT_PHONE+" text,"+PropertiesContract.PPRPERTY_POSTCODE+" text,"+PropertiesContract.DATE_ORDERED+" text,"+PropertiesContract.DUE_DATE+");";


    public PropertiesDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        Log.d("Database Operations","Database Created");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_QUERY);
        Log.d("Database Table", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(DROP_QUERY);
        Log.d("Database Update", "Database Deleted");

    }

    public void putInformation(String refNomber, String clientName, String clientPhone, String postcode, String dateOrdered, String dueDate,SQLiteDatabase db){

        ContentValues  contentValues = new ContentValues();
        contentValues.put(PropertiesContract.REFERENCE_NO,refNomber);
        contentValues.put(PropertiesContract.CLIENT_NAME,clientName);
        contentValues.put(PropertiesContract.CLIENT_PHONE,clientPhone);
        contentValues.put(PropertiesContract.PPRPERTY_POSTCODE,postcode);
        contentValues.put(PropertiesContract.DATE_ORDERED,dateOrdered);
        contentValues.put(PropertiesContract.DUE_DATE,dueDate);

        long l = db.insert(PropertiesContract.TABLE_NAME,null,contentValues);
        Log.d("Database Update", "One row inserted");


    }

    public Cursor getInformation(SQLiteDatabase db){

        String[] projection = {PropertiesContract.REFERENCE_NO,PropertiesContract.CLIENT_NAME,PropertiesContract.CLIENT_PHONE,PropertiesContract.PPRPERTY_POSTCODE,PropertiesContract.DATE_ORDERED,PropertiesContract.DUE_DATE };
        Cursor cursor = db.query(PropertiesContract.TABLE_NAME,projection,null,null,null,null,null);
        return cursor;
     }
}
