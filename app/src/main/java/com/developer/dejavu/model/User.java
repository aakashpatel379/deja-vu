package com.developer.dejavu.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String uID;
    public String name;
    public String email;
    public String displayName;
    public String imgURL;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


}
