package com.beautycare.makeup;

/**
 * Created by ShenLing on 2016/3/29.
 */
public class commentItems {
    private String userName;
    private String commentTime;
    private String commentMark;
    private String commentText;

//    public commentItems(String userName, String commentTime, String commentMark, String commentText){
//        this.userName = userName;
//        this.commentTime = commentTime;
//        this.commentMark = commentMark;
//        this.commentText = commentText;
//    }

    public void setUserName(String userName){this.userName = userName;}
    public void setCommentTime(String commentTime){this.commentTime = commentTime;}
    public void setCommentMark(String commentMark){this.commentMark = commentMark;}
    public void setCommentText(String commentText){this.commentText = commentText;}

    public String getUserName(){return userName;}
    public String getCommentTime(){return commentTime;}
    public String getCommentMark(){return commentMark;}
    public String getCommentText(){return commentText;}
}
