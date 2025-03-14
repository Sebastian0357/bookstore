package com.bookstore.backend.service.impl.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.mapper.GoodsMapper;
import com.bookstore.backend.service.admin.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Book> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public IPage pageCC(IPage<Book> page, Wrapper wrapper) {
        return goodsMapper.pageCC(page, wrapper);
    }
}
