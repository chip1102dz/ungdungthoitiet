package com.example.bt2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity (tableName = "UserNgayThang")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String ngayThang;
    private String nhietDo;
    private String doAm;

    public User(){

    }
    public User(String ngayThang, String nhietDo, String doAm) {
        this.ngayThang = ngayThang;
        this.nhietDo = nhietDo;
        this.doAm = doAm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public String getNhietDo() {
        return nhietDo;
    }

    public void setNhietDo(String nhietDo) {
        this.nhietDo = nhietDo;
    }

    public String getDoAm() {
        return doAm;
    }

    public void setDoAm(String doAm) {
        this.doAm = doAm;
    }
}
