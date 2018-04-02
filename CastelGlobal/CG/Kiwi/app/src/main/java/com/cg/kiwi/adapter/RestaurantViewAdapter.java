package com.cg.kiwi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cg.kiwi.R;
import com.cg.kiwi.model.Address;
import com.cg.kiwi.model.Restaurants;
import com.cg.kiwi.model.UserRating;
import com.cg.kiwi.persistence.sqlite.SqliteDBManager;
import com.cg.kiwi.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantViewAdapter extends RecyclerView.Adapter<RestaurantViewAdapter.CustomViewHolder> {
    private ArrayList<Restaurants> items;
    private Context mContext;

    public RestaurantViewAdapter(Context context, ArrayList<Restaurants> items) {
        this.items = items;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final Restaurants restroItems = items.get(i);

        //Setting text view title
        customViewHolder.tvRestroName.setText(restroItems.getName());
        if (restroItems.getLocAddress() != null) {
            try {
                for (Address address : restroItems.getLocAddress()) {
                    customViewHolder.tvRestroLocality.setText(address.getLocality());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (restroItems.getUserRatings() != null) {
            try {
                for (UserRating rating : restroItems.getUserRatings()) {
                    customViewHolder.tvRestroRating.setText(rating.getAggregateRating());
                    //get drawable from image button
                    GradientDrawable drawable = (GradientDrawable) customViewHolder.tvRestroRating.getBackground();
                    drawable.setColor(Color.parseColor("#" + rating.getRatingColor()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        customViewHolder.tvRestroCuisines.setText(restroItems.getCuisines());
        String rupeeSymbol = mContext.getResources().getString(R.string.ruppess_symbol);
        customViewHolder.tvRestroCost.setText(rupeeSymbol + " " + restroItems.getAverageCostTwo());

        if (restroItems.getFeaturedImage() != null && !restroItems.getFeaturedImage().isEmpty()) {
            //Load image from network
            Picasso.with(mContext)
                    .load(restroItems.getFeaturedImage())
                    .placeholder(R.drawable.default_placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(customViewHolder.imageViewFeatured);
        }

        if (restroItems.getImage() != null) {
            try {
                // Show Image from DB in ImageView
                customViewHolder.imageViewFeatured.setImageBitmap(Utils.getImage(restroItems.getImage()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        customViewHolder.imageViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Snackbar.make(view, "Offline Data Save", Snackbar.LENGTH_LONG).show();
                    // Storing Offline data using asynctask for not blocking UI thread
                    new insertIntoDB(restroItems, ((BitmapDrawable) customViewHolder.imageViewFeatured.getDrawable()).getBitmap()).execute();
                    customViewHolder.imageViewFav.setImageResource(R.drawable.ic_heart_fill);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        customViewHolder.itemView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.zoom_in));
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        Object listItem = items.get(position);
        return listItem.hashCode();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.clearAnimation();
    }


    /*
    Insert into database call Aysnctask not block UI thread
    because data Offline data store.
     */
    private class insertIntoDB extends AsyncTask<String, Integer, String> {

        Restaurants restaurants;
        Bitmap bitmap;

        insertIntoDB(Restaurants restroItems, Bitmap bitmap) {
            this.restaurants = restroItems;
            this.bitmap = bitmap;
        }

        @Override
        protected String doInBackground(String... strings) {
            SqliteDBManager sqliteDBManager = new SqliteDBManager(mContext);
            sqliteDBManager.insertResturant(restaurants, Utils.getImageBytes(bitmap));
            return null;
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_restro_name)
        TextView tvRestroName;
        @BindView(R.id.tv_restro_locality)
        TextView tvRestroLocality;
        @BindView(R.id.tv_restro_rating)
        TextView tvRestroRating;
        @BindView(R.id.tv_restro_cuisines)
        TextView tvRestroCuisines;
        @BindView(R.id.tv_restro_cost)
        TextView tvRestroCost;
        @BindView(R.id.iv_feature_image)
        ImageView imageViewFeatured;
        @BindView(R.id.iv_favourite)
        ImageView imageViewFav;

        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}