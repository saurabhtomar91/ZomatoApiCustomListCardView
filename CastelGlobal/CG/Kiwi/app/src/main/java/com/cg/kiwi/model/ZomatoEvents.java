package com.cg.kiwi.model;

import java.util.ArrayList;

/**
 * Created by Saurabh on 6/20/2017.
 */

public class ZomatoEvents {

    int eventId;
    String friendlyStartDate;
    String friendlyEndDate;
    String startDate;
    String endDate;
    String endTime;
    String startTime;
    int isActive;
    ArrayList<EventPhotos> eventPhotos;
    String shareUrl;
    String title;
    String description;
    String displayTime;
    String displayDate;
    String disclaimer;
    String bookLink;

    public ZomatoEvents(int eventId, String friendlyStartDate, String friendlyEndDate, String startDate, String endDate, String endTime, String startTime, int isActive, ArrayList<EventPhotos> eventPhotos, String shareUrl, String title, String description, String displayTime, String displayDate, String disclaimer, String bookLink) {
        this.eventId = eventId;
        this.friendlyStartDate = friendlyStartDate;
        this.friendlyEndDate = friendlyEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endTime = endTime;
        this.startTime = startTime;
        this.isActive = isActive;
        this.eventPhotos = eventPhotos;
        this.shareUrl = shareUrl;
        this.title = title;
        this.description = description;
        this.displayTime = displayTime;
        this.displayDate = displayDate;
        this.disclaimer = disclaimer;
        this.bookLink = bookLink;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getFriendlyStartDate() {
        return friendlyStartDate;
    }

    public void setFriendlyStartDate(String friendlyStartDate) {
        this.friendlyStartDate = friendlyStartDate;
    }

    public String getFriendlyEndDate() {
        return friendlyEndDate;
    }

    public void setFriendlyEndDate(String friendlyEndDate) {
        this.friendlyEndDate = friendlyEndDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int isActive() {
        return isActive;
    }

    public void setActive(int active) {
        isActive = active;
    }

    public ArrayList<EventPhotos> getEventPhotos() {
        return eventPhotos;
    }

    public void setEventPhotos(ArrayList<EventPhotos> eventPhotos) {
        this.eventPhotos = eventPhotos;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }
}
