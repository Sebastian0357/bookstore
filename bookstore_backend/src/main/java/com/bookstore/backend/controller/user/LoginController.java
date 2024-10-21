package com.bookstore.backend.controller.user;

import com.bookstore.backend.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName LoginController
 * @Author WuTong w13694105330@163.com
 * @Date 2024/8/19 22:37
 * @Version V1.0
 **/

/**
 * @RestController 是一个组合注解，它结合了 @Controller 和 @ResponseBody 注解的功能（就相当于把两个注解组合在一起）。
 * 在使用 @RestController 注解标记的类中，每个方法的返回值都会以 JSON 或 XML 的形式直接写入 HTTP 响应体中，
 * 相当于在每个方法上都添加了 @ResponseBody 注解。
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * @param map
     * @return
     * @RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解） 注解@RequestParam接收的参数是来自requestHeader中，即请求头。通常用于GET请求
     * <p>
     * 注解@RequestBody接收的参数是来自requestBody中，即请求体。
     * 一般用于处理非 Content-Type: application/form-data编码格式的数据，
     * 比如：application/json、application/xml、applicatioin/x-www-form-urlencoded等类型的数据。
     * 就application/json类型的数据而言，使用注解@RequestBody可以将body里面所有的json数据传到后端，后端再进行解析。
     * 由于@RequestBody可用来处理 Content-Type 为 application/json 编码的内容，
     * <p>
     * 所以在postman中，选择body的类型为row -> JSON(application/json)，
     * 这样在 Headers 中也会自动变为 Content-Type : application/json 编码格式。
     */
    @PostMapping("/user/token/")
    public Map<String, String> getToken(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        return loginService.getToken(username, password);
    }
}
