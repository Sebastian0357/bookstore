package com.bookstore.backend.service.book;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.Book;

import java.util.List;


/**
 * @ClassName BookService
 * @Author WuTong w13694105330@163.com
 * @Date 2024/12/1 19:05
 * @Version V1.0
 **/
public interface BookService extends IService<Book> {
    IPage pageCC(IPage<Book> page, Wrapper wrapper);//分页查询

    List<Book> getHotRank(int limit);// 热度榜

    List<Book> getNewBookRank(int limit);// 新书榜

    List<Book> getBestSellerRank(int limit);// 推荐榜

    List<Book> getRatingRank(int limit);// 畅销榜

    Book getBookById(Integer id);

    List<Book> searchBooks(String query);

    List<Book> hotSearch(int limit);

    Integer increaseSearchCount(int limit);
}
