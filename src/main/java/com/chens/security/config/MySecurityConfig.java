package com.chens.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MySecurityConfig  extends WebSecurityConfigurerAdapter {
   //
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //super.configure(http);
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        //开启自动配置的登录功能 ，演示效果如果没有权限就会来到登录页面
        http.formLogin();
        //1./login 来到登录页
        //2.重定向到/login?error标识登录失败
        //3.更多详细的规定
        //生成了springSecurity的自动登录页面

        //开启自动配置的注销功能
       // http.logout();
        //1.访问/logout 表示用户注销，并清空session
        //2. 注销成功会返回 /login?logout 页面就是登录页面，可以自定义如下
        http.logout().logoutSuccessUrl("/");  //注销成功来首页

        //开启记住我功能
        http.rememberMe();
        //登录成功以后，将cookie发给浏览器，以后访问页面带上这个cookie，只要通过检查就可以免登录
        //点击注销可以删除这个cookie


    }
    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // super.configure(auth);
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).
                withUser("zhangsan").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1","VIP2")  //保存在内存中应该连接数据库
        .and().withUser("lisi").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP3","VIP2")
        .and().withUser("wangwu").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1","VIP3");

    }
}
