package com.bookstore.backend.service.impl.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookstore.backend.entity.Menu;
import com.bookstore.backend.mapper.MenuMapper;
import com.bookstore.backend.service.admin.MenuService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
