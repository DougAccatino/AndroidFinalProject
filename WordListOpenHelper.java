package example.basic.com.cmp430_mywordlistcrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by esofianos1 on 11/27/18.
 */

public class WordListOpenHelper extends SQLiteOpenHelper {

    //DB Name, Version, Table Name
    private static final String DATABASE_NAME = "wordList";
    private static final int DATABASE_VERSION = 1 ;//important!!!! especially first time around
    private static final String TABLE_WORD_LIST = "wordEntries";

    //column names
    protected static final String KEY_ID = "_id";
    protected static final String KEY_WORD = "word";

    private static final String [] COLUMNS = {KEY_ID, KEY_WORD };

    private static final String CREATE_TABLE_WORD_LIST =
            "CREATE TABLE " + TABLE_WORD_LIST + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " + //if no id is specified it will be autoincremented
                    KEY_WORD +  TEXT  ;
    private SQLiteDatabase myReadableDB, myWritableDB;
    public WordListOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("WordListOpenHelper","Inside WordListOpenHelper constructor");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("WordListOpenHelper", "inside onCreate of WordListOpenHelper");
        sqLiteDatabase.execSQL(CREATE_TABLE_WORD_LIST);
        fillMyDBwithData(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("WordListOpenHelper", "inside onUpgrade of WordListOpenHelper");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD_LIST);
        onCreate(sqLiteDatabase);
    }

    protected void fillMyDBwithData(SQLiteDatabase db){
        String [] words =
                {"Android", "Database", "Hungry", "Pickle", "Burger", "Onions",
                "Exam", "Sleepiness", "Water", "Jameson", "Juice", "Bread", "Banana",
                "Peanut Butter"};

        ContentValues values = new ContentValues();
        for(int i=0; i < words.length; i++){
            values.put(KEY_WORD, words[i]);
            db.insert(TABLE_WORD_LIST, null, values);
        }
    }

    public WordItem query(int position){
        String theQuery = "SELECT * FROM " + TABLE_WORD_LIST +
                " ORDER BY " + KEY_WORD + " ASC " + "LIMIT " + position + ",1";

        Cursor cursor = null;
        WordItem wordItem = new WordItem();

        try{
            if(myReadableDB == null ){
                myReadableDB = getReadableDatabase();
            }
            cursor = myReadableDB.rawQuery(theQuery, null);
            cursor.moveToFirst();
            wordItem.setMyID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            wordItem.setMyWord(cursor.getString(cursor.getColumnIndex(KEY_WORD)));


        }catch(Exception e){
            Log.wtf("WordListOpenHelper", "OMG What a terrible failure... could not query the DB");
            e.printStackTrace();
        }finally {
            cursor.close();
            return wordItem;
        }
    }
    public long count(){
        if(myReadableDB == null){
            myReadableDB = getReadableDatabase();
        }
        long theCount =  DatabaseUtils.queryNumEntries(myReadableDB, TABLE_WORD_LIST);
        return theCount;
    }

    public long insert(String word){
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word);
        long theID = 0;
        try{
            if(myWritableDB == null){
                myWritableDB = getWritableDatabase();
            }
            theID = myWritableDB.insert(TABLE_WORD_LIST, null, values);

        }
        catch(Exception e){
            Log.d("WordListOpenHelper", "inside insert OOPS couldnot insert");
        }
        finally {
            return theID;
        }

    }

    public long delete(long id){
        long deletedID = 0;
        try{
            if(myWritableDB == null){
                myWritableDB = getWritableDatabase();
            }
            deletedID = myWritableDB.delete(TABLE_WORD_LIST,
                    KEY_ID + " = ? ",
                    new String[]{String.valueOf(id)});
        }
        catch(Exception e){
            Log.d("WordListOpenHelper", "inside delete OOPS couldnot delete");
        }
        finally{
            return deletedID;
        }
    }

    public long update(long id, String word) {
        int numOfRecordsUpdated = -1;
        try {
            if (myWritableDB == null) {
                myWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(KEY_WORD, word);
            numOfRecordsUpdated = myWritableDB.update(TABLE_WORD_LIST,
                    values,
                    KEY_ID + " = ?",
                    new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d("WordListOpenHelper", "inside delete OOPS couldnot delete");
        } finally {
            return numOfRecordsUpdated;

        }
    }

}