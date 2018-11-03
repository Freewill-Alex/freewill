package com.freewill.console.common.controller;

import com.freewill.common.web.annotation.ResponseResult;
import com.freewill.console.common.exception.BussinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("/index")
    @ResponseBody
    public List<Map> getName() {
        log.info("hello,{}", "Gaojian");
        return getList();
    }

    @GetMapping("/index4")
    @ResponseBody
    public int getName4() throws BussinessException {
        log.info("hello,{}", "Gaojian");
        int a;
        try {

            a = 1 / 0;
        } catch (Exception e) {
            throw new BussinessException(e.getLocalizedMessage());
        }
        return a;
    }

    @GetMapping("/index3")
    @ResponseBody
    public int getName3() {
        log.info("hello,{}", "Gaojian");
        return 1 / 0;
    }

    @PostMapping("/index2")
    public String name2(Model model, String name) {
        model.addAttribute("name", "Gaojian");
        log.info("hello,{}", "Gaojian");
        return "/index.html";
    }
}
