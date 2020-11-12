package com.example.smtrick.electionappuser.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PostVO implements Serializable {

    private String postName, postCategory, postDetails, postImage, postId;


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

    public Long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = createdDateTime;
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

        return leedMap;

    }

}


