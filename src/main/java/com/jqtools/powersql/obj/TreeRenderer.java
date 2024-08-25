package com.jqtools.powersql.obj;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.jqtools.powersql.constants.Constants;

public class TreeRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -7708912108715261741L;

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
				setIcon(Constants.ICON_DATABASES);
			} else if (node.getInfo().getNodeType() == Constants.NODE_CONNECTION) {
				setIcon(Constants.ICON_DATABASE);
			} else if (node.getInfo().getNodeType() == Constants.NODE_CATALOG) {
				setIcon(Constants.ICON_CATALOG);
			} else if (node.getInfo().getNodeType() == Constants.NODE_SCHEMA) {
				setIcon(Constants.ICON_SCHEMA);
			} else if (node.getInfo().getNodeType() == Constants.NODE_TABLES) {
				setIcon(Constants.ICON_TABLES);
			} else if (node.getInfo().getNodeType() == Constants.NODE_TABLE) {
				setIcon(Constants.ICON_TABLE);
			} else if (node.getInfo().getNodeType() == Constants.NODE_VIEWS) {
				setIcon(Constants.ICON_VIEWS);
			} else if (node.getInfo().getNodeType() == Constants.NODE_VIEW) {
				setIcon(Constants.ICON_VIEW);
			}
		}

		return this;
	}
}
