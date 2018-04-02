package com.cg.kiwi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Saurabh on 6/20/2017.
 */

public class Restaurants implements Parcelable {


    String id;
    String name;
    String url;
    ArrayList<Address> locAddress;
    int switchToOderMenu;
    String cuisines;
    String averageCostTwo;
    String priceRange;
    String currency;
    ArrayList<ZomatoEvents> zomatoEvents;
    String thumbUrl;
    ArrayList<UserRating> userRatings;
    String photosUrl;
    String menuUrl;
    String featuredImage;
    int hasOnlineDelivery;
    int isDeliveringNow;
    String deepLink;
    int hasTabelBooking;
    String eventsUrl;
    byte[] image;


    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the Student data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeArray(locAddress.toArray());
        dest.writeString(cuisines);
        dest.writeString(averageCostTwo);
        dest.writeString(priceRange);
        dest.writeString(currency);
        dest.writeArray(zomatoEvents.toArray());
        dest.writeString(thumbUrl);
        dest.writeArray(userRatings.toArray());
        dest.writeString(photosUrl);
        dest.writeString(menuUrl);
        dest.writeString(featuredImage);
        dest.writeString(thumbUrl);
        dest.writeString(deepLink);
        dest.writeString(eventsUrl);
    }

    public Restaurants(){

    }

    /**
     * A constructor that initializes the Student object
     **/
    public Restaurants(String id, String name, String url, ArrayList<Address> locAddress,int switchToOderMenu, String cuisines, String averageCostTwo, String priceRange, String currency, ArrayList<ZomatoEvents> zomatoEvents,String thumbUrl, ArrayList<UserRating> userRatings, String photosUrl, String menuUrl, String featuredImage, int hasOnlineDelivery, int isDeliveringNow, String deepLink, int hasTabelBooking, String eventsUrl) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.locAddress = locAddress;
        this.switchToOderMenu = switchToOderMenu;
        this.cuisines = cuisines;
        this.averageCostTwo = averageCostTwo;
        this.priceRange = priceRange;
        this.currency = currency;
        this.zomatoEvents = zomatoEvents;
        this.thumbUrl = thumbUrl;
        this.userRatings = userRatings;
        this.photosUrl = photosUrl;
        this.menuUrl = menuUrl;
        this.featuredImage = featuredImage;
        this.hasOnlineDelivery = hasOnlineDelivery;
        this.isDeliveringNow = isDeliveringNow;
        this.deepLink = deepLink;
        this.hasTabelBooking = hasTabelBooking;
        this.eventsUrl = eventsUrl;
    }

    /**
     * Retrieving Student data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Restaurants(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.url = in.readString();
        this.locAddress = new ArrayList<Address>();
        this.locAddress = in.readArrayList(Address.class.getClassLoader());
        this.cuisines = in.readString();
        this.averageCostTwo = in.readString();
        this.priceRange = in.readString();
        this.currency = in.readString();
        this.zomatoEvents = new ArrayList<ZomatoEvents>();
        this.zomatoEvents = in.readArrayList(ZomatoEvents.class.getClassLoader());
        this.thumbUrl = in.readString();
        this.userRatings = new ArrayList<UserRating>();
        this.userRatings = in.readArrayList(UserRating.class.getClassLoader());
        this.photosUrl = in.readString();
        this.menuUrl = in.readString();
        this.featuredImage = in.readString();
        this.deepLink = in.readString();
        this.eventsUrl = in.readString();
    }

    public static final Parcelable.Creator<Restaurants> CREATOR = new Parcelable.Creator<Restaurants>() {

        @Override
        public Restaurants createFromParcel(Parcel source) {
            return new Restaurants(source);
        }

        @Override
        public Restaurants[] newArray(int size) {
            return new Restaurants[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Address> getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(ArrayList<Address> locAddress) {
        this.locAddress = locAddress;
    }

    public int isSwitchToOderMenu() {
        return switchToOderMenu;
    }

    public void setSwitchToOderMenu(int switchToOderMenu) {
        this.switchToOderMenu = switchToOderMenu;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getAverageCostTwo() {
        return averageCostTwo;
    }

    public void setAverageCostTwo(String averageCostTwo) {
        this.averageCostTwo = averageCostTwo;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ArrayList<ZomatoEvents> getZomatoEvents() {
        return zomatoEvents;
    }

    public void setZomatoEvents(ArrayList<ZomatoEvents> zomatoEvents) {
        this.zomatoEvents = zomatoEvents;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public ArrayList<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(ArrayList<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public int isHasOnlineDelivery() {
        return hasOnlineDelivery;
    }

    public void setHasOnlineDelivery(int hasOnlineDelivery) {
        this.hasOnlineDelivery = hasOnlineDelivery;
    }

    public int isDeliveringNow() {
        return isDeliveringNow;
    }

    public void setDeliveringNow(int deliveringNow) {
        isDeliveringNow = deliveringNow;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public int isHasTabelBooking() {
        return hasTabelBooking;
    }

    public void setHasTabelBooking(int hasTabelBooking) {
        this.hasTabelBooking = hasTabelBooking;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public static Creator<Restaurants> getCREATOR() {
        return CREATOR;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
