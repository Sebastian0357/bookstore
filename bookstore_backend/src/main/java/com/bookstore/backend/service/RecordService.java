package com.bookstore.backend.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.Record;

/**
 * @ClassName CommentService
 * @Author Kangyx w13694105330@163.com
 * @Date 2025/2/11 23:09
 * @Version V1.0
 **/
public interface RecordService extends IService<Record> {
    IPage pageCC(IPage<Record> page, Wrapper wrapper);//分页查询

    Record getByOrderId(Integer orderId);

}
