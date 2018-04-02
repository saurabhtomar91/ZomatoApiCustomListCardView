package com.cg.kiwi.model;

/**
 * Created by Saurabh on 6/20/2017.
 */

public class UserRating {

    String aggregateRating;
    String ratingText;
    String ratingColor;
    String votes;

    public UserRating(String aggregateRating, String ratingText, String ratingColor, String votes) {
        this.aggregateRating = aggregateRating;
        this.ratingText = ratingText;
        this.ratingColor = ratingColor;
        this.votes = votes;
    }

    public String getAggregateRating() {
        return aggregateRating;
    }

    public void setAggregateRating(String aggregateRating) {
        this.aggregateRating = aggregateRating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}
