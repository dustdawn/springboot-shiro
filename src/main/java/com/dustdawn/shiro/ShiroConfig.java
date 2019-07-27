package com.dustdawn.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     * Subject： 用户主体（把操作交给SecurityManager）
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         * 常用的过滤器
         *  anon：无需认证(登录)可以访问
         *  authc：必须认证才可以访问
         *  user：如果使用rememberMe的功能可以只直接访问
         *  perms：该资源必须得到资源权限才可以访问
         *  role：该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        /*filterMap.put("/add","authc");
        filterMap.put("/update","authc");*/

        //放行，跳过认证
        filterMap.put("/testThymeleaf","anon");
        filterMap.put("/login","anon");

        //授权过滤器
        //授权拦截后，shiro会自动跳到未授权的页面
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");

        //需要认证
        filterMap.put("/*","authc");



        //修改未认证跳转的页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        //设置未授权跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 创建DefaultWebSecurityManager
     * 安全管理器（关联Realm）
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    /**
     * 创建Realm
     * Shiro连接数据的桥梁
     */
    @Bean(name="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
    /**
     * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
