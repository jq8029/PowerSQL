package com.jqtools.powersql;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 7976543774029147512L;
	private JTree tree = null;

	public static void main(String args[]) {
		new MainFrame();
	}

	public MainFrame() {
		this.setTitle(Constants.TITLE);
		JSplitPane mainSplitPane = new JSplitPane();
		this.add(Constants.PANEL_CENTER, mainSplitPane);
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

		// Left panel
		JPanel leftPanel = new JPanel(new BorderLayout());
		JScrollPane leftScrollPane = new JScrollPane();
		leftPanel.add(Constants.PANEL_CENTER, leftScrollPane);
		// add root node (Databases) to left panel
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(Constants.ROOT);
		tree = new JTree(root);
		leftScrollPane.setViewportView(tree);
		leftScrollPane.updateUI();

		// Right Panel : data/sql
		JTabbedPane rightPanel = new JTabbedPane();
		JPanel dataPanel = new JPanel(new BorderLayout());
		JSplitPane sqlSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightPanel.add(Constants.TAB_DATA, dataPanel);
		rightPanel.add(Constants.TAB_SQL, sqlSplitPane);

		// add left and right panel
		mainSplitPane.setLeftComponent(leftPanel);
		mainSplitPane.setRightComponent(rightPanel);
		mainSplitPane.setDividerSize(Constants.DIVIDER_SIZE);
		mainSplitPane.setDividerLocation(Constants.LEFT_WIDE);

		// show main frame
		this.pack();
		this.setVisible(true);
	}

	public void displayNode(DefaultMutableTreeNode node) {
		try {
			// display node
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}
	}
}
