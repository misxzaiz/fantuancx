package com.fantuancx.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantuancx.user.mapper.UserMapper;
import com.fantuancx.user.pojo.User;
import com.fantuancx.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
