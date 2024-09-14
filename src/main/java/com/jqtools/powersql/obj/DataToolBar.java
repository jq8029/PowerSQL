package com.jqtools.powersql.obj;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.utils.Tools;

public class DataToolBar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = -7341179788939094727L;
	private JButton buttons[] = new JButton[Constants.DATA_TEXTS.length];
	private JScrollPane dataScroll = new JScrollPane();
	private DataTable dataTable = null;
	private Session session = null;
	private String sql = null;

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

		ResultTableModel dataTableModel = new ResultTableModel();
		this.dataTable = new DataTable(dataTableModel);
		dataTableModel.setTable(this.dataTable);

		this.setForeground(Color.BLACK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = e.getActionCommand();

		if (text == null) {
			return;
		}

		try {
			if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_REFRESH])) {

			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_FILTER])) {

			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_EXPORT])) {

			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_ADD])) {

			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_DELETE])) {

			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_DUPLICATE])) {

			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_SAVE])) {

			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_SEARCH])) {

			}
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

	public JScrollPane getDataScroll() {
		return dataScroll;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void refresh() {
		if (this.session == null || this.sql == null) {
			return;
		}

	}
}
