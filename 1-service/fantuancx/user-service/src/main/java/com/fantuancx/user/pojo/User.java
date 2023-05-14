package com.fantuancx.user.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String avatar;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
