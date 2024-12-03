package com.bookstore.backend.service.book;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;

import java.util.List;


/**
 * @ClassName BookService
 * @Author WuTong w13694105330@163.com
 * @Date 2024/12/1 19:05
 * @Version V1.0
 **/
public interface BookService {
    IPage pageCC(IPage<Book> page, Wrapper wrapper);

    List<Book> getBookList();
}
