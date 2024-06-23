package com.jqtools.powersql.obj;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.jqtools.powersql.constants.Constants;

public class TreeNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5351735513734659428L;

	private String name = "";

	private int nodeType = Constants.NODE_TEXT;

	public void removeFromParent(JTree tree, String name) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		model.removeNodeFromParent(this);
		this.name = name;
	}

	public void add(JTree tree, TreeNode child) {
		child.addToParent(tree, this);
	}

	public void addToParent(JTree tree, TreeNode parent) {
		addToParent(tree, parent, parent.getChildCount());
	}

	public void addToParent(JTree tree, TreeNode parent, int index) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		model.insertNodeInto((DefaultMutableTreeNode) this, (DefaultMutableTreeNode) parent, index);
		model.nodeChanged(this);
	}

	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	// return the display tree node name
	public String toString() {
		return "";
	}
}
