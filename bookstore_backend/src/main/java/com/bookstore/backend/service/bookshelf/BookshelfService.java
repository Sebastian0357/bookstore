package com.bookstore.backend.service.bookshelf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Bookshelf;

import java.util.List;

/**
 * @ClassName BookShelfService
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/22 22:26
 * @Version V1.0
 **/
public interface BookshelfService extends IService<Bookshelf> {

    boolean addBookToShelf(Bookshelf bookshelf);

    List<Book> getUserBookshelf(Integer userId);

    // 从书架移除书籍
    boolean removeBooksFromShelf(List<Integer> bookIds, Integer userId);

}
