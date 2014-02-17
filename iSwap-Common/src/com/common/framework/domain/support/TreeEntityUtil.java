package com.common.framework.domain.support;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.common.framework.domain.TreeEntity;



/**
 *生成树的工具类
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap BI V1.0
 *@date  2010-10-1 下午06:07:06
 *@Team 研发中心
 */

public class TreeEntityUtil {
	/**
	 * 不允许将外键关系设置成环. 就是说，当前的current的子节点中，如果包含parent，就不能把parent设置为current的上级节
	 * 
	 * @param current
	 *            当前节点
	 * @param parentNode
	 *            父节点
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static <E extends TreeEntity> boolean isDeadLock(
			TreeEntity<E> current, TreeEntity<E> parentNode) {

		if ((current == null) || (parentNode == null)
				|| current.equals(parentNode)) {
			return true;
		} else {
			for (TreeEntity<E> child : current.getChildren()) {
				boolean isDeadLock = isDeadLock(child, parentNode);
				if (isDeadLock) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 得到TreeEntity自己和所有的孩子列表
	 * 
	 * @param <E>
	 * @param me
	 *            TreeEntity
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static <E extends TreeEntity> Set<E> getMeAndListAllChildren(E me) {
		Set<E> lst = new HashSet<E>();
		lst.add(me);
		Set<E> children = me.getChildren();
		for (E ac : children) {
			lst.addAll(getMeAndListAllChildren(ac));
		}
		return lst;
	}

	/**
	 * 得到TreeEntity自己所有的孩子（包括树干和叶子节点)
	 * 
	 * @param <E>
	 * @param me
	 *            TreeEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E extends TreeEntity> Set<E> getAllChildren(E me) {
		Set<E> list = new HashSet<E>();
		Stack<E> stack = new Stack<E>();
		stack.push(me);
		while (!stack.empty()) {
			E bt = stack.pop();
			list.add(bt);
			Set<E> children = bt.getChildren();
			for (E ac : children) {
				stack.push(ac);
			}
		}
		list.remove(me);
		return list;
	}

	/**
	 * 得到TreeEntity自己所有的叶子节点
	 * 
	 * @param <E>
	 * @param me
	 *            TreeEntity
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static <E extends TreeEntity> Set<E> getAllLeaves(E me) {
		Set<E> for_filter = getAllChildren(me);
		Set<E> list = new HashSet<E>();
		list.addAll(for_filter);
		for (E ac : for_filter) {
			if (!ac.isLeaf())
				list.remove(ac);
		}
		return list;
	}

	/**
	 * 本节点是否为某节点的父节点
	 * 
	 * @param <E>
	 * @param parent
	 *            父节点
	 * @param son
	 *            子节点
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static <E extends TreeEntity> boolean isParentOf(
			TreeEntity<E> parent, TreeEntity<E> son) {
		if (son == null || son.equals(parent)) {
			/* 如果对方为空 */
			return false;
		} else if (parent.isLeaf()) {
			/* 如果自己为叶子,则返回FALSE */
			return false;
		} else if (son.isRoot()) {
			/* 如果对方为根,返回FALSE */
			return false;
		} else {
			TreeEntity<E> bt = son.getParent();
			if (parent.equals(bt)) {
				/* 如果对方的父节点是自己,则返回TRUE */
				return true;
			} else {
				/* 判断对方的父节点是否是自己的孩子,进行递归 */
				return isParentOf(parent, bt);
			}
		}
	}
}
