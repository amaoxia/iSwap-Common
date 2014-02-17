package com.common.utils.tree;

public class CreateTree {
	
	/**
	 * 生成带有checkbox的树
	 *@author hudaowan
	 *@date  2008-4-12 下午04:34:35
	 *@param menu
	 *@return
	 */
	public String getCheckBoxTree(MenuInfo menu){
		StringBuffer treebuff = new StringBuffer();
		treebuff.append("<script language=\"javascript\">");
		treebuff.append(" var tree = new WebFXTree('"+menu.getMenuName()+"');");
		int i = 1;
		for(MenuInfo childMenu:menu.getChildMenu()){
			String varName = "childMenu_"+i;
			String idName = "t_"+i;
			this.createCheckBoxChildTree(childMenu, treebuff, varName,idName,"tree");
			i++;
		}  
		treebuff.append("document.write(tree);");
		treebuff.append("</script>");
		return treebuff.toString();
	}
	
	private void createCheckBoxChildTree(MenuInfo menu,StringBuffer treebuff,String varName,String idName,String pnode){
		String checkedstr = menu.getChecked()==null?"":menu.getChecked();
		treebuff.append(" var "+varName+" = new WebFXCheckBoxTreeItem('"+idName+"','"+menu.getName()+"','"+menu.getMenuName()+"','"+menu.getValue()+"','"+menu.getUrl()+"', '"+checkedstr+"',"+pnode+", '"+menu.getOpenIcon()+"', '"+menu.getCloseIcon()+"');");
		int i = 1;
		String pVarName = varName;
		for(MenuInfo childMenu:menu.getChildMenu()){
			String cvarName = varName+"_"+i;
			String cidName = idName+"_"+i;
			this.createCheckBoxChildTree(childMenu, treebuff, cvarName,cidName,pVarName);
			i++;
		}
	}
	/**
	 * 生成radio的tree
	 *@author hudaowan
	 *@date  2008-5-13 下午01:56:15
	 *@param menu
	 *@return
	 */
	public String getRadioTree(MenuInfo menu){
		StringBuffer treebuff = new StringBuffer();
		treebuff.append("<script language=\"javascript\">");
		treebuff.append(" var tree = new WebFXTree('"+menu.getMenuName()+"');");
		int i = 1;
		for(MenuInfo childMenu:menu.getChildMenu()){
			String varName = "childMenu_"+i;
			this.createChildRadioTree(childMenu, treebuff, varName,"tree");
			i++;
		}  
		treebuff.append("document.write(tree);");
		treebuff.append("</script>");
		return treebuff.toString();
	}
	
	private void createChildRadioTree(MenuInfo menu,StringBuffer treebuff,String varName,String pnode){
		String checkedstr = menu.getChecked()==null?"":menu.getChecked();
		
		treebuff.append(" var "+varName+" = new WebFXRadioTreeItem('"+menu.getName()+"','"+menu.getMenuName()+"','"+menu.getValue()+"','#', '"+checkedstr+"',"+pnode+", '"+menu.getOpenIcon()+"', '"+menu.getCloseIcon()+"');");
		int i = 1;
		String pVarName = varName;
		for(MenuInfo childMenu:menu.getChildMenu()){
			String cvarName = varName+"_"+i;
			this.createChildRadioTree(childMenu, treebuff, cvarName,pVarName);
			i++;
		}
	}
	
	/**
	 * 生成树
	 *@author hudaowan
	 *@date  2008-4-10 下午03:32:00
	 *@param menu
	 *@return
	 */
	public String getTree(MenuInfo menu){
		StringBuffer treebuff = new StringBuffer();
		treebuff.append("<script language=\"javascript\">");
		treebuff.append(" var tree = new WebFXTree('"+menu.getMenuName()+"');");
		int i = 0;
		for(MenuInfo childMenu:menu.getChildMenu()){
			String varName = "childMenu_"+i;
			this.createChildTree(childMenu, treebuff, varName);
			i++;
			treebuff.append(" tree.add("+varName+"); ");
		}
		treebuff.append("document.write(tree);");
		treebuff.append("</script>");
		return treebuff.toString();
	}
	
	private void createChildTree(MenuInfo menu,StringBuffer treebuff,String varName){
		treebuff.append(" var "+varName+" = new WebFXTreeItem('"+menu.getMenuName()+"','"+menu.getUrl()+"', '', '"+menu.getOpenIcon()+"', '"+menu.getCloseIcon()+"');");
		treebuff.append(" "+varName+".target='"+menu.getTarget()+"';");
		int i = 0;
		String pVarName = varName;
		for(MenuInfo childMenu:menu.getChildMenu()){
			varName = varName+"_"+i;
			this.createChildTree(childMenu, treebuff, varName);
			treebuff.append(" "+pVarName+".add("+varName+"); ");
		}
	}
}
