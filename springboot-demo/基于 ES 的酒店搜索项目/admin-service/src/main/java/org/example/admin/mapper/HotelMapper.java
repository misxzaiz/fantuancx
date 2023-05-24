package org.example.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.admin.pojo.Hotel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface HotelMapper extends BaseMapper<Hotel> {
}
