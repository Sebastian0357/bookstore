package com.bookstore.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.Record;
import com.bookstore.backend.mapper.RecordMapper;
import com.bookstore.backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CommentController
 * @Author Kangyx w13694105330@163.com
 * @Date 2025/2/11 23:19
 * @Version V1.0
 **/
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordMapper recordMapper;

    @PostMapping("/add")
    public Result addRecord(@RequestBody Record record) {
        try {
            recordMapper.insert(record);
            return Result.success();
        } catch (Exception e) {
            return Result.fail();
        }
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.apply("b.bookname like {0}", "%" + name + "%");
        }

        IPage result = recordService.pageCC(page, queryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }

    @PostMapping("/update")
    public Result update(@RequestBody Record record) {
        return recordService.updateById(record) ? Result.success() : Result.fail();
    }

    /*
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, Integer> request) {
        Integer id = request.get("id");
        if (id == null) {
            return Result.fail("缺少 id 参数");
        }
        return recordService.removeById(id) ? Result.success() : Result.fail();
    }

    @GetMapping("/queryByOrder/{orderId}")
    public Result queryByOrder(@PathVariable Integer orderId) {
        Record record = recordService.getByOrderId(orderId);
        if (record != null) {
            return Result.success(record);
        } else {
            return Result.success(null); // 没有评论也返回成功
        }
    }

}
