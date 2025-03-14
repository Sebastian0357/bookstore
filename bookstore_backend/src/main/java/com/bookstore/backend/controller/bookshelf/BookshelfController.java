package com.bookstore.backend.controller.bookshelf;

import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Bookshelf;
import com.bookstore.backend.service.bookshelf.BookshelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BookshelfController
 * @Author WuTong w13694105330@163.com
 * @Date 2025/2/22 22:52
 * @Version V1.0
 **/
@RestController
@RequestMapping("/bookshelf")
public class BookshelfController {

    @Autowired
    private BookshelfService bookshelfService;

    // 加入书架
    @PostMapping("/add")
    public Result addBookToShelf(@RequestBody Bookshelf bookshelf) {
        boolean success = false;
        try {
            success = bookshelfService.addBookToShelf(bookshelf);

        } catch (DataIntegrityViolationException e) {
            return Result.fail("已经在书架中了");
        }
        return success ? Result.success() : Result.fail();
    }

    // 获取用户书架
    @GetMapping("/list/{userId}")
    public List<Book> getUserBookshelf(@PathVariable Integer userId) {
        return bookshelfService.getUserBookshelf(userId);
    }
}
