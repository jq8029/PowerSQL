package com.jqtools.powersql.obj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPopupMenu;

import com.jqtools.powersql.constants.Constants;

public class TreePopMenu extends JPopupMenu implements ActionListener, ItemListener {

	private static final long serialVersionUID = -3018117749624583256L;

	private static final String MENUS[] = { Constants.MENU_CREATE, Constants.MENU_EDIT, Constants.MENU_DELETE };

	public TreePopMenu() {
		super("");

		init();
	}

	private void init() {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
