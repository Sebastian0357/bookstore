package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bookstore.backend.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    IPage pageC(IPage<Record> page);
    IPage pageCC(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
