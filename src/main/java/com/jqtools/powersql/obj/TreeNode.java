package com.jqtools.powersql.obj;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5351735513734659428L;

	private Info info = null;
	private JTree tree = null;

	public TreeNode(Info info) {
		this.info = info;
	}

	public TreeNode(String name) {
		this.info = new Info();
		this.info.setName(name);
	}

	public TreeNode(String name, int nodeType) {
		this.info = new Info();
		this.info.setName(name);
		this.info.setNodeType(nodeType);
	}

	public void removeFromParent() {
		DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
		model.removeNodeFromParent(this);
	}

	public void add(TreeNode child) {
		child.addToParent(this);
	}

	public void addToParent(TreeNode parent) {
		addToParent(parent, parent.getChildCount());
	}

	public void addToParent(TreeNode parent, int index) {
		this.tree = parent.getTree();
		DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
		model.insertNodeInto((DefaultMutableTreeNode) this, (DefaultMutableTreeNode) parent, index);
		model.nodeChanged(this);
	}

	public Info getInfo() {
		return info;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	// return the display tree node name
	public String toString() {
		return this.info == null ? "" : this.toString();
	}
}
