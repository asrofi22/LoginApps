package com.nonadev.loginapps;

public class User {
    public String id;
    public String username;
    public String email;
    public String password;
    public String profileImage;



    public User(String id, String username, String email, String password, String profileImage) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
    }
}
