package com.fantuancx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.fantuancx.user.pojo.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
