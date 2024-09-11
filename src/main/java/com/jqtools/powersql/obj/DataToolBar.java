package com.jqtools.powersql.obj;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.utils.Tools;

public class DataToolBar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = -7341179788939094727L;
	private JButton buttons[] = new JButton[Constants.DATA_TEXTS.length];
	private DataTable dataTable = null;

	public DataToolBar(boolean isResult) {
		int space = Constants.SPACE_03;
		int count = 0;

		for (int i = 0; i < Constants.DATA_TEXTS.length; i++) {
			if (isResult) {
				buttons[i] = Tools.getImageButton(Constants.DATA_TEXTS[i], Constants.DATA_ENABLES[i]);
			} else {
				buttons[i] = Tools.getImageButton(Constants.DATA_TEXTS[i], true);
			}
			buttons[i].addActionListener(this);

			buttons[i].setBounds(Constants.SPACE_23 * count + space, Constants.SPACE_02, Constants.SPACE_21,
					Constants.SPACE_21);
			add(buttons[i]);
		}

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

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

}
