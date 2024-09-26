package com.jqtools.powersql.obj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.utils.ExecuteSQL;
import com.jqtools.powersql.utils.Tools;

public class DataToolBar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = -7341179788939094727L;
	private JButton buttons[] = new JButton[Constants.DATA_TEXTS.length];
	private JTextField searchField = new JTextField();
	private JScrollPane dataScroll = new JScrollPane();
	private DataTable dataTable = null;
	private Session session = null;
	private String sql = null;
	private FilterSortFrame filterSortFrame = new FilterSortFrame();

	public DataToolBar(boolean isResult) {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Constants.DATA_TEXTS.length * 29 + 300, 29));

		int space = Constants.SPACE_03;
		int count = 0;

		for (int i = 0; i < Constants.DATA_TEXTS.length; i++) {
			if (i == Constants.DATA_TOOLBAR_SEARCH) {
				space += Constants.SPACE_02;
				searchField = new JTextField();
				searchField.setBounds(Constants.SPACE_24 * count + space, Constants.SPACE_02, Constants.SPACE_100,
						Constants.SPACE_21);
				searchField.setPreferredSize(new Dimension(Constants.SPACE_100, Constants.SPACE_21));
				searchField.addActionListener(this);
				add(searchField);
				space += Constants.SPACE_100;
				space += Constants.SPACE_10;
			}

			if (isResult) {
				buttons[i] = Tools.getImageButton(Constants.DATA_TEXTS[i], Constants.DATA_ENABLES[i]);
			} else {
				buttons[i] = Tools.getImageButton(Constants.DATA_TEXTS[i], true);
			}
			buttons[i].addActionListener(this);

			buttons[i].setBounds(Constants.SPACE_23 * count + space, Constants.SPACE_02, Constants.SPACE_21,
					Constants.SPACE_21);
			add(buttons[i]);

			count++;
		}

		ResultTableModel dataTableModel = new ResultTableModel();
		this.dataTable = new DataTable(dataTableModel);
		dataTableModel.setTable(this.dataTable);
		this.filterSortFrame.setDataToolBar(this);

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
				refresh();
			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_FILTER])) {
				filter();
			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_EXPORT])) {
				export();
			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_ADD])) {
				add();
			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_DELETE])) {
				delete();
			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_DUPLICATE])) {
				duplicate();
			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_SAVE])) {
				save();
			} else if (text.equals(Constants.DATA_TEXTS[Constants.DATA_TOOLBAR_SEARCH])) {
				search();
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

	public String getExecSQL() {
		String filterSQL = this.filterSortFrame.getFilterSQL();
		if (filterSQL != null && filterSQL.trim().length() == 0) {
			filterSQL = null;
		}
		String sortSQL = this.filterSortFrame.getSortSQL();
		if (sortSQL != null && sortSQL.trim().length() == 0) {
			sortSQL = null;
		}

		StringBuffer buffer = new StringBuffer();
		if (filterSQL == null && sortSQL == null) {
			buffer.append(sql);
		} else {
			if (filterSQL != null) {
				buffer.append(session.getDbData().getFilterSQL(sql, filterSQL));
			} else {
				buffer.append(sql);
			}

			if (sortSQL != null && sortSQL.trim().length() > 0) {
				buffer.append(" order by ").append(sortSQL);
			}
		}

		return buffer.toString();
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
		this.filterSortFrame.setFilterSQL(null);
		this.filterSortFrame.setSortSQL(null);
	}

	public void refresh() {
		if (this.session == null || this.sql == null) {
			return;
		}

		ExecuteSQL.execute(this);
	}

	public void filter() {
		this.filterSortFrame.setVisible(this.sql != null && this.sql.trim().length() > 0);
	}

	public void export() {

	}

	public void add() {

	}

	public void delete() {

	}

	public void duplicate() {

	}

	public void save() {

	}

	public void search() {

	}
}
