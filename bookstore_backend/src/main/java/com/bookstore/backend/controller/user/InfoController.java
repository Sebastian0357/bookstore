package com.bookstore.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookstore.backend.config.QueryPageParam;
import com.bookstore.backend.config.Result;
import com.bookstore.backend.entity.User;
import com.bookstore.backend.service.user.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InfoController
 * @Author Kangyx w13694105330@163.com
 * @Date 2024/8/19 23:05
 * @Version V1.0
 **/
@RestController
@RequestMapping("/user")
public class InfoController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/info/")
    public Map<String, String> getinfo() {
        return infoService.getinfo();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like(User::getUsername, name);
        }


        IPage result = infoService.pageCC(page, queryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }

    @PostMapping("/detail")
    public Result getUserDetail(@RequestBody Map<String, String> request) {
        User u;
        try {
            Integer id = Integer.parseInt(request.get("id"));
            u = infoService.getById(id);
        } catch (Exception e) {
            return Result.fail();
        }
        return Result.success(u);
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return infoService.updateById(user) ? Result.success() : Result.fail();
    }

    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody User user) {
        if ("0".equals(user.getRole())) {
            user.setRole("1");
        } else {
            user.setRole("0");
        }
        return infoService.updateById(user) ? Result.success() : Result.fail();
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
        return infoService.removeById(id) ? Result.success() : Result.fail();
    }
}
