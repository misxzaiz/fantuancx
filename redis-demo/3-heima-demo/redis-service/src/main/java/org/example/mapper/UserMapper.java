package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
