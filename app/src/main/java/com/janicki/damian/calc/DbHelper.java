package com.janicki.damian.calc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database";

    //String z kodem tworzacym tabele w bazie danych, w naszej bazie mamy jedna tabele o nazwie history, z dwoma kolumnami id i data
    private static final String CREATE_TABLE =
            "CREATE TABLE " + DbContract.FeedHistory.TABLE_NAME + "( " +
                    DbContract.FeedHistory._ID          + " " +     DbContract.FeedHistory.ID_OPTIONS + ", " +
                    DbContract.FeedHistory.COLUMN_NAME_DATA + " " + DbContract.FeedHistory.DESCRIPTION_OPTIONS +
                    ")";

    //String usuwajacy tabele z bazy danych
    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.FeedHistory.TABLE_NAME;

//konstruktor
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //tworzymy tabele w bazie
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //przy zmianie bazy na wyzsza wersje usuwamy nasza tabele i tworzymy ja od nowa
        db.execSQL(DROP_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    public void addValue(String string){ //funkcja dodaje wartosc do bazy
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.FeedHistory.COLUMN_NAME_DATA,string);
        database.insert(DbContract.FeedHistory.TABLE_NAME, null, values);
    }

    public ArrayList<String> getAllToArray() { //funkcja zwracajaca cala zawartosc z bazy w postaci ArrayListy
      ArrayList<String> list = new ArrayList<>();

      String selectQuery = "SELECT * FROM " + DbContract.FeedHistory.TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery,null);

        while(cursor.moveToNext()) {
            list.add(cursor.getString(1));
        }
        cursor.close();

        return list;
    }



}
