package com.jqtools.powersql.obj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.gui.ColumnChangeFrame;
import com.jqtools.powersql.gui.ConnectionFrame;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.log.NoticeMessage;
import com.jqtools.powersql.utils.DBTools;

public class TreePopMenu extends JPopupMenu implements ActionListener {

	private static final long serialVersionUID = -3018117749624583256L;
	private static final String MENUS[] = { Constants.MENU_CREATE, Constants.MENU_EDIT, Constants.MENU_DELETE,
			Constants.MENU_DUPLICATE };

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

		if (text == null || node == null) {
			return;
		}

		if (Constants.MENU_CREATE.equalsIgnoreCase(text)) {
			if (node.getInfo().getNodeType() == Constants.NODE_CONNECTIONS) {
				ConnectionFrame.getInstance().setDatabaseInfo(null, node);
				ConnectionFrame.getInstance().setVisible(true);
			} else if (node.getInfo().getNodeType() == Constants.NODE_TABLE_COLUMNS
					|| node.getInfo().getNodeType() == Constants.NODE_COLUMN) {
				ColumnInfo info = new ColumnInfo(node.getInfo());
				info.setNodeType(Constants.NODE_COLUMN);
				ColumnChangeFrame.getInstance().setData(node.getSession(), info);
				ColumnChangeFrame.getInstance().setVisible(true);
			}
		} else if (Constants.MENU_EDIT.equalsIgnoreCase(text)) {
			if (node.getInfo().getNodeType() == Constants.NODE_CONNECTION) {
				if (node.getSession() == null) {
					ConnectionFrame.getInstance().setDatabaseInfo(null, node);
				} else {
					ConnectionFrame.getInstance().setDatabaseInfo(node.getSession().getDbInfo(), node);
				}
				ConnectionFrame.getInstance().setVisible(true);
			} else if (node.getInfo().getNodeType() == Constants.NODE_COLUMN) {
				ColumnChangeFrame.getInstance().setData(node.getSession(), (ColumnInfo) node.getInfo());
				ColumnChangeFrame.getInstance().setVisible(true);
			}
		} else if (Constants.MENU_DELETE.equalsIgnoreCase(text)) {
			if (node.getInfo().getNodeType() == Constants.NODE_CONNECTION) {
				if (NoticeMessage.showConfirm(Constants.MSG_DELETE_CONN) == JOptionPane.YES_OPTION) {
					DBTools.deleteDBConnection(node.getInfo().getName());
					node.removeFromParent();
				}
			} else if (node.getInfo().getNodeType() == Constants.NODE_COLUMN) {
				ColumnChangeFrame.getInstance().setData(node.getSession(), (ColumnInfo) node.getInfo());
				ColumnChangeFrame.getInstance().setVisible(true);
			}
		} else if (Constants.MENU_DUPLICATE.equalsIgnoreCase(text)) {
			if (node.getInfo().getNodeType() == Constants.NODE_CONNECTION) {
				DatabaseInfo dbInfo = node.getSession().getDbInfo().clone();
				dbInfo.setName(dbInfo.getName() + " - dup");
				DBTools.updateDBConnection(dbInfo.getName(), dbInfo);
				TreeNode newNode = new TreeNode(dbInfo.getName(), Constants.NODE_CONNECTION);
				newNode.addToParent((com.jqtools.powersql.obj.TreeNode) node.getParent());
				newNode.setSession(new Session());
				newNode.getSession().setDbInfo(dbInfo);
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
