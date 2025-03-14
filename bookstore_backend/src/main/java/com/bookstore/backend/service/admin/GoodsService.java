package com.bookstore.backend.service.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookstore.backend.entity.Book;

/**
 * 服务类
 */
public interface GoodsService extends IService<Book> {
    IPage pageCC(IPage<Book> page, Wrapper wrapper);
}
