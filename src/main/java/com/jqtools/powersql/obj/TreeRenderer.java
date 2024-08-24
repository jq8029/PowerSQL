package com.jqtools.powersql.obj;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

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

		return this;
	}
}
