package com.jqtools.powersql.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jqtools.powersql.constants.Constants;

public class ExportDataFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7199346870193411461L;

	public ExportDataFrame() {
		initialize();
	}

	public void initialize() {
		this.setTitle(Constants.TITLE_EXPORT_DATA);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setPreferredSize(new Dimension(350, 35));

		JButton exportButton = new JButton(Constants.BUTTON_EXPORT);
		exportButton.setBounds(100, 10, 80, 18);
		buttonPanel.add(exportButton);
		JButton cancelButton = new JButton(Constants.BUTTON_CLOSE);
		cancelButton.setBounds(200, 10, 80, 18);
		buttonPanel.add(cancelButton);

		this.getContentPane().add("South", buttonPanel);

		this.setPreferredSize(new Dimension(420, 200));
		this.pack();
		setLocationRelativeTo(this);
	}

	public void export() {
	}

	public void cancel() {
		this.setVisible(false);
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
	}

	public static void main(String[] args) {
		new ExportDataFrame().setVisible(true);
	}
}
