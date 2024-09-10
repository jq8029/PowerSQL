package com.jqtools.powersql.obj;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;

public class DataToolBar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = -7341179788939094727L;
	private JButton buttons[] = new JButton[Constants.DATA_TEXTS.length];

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

	public boolean isButtonEnabled(int buttonId) {
		if (buttonId < 0 || buttonId >= buttons.length || buttons[buttonId] == null)
			return false;

		return buttons[buttonId].isEnabled();
	}

	public void setButtonEnabled(int buttonId, boolean enable) {
		if (buttonId < 0 || buttonId >= buttons.length || buttons[buttonId] == null)
			return;

		buttons[buttonId].setEnabled(enable);
	}
}
