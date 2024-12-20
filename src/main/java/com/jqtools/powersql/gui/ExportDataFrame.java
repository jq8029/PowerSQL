package com.jqtools.powersql.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jqtools.powersql.constants.Constants;

public class ExportDataFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7199346870193411461L;
	private JLabel fileLabel = new JLabel(Constants.LABEL_EXPORT_TO_FILE + " : ");

	public ExportDataFrame() {
		initialize();
	}

	public void initialize() {
		this.setTitle(Constants.TITLE_EXPORT_DATA);

		JPanel dataPanel = new JPanel(null);

		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setPreferredSize(new Dimension(350, 35));

		JButton exportButton = new JButton(Constants.BUTTON_EXPORT);
		exportButton.setBounds(100, 10, 80, 18);
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				export();
			}
		});
		buttonPanel.add(exportButton);
		JButton cancelButton = new JButton(Constants.BUTTON_CLOSE);
		cancelButton.setBounds(200, 10, 80, 18);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		buttonPanel.add(cancelButton);

		this.getContentPane().add(Constants.PANEL_CENTER, dataPanel);
		this.getContentPane().add(Constants.PANEL_SOUTH, buttonPanel);

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
