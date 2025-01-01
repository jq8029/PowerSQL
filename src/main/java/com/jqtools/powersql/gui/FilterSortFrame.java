package com.jqtools.powersql.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.NoticeMessage;
import com.jqtools.powersql.obj.DataToolBar;
import com.jqtools.powersql.utils.Tools;

public class FilterSortFrame extends JFrame {

	private static final long serialVersionUID = 2086152060767330545L;

	private RSyntaxTextArea filterArea = new RSyntaxTextArea();
	private JComboBox<String> filterColBox = new JComboBox<String>();
	private JComboBox<String> condBox = new JComboBox<String>(Constants.CONDITIONS);
	private JComboBox<String> andOrBox = new JComboBox<String>(Constants.AND_OR);
	private RSyntaxTextArea sortArea = new RSyntaxTextArea();
	private JComboBox<String> sortColBox = new JComboBox<String>();
	private JComboBox<String> ascDescBox = new JComboBox<String>(Constants.ORDERS);
	private JTextField filterField = new JTextField();
	private DataToolBar dataToolBar = null;
	private int colTypes[] = null;
	private String colNames[] = null;

	public FilterSortFrame() {
		super();

		this.setTitle(Constants.TITLE_FILTER_SORT);
		this.getContentPane().setLayout(null);

		int height = 16;
		int x1 = 15;
		int y1 = 20;

		// add filter panel
		JPanel filterPanel = new JPanel(null);
		filterPanel.setBounds(5, 5, 600, 140);
		filterPanel.setBorder(BorderFactory.createTitledBorder(" " + Constants.TITLE_FILTER + " "));

		filterColBox.setBounds(x1, y1, 160, height);
		filterPanel.add(filterColBox);
		condBox.setBounds(180, y1, 110, height);
		filterPanel.add(condBox);
		filterField.setBounds(295, y1, 170, height);
		filterPanel.add(filterField);
		andOrBox.setBounds(470, y1, 50, height);
		filterPanel.add(andOrBox);
		JButton filterAddButton = new JButton(Constants.BUTTON_ADD);
		filterAddButton.setBounds(525, y1, 60, height);
		filterAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFilter();
			}
		});
		filterPanel.add(filterAddButton);
		RTextScrollPane filterScrollPanel = new RTextScrollPane(filterArea);
		filterScrollPanel.setLineNumbersEnabled(true);
		filterScrollPanel.setBounds(x1, 45, 570, 80);
		filterPanel.add(filterScrollPanel);

		// add sort panel
		JPanel sortPanel = new JPanel(null);
		sortPanel.setBounds(5, 155, 600, 140);
		sortPanel.setBorder(BorderFactory.createTitledBorder(" " + Constants.TITLE_SORT + " "));

		sortColBox.setBounds(x1, y1, 160, height);
		sortPanel.add(sortColBox);
		ascDescBox.setBounds(180, y1, 80, height);
		sortPanel.add(ascDescBox);
		JButton sortAddButton = new JButton(Constants.BUTTON_ADD);
		sortAddButton.setBounds(525, y1, 60, height);
		sortAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSort();
			}
		});
		sortPanel.add(sortAddButton);
		RTextScrollPane sortScrollPanel = new RTextScrollPane(sortArea);
		sortScrollPanel.setLineNumbersEnabled(true);
		sortScrollPanel.setBounds(x1, 45, 570, 80);
		sortPanel.add(sortScrollPanel);

		// add control button
		JButton applyButton = new JButton(Constants.BUTTON_APPLY);
		applyButton.setBounds(200, 310, 70, height);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apply();
			}
		});
		JButton resetButton = new JButton(Constants.BUTTON_RESET);
		resetButton.setBounds(275, 310, 70, height);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		JButton cancelButton = new JButton(Constants.BUTTON_CANCEL);
		cancelButton.setBounds(350, 310, 80, height);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});

		this.getContentPane().add(filterPanel);
		this.getContentPane().add(sortPanel);
		this.getContentPane().add(applyButton);
		this.getContentPane().add(resetButton);
		this.getContentPane().add(cancelButton);

		this.setPreferredSize(new Dimension(625, 375));
		this.pack();
		setLocationRelativeTo(this);
	}

	public void addFilter() {
		String value = this.filterField.getText().trim();
		String name = Tools.getSelectedItem(this.filterColBox);
		String cond = Tools.getSelectedItem(this.condBox);
		String andOr = Tools.getSelectedItem(this.andOrBox);

		if (name == null || name.length() == 0) {
			NoticeMessage.showMessage(Constants.MSG_SELECT_COL);
		} else {
			if (colNames != null && colTypes != null) {
				if ((Constants.CONDITION_BETWEEN.equals(cond) || Constants.CONDITION_NOT_BETWEEN.equals(cond))
						&& value.indexOf(",") < 0) {
					NoticeMessage.showMessage(Constants.MSG_ENTER_TWO);
					return;
				}

				for (int i = 0; i < colNames.length; i++) {
					if (Tools.isEqual(name, colNames[i])) {
						StringBuffer buffer = new StringBuffer();

						buffer.append(this.filterArea.getText());
						if (buffer.length() > 0) {
							buffer.append(" ").append(andOr).append(" ");
						}

						if (!Constants.CONDITION_IS_NOT_NULL.equals(andOr)
								&& !Constants.CONDITION_IS_NULL.equals(andOr)) {
							buffer.append(name).append(" ").append(cond).append(" ");
							if (Tools.isQuote(colTypes[i])) {
								buffer.append("'");
							}

							buffer.append(value);

							if (Tools.isQuote(colTypes[i])) {
								buffer.append("'");
							}
						}

						setFilterSQL(buffer.toString());
						updateCache();

						break;
					}
				}
			}
		}
	}

	public void addSort() {
		String name = Tools.getSelectedItem(this.sortColBox);
		String sort = Tools.getSelectedItem(this.ascDescBox);
		String text = null;

		if (name == null || name.length() == 0) {
			NoticeMessage.showMessage(Constants.MSG_SELECT_COL);
		} else {
			if (colNames != null) {
				for (int i = 0; i < colNames.length; i++) {
					if (Tools.isEqual(name, colNames[i])) {
						StringBuffer buffer = new StringBuffer();
						text = this.sortArea.getText();

						if (text.trim().endsWith(",")) {
							text = text.substring(0, text.lastIndexOf(","));
						}

						buffer.append(text);
						if (buffer.length() > 0) {
							buffer.append(", ");
						}

						buffer.append(name).append(" ").append(sort);

						setSortSQL(buffer.toString());
						updateCache();

						break;
					}
				}
			}
		}
	}

	public void apply() {
		if (this.dataToolBar != null) {
			this.dataToolBar.refresh();
			if (Tools.hasValue(this.filterArea.getText()) || Tools.hasValue(this.sortArea.getText())) {
				this.dataToolBar.setButtonBackgroud(Constants.DATA_TOOLBAR_FILTER, Color.GREEN);
			} else {
				this.dataToolBar.setButtonBackgroud(Constants.DATA_TOOLBAR_FILTER, Constants.DEFAULT_BACKGROUND);
			}
		}

		this.setVisible(false);
	}

	public void reset() {
		this.filterArea.setText("");
		this.sortArea.setText("");
		this.condBox.setSelectedIndex(0);
		this.filterField.setText("");
		if (this.sortColBox.getItemCount() > 0) {
			this.sortColBox.setSelectedIndex(0);
		}
	}

	public void cancel() {
		super.setVisible(false);
	}

	public void setDataToolBar(DataToolBar dataToolBar) {
		this.dataToolBar = dataToolBar;
	}

	public void setColData(String[] colNames, int[] colTypes) {
		this.colNames = colNames;
		this.colTypes = colTypes;
	}

	public String getFilterSQL() {
		return this.filterArea.getText();
	}

	public void setFilterSQL(String filterSQL) {
		if (Tools.hasValue(filterSQL)) {
			this.filterArea.setText(filterSQL);
		} else {
			this.filterArea.setText("");
		}
	}

	public String getSortSQL() {
		return this.sortArea.getText();
	}

	public void updateCache() {
		if (this.dataToolBar.getSession().getCurrentNode() != null) {
			String path = this.dataToolBar.getSession().getCurrentNode().getFullPath();
			Tools.saveToCache(path + Constants.CACHE_FILTER, this.filterArea.getText());
			Tools.saveToCache(path + Constants.CACHE_SORT, this.sortArea.getText());
		}
	}

	public void setSortSQL(String sortSQL) {
		if (Tools.hasValue(sortSQL)) {
			this.sortArea.setText(sortSQL);
		} else {
			this.sortArea.setText("");
		}
	}

	public void setVisible(boolean b) {
		if (b) {
			filterColBox.removeAllItems();
			sortColBox.removeAllItems();

			if (this.colNames != null) {
				for (String s : colNames) {
					filterColBox.addItem(s);
					sortColBox.addItem(s);
				}
			}
		}

		super.setVisible(b);
	}
}
