package com.jqtools.powersql.obj;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5351735513734659428L;

	public void removeFromParent(JTree tree) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		model.removeNodeFromParent(this);
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
}
