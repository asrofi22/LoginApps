package com.nonadev.loginapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_test";
    public static final  int DATABASE_VERSION = 1;
    public static final String TABLE_USER = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PROFILE_IMAGE_PATH = "profile_img_path";


    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USER
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USERNAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_PROFILE_IMAGE_PATH + " TEXT"
            + " ) ";

    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
    }

    public void addUser (User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.username);
        values.put(KEY_EMAIL, user.email);
        values.put(KEY_PASSWORD, user.password);
        values.put(KEY_PROFILE_IMAGE_PATH, user.profileImage);
        long todo_id = db.insert(TABLE_USER, null, values);
    }

    public User Authenticate (User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, // Selecting Table
                new String[]{KEY_ID, KEY_USERNAME, KEY_EMAIL, KEY_PASSWORD, KEY_PROFILE_IMAGE_PATH},
                KEY_USERNAME  + "=?",
                new String[]{user.username}, null, null, null);

        if (cursor !=null && cursor.moveToFirst()&& cursor.getCount()>0){
            User user1 = new User(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4));
            if (user.password.equalsIgnoreCase(user1.password)){
                return user1;
            }
        }
        return null;
    }

    public boolean isEmailExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{KEY_ID, KEY_USERNAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null );

        if (cursor !=null && cursor.moveToFirst()&& cursor.getCount()>0){
            return true;
        }
        return false;
    }

}

