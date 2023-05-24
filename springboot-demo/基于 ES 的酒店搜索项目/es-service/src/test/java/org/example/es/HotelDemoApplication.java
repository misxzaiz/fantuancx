package org.example.es;

import org.example.es.service.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HotelDemoApplication {

    @Autowired
    private HotelService hotelService;

    @Test
    void contextLoads() {
        System.out.println("Hello Word!");
        System.out.println(hotelService.list());
    }


}
