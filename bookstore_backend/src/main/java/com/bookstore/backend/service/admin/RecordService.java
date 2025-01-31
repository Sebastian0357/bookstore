package com.hotel.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hotel.entity.Record;
import com.hotel.entity.User;


public interface RecordService extends IService<Record> {
    IPage pageC(IPage<Record> page);
    IPage pageCC(IPage<Record> page, Wrapper wrapper);

}
