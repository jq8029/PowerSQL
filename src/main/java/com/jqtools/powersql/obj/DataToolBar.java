package com.jqtools.powersql.obj;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;

import com.jqtools.powersql.log.MessageLogger;

public class DataToolBar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = -7341179788939094727L;

	public DataToolBar() {

		this.setForeground(Color.BLACK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = e.getActionCommand();

		if (text == null) {
			return;
		}

		try {
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}
	}
}
