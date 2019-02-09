package com.portillo.naomyportillo.psychictest.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.portillo.naomyportillo.psychictest.model.GuessModel;

import java.util.ArrayList;
import java.util.List;

public class GuessDataBaseHelper extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "guesses.db";
    private static final String TABLE_NAME = "guesses";
    private static final int SCHEMA_VERSION = 1;

    private static GuessDataBaseHelper singletonInstance;


    private GuessDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    public static synchronized GuessDataBaseHelper getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new GuessDataBaseHelper(context.getApplicationContext());
        }
        return singletonInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "success INTEGER, failed INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addGuess(GuessModel guess) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE success = '" + guess.getSuccess() +
                        "' AND failed = '" + guess.getFails() + "';", null);

        if (cursor.getCount() == 0) {
            getWritableDatabase().execSQL("INSERT INTO " + TABLE_NAME +
                    "(success, failed) VALUES('" +
                    guess.getSuccess() + "', '" +
                    guess.getFails() + "');");
        }
        cursor.close();
    }

    public List<GuessModel> getGuessList() {
        List<GuessModel> guessList = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + ";", null);

        if(cursor != null) {

            if(cursor.moveToFirst()) {
                do {
                    GuessModel guessModel = new GuessModel(
                            cursor.getInt(cursor.getColumnIndex("success")),
                            cursor.getInt(cursor.getColumnIndex("failure")));

                    guessList.add(guessModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return guessList;
    }
}


