package com.example.bt2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bt2.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void InsertUser(User user);

    @Query("SELECT * FROM UserNgayThang")
    List<User> getListUser();

    @Update
    void UpdateUser(User user);

    @Query("SELECT * FROM UserNgayThang where ngayThang= :ngay")
    List<User> checkDate(String ngay);

    @Delete
    void DeleteUser(User user);
}
