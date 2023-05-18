package com.fantuancx.user.pojo;

import lombok.Data;

@Data
public class UserReq {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String avatar;
    private String createdAt;
    private String updatedAt;

    public UserReq(){}

    public UserReq(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.createdAt = user.getFormattedCreateTime();
        this.updatedAt = user.getFormattedUpdateTime();
    }
}
