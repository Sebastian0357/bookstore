package com.bookstore.backend.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Goods;
import com.bookstore.backend.service.admin.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 前端控制器：物品管理
 *
 * @ClassName GoodsController.java
 * @Author WuTong w13694105330@163.com
 * @Date 2024/6/20 17:46
 * @Version V1.0
 **/
//@RestController 是一个组合注解，它结合了 @Controller 和 @ResponseBody 注解的功能
@RestController
@RequestMapping("/goods")
public class GoodsController {
    //它可以对类成员变量、方法及构造函数进行标注，让 spring 完成 bean 自动装配的工作。
    //@Autowired注解是Spring提供的，而@Resource注解是J2EE本身提供的
    @Autowired
    private GoodsService goodsService;

//    /*
//     * 新增物品
//     * @RequestBody主要用来接收前端传递给后端的json字符串中的数据的
//     */
//    @PostMapping("/save")
//    public Result save(@RequestBody Goods goods) {
//        return goodsService.save(Book) ? Result.success() : Result.fail();
//    }
//
//    /*
//     * 更新物品
//     */
//    @PostMapping("/update")
//    public Result update(@RequestBody book Book) {
//        return goodsService.updateById(Book) ? Result.success() : Result.fail();
//    }
//
//    /*
//     * 删除物品
//     * @RequestParam用于将指定的请求参数赋值给方法中的形参。
//     */
//    @GetMapping("/del")
//    public Result del(@RequestParam String id) {
//        return goodsService.removeById(id) ? Result.success() : Result.fail();
//    }

    /*
     * 模糊查询：根据输入查询物品并以分页的形式展示
     *
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String goodstype = (String) param.get("goodstype");
        String storage = (String) param.get("storage");

        Page<Book> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like(Goods::getName, name);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) {
            queryWrapper.like(Goods::getGoodstype, goodstype);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            queryWrapper.like(Goods::getStorage, storage);
        }

        IPage result = goodsService.pageCC(page, queryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }

}
