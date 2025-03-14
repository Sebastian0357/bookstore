package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bookstore.backend.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface GoodsMapper extends BaseMapper<Book> {
    IPage pageCC(IPage<Book> page, @Param(Constants.WRAPPER) Wrapper<Book> wrapper);
}
