package com.blog.controller;

import com.blog.model.User;
import com.blog.service.UserService;
import com.blog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: i_jianghao
 * @Date: 2018/6/8 9:24
 * Describe: 登录控制
 */
@Controller
public class LoginControl {

    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("phone") String phone,
                                 @RequestParam("authCode") String authCode,
                                 @RequestParam("newPassword") String newPassword,
                                 HttpServletRequest request){
        String trueMsgCode = (String) request.getSession().getAttribute("trueMsgCode");
        String msgCodePhone = (String) request.getSession().getAttribute("msgCodePhone");

        //判断验证码是否正确
        if(!authCode.equals(trueMsgCode)){
            return "0";
        }
        //判断获得的手机号是否是发送验证码的手机号
        if(!phone.equals(msgCodePhone)){
            return "3";
        }
        User user = userService.findUserByPhone(phone);
        if(user == null){
            return "2";
        }
        MD5Util md5Util = new MD5Util();
        String MD5Password = md5Util.encode(newPassword);
        userService.updatePasswordByPhone(phone, MD5Password);

        return "1";
    }

}
