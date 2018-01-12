package com.example.jay.quizapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jay.quizapp.QuestionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 12-Jan-18.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private int rowCount = 0;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


    public List<QuestionModel> getAllQuestions(){
        List <QuestionModel> questionModelList = new ArrayList<QuestionModel>();

        String selectQuery = "SELECT * FROM "+"questions";
        Cursor cursor = database.rawQuery(selectQuery,null);
        rowCount = cursor.getCount();

        if(cursor.moveToFirst()){
            do{
                QuestionModel q = new QuestionModel(cursor.getInt(6),
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(7));

                questionModelList.add(q);

            }while (cursor.moveToNext());
        }
        return questionModelList;
    }

    public boolean updateData(int id,String givenanswer) {

        String updatetQuery = "UPDATE questions SET givenanswer = '"+givenanswer.trim()+"' WHERE id = '"+id+"'";
        Log.d("updatetQuery---",updatetQuery);

        Cursor cursor =  database.rawQuery(updatetQuery,null);
        cursor.moveToFirst();
        cursor.close();

        /*ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("givenanswer",givenanswer);
        database.update("questions", contentValues, "id = ?",new int[] { id });*/
        return true;
    }

    public boolean deleteData() {

        String updatetQuery = "UPDATE questions SET givenanswer = "+ null;
        Cursor cursor =  database.rawQuery(updatetQuery,null);
        cursor.moveToFirst();
        cursor.close();

        /*ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("givenanswer",givenanswer);
        database.update("questions", contentValues, "id = ?",new int[] { id });*/
        return true;
    }
}
