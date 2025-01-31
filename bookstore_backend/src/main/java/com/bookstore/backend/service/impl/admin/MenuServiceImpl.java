package com.hotel.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotel.entity.Menu;
import com.hotel.mapper.MenuMapper;
import com.hotel.service.MenuService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
