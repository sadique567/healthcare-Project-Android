package com.org.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseConn extends SQLiteOpenHelper {
    String readUrl = "https://784907c4-7b12-488e-8381-01f7172ebc1b.mock.pstmn.io";
    public DatabaseConn(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String qry1 = "create table users (username text ,aadharNo text , email text ,phoneNo int, password text)";
        sqLiteDatabase.execSQL(qry1);

        String qryCart = "create table cart (username text , product text , price float , otype text)";
        sqLiteDatabase.execSQL(qryCart);

        String qryCheckOut = "create table checkout (ODusername text,fullname text ,aadharNo text , email text,phoneNo int,date text, time text, amount double, otype text)";
        sqLiteDatabase.execSQL(qryCheckOut);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
        // user for insert data
    public void userRegistration(String name , int aadharNo , String email , int phoneNo , String password){
        ContentValues cv = new ContentValues();
        cv.put("username" , name);
        cv.put("aadharNo" , aadharNo);
        cv.put("email" , email);
        cv.put("phoneNo" , phoneNo);
        cv.put("password" , password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users" , null , cv);
        db.close();
    }

        //use for fetch data or check data is available or not

    public int userLogin(String username , String password){
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" select * from users where username=? and password=? ", str);
        if (c.moveToFirst())
        {
            result = 1;
        }

        return  result;
    }
    //use for insert data
    public void addToCart(String userName , String product , float price , String otype){
        ContentValues cv = new ContentValues();
        cv.put("username ",userName);
        cv.put("product" , product);
        cv.put("price",price);
        cv.put("otype",otype);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart" , null , cv);
        db.close();
    }

    //check data is available or not

    public int checkCart(String userName , String product){
        int result  = 0;
        String [] str = new String [2];
        str[0] = userName;
        str[1] = product;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from cart where username = ? and product = ?" , str);
        if (c.moveToFirst()){
            result = 1;
        }
        db.close();
        return result;
    }

    // for deleting data


    public void removeCart(String userName  , String otype){
        String [] str = new String [2];
        str[0] = userName;
        str[1] = otype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart" , "username = ? and otype = ?", str);
        db.close();

    }

    public ArrayList getCartData(String username , String otype){
    ArrayList<String> arr = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();
    String [] str = new String[2];
    str[0] = username;
    str[1] = otype;
    Cursor c = db.rawQuery("select * from cart where username = ? and otype = ?",str);
    if(c.moveToFirst()){
        do {
            String product = c.getString(1);
            String price = c.getString(2);
            arr.add(product + "$:"+price);
        }while (c.moveToNext());
    }
    db.close();
    return arr;
    }
//chechout or orderplace
    public void addOrder(String uname , String fullname ,String aadhar,
                         String email, String phone, String date , String time ,
                         double amount , String otype){
        ContentValues cv = new ContentValues();
        cv.put("ODusername" , uname );
        cv.put("fullname" , fullname );
        cv.put("aadharNo" , aadhar );
        cv.put("email" , email );
        cv.put("phoneNo" , phone );
        cv.put("date" , date );
        cv.put("time" , time );
        cv.put("amount" , amount );
        cv.put("otype" , otype );
        SQLiteDatabase db = getWritableDatabase();
        db.insert("checkout" , null , cv);
        db.close();
    }

    public ArrayList getOrderDetailsData(String userName){
        ArrayList<String> rr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String [] str = new String[1];
        str[0] = userName;
        Cursor c = db.rawQuery("select * from checkout where ODusername = ?",str);
        if(c.moveToFirst()){
            do{
                rr.add(c.getString(0)+"$"+c.getString(1)+"$"+c.getString(2)+"$"+c.getString(3)+"$"+c.getString(4)+"$"
                +c.getString(5)+"$"+c.getString(6)+"$"+c.getString(7)+"$"+c.getString(8));
            }while (c.moveToNext());
        }
        db.close();
        return rr;
    }

    public int checkAppoinmentExists(String username , String fullname){
        SQLiteDatabase db = getReadableDatabase();
        int result = 0;
        String [] str = new String[2];
        str[0] = username;
        str[1]=fullname;
        Cursor c = db.rawQuery("select * from checkout where ODusername=? and  fullname=?" ,str);
        if (c.moveToFirst()){
            result = 1;
        }
        db.close();
        return result;
    }

}
