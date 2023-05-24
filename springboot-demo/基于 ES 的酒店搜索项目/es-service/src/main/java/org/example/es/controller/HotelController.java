package org.example.es.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.es.pojo.PageResult;
import org.example.es.pojo.RequestParams;
import org.example.es.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Resource
    private HotelService hotelService;
    // 搜索酒店数据
    @PostMapping("/list")
    public PageResult search(@RequestBody RequestParams params){
        return hotelService.search(params);
    }

    @PostMapping("filters")
    public Map<String, List<String>> getFilters(@RequestBody RequestParams params){
        return hotelService.filters(params);
    }

    @GetMapping("suggestion")
    public List<String> getSuggestions(@RequestParam("key") String prefix) {
        log.info("key:{}",prefix);
        return hotelService.getSuggestions(prefix);
    }
}