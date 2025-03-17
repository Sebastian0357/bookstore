package com.bookstore.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Order;
import com.bookstore.backend.mapper.BookMapper;
import com.bookstore.backend.mapper.OrderMapper;
import com.bookstore.backend.service.OrderService;
import com.bookstore.backend.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName OrderServiceImpl
 * @Author WuTong w13694105330@163.com
 * @Date 2025/3/15 15:43
 * @Version V1.0
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public IPage pageCC(IPage<Order> page, Wrapper wrapper) {
        return orderMapper.pageCC(page, wrapper);
    }

    @Override
    public List<Order> getUserOrder(Integer userId) {
        return orderMapper.getOrdersByUserId(userId);  // 查询用户书架
    }

}
