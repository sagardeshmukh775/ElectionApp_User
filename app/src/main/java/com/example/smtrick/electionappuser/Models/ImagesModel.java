package com.example.smtrick.electionappuser.Models;

import java.io.Serializable;

public class ImagesModel implements Serializable {
    private String smallImage;
    private String largImage;

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargImage() {
        return largImage;
    }

    public void setLargImage(String largImage) {
        this.largImage = largImage;
    }
}
