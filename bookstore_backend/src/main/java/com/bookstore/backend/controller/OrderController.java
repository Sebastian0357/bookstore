package com.bookstore.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Orders;
import com.bookstore.backend.entity.Record;
import com.bookstore.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

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


    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<Orders> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String orderId = (String) param.get("orderId");
        String username = (String) param.get("username");

        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(orderId) && !"null".equals(orderId)) {
            queryWrapper.eq("o.id", orderId);
        }
        if (StringUtils.isNotBlank(username) && !"null".equals(username)) {
            queryWrapper.like("u.username", username);
        }

        IPage result = orderService.pageCC(page, queryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }

    // 取消订单接口
    @PostMapping("/cancel/{orderId}")
    public Result cancelOrder(@PathVariable Integer orderId) {
        Orders order = new Orders();
        order.setId(orderId);
        // 更新订单状态为已取消
        order.setStatus("已取消");
        boolean updated = orderService.updateById(order);

        if (updated) {
            return Result.success("订单已取消");
        } else {
            return Result.fail("取消订单失败，请重试");
        }
    }

    // 删除订单接口
    @DeleteMapping("/delete/{orderId}")
    public Result deleteOrder(@PathVariable Integer orderId) {
        // 删除订单
        boolean removed = orderService.removeById(orderId);

        if (removed) {
            return Result.success("订单已删除");
        } else {
            return Result.fail("删除订单失败，请重试");
        }
    }
    @PostMapping("/add")
    public Result addRecord(@RequestBody Orders order) {
        try {
            orderService.save(order);
            return Result.success();
        } catch (Exception e) {
            return Result.fail();
        }
    }
    @PostMapping("/update")
    public Result update(@RequestBody Orders orders) {
        return orderService.updateById(orders) ? Result.success() : Result.fail();
    }

}
