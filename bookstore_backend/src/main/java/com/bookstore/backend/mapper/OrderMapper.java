package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName OrderMapper
 * @Author WuTong w13694105330@163.com
 * @Date 2025/3/14 23:54
 * @Version V1.0
 **/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    void insertBatch(List<Order> list);

    // 分页查询：根据条件查询
    IPage pageCC(IPage<Order> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    List<Order> getOrdersByUserId(Integer userId);
}
