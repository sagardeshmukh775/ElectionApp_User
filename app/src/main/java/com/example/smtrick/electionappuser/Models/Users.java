package com.example.smtrick.electionappuser.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Users implements Serializable {

    public String name, email,  language, userId, regId, password,mobileNumber,profileImage;
    ;

    public Users() {

    }

    Users(String name, String email, String language, String userId, String regId, String password,String mobileNumber,String profileImage) {
        this.name = name;
        this.email = email;
        this.language = language;
        this.userId = userId;
        this.regId = regId;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("name", getName());
        leedMap.put("email", getEmail());
        leedMap.put("language", getLanguage());
        leedMap.put("userId", getUserId());
        leedMap.put("regId", getRegId());
        leedMap.put("password", getPassword());
        leedMap.put("mobileNumber", getMobileNumber());
        leedMap.put("profileImage", getProfileImage());

        return leedMap;

    }
}
