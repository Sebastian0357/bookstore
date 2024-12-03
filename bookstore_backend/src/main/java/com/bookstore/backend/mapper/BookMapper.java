package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bookstore.backend.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName BookMapper
 * @Author WuTong w13694105330@163.com
 * @Date 2024/12/1 19:03
 * @Version V1.0
 **/
@Mapper
public interface BookMapper extends BaseMapper<Book> {
    IPage pageCC(IPage<Book> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
