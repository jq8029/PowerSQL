package com.jqtools.powersql;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.log.NoticeMessage;
import com.jqtools.powersql.obj.MainTextArea;
import com.jqtools.powersql.obj.ResultTableModel;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.obj.TreeNode;
import com.jqtools.powersql.utils.DBLoader;
import com.jqtools.powersql.utils.DBTools;
import com.jqtools.powersql.utils.ExecuteSQL;

public class MainFrame extends JFrame implements TreeSelectionListener {
	private static final long serialVersionUID = 7976543774029147512L;
	private JTree tree = null;
	private MainTextArea textArea = null;
	private JScrollPane resultScroll = new JScrollPane();
	private JScrollPane dataScroll = new JScrollPane();
	private JTabbedPane rightPanel = new JTabbedPane();

	public static void main(String args[]) {
		new MainFrame();
	}

	public MainFrame() {
		this.setTitle(Constants.TITLE);
		JSplitPane mainSplitPane = new JSplitPane();
		this.add(Constants.PANEL_CENTER, mainSplitPane);
		this.setPreferredSize(this.getMaximumSize());

		// Left panel
		JPanel leftPanel = new JPanel(new BorderLayout());
		JScrollPane leftScrollPane = new JScrollPane();
		leftPanel.add(Constants.PANEL_CENTER, leftScrollPane);
		// add root node (Databases) to left panel
		TreeNode root = new TreeNode(Constants.ROOT);
		tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			// double click on tree node
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					doubleClickNode();
				}
			}
		});

		root.setTree(tree);
		leftScrollPane.setViewportView(tree);
		leftScrollPane.updateUI();

		// Right Panel : data/sql
		JPanel dataPanel = new JPanel(new BorderLayout());
		ResultTableModel dataTableModel = new ResultTableModel();
		dataTableModel.setTable(new JTable(dataTableModel));
		dataPanel.add(Constants.PANEL_CENTER, dataScroll);
		JSplitPane sqlSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		rightPanel.add(Constants.TAB_DATA, dataPanel);
		rightPanel.add(Constants.TAB_SQL, sqlSplitPane);

		// sql panel : text area/table data
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setLayout(new BorderLayout());
		textArea = new MainTextArea();
		textArea.setResultScroll(resultScroll);
		textAreaPanel.add("Center", textArea);
		JPanel resultPanel = new JPanel(new BorderLayout());
		ResultTableModel resultTableModel = new ResultTableModel();
		resultTableModel.setTable(new JTable(resultTableModel));
		resultPanel.add(Constants.PANEL_CENTER, resultScroll);
		sqlSplitPane.setTopComponent(textAreaPanel);
		sqlSplitPane.setBottomComponent(resultPanel);
		sqlSplitPane.setDividerSize(Constants.DIVIDER_SIZE);
		sqlSplitPane.setDividerLocation(Constants.TOP_WIDE);

		// add left and right panel
		mainSplitPane.setLeftComponent(leftPanel);
		mainSplitPane.setRightComponent(rightPanel);
		mainSplitPane.setDividerSize(Constants.DIVIDER_SIZE);
		mainSplitPane.setDividerLocation(Constants.LEFT_WIDE);

		// load all table nodes
		loadTreeNodes(root);

		// exit the tool after close the window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// show main frame
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		try {
			TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

			if (node.getInfo().getNodeType() == Constants.NODE_TABLE
					|| node.getInfo().getNodeType() == Constants.NODE_VIEW) {
				ExecuteSQL.execute(node.getSession(), node.getSession().getDbData().getTableSQL(node.getInfo()),
						dataScroll);
				rightPanel.setSelectedIndex(0);
			} else if (node.getInfo().getNodeType() == Constants.NODE_CONNECTION) {
				DBTools.showDBInfo(dataScroll, node.getSession().getDbInfo());
			}

			textArea.setSession(node.getSession());
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}
	}

	public void loadTreeNodes(TreeNode root) {
		ArrayList<String> conNames = DBTools.getConNames();
		TreeNode node = null;
		Session session = null;
		for (String name : conNames) {
			node = new TreeNode(name);
			session = new Session();

			// add database node to root node
			node.addToParent(root);
			node.getInfo().setNodeType(Constants.NODE_CONNECTION);
			node.setSession(session);

			// add dbnode, dbinfo and dbdata to session
			session.setDbNode(node);
			session.setDbInfo(DBTools.getDBConnection(name));
			session.setDbData(DBTools.getDatabaseData(session.getDbInfo().getDbName()));
		}
	}

	private void doubleClickNode() {
		try {
			TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

			if (node.getInfo().getNodeType() == Constants.NODE_CONNECTION && node.getChildCount() == 0) {
				// load database child nodes
				if (DBLoader.loadDBNode(node.getSession())) {
//					NoticeMessage.showMessage("Connected to database : " + node.getInfo().getName());
				} else {
					NoticeMessage.showMessage("Failed to connect database : " + node.getInfo().getName());
				}
			}
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}
	}

}
