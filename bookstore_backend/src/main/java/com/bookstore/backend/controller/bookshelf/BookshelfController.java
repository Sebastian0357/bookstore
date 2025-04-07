package com.bookstore.backend.controller.bookshelf;

import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Bookshelf;
import com.bookstore.backend.entity.Orders;
import com.bookstore.backend.mapper.OrderMapper;
import com.bookstore.backend.service.bookshelf.BookshelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private OrderMapper orderMapper;

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

    // 购买书籍
    @PostMapping("/purchase")
    public Result purchaseBooks(@RequestBody List<Book> books, @RequestParam Integer userId) {
        try {
            List<Orders> orders = new ArrayList<>();

            for (Book book : books) {
                Orders order = new Orders();
                order.setUserId(userId);
                order.setBookId(book.getId());
                order.setBookname(book.getBookname());
                order.setAuthor(book.getAuthor());
                order.setPrice(book.getPrice());
                order.setDate(new Date());
                order.setStatus("已购买");

                orders.add(order);
            }

            if (!orders.isEmpty()) {
                orderMapper.insertBatch(orders);
            }
            return Result.success("购买成功", 1L);
        } catch (Exception e) {
            return Result.fail("购买失败，请稍后重试");
        }
    }

    // 从书架移除书籍
    @PostMapping("/remove")
    public Result removeBooks(@RequestBody List<Integer> bookIds, @RequestParam Integer userId) {
        try {
            bookshelfService.removeBooksFromShelf(bookIds, userId);  // 调用BookshelfService移除书籍
            return Result.success("移除书籍成功");
        } catch (Exception e) {
            return Result.fail("移除书籍失败，请稍后重试");
        }
    }
}
