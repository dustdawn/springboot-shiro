package com.dustdawn.controller;

import com.dustdawn.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    /**
     * 测试方法
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        System.out.println("UserController.hello()");
        return "ok";
    }

    @RequestMapping("/add")
    public String add(){
        return "/user/add";
    }

    @RequestMapping("/update")
    public String update(){
        return "/user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }
    /**
     * 测试thymeleaf
     */
    @RequestMapping("/testThymeleaf")
    public String testThymeleaf(Model model){
        //把数据存入model
        model.addAttribute("name", "程序员");
        //返回test.html
        return "test";
    }

    //注入业务
    @Autowired
    private UserService userService;
    /**
     * 登录逻辑处理
     */
    @RequestMapping("/login")
    public String login(String name,String password,Model model){
        /**
         * 使用Shiro编写认证操作
         */
        System.out.println(name+"---"+password);
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        //3.执行登录方法
        try {
            subject.login(token);
            return "redirect:/testThymeleaf";   //重定向到请求,跳转到得到认证的test页面
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("msg","用户名不存在");
            return "login";                     //转发到login页面
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            model.addAttribute("msg", "密码错误");
            return "login";
        }

    }

    @RequestMapping("noAuth")
    public String anAuth() {
        return "/noAuth";
    }
}
