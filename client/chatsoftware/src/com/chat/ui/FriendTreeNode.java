package com.chat.ui;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

public class FriendTreeNode implements TreeNode {

	private String ID;// 该节点的ID号
	private ImageIcon img;// 节点存放图片
	private String nickname;// 第一行文字（显示名字）
	private String signature;// 第二行文字（显示签名）

	private ArrayList<TreeNode> children;// 孩子节点
	private TreeNode parent;// 父亲节点

	public FriendTreeNode(String ID) {
		this.ID = ID;
	}

	public FriendTreeNode() {

	}

	public FriendTreeNode(String name, String text, ImageIcon img, String ID) {
		this.nickname = name;
		this.signature = text;
		this.img = img;
		this.ID = ID;
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @return the uName
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param uName
	 *            the uName to set
	 */
	public void setNickname(String uName) {
		this.nickname = uName;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(FriendTreeNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the img
	 */
	public ImageIcon getImg() {
		return img;
	}

	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(ImageIcon img) {
		this.img = img;
	}

	/**
	 * @return the text
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setSignature(String text) {
		this.signature = text;
	}

	// 添加孩子节点
	public void addChild(FriendTreeNode node) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		children.add(node);
		node.parent = this;
	}

	/***
	 * 判断是否为根节点
	 * 
	 * @return
	 */
	public boolean isroot() {

		return (getParent() == null);

	}

	// 实现TreNode的方法，获取到所有的孩子节点
	@Override
	public TreeNode getChildAt(int childIndex) {
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException("node has no children");
		}
		return children.get(childIndex);
	}

	// 获取到孩子节点的个数
	@Override
	public int getChildCount() {
		if (children == null) {
			return -1;
		}
		return children.size();
	}

	// 获取到父节点的个数
	@Override
	public TreeNode getParent() {

		return parent;
	}

	// 获取节点在数组孩子节点中的位置
	@Override
	public int getIndex(TreeNode aChild) {

		if (aChild == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(aChild)) {
			return -1;
		}
		return children.indexOf(aChild);
	}

	@Override
	public boolean getAllowsChildren() {

		return true;
	}

	/**
	 * 判断是否是叶子节点
	 */
	@Override
	public boolean isLeaf() {

		return (getChildCount() == -1) && (getParent() != null);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration children() {
		return null;
	}

	// 判断该节点是否当前节点的孩子节点
	public boolean isNodeChild(TreeNode aNode) {
		boolean retval;

		if (aNode == null) {
			retval = false;
		} else {
			if (getChildCount() == 0) {
				retval = false;
			} else {
				retval = (aNode.getParent() == this);
			}
		}

		return retval;
	}
}
