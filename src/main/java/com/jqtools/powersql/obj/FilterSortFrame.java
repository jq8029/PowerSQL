package com.jqtools.powersql.obj;

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
		filterField.setBounds(295, y1, 180, height);
		filterPanel.add(filterField);
		andOrBox.setBounds(480, y1, 50, height);
		filterPanel.add(andOrBox);
		JButton filterAddButton = new JButton(Constants.BUTTON_ADD);
		filterAddButton.setBounds(535, y1, 50, height);
		filterAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFilter();
			}
		});
		filterPanel.add(filterAddButton);
		RTextScrollPane textScrollPanel = new RTextScrollPane(filterArea);
		textScrollPanel.setLineNumbersEnabled(true);
		textScrollPanel.setBounds(x1, 45, 570, 80);
		filterPanel.add(textScrollPanel);

		// add sort panel
		JPanel sortPanel = new JPanel(null);
		sortPanel.setBounds(5, 155, 600, 140);
		sortPanel.setBorder(BorderFactory.createTitledBorder(" " + Constants.TITLE_SORT + " "));

		sortColBox.setBounds(x1, y1, 160, height);
		sortPanel.add(sortColBox);
		ascDescBox.setBounds(180, y1, 80, height);
		sortPanel.add(ascDescBox);
		JButton sortAddButton = new JButton(Constants.BUTTON_ADD);
		sortAddButton.setBounds(535, y1, 50, height);
		sortAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSort();
			}
		});
		sortPanel.add(sortAddButton);
		textScrollPanel = new RTextScrollPane(filterArea);
		textScrollPanel.setLineNumbersEnabled(true);
		textScrollPanel.setBounds(x1, 45, 570, 80);
		sortPanel.add(textScrollPanel);

		// add control button
		JButton applyButton = new JButton(Constants.BUTTON_APPLY);
		applyButton.setBounds(220, 310, 60, height);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apply();
			}
		});
		JButton resetButton = new JButton(Constants.BUTTON_RESET);
		resetButton.setBounds(285, 310, 60, height);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		JButton cancelButton = new JButton(Constants.BUTTON_CANCEL);
		cancelButton.setBounds(350, 310, 60, height);
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

		this.pack();
		setLocationRelativeTo(this);
	}

	public void addFilter() {

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

						this.sortArea.setText(buffer.toString());

						break;
					}
				}
			}
		}
	}

	public void apply() {
		if (this.dataToolBar != null) {
			String filterSQL = this.filterArea.getText();
			String sortSQL = this.sortArea.getText();

			this.dataToolBar.setFilterSort(filterSQL, sortSQL);
			this.dataToolBar.refresh();
		}

		this.setVisible(false);
	}

	public void reset() {
		this.filterArea.setText("");
		this.sortArea.setText("");
	}

	public void cancel() {
		super.setVisible(false);
	}

	public void setDataToolBar(DataToolBar dataToolBar, String[] colNames, int[] colTypes) {
		this.dataToolBar = dataToolBar;
		this.colNames = colNames;
		this.colTypes = colTypes;
	}
}
