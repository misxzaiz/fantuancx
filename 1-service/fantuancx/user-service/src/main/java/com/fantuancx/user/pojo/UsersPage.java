package com.fantuancx.user.pojo;

import lombok.Data;

import java.util.List;

@Data
public class UsersPage {
    private Long pages;
    private List<User> users;
}
