package com.developer.dejavu.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * User Model to handle app users information.
 */
@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public String displayName;
    public int imgIndex =-1;

    public int getImgIndex() {
        return imgIndex;
    }

    public void setImgIndex(int imgIndex) {
        this.imgIndex = imgIndex;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
