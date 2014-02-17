package com.common.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单的对象
 *@author hudaowan
 *@version   V1.0
 *@date  2008-4-10 下午03:31:20
 *@Team 数据交换平台研发小组
 */
public class MenuInfo {
	private String menuName;//菜单的名称
	private String url;//链接的地址
	private String openIcon;//打开的图片
	private String closeIcon;//关闭的图片
	private String target;//打开的显示目标
	private String name;//在页面checkbox  name的名称
	private String value;//在页面上 checkbox value的值
	private String checked;//在页面上表示checkbox是否选中
	private List<MenuInfo> childMenu = new ArrayList<MenuInfo>();//子菜单
	
	public List<MenuInfo> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(List<MenuInfo> childMenu) {
		this.childMenu = childMenu;
	}
	public String getCloseIcon() {
		return closeIcon;
	}
	public void setCloseIcon(String closeIcon) {
		this.closeIcon = closeIcon;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getOpenIcon() {
		return openIcon;
	}
	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
   
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
