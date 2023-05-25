package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
