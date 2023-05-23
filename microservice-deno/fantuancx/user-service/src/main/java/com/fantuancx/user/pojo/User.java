package com.fantuancx.user.pojo;

import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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

    public String getFormattedCreateTime() {
        // 定义时间格式化方式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 格式化时间并返回
        return dateFormat.format(this.createdAt);
    }

    public String getFormattedUpdateTime() {
        // 定义时间格式化方式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 格式化时间并返回
        return dateFormat.format(this.updatedAt);
    }


}
