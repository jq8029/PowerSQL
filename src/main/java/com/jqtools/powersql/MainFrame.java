package com.jqtools.powersql;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.ResultTableModel;
import com.jqtools.powersql.obj.TreeNode;

public class MainFrame extends JFrame implements TreeSelectionListener {
	private static final long serialVersionUID = 7976543774029147512L;
	private JTree tree = null;
	private RSyntaxTextArea textArea = null;
	private ResultTableModel resultTableModel = new ResultTableModel();
	private ResultTableModel dataTableModel = new ResultTableModel();

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
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(dataTableModel.getTable());
		dataPanel.add(Constants.PANEL_CENTER, scroll);
		JSplitPane sqlSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightPanel.add(Constants.TAB_DATA, dataPanel);
		rightPanel.add(Constants.TAB_SQL, sqlSplitPane);

		// sql panel : text area/table data
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setLayout(new BorderLayout());
		textArea = new RSyntaxTextArea();
		textAreaPanel.add("Center", textArea);
		JPanel resultPanel = new JPanel(new BorderLayout());
		scroll = new JScrollPane();
		scroll.setViewportView(resultTableModel.getTable());
		resultPanel.add(Constants.PANEL_CENTER, scroll);
		sqlSplitPane.setTopComponent(textAreaPanel);
		sqlSplitPane.setBottomComponent(resultPanel);
		sqlSplitPane.setDividerSize(Constants.DIVIDER_SIZE);
		sqlSplitPane.setDividerLocation(Constants.TOP_WIDE);

		// add left and right panel
		mainSplitPane.setLeftComponent(leftPanel);
		mainSplitPane.setRightComponent(rightPanel);
		mainSplitPane.setDividerSize(Constants.DIVIDER_SIZE);
		mainSplitPane.setDividerLocation(Constants.LEFT_WIDE);

		// show main frame
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		try {
			TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

			displayNode(node);
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}
	}

	public void displayNode(TreeNode node) {
		try {
			// display node
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}
	}
}
