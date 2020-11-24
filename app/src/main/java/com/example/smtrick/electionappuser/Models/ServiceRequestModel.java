package com.example.smtrick.electionappuser.Models;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.smartloan.smtrick.smart_loan.constants.Constant;

public class ServiceRequestModel {
    private String storagePath,bitmapPath,imagePath;
    private Uri uri;
    private String leedId;
    private int imageCount;
    private int totalCount;
    private Bitmap bitmap;
    public void initReuestModel(Intent intent) {
        storagePath = intent.getStringExtra(Constant.STORAGE_PATH);
        uri = intent.getParcelableExtra(Constant.BITMAP_IMG);
        leedId = intent.getStringExtra(Constant.LEED_ID);
        imageCount = intent.getIntExtra(Constant.IMAGE_COUNT, 0);
        totalCount = intent.getIntExtra(Constant.TOTAL_IMAGE_COUNT, 0);
    }

    public String getStoragePath() {
        return storagePath;
    }

    public String getBitmapPath() {
        return bitmapPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setBitmapPath(String bitmapPath) {
        this.bitmapPath = bitmapPath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getLeedId() {
        return leedId;
    }

    public void setLeedId(String leedId) {
        this.leedId = leedId;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
