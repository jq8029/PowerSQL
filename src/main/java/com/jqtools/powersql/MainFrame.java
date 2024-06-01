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

import com.jqtools.powersql.constants.Constants;;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JSplitPane mainSplitPane = new JSplitPane();;
	private JScrollPane leftScrollPane = new JScrollPane();
	private JPanel sqlPanel = null;
	private JSplitPane sqlSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	public static void main(String args[]) {
		new MainFrame();
	}

	public MainFrame() {
		this.setTitle(Constants.TITLE);
		this.add(Constants.PANEL_CENTER, mainSplitPane);
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add("Center", leftScrollPane);

		JTabbedPane mainPanel = new JTabbedPane();

		mainSplitPane.setLeftComponent(leftPanel);
		mainSplitPane.setRightComponent(mainPanel);
		mainSplitPane.setDividerSize(Constants.DIVIDER_SIZE);
		mainSplitPane.setDividerLocation(Constants.LEFT_WIDE);

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(Constants.ROOT);
		JTree tree = new JTree(root);
		leftScrollPane.setViewportView(tree);
		leftScrollPane.updateUI();

		this.pack();
		this.setVisible(true);
	}
}
