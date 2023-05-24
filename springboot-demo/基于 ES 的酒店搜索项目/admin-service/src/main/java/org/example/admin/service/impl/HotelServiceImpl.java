package org.example.admin.service.impl;

import org.example.admin.mapper.HotelMapper;
import org.example.admin.pojo.Hotel;
import org.example.admin.service.HotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements HotelService {
}
