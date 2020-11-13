package com.example.smtrick.electionappuser.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostVO implements Serializable {

    private String postName, postCategory, postDetails, postImage, postId;

    private List<String> likes;
    public Long createdDateTime;

    public PostVO() {

    }

    public PostVO(int id) {
    }

    public PostVO(String postName, String postCategory, String postDetails, String postImage, String postId) {

        this.createdDateTime = createdDateTime;
        this.postName = postName;
        this.postCategory = postCategory;
        this.postDetails = postDetails;
        this.postImage = postImage;
        this.postId = postId;
        this.likes = new ArrayList<String>();

    }


    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    public String getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(String postDetails) {
        this.postDetails = postDetails;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Exclude
    public Long getCreatedDateTimeLong() {
        return createdDateTime;
    }

    public Map<String, String> getCreatedDateTime() {
        return ServerValue.TIMESTAMP;
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = (Long) createdDateTime;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("createdDateTime", getCreatedDateTime());
        leedMap.put("postName", getPostName());
        leedMap.put("postCategory", getPostCategory());
        leedMap.put("postDetails", getPostDetails());
        leedMap.put("postImage", getPostImage());
        leedMap.put("postId", getPostId());
        leedMap.put("likes", getLikes());

        return leedMap;

    }

}


