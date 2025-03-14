package com.bookstore.backend.service.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.Record;


public interface RecordService extends IService<Record> {
    IPage pageC(IPage<Record> page);

    IPage pageCC(IPage<Record> page, Wrapper wrapper);

}
