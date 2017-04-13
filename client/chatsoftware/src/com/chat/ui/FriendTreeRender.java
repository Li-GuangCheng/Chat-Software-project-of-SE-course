package com.chat.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

public class FriendTreeRender extends JLabel implements TreeCellRenderer {

	ImageIcon arrowRight = new ImageIcon(this.getClass().getResource("/Icons16/right.png"));
	ImageIcon arrowDown = new ImageIcon(this.getClass().getResource("/Icons16/down.png"));

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		FriendTreeNode f = (FriendTreeNode) value;
		if (leaf && f.getParent() != tree.getModel().getRoot()) {
			/******* 设置JLable的文字 ******/
			String text = "<html>" + f.getNickname() + "<br/><font color='red'>" + f.getSignature() + "</font> <html/>";
			setText(text);// 设置JLable的文字
			/******* 设置JLable的图片 *****/
			// 得到此图标的 Image,然后创建此图像的缩放版本。
			Image img = f.getImg().getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
			setIcon(new ImageIcon(img));// 设置JLable的图片
			setIconTextGap(10);// 设置JLable的图片与文字之间的距离
//			setPreferredSize(new Dimension(150, 0));

			if (selected)
				this.setBackground(new Color(128, 255, 255));
		} else { // 非叶子节点的文字为节点的ID
			setText(f.getID());// 设置JLable的文字
			if (expanded)
				setIcon(arrowDown);
			else
				setIcon(arrowRight);// 设置JLable的图片
		}

		return this;
	}

}