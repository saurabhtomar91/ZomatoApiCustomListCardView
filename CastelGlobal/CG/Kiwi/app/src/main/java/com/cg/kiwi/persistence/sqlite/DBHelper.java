package com.cg.kiwi.persistence.sqlite;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cg.kiwi.model.Address;
import com.cg.kiwi.model.Restaurants;
import com.cg.kiwi.model.UserRating;
import com.cg.kiwi.utils.AppConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by abhishekk on 08-09-2015.
 */
public class DBHelper extends SQLiteOpenHelper implements AppConstants {

    private static DBHelper mInstance;
    public Context context;


    public static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHelper", "in Oncreate");

        db.execSQL("CREATE TABLE " + USER_RESTRO_TABLE_NAME + "(" +
                USER_RESTRO_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                USER_RESTRO_COLUMN_USER_ID + " TEXT, " +
                USER_RESTRO_COLUMN_NAME + " TEXT, " +
                USER_RESTRO_COLUMN_URL + " TEXT, " +
                USER_RESTRO_COLUMN_LOCALITY + " TEXT, " +
                USER_RESTRO_COLUMN_AVGCOST + " TEXT, " +
                USER_RESTRO_COLUMN_CUISINES + " TEXT, " +
                USER_RESTRO_COLUMN_IMAGE + " BLOB, " +
                USER_RESTRO_COLUMN_RATING + " TEXT, "+
                "UNIQUE (" + USER_RESTRO_COLUMN_USER_ID + " ) ON CONFLICT REPLACE" +
                        ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USER_RESTRO_TABLE_NAME);

        db.execSQL("CREATE TABLE " + USER_RESTRO_TABLE_NAME + "(" +
                USER_RESTRO_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                USER_RESTRO_COLUMN_USER_ID + " TEXT, " +
                USER_RESTRO_COLUMN_NAME + " TEXT, " +
                USER_RESTRO_COLUMN_URL + " TEXT, " +
                USER_RESTRO_COLUMN_LOCALITY + " TEXT, " +
                USER_RESTRO_COLUMN_AVGCOST + " TEXT, " +
                USER_RESTRO_COLUMN_CUISINES + " TEXT, " +
                USER_RESTRO_COLUMN_IMAGE + " BLOB, " +
                USER_RESTRO_COLUMN_RATING + " TEXT, "+
                "UNIQUE (" + USER_RESTRO_COLUMN_USER_ID + " ) ON CONFLICT REPLACE" +
                ");");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USER_RESTRO_TABLE_NAME);

        db.execSQL("CREATE TABLE " + USER_RESTRO_TABLE_NAME + "(" +
                USER_RESTRO_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                USER_RESTRO_COLUMN_USER_ID + " TEXT, " +
                USER_RESTRO_COLUMN_NAME + " TEXT, " +
                USER_RESTRO_COLUMN_URL + " TEXT, " +
                USER_RESTRO_COLUMN_LOCALITY + " TEXT, " +
                USER_RESTRO_COLUMN_AVGCOST + " TEXT, " +
                USER_RESTRO_COLUMN_CUISINES + " TEXT, " +
                USER_RESTRO_COLUMN_IMAGE + " BLOB, " +
                USER_RESTRO_COLUMN_RATING + " TEXT, "+
                "UNIQUE (" + USER_RESTRO_COLUMN_USER_ID + " ) ON CONFLICT REPLACE" +
                ");");


    }

    public void clearAppData() {
        context.deleteDatabase(DATABASE_NAME);

    }


    private boolean notNull(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String && ((String) value).equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    private void buildQuery(boolean isFirst, StringBuffer query, String param, String value, List selectionArgs) {
        if (notNull(value)) {
            if (!isFirst) {
                query.append(" AND ");
            }
            query.append(param + "=?");
            selectionArgs.add(value);
        }
    }


    public boolean containsWhiteSpace(final String testCode) {
        if (testCode != null) {
            for (int i = 0; i < testCode.length(); i++) {
                if (Character.isWhitespace(testCode.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }


    private ContentValues insertResturantValues(Restaurants restaurants,byte[] image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_RESTRO_COLUMN_USER_ID, restaurants.getId());
        contentValues.put(USER_RESTRO_COLUMN_NAME, restaurants.getName());
        contentValues.put(USER_RESTRO_COLUMN_URL, restaurants.getUrl());
        contentValues.put(USER_RESTRO_COLUMN_CUISINES, restaurants.getCuisines());
        contentValues.put(USER_RESTRO_COLUMN_IMAGE, image);

        for(Address address :restaurants.getLocAddress()){

            contentValues.put(USER_RESTRO_COLUMN_LOCALITY, address.getLocality());
        }
        contentValues.put(USER_RESTRO_COLUMN_AVGCOST, restaurants.getAverageCostTwo());
        for(UserRating userRating : restaurants.getUserRatings()){
            contentValues.put(USER_RESTRO_COLUMN_RATING, userRating.getAggregateRating());
        }
        return contentValues;
    }

    public boolean insertResturant(Restaurants restaurants,byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = insertResturantValues(restaurants,image);
        db.insertWithOnConflict(USER_RESTRO_TABLE_NAME, null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return true;
    }

    public ArrayList<Restaurants> loadResturantData(int offset, int pagesize) {
        Cursor res = queryRestaurant(offset, pagesize);
        ArrayList<Restaurants> restaurantsArrayList = new ArrayList<Restaurants>();
        try {
            if (res != null) {
                for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
                    Restaurants restaurant = new Restaurants();
                    restaurant.setName(res.getString(res.getColumnIndex(USER_RESTRO_COLUMN_USER_ID)));
                    restaurant.setName(res.getString(res.getColumnIndex(USER_RESTRO_COLUMN_NAME)));
                    restaurant.setUrl(res.getString(res.getColumnIndex(USER_RESTRO_COLUMN_URL)));
                    restaurant.setCuisines(res.getString(res.getColumnIndex(USER_RESTRO_COLUMN_CUISINES)));
                    restaurant.setAverageCostTwo(res.getString(res.getColumnIndex(USER_RESTRO_COLUMN_AVGCOST)));
                    restaurant.setImage(res.getBlob(res.getColumnIndex(USER_RESTRO_COLUMN_IMAGE)));
                    ArrayList<Address> addresses = new ArrayList<>();
                    String locality = res.getString(res.getColumnIndex(USER_RESTRO_COLUMN_LOCALITY));
                    addresses.add(new Address(null,locality,null,null,null,null,null,null,null));
                    restaurant.setLocAddress(addresses);

                    ArrayList<UserRating> userRatingArrayList = new ArrayList<>();
                    String aggrearteRating = res.getString(res.getColumnIndex(USER_RESTRO_COLUMN_RATING));
                    userRatingArrayList.add(new UserRating(aggrearteRating,null,null,null));
                    restaurant.setUserRatings(userRatingArrayList);
                    restaurantsArrayList.add(restaurant);
                }
            }
            res.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurantsArrayList;
    }

    private Cursor queryRestaurant(int offset, int count) {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer query = new StringBuffer();
        List<String> selectionArgs = new ArrayList<String>();
        String[] selectionArr = new String[]{};
        query.append("SELECT * FROM ");
        query.append(USER_RESTRO_TABLE_NAME);
        query.append(" LIMIT ");
        query.append(offset);
        query.append(" , ");
        query.append(count);
        Cursor res = db.rawQuery(query.toString(), selectionArgs.toArray(selectionArr));
        return res;

    }

}
