package com.bookstore.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.Orders;
import com.bookstore.backend.entity.Record;
import com.bookstore.backend.mapper.RecordMapper;
import com.bookstore.backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommentService
 * @Author Kangyx w13694105330@163.com
 * @Date 2025/2/11 23:09
 * @Version V1.0
 **/
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public IPage pageCC(IPage<Record> page, Wrapper wrapper) {
        return recordMapper.pageCC(page, wrapper);
    }

    @Override
    public Record getByOrderId(Integer orderId) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderid", orderId);
        List<Record> res = recordMapper.selectByMap(param);
        if(res.size() == 1) {
            return res.get(0);
        }
        return null;
    }


}
