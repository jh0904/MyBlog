package com.blog.component;

import org.springframework.stereotype.Component;

/**
 * @author: i_jianghao
 * @Date: 2018/6/4 15:07
 * Describe: 手机验证码随机生成
 */
@Component
public class PhoneRandomBuilder {

    public static String randomBuilder(){

        StringBuilder result = new StringBuilder();
        for(int i=0;i<4;i++){
            result.append(Math.round(Math.random() * 9));
        }

        return result.toString();

    }

}
