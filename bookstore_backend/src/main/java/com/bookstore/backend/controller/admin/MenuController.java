package com.bookstore.backend.controller.admin;


import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Menu;
import com.bookstore.backend.service.admin.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



/**
 * 前端控制器：菜单栏模块
* @ClassName MenuController.java
* @Author WuTong w13694105330@163.com
* @Date 2024/6/20 9:41
* @Version V1.0
**/

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /*
     * 根据用户身份获取菜单列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List list = menuService.lambdaQuery()
                .like(Menu::getMenuright,roleId)
                .list();
        return Result.success(list);
    }
}
