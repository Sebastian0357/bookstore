package com.bookstore.backend.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.Orders;

/**
 * @ClassName OrderService
 * @Author Kangyx w13694105330@163.com
 * @Date 2025/3/15 15:37
 * @Version V1.0
 **/
public interface OrderService extends IService<Orders> {
    IPage pageCC(IPage<Orders> page, Wrapper wrapper);//分页查询

}
