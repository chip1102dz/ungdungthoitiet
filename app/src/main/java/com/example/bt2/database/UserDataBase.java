package com.example.bt2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bt2.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "UserNgayThang.db";
    private static UserDataBase instacce;

    public static synchronized UserDataBase getInstance(Context context){
        if(instacce == null){
            instacce = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instacce;
    }

    public abstract UserDAO userDAO();

}
