package com.bookstore.backend.service.impl.book;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.mapper.BookMapper;
import com.bookstore.backend.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BookServiceImpl
 * @Author WuTong w13694105330@163.com
 * @Date 2024/12/1 19:05
 * @Version V1.0
 **/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public IPage pageCC(IPage<Book> page, Wrapper wrapper) {
        return bookMapper.pageCC(page,wrapper);
    }

    @Override
    public List<Book> getBookList() {
        QueryWrapper<Book> wrapper = new QueryWrapper();

        return bookMapper.selectList(wrapper);
    }

}
