package com.jqtools.powersql.obj;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.jqtools.powersql.constants.Constants;

public class TreeRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -7708912108715261741L;
	private JLabel labelNull = new JLabel();

	public TreeRenderer() {
		super();
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		if (tree.getPathForRow(row) == null || tree.getPathForRow(row).getPath() == null) {
			return this;
		}

		if (value != null && value instanceof TreeNode) {
			// cast to TreeNode
			TreeNode node = (TreeNode) value;

			if (node.getInfo().getNodeType() == Constants.NODE_CONNECTIONS) {

			} else if (node.getInfo().getNodeType() == Constants.NODE_CONNECTION) {

			} else if (node.getInfo().getNodeType() == Constants.NODE_CATALOG) {

			} else if (node.getInfo().getNodeType() == Constants.NODE_CATALOG) {

			} else if (node.getInfo().getNodeType() == Constants.NODE_SCHEMA) {

			} else if (node.getInfo().getNodeType() == Constants.NODE_TABLES) {

			} else if (node.getInfo().getNodeType() == Constants.NODE_TABLE) {

			} else if (node.getInfo().getNodeType() == Constants.NODE_VIEWS) {

			} else if (node.getInfo().getNodeType() == Constants.NODE_VIEW) {

			}
		}

		return labelNull;
	}
}