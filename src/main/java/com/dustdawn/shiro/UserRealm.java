package com.dustdawn.shiro;

import com.dustdawn.entity.User;
import com.dustdawn.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm类
 */
public class UserRealm extends AuthorizingRealm {
    /*
    执行授权逻辑
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");

        //给资源进行授权
        //SimpleAuthorizationInfo：AuthorizationInfo接口的实现类
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //1.添加资源的授权字符串
        //info.addStringPermission("user:add");

        //2.到数据库查询当前登录用户的授权字符串
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User dbUser = userService.findById(user.getId());
        info.addStringPermission(dbUser.getPerms());


        return info;
    }

    @Autowired
    private UserService userService;
    /*
    执行认证逻辑
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        User user = userService.findByName(token.getUsername());

        System.out.println(user);
        if(user == null){
            return null;  //shiro底层会抛出UnKnowAccountException
        }

        return new SimpleAuthenticationInfo(user,user.getPassword(),"");//三个参数：返回数据，密码,realmName
    }
}
