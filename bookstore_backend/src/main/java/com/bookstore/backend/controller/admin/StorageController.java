package com.bookstore.backend.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Storage;
import com.bookstore.backend.service.admin.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 前端控制器：房间管理模块
 *
 * @ClassName StorageController.java
 * @Author WuTong w13694105330@163.com
 * @Date 2024/6/24
 **/
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    /*
     * 新增房间
     */
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage) {
        return storageService.save(storage) ? Result.success() : Result.fail();
    }

    /*
     * 更新房间
     */
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage) {
        return storageService.updateById(storage) ? Result.success() : Result.fail();
    }

    /*
     * 删除房间
     */
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return storageService.removeById(id) ? Result.success() : Result.fail();
    }

//    @GetMapping("/check")
//    public Result check(@RequestParam String id){
//        return storageService.updateById(id)?Result.success():Result.fail();
//    }

    /*
     * 查询房间列表
     */
    @GetMapping("/list")
    public Result list() {
        List list = storageService.list();
        return Result.success(list);
    }

    /*
     * 模糊查询：根据输入查询房间并以分页的形式展示

     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<Storage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like(Storage::getName, name);
        }

        IPage result = storageService.pageCC(page, queryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String id = (String) param.get("id");

        Page<Storage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Storage::getName, name);
        }
        if (StringUtils.isNotBlank(id)) {
            lambdaQueryWrapper.eq(Storage::getId, id);
        }

        IPage result = storageService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total=>" + result.getTotal());

        return Result.success(result.getRecords(), result.getTotal());
    }

}
