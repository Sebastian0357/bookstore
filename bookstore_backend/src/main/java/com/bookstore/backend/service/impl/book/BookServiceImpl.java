package com.bookstore.backend.service.impl.book;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.mapper.BookMapper;
import com.bookstore.backend.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BookServiceImpl
 * @Author Kangyx w13694105330@163.com
 * @Date 2024/12/1 19:05
 * @Version V1.0
 **/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public IPage pageCC(IPage<Book> page, Wrapper wrapper) {
        return bookMapper.pageCC(page, wrapper);
    }

    @Override
    public IPage getHotRank(IPage<Book> page) {
        return bookMapper.getHotRank(page);
    }

    @Override
    public IPage getNewBookRank(IPage<Book> page) {
        return bookMapper.getNewBookRank(page);
    }

    @Override
    public IPage getBestSellerRank(IPage<Book> page) {
        return bookMapper.getBestSellerRank(page);
    }

    @Override
    public IPage getRatingRank(IPage<Book> page) {
        return bookMapper.getRatingRank(page);
    }

    @Override
    public Book getBookById(Integer id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public List<Book> searchBooks(String query) {
        return bookMapper.searchBooks(query);
    }

    @Override
    public List<Book> hotSearch(int limit) {
        return bookMapper.hotSearch(limit);
    }

    @Override
    public Integer increaseSearchCount(int id) {
        return bookMapper.increaseSearchCount(id);
    }
}
