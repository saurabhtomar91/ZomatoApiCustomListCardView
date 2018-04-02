package com.cg.kiwi.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cg.kiwi.R;
import com.cg.kiwi.adapter.RestaurantViewAdapter;
import com.cg.kiwi.apiHandler.HttpUtils;
import com.cg.kiwi.model.Address;
import com.cg.kiwi.model.EventPhotos;
import com.cg.kiwi.model.Restaurants;
import com.cg.kiwi.model.UserRating;
import com.cg.kiwi.model.ZomatoEvents;
import com.cg.kiwi.persistence.sqlite.SqliteDBManager;
import com.cg.kiwi.utils.AppConstants;
import com.cg.kiwi.utils.Utils;
import com.cg.kiwi.widget.EndlessScrollRecyclListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class HomeActivity extends BaseActivity implements AppConstants{

    private int offsetValue = 0;  // offset value
    private int limitOff = 10;    // limit off
    private ArrayList<Restaurants> restaurantsArrayList = new ArrayList<Restaurants>();
    private RestaurantViewAdapter adapter;

    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.recycler_view_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.edt_location_search)
    EditText edtSearch;

    @BindView(R.id.iv_search)
    ImageView ivSearch;

    @BindView(R.id.iv_filter)
    ImageView ivFilter;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    private String locationString;
    private String sortBy = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //ButterKnife binding to view
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnScrollListener(new EndlessScrollRecyclListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                fetchApi(totalItemsCount, true);
                return true; // ONLY if more data is actually being loaded; false otherwise.

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchApi(offsetValue, false);
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   searchCommand();
                    return true;
                }
                return false;
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    searchCommand();
            }
        });

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFilter(HomeActivity.this);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void searchCommand() {
        if(Utils.isNetworkConnected(HomeActivity.this)) {
            if (!edtSearch.getText().toString().isEmpty()) {
                locationString = edtSearch.getText().toString();
                fetchApi(offsetValue, false);
            }
        }else {
            Snackbar.make(coordinatorLayout,"No internet connection",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Utils.isNetworkConnected(HomeActivity.this)){
            try {
                SqliteDBManager sqliteDBManager = new SqliteDBManager(HomeActivity.this);
                restaurantsArrayList = sqliteDBManager.loadResturantData(offsetValue, limitOff);
                adapter = new RestaurantViewAdapter(HomeActivity.this, restaurantsArrayList);
                adapter.setHasStableIds(true);
                mRecyclerView.setAdapter(adapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /*
     Method Use for showing Filter Dialog
     */
    public void dialogFilter(Context mContext){
        try {
            final Dialog mDialog = new Dialog(mContext);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.dialog_filter);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.getWindow().getAttributes().windowAnimations = R.style.Dialog_language;
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.show();

            final LinearLayout llRating = (LinearLayout) mDialog.findViewById(R.id.ll_rating);
            final LinearLayout llCost = (LinearLayout) mDialog.findViewById(R.id.ll_cost);
            final LinearLayout llDone = (LinearLayout) mDialog.findViewById(R.id.ll_done);

            llRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortBy = SORT_BY_RATING;
                    llRating.setBackgroundColor(Utils.getColor(HomeActivity.this,R.color.black_overlay));
                    llCost.setBackgroundColor(Utils.getColor(HomeActivity.this,R.color.white));
                }
            });

            llCost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortBy = SORT_BY_COST;
                    llRating.setBackgroundColor(Utils.getColor(HomeActivity.this,R.color.white));
                    llCost.setBackgroundColor(Utils.getColor(HomeActivity.this,R.color.black_overlay));
                }
            });

            llDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        mDialog.dismiss();
                        if(Utils.isNetworkConnected(HomeActivity.this)) {
                            if(!sortBy.equals("")) {
                                fetchApi(offsetValue, false);
                                restaurantsArrayList.clear();
                            }
                        }else{
                            Snackbar.make(coordinatorLayout,"No internet connection",Snackbar.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Performs a API call to the Zomato Search for particluar location based.
     * Same API call for Pagination.
     * As {@link #offsetValue} will be incremented automatically
     * and {@link #limitOff} as Counted number of limits.
     */

    public void fetchApi(int offsetValue, final boolean isLoadMore) {
        RequestParams requestParams = new RequestParams();
        requestParams.put(ENTITY_TYPE, CITY);
        requestParams.put(ENTITY_ID, 1);
        requestParams.put(Q, locationString);
        requestParams.put(START, offsetValue);
        requestParams.put(COUNT, limitOff);
        requestParams.put(SORT,sortBy);
        HttpUtils.addHeader(USER_KEY, this.getString(R.string.zomato_api_key));
        HttpUtils.get(SEARCH_API, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Snackbar.make(coordinatorLayout,"On Success",Snackbar.LENGTH_LONG).show();
                try {
                    JSONArray data = response.getJSONArray("restaurants");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonResturants = data.getJSONObject(i);
                        JSONObject jsonRestro = jsonResturants.getJSONObject("restaurant");
                        JSONObject jsonR = jsonRestro.getJSONObject("R");
                        String res_id = jsonR.getString("res_id");
                        String id = jsonRestro.getString("id");
                        String name = jsonRestro.getString("name");
                        String url = jsonRestro.getString("url");
                        JSONObject jsonLocation = jsonRestro.getJSONObject("location");
                        String Address = jsonLocation.getString("address");
                        String locality = jsonLocation.getString("locality");
                        String city = jsonLocation.getString("city");
                        String city_id = jsonLocation.getString("city_id");
                        String latitude = jsonLocation.getString("latitude");
                        String longitude = jsonLocation.getString("longitude");
                        String zipcode = jsonLocation.getString("zipcode");
                        String country_id = jsonLocation.getString("country_id");
                        String locality_verbose = jsonLocation.getString("locality_verbose");
                        ArrayList<Address> locAddress = new ArrayList<Address>();
                        locAddress.add(new Address(Address, locality, city, city_id, latitude, longitude, zipcode, country_id, locality_verbose));

                        int switchToOderMenu = jsonRestro.getInt("switch_to_order_menu");

                        String cuisines = jsonRestro.getString("cuisines");
                        String averageCostTwo = jsonRestro.getString("average_cost_for_two");
                        String priceRange = String.valueOf(jsonRestro.getInt("price_range"));
                        String currency = jsonRestro.getString("currency");
                        ArrayList<ZomatoEvents> zomatoEvents = new ArrayList<ZomatoEvents>();
                        if (jsonRestro.has("zomato_events")) {
                            JSONArray jsonZomatoEvents = jsonRestro.getJSONArray("zomato_events");
                            for (int j = 0; j < jsonZomatoEvents.length(); j++) {
                                JSONObject jsonEvents = jsonZomatoEvents.getJSONObject(j);
                                JSONObject jsonEvent = jsonEvents.getJSONObject("event");
                                int eventId = jsonEvent.getInt("event_id");
                                String friendlyStartDate = jsonEvent.getString("friendly_start_date");
                                String friendlyEndDate = jsonEvent.getString("friendly_end_date");
                                String startDate = jsonEvent.getString("start_date");
                                String endDate = jsonEvent.getString("end_date");
                                String endTime = jsonEvent.getString("end_time");
                                String startTime = jsonEvent.getString("start_time");
                                int isActive = jsonEvent.getInt("is_active");
                                JSONArray jsonPhotos = jsonEvent.getJSONArray("photos");
                                ArrayList<EventPhotos> eventPhotos = new ArrayList<EventPhotos>();
                                for (int k = 0; k < jsonPhotos.length(); k++) {
                                    JSONObject jsonPhoto = jsonPhotos.getJSONObject(k);
                                    JSONObject jsPhoto = jsonPhoto.getJSONObject("photo");
                                    String urlPhoto = jsPhoto.getString("url");
                                    String thumbUrl = jsPhoto.getString("thumb_url");
                                    int order = jsPhoto.getInt("order");
                                    int photoId = jsPhoto.getInt("photo_id");
                                    String type = jsPhoto.getString("type");
                                    eventPhotos.add(new EventPhotos(urlPhoto, thumbUrl, order, photoId, type));
                                }
                                String shareUrl = jsonEvent.getString("share_url");
                                String title = jsonEvent.getString("title");
                                String description = jsonEvent.getString("description");
                                String displayTime = jsonEvent.getString("display_time");
                                String displayDate = jsonEvent.getString("display_date");
                                String disclaimer = jsonEvent.getString("disclaimer");
                                String bookLink = jsonEvent.getString("book_link");

                                zomatoEvents.add(new ZomatoEvents(eventId, friendlyStartDate, friendlyEndDate, startDate, endDate, endTime, startTime, isActive, eventPhotos,
                                        shareUrl, title, description, displayTime, displayDate, disclaimer, bookLink));
                            }
                        }

                        String thumbUrl = jsonRestro.getString("thumb");
                        JSONObject jsonRestroRating = jsonRestro.getJSONObject("user_rating");
                        String aggregateRating = jsonRestroRating.getString("aggregate_rating");
                        String ratingText = jsonRestroRating.getString("rating_text");
                        String ratingColor = jsonRestroRating.getString("rating_color");
                        String votes = jsonRestroRating.getString("votes");
                        ArrayList<UserRating> userRatings = new ArrayList<UserRating>();
                        userRatings.add(new UserRating(aggregateRating, ratingText, ratingColor, votes));
                        String photosUrl = jsonRestro.getString("photos_url");
                        String menuUrl = jsonRestro.getString("menu_url");
                        String featuredImage = jsonRestro.getString("featured_image");
                        int hasOnlineDelivery = jsonRestro.getInt("has_online_delivery");
                        int isDeliveringNow = jsonRestro.getInt("is_delivering_now");
                        String deepLink = jsonRestro.getString("deeplink");
                        int hasTabelBooking = jsonRestro.getInt("has_table_booking");
                        String eventsUrl = jsonRestro.getString("events_url");

                        restaurantsArrayList.add(new Restaurants(id, name, url, locAddress,switchToOderMenu, cuisines, averageCostTwo,
                                priceRange, currency, zomatoEvents, thumbUrl, userRatings, photosUrl, menuUrl, featuredImage,
                                hasOnlineDelivery, isDeliveringNow, deepLink, hasTabelBooking, eventsUrl));
                    }

                    if (!isLoadMore) {
                        adapter = new RestaurantViewAdapter(HomeActivity.this, restaurantsArrayList);
                        adapter.setHasStableIds(true);
                        mRecyclerView.setAdapter(adapter);
                        Utils.hideSoftKeyboard(HomeActivity.this, mRecyclerView);
                        mSwipeRefreshLayout.setRefreshing(false);
                        edtSearch.setText("");
                    } else {
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Snackbar.make(coordinatorLayout,"On Failure",Snackbar.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        });

    }

}