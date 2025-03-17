package com.bookstore.backend.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Order;

import java.util.List;

/**
 * @ClassName OrderService
 * @Author WuTong w13694105330@163.com
 * @Date 2025/3/15 15:37
 * @Version V1.0
 **/
public interface OrderService extends IService<Order> {
    IPage pageCC(IPage<Order> page, Wrapper wrapper);//分页查询

    List<Order> getUserOrder(Integer userId);
}
