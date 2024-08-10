package com.jqtools.powersql.obj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.jqtools.powersql.constants.Constants;

public class TreePopMenu extends JPopupMenu implements ActionListener {

	private static final long serialVersionUID = -3018117749624583256L;

	private static final String MENUS[] = { Constants.MENU_CREATE, Constants.MENU_EDIT, Constants.MENU_DELETE };

	public TreePopMenu() {
		super("");

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
		if (text == null) {
			return;
		}

	}

}
