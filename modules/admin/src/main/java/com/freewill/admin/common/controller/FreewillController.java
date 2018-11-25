package com.freewill.admin.common.controller;

import com.freewill.common.web.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description Restful测试接口
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-15 15:58
 */
@ResponseResult
@Slf4j
@Controller
public class FreewillController {
    private static List<Map> getList() {
        // 模拟从数据库获取返回的数据
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<Object, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("name", i);
            map.put("sex", "女");
            list.add(map);
        }
        return list;
    }

    /**
     * 使用Thymeleaf模板时测试
     *
     * @return
     */
    @RequestMapping("/unlogin")
    @ResponseBody
    public List<Map> unlogin() {
        log.info("hello,{}", "unlogin");
        return getList();
    }

    @RequestMapping("/unauth")
    @ResponseBody
    public int unauth() {
        log.info("hello,{}", "unauth");
        return 403;
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "Gaojian");
        log.info("hello,{}", "index");
        return "/index.html";
    }
}
