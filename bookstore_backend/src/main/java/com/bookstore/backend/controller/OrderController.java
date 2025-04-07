package com.bookstore.backend.controller;

import com.bookstore.backend.entity.Orders;
import com.bookstore.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName OrderController
 * @Author WuTong w13694105330@163.com
 * @Date 2025/3/15 15:36
 * @Version V1.0
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    // 获取用户书架
    @GetMapping("/list/{userId}")
    public List<Orders> getUserOrder(@PathVariable Integer userId) {
        return orderService.getUserOrder(userId);
    }
}
