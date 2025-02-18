package com.example.bmpmessanger;

public class Users {
    String profileImg,email,Username,Password,UserId,LastName,Status;
    public Users(){}
    public Users(String id, String username, String email, String password, String imgUri, String status) {
        this.UserId=id;
        this.Username=username;
        this.email=email;
        this.Password=password;
        this.profileImg=imgUri;
        this.Status=status;
    }


    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
