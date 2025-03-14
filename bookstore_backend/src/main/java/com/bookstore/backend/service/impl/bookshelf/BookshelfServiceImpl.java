package com.bookstore.backend.service.impl.bookshelf;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.BookShelf;
import com.bookstore.backend.mapper.BookMapper;
import com.bookstore.backend.mapper.BookShelfMapper;
import com.bookstore.backend.service.book.BookService;
import com.bookstore.backend.service.bookshelf.BookShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName BookShelfServiceImpl
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/22 22:29
 * @Version V1.0
 **/
@Service
public class BookShelfServiceImpl extends ServiceImpl<BookShelfMapper, BookShelf> implements BookShelfService {

    @Autowired
    BookShelfMapper 
    @Override
    public void addToBookshelf() {

    }
}
