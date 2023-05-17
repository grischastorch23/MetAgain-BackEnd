package com.example.demo.rest.data;

import jakarta.validation.constraints.NotEmpty;

public class FriendsDto {

    @NotEmpty
    private String username1;

    @NotEmpty
    private String username2;

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getUsername1() {
        return username1;
    }

    public String getUsername2() {
        return username2;
    }
}
