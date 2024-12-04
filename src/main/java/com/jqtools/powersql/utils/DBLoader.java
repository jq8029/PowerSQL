package com.jqtools.powersql.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.ColumnInfo;
import com.jqtools.powersql.obj.Info;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.obj.TreeNode;

public class DBLoader {
	public static boolean loadDBNode(Session session) {
		if (session == null || session.getDbInfo() == null || session.getDbNode() == null
				|| session.getDbData() == null) {
			return false;
		}

		Connection con = null;

		try {
			// create connection first
			con = DBTools.createConnection(session.getDbInfo());
			session.setConnection(con);

			// load all database nodes
			loadCatalogNode(session, con, session.getDbNode());
		} catch (Throwable e) {
			MessageLogger.error(e);

			return false;
		}

		return true;
	}

	private static boolean loadCatalogNode(Session session, Connection con, TreeNode node) throws Exception {
		if (session.getDbData().getCatalogAllSQL() == null) {
			// load schemas if there is no catalog
			return loadSchemaNode(session, con, node);
		}

		// load all catalogs
		loadNode(con, node, session.getDbData().getCatalogAllSQL(), Constants.NODE_CATALOG);

		// load tables and views for each schema
		for (int i = 0; i < node.getChildCount(); i++) {
			loadSchemaNode(session, con, (TreeNode) node.getChildAt(i));
		}
		return true;
	}

	private static boolean loadSchemaNode(Session session, Connection con, TreeNode node) throws Exception {
		String sql = session.getDbData().getSchemaAllSQL(node.getInfo());

		if (sql == null) {
			return false;
		}

		// load all schemas
		loadNode(con, node, sql, Constants.NODE_SCHEMA);

		// load tables and views for each schema
		for (int i = 0; i < node.getChildCount(); i++) {
			TreeNode child = (TreeNode) node.getChildAt(i);

			// load tables
			TreeNode tables = new TreeNode(Constants.NAME_TABLES, Constants.NODE_TABLES);
			tables.addToParent(child);
			tables.getInfo().setCatalog(child.getInfo().getCatalog());
			tables.getInfo().setSchema(child.getInfo().getSchema());
			loadTableNode(session, con, tables);

			// load views
			TreeNode views = new TreeNode(Constants.NAME_VIEWS, Constants.NODE_VIEWS);
			views.addToParent(child);
			views.getInfo().setCatalog(child.getInfo().getCatalog());
			views.getInfo().setSchema(child.getInfo().getSchema());
			loadViewNode(session, con, views);
		}

		return true;
	}

	private static boolean loadTableNode(Session session, Connection con, TreeNode node) throws Exception {
		boolean rc = loadNode(con, node, session.getDbData().getTableSchemaSQL(node.getInfo()), Constants.NODE_TABLE);

		// load tables and views for each schema
		for (int i = 0; i < node.getChildCount(); i++) {
			TreeNode child = (TreeNode) node.getChildAt(i).getChildAt(0);
			rc = rc && loadNode(con, child, session.getDbData().getColumnSQL(child.getInfo()), Constants.NODE_COLUMN);
		}

		return rc;
	}

	private static boolean loadViewNode(Session session, Connection con, TreeNode node) throws Exception {
		boolean rc = loadNode(con, node, session.getDbData().getViewSchemaSQL(node.getInfo()), Constants.NODE_VIEW);

		// load tables and views for each schema
		for (int i = 0; i < node.getChildCount(); i++) {
			TreeNode child = (TreeNode) node.getChildAt(i).getChildAt(0);
			rc = rc && loadNode(con, child, session.getDbData().getColumnSQL(child.getInfo()), Constants.NODE_COLUMN);
		}

		return rc;
	}

	public static boolean loadNode(Connection con, TreeNode node, String sql, int nodeType) throws Exception {
		if (sql == null) {
			return false;
		}

		ResultSet rs = null;
		PreparedStatement stmt = null;

		try {
			rs = (stmt = DBTools.getStatement(con, sql)).executeQuery();
			while (rs.next()) {
				loadNode(rs, node, nodeType);
			}
		} finally {
			DBTools.close(stmt, rs);
		}

		return true;
	}

	private static TreeNode loadNode(ResultSet rs, TreeNode node, int nodeType) {
		if (nodeType == Constants.NODE_COLUMN) {
			return loadColumnNode(rs, node, nodeType);
		}

		Info info = node.getInfo().clone();
		info.setName(DBTools.getValue(rs, null));
		info.setNodeType(nodeType);

		// update the node catalog or schema
		if (nodeType == Constants.NODE_CATALOG) {
			info.setCatalog(info.getName());
		} else if (nodeType == Constants.NODE_SCHEMA) {
			info.setSchema(info.getName());
		}

		TreeNode newNode = new TreeNode(info);
		newNode.addToParent(node);

		if (nodeType == Constants.NODE_TABLE || nodeType == Constants.NODE_VIEW) {
			Info colsInfo = info.clone();
			colsInfo.setNodeType(Constants.NODE_TABLE_COLUMNS);
			TreeNode colsNode = new TreeNode(colsInfo);
			colsNode.addToParent(newNode);
			colsNode.setLeaf(false);

			if (nodeType == Constants.NODE_TABLE) {
				// add table indexes
				Info subInfo = info.clone();
				colsInfo.setNodeType(Constants.NODE_TABLE_INDEXES);
				TreeNode subNode = new TreeNode(subInfo);
				subNode.addToParent(newNode);
				subNode.setLeaf(false);

				subInfo = info.clone();
				colsInfo.setNodeType(Constants.NODE_TABLE_CONSTRAINTS);
				subNode = new TreeNode(subInfo);
				subNode.addToParent(newNode);
				subNode.setLeaf(false);
			}
		}

		return newNode;
	}

	private static TreeNode loadColumnNode(ResultSet rs, TreeNode node, int nodeType) {
		ColumnInfo info = new ColumnInfo();
		info.setCatalog(node.getInfo().getCatalog());
		info.setSchema(node.getInfo().getSchema());
		info.setName(node.getInfo().getName());
		info.setNodeType(nodeType);
		info.setColumnName(DBTools.getValue(rs, Constants.COL_NAME));
		info.setTypeName(DBTools.getValue(rs, Constants.COL_TYPE_NAME));
		info.setDataType(DBTools.getIntValue(rs, Constants.COL_DATA_TYPE, Types.VARCHAR));
		info.setMaxLength(DBTools.getIntValue(rs, Constants.COL_MAX_LENGTH, 0));
		info.setNullable(DBTools.getBooleanValue(rs, Constants.COL_IS_NULLABLE, true));
		info.setNumericLen(DBTools.getIntValue(rs, Constants.COL_NUM_LENGTH, 0));
		info.setNumericScale(DBTools.getIntValue(rs, Constants.COL_NUM_SCALE, 0));
		info.setOrdinalPosition(DBTools.getIntValue(rs, Constants.COL_ORDINAL_POSITION, 0));
		info.setPrimaryKey(false);

		TreeNode newNode = new TreeNode(info);
		newNode.addToParent(node);

		return newNode;
	}
}