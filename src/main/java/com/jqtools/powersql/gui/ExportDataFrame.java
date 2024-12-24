package com.jqtools.powersql.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.utils.Tools;

public class ExportDataFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7199346870193411461L;
	private JComboBox fileTypeBox = new JComboBox(Constants.FILE_TYPES[0]);
	private JTextField fileField = new JTextField();;
	private JButton openButton = new JButton(Constants.BUTTON_OPEN);
	private Session session;
	private String sql;
	private static ExportDataFrame instance = null;

	public static ExportDataFrame getInstance() {
		if (instance == null) {
			instance = new ExportDataFrame();
		}

		return instance;
	}

	public ExportDataFrame() {
		initialize();
	}

	public void initialize() {
		this.setTitle(Constants.TITLE_EXPORT_DATA);

		JPanel dataPanel = new JPanel(null);
		JLabel fileTypeLabel = new JLabel(Constants.LABEL_FILE_TYPE + " : ");
		fileTypeLabel.setBounds(25, 15, 120, 20);
		fileTypeBox.setBounds(25, 45, 320, 20);
		JLabel fileLabel = new JLabel(Constants.LABEL_EXPORT_TO_FILE + " : ");
		fileLabel.setBounds(25, 45, 100, 20);
		fileField.setBounds(115, 45, 230, 20);
		openButton.setBounds(25 + 320 + 3, 45, 20, 18);
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		dataPanel.add(fileTypeLabel);
		dataPanel.add(fileLabel);
		dataPanel.add(fileField);
		dataPanel.add(openButton);

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

	public void openFile() {
		File file = new File(fileField.getText().trim());
		String exts[] = { ".xls" };

		file = Tools.chooseFiles(file, exts, this);

		if (file != null) {
			fileField.setText(file.getAbsolutePath());
		}
	}

	public void export() {
		try {
			Tools.exportDataToXLS(session, sql, new File(fileField.getText().trim()));
		} catch (Exception e) {
			MessageLogger.info(e);
		}

		this.setVisible(false);
	}

	public void cancel() {
		this.setVisible(false);
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
	}

	public void setData(Session session, String sql) {
		this.session = session;
		this.sql = sql;
	}

	public static void main(String[] args) {
		new ExportDataFrame().setVisible(true);
	}
}
