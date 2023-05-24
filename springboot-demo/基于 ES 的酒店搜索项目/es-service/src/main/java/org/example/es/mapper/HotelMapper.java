package org.example.es.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.es.pojo.Hotel;

@Mapper
public interface HotelMapper extends BaseMapper<Hotel> {
}

