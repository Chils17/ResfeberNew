package com.travel.resfeber.api.model.Feedback;

public class FeedbackRequest {

    /**
     * AddDate : /Date(928167600000-0500)/
     * Feedback : String content
     * FeedbackID : 9223372036854775807
     * IsDeleted : true
     * UserID : 9223372036854775807
     */

    private long UserID;
    private String Feedback;

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }
}
