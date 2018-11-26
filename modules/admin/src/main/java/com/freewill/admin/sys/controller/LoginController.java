package com.freewill.admin.sys.controller;

import com.freewill.admin.common.utils.ShiroUtils;
import com.freewill.admin.sys.form.LoginForm;
import com.freewill.common.group.SelectGroup;
import com.freewill.common.web.annotation.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 用户登录接口
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-11-24 12:30
 */
@RestController
@RequestMapping("/")
@Log4j2
@ResponseResult
public class LoginController {
    //    @Autowired
//    LoginService loginService;
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

//        //生成文字验证码
//        String text = producer.createText();
//        //生成图片验证码
//        BufferedImage image = producer.createImage(text);
//        //保存到shiro session
//        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
//        ImageIO.write(image, "jpg", out);
    }

    /**
     * 登录操作
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, @Validated(SelectGroup.class) LoginForm form) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if(!form.getCaptcha().equalsIgnoreCase(kaptcha)){
//            throw  new BussinessException("验证码不正确");
//        }
        try {
            //当前Subject
            Subject currentUser = ShiroUtils.getSubject();

            //传递token给shiro的realm
            UsernamePasswordToken token = new UsernamePasswordToken(form.getUsername(), form.getPassword());
            currentUser.login(token);
        } catch (UnknownAccountException e) {
            throw new UnknownAccountException("账号不存在");
        } catch (IncorrectCredentialsException e) {
            throw new IncorrectCredentialsException("账号和密码不正确");
        } catch (LockedAccountException e) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            throw new AuthenticationException("账户验证失败");
        }
        return "登录成功";
    }


    /**
     * 注销操作
     */
    @RequestMapping("/logout")
    public String logout() {
        //注销
        ShiroUtils.logout();
        return "注销";
    }

}
