package com.bookstore.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Order;
import com.bookstore.backend.mapper.OrderMapper;
import com.bookstore.backend.service.OrderService;
import com.bookstore.backend.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Order> getUserOrder(@PathVariable Integer userId) {
        return orderService.getUserOrder(userId);
    }
}
