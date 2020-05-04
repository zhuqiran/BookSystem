package com.java456.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.java456.dao.RoleMenuDao;
import com.java456.dao.UserDao;
import com.java456.entity.Menu;
import com.java456.entity.RoleMenu;
import com.java456.entity.User;

/**
 * 自定义Realm
 *
 */
public class MyRealm extends AuthorizingRealm{
	
	@Resource
	private UserDao  userDao;
	@Resource
	private RoleMenuDao  roleMenuDao;
	/**
	 *  #授权--权限url
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String name=(String) SecurityUtils.getSubject().getPrincipal();
		
		User user=userDao.findByName(name);
		//有了用户可以拿到，角色，  有角色，就有对应的菜单 list集合。  --- permissions
		
		//这个角色对应 的菜单 
		List<RoleMenu> roleMenuList= roleMenuDao.findByRole(user.getRole());
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		for(RoleMenu roleMenu:roleMenuList) {
			info.addStringPermission(roleMenu.getMenu().getPermissions());//添加权限   permissions
		}
		//这个角色对应 的菜单 
		
		//设置角色
		Set<String> roles=new HashSet<String>();
		roles.add(user.getRole().getName());
		info.setRoles(roles);
		//设置角色
		
		return info;
	}
	
	/**
	 * 权限认证--登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String name=(String)token.getPrincipal();//用户名  UsernamePasswordTokenr的第一个参数  name
		User user=userDao.findByName(name);
		if(user!=null){
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getName(),user.getPwd(),"xxx");
			return authcInfo;
		}else{
			return null;				
		}
	}
	
	

}
