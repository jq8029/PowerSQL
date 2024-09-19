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

public class FilterSortFrame extends JFrame {

	private static final long serialVersionUID = 2086152060767330545L;

	private RSyntaxTextArea filterArea = new RSyntaxTextArea();
	private JComboBox<String> filterColBox = new JComboBox<String>();
	private JComboBox<String> condBox = new JComboBox<String>(Constants.CONDITIONS);
	private JComboBox<String> andOrBox = new JComboBox<String>(Constants.AND_OR);
	private JComboBox<String> ascDescBox = new JComboBox<String>(Constants.ORDERS);
	private JTextField filterField = new JTextField();

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
		;
		filterPanel.add(textScrollPanel);

		// add sort panel
		JPanel sortPanel = new JPanel(null);

		// add control button
		JButton applyButton = new JButton(Constants.BUTTON_APPLY);
		JButton resetButton = new JButton(Constants.BUTTON_RESET);
		JButton cancelButton = new JButton(Constants.BUTTON_CANCEL);

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
}
