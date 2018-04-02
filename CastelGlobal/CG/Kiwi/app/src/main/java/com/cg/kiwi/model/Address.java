package com.cg.kiwi.model;

/**
 * Created by Saurabh on 6/20/2017.
 */

public class Address {


    String Address;
    String locality;
    String city;
    String city_id;
    String latitude;
    String longitude;
    String zipcode;
    String country_id;
    String locality_verbose;

    public Address(String address, String locality, String city, String city_id, String latitude, String longitude, String zipcode, String country_id, String locality_verbose) {
        Address = address;
        this.locality = locality;
        this.city = city;
        this.city_id = city_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipcode = zipcode;
        this.country_id = country_id;
        this.locality_verbose = locality_verbose;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getLocality_verbose() {
        return locality_verbose;
    }

    public void setLocality_verbose(String locality_verbose) {
        this.locality_verbose = locality_verbose;
    }
}
