package com.bookstore.backend.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.bookstore.backend.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName BookMapper
 * @Author WuTong w13694105330@163.com
 * @Date 2024/12/1 19:03
 * @Version V1.0
 **/
@Mapper
public interface BookMapper extends BaseMapper<Book> {

    // 分页查询：根据条件查询
    IPage pageCC(IPage<Book> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 获取热度榜（按热度排序，返回前N条）
     */
    IPage getHotRank(IPage<Book> page);

    /**
     * 获取新书榜（按出版日期排序，返回前N条）
     */
    IPage getNewBookRank(IPage<Book> page);

    /**
     * 获取畅销榜（按销量排序，返回前N条）
     */
    IPage getBestSellerRank(IPage<Book> page);

    /**
     * 获取评分榜（按评分排序，返回前N条）
     */
    IPage getRatingRank(IPage<Book> page);

    /**
     * 根据id获取书籍详细信息
     */
    Book getBookById(@Param("id") Integer id);

    /**
     * 搜索书籍
     */
    List<Book> searchBooks(String query);

    /**
     * 最近热搜
     */
    List<Book> hotSearch(@Param("limit") Integer limit);

    /**
     * 增加搜索次数
     */
    Integer increaseSearchCount(@Param("id") Integer id);
}
