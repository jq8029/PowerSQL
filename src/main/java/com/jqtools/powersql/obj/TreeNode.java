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
	private Session session = null;
	private boolean leaf = true;

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
		this.session = parent.session;
	}

	public Info getInfo() {
		return info;
	}

	@Override
	public boolean isLeaf() {
		if (this.leaf) {
			return super.isLeaf();
		} else {
			return this.leaf;
		}
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public Session getSession() {
		if (this.session == null) {
			if (this.parent != null) {
				return ((TreeNode) this.parent).getSession();
			} else {
				return null;
			}
		} else {
			return session;
		}
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getFullPath() {
		StringBuffer buffer = new StringBuffer();
		javax.swing.tree.TreeNode[] path = this.getPathToRoot(session.getCurrentNode(), 0);
		if (path != null) {
			for (javax.swing.tree.TreeNode node : path) {
				if (node == null)
					continue;

				if (buffer.length() > 0)
					buffer.append("->");

				buffer.append(node.toString());
			}
		}

		return buffer.toString();
	}

	// return the display tree node name
	public String toString() {
		return this.info == null ? "" : this.info.toString();
	}
}
