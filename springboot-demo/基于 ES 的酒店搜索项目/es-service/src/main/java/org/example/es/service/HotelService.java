package org.example.es.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.es.pojo.Hotel;
import org.example.es.pojo.PageResult;
import org.example.es.pojo.RequestParams;

import java.util.List;
import java.util.Map;

public interface HotelService extends IService<Hotel> {
    /**
     * 根据关键字搜索酒店信息
     * @param params 请求参数对象，包含用户输入的关键字
     * @return 酒店文档列表
     */
    PageResult search(RequestParams params);

    Map<String, List<String>> filters(RequestParams params);

    List<String> getSuggestions(String prefix);

    void deleteById(Long id);

    void insertById(Long id);

}
