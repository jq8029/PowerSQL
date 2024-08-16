package com.jqtools.powersql.obj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;

public class TreePopMenu extends JPopupMenu implements ActionListener {

	private static final long serialVersionUID = -3018117749624583256L;
	private static final String MENUS[] = { Constants.MENU_CREATE, Constants.MENU_EDIT, Constants.MENU_DELETE };

	private JTree tree;

	public TreePopMenu(JTree tree) {
		super("");
		this.tree = tree;

		// add all menu items
		JMenuItem item;
		for (String name : MENUS) {
			item = new JMenuItem(name);
			item.addActionListener(this);
			add(item);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = e.getActionCommand();
		TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

		if (text == null || node == null || node.getInfo().getNodeType() == Constants.NODE_CONNECTION) {
			return;
		}

		if (Constants.MENU_CREATE.equalsIgnoreCase(text)) {

		} else if (Constants.MENU_EDIT.equalsIgnoreCase(text)) {
			if (node == null) {
				return;
			}

			if (node.getSession() == null) {
				ConnectionFrame.getInstance().setDatabaseInfo(null);
			} else {
				ConnectionFrame.getInstance().setDatabaseInfo(node.getSession().getDbInfo());
			}
			ConnectionFrame.getInstance().setVisible(true);
		} else if (Constants.MENU_DELETE.equalsIgnoreCase(text)) {
			if (node == null) {
				return;
			}
		}
	}

	public void showPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			if ((TreeNode) tree.getLastSelectedPathComponent() != null) {
				try {
					show(e.getComponent(), e.getX(), e.getY());
				} catch (Exception ex) {
					MessageLogger.error(ex);
				} finally {
					System.gc();
				}
			}
		}
	}
}
