package com.hotel.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.common.QueryPageParam;
import com.hotel.common.Result;
import com.hotel.entity.Record;
import com.hotel.entity.User;
import com.hotel.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @PostMapping("/list")
    public Result list(@RequestBody QueryPageParam query){
        Page page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        List list = recordService.list();
        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        IPage result = recordService.pageCC(page,queryWrapper);
        return Result.success(result.getRecords(),result.getTotal());
//        return Result.success(list);
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        IPage result = recordService.pageC(page);
        System.out.println("total=>"+result.getTotal());

        return Result.success(result.getRecords(),result.getTotal());

    }

    @PostMapping("/save")
    public Result save(@RequestBody Record record) {
        return recordService.save(record) ? Result.success() : Result.fail();


    }


//    @PostMapping("/listPage")
//    public Result listPage(@RequestBody QueryPageParam query){
//        HashMap param = query.getParam();
////        String name = (String)param.get("name");
////        String goodstype = (String)param.get("goodstype");
////        String storage = (String)param.get("storage");
//
//        Page page = new Page();
//        page.setCurrent(query.getPageNum());
//        page.setSize(query.getPageSize());
//
//        QueryWrapper<Record> queryWrapper = new QueryWrapper();
////        queryWrapper.apply("a.goods=b.id and b.storage=c.id and b.goodsType=d.id ");
//        queryWrapper.apply("");
//
////        if("2".equals(roleId)){
////            // queryWrapper.eq(Record::getUserid,userId);
////            queryWrapper.apply(" a.userId= "+userId);
////        }
//
////        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
////            queryWrapper.like("b.name",name);
////        }
////        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
////            queryWrapper.eq("d.id",goodstype);
////        }
////        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
////            queryWrapper.eq("c.id",storage);
////        }
//
//        IPage result = recordService.pageCC(page,queryWrapper);
//        return Result.success(result.getRecords(),result.getTotal());
//    }

}
