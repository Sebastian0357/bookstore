package com.bookstore.backend.service.impl.bookshelf;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Bookshelf;
import com.bookstore.backend.mapper.BookshelfMapper;
import com.bookstore.backend.service.bookshelf.BookshelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BookShelfServiceImpl
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/22 22:29
 * @Version V1.0
 **/
@Service
public class BookshelfServiceImpl extends ServiceImpl<BookshelfMapper, Bookshelf> implements BookshelfService {

    @Autowired
    BookshelfMapper bookshelfMapper;

    @Override
    public boolean addBookToShelf(Bookshelf bookshelf) {
        return save(bookshelf);  // 保存书籍到书架
    }

    @Override
    public List<Book> getUserBookshelf(Integer userId) {
        return bookshelfMapper.getBooksByUserId(userId);  // 查询用户书架
    }

    @Override
    public boolean removeBooksFromShelf(List<Integer> bookIds, Integer userId) {
        if (bookIds == null || bookIds.isEmpty() || userId == null) {
            return false;
        }

        return this.remove(new QueryWrapper<Bookshelf>()
                .in("bookid", bookIds)
                .eq("userid", userId));

    }
}