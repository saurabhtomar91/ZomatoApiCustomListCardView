package com.cg.kiwi.persistence.sqlite;

import android.content.Context;

import com.cg.kiwi.model.Restaurants;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by abhishekk on 09-09-2015.
 */
public class SqliteDBManager {

    private DBHelper dbHelper;

    public SqliteDBManager(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }


    public ArrayList<Restaurants> loadResturantData(int offset, int pagesize){
        return dbHelper.loadResturantData(offset, pagesize);
    }


    public boolean insertResturant(Restaurants restaurants, byte[] image) {
        return dbHelper.insertResturant(restaurants, image);
    }


}
