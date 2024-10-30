package com.jqtools.powersql.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
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
		loadNode(con, node, session.getDbData().getCatalogAllSQL(), Constants.NODE_CATALOG,
				session.getDbData().getCatalogName());

		// load tables and views for each schema
		for (int i = 0; i < node.getChildCount(); i++) {
			loadSchemaNode(session, con, (TreeNode) node.getChildAt(i));
		}
		return true;
	}

	private static boolean loadSchemaNode(Session session, Connection con, TreeNode node) throws Exception {
		String sql = session.getDbData().getSchemaAllSQL();

		if (sql == null) {
			return false;
		} else {
			if (node.getInfo().getCatalog() != null) {
				sql = updateSQL(sql, node.getInfo().getCatalog());
			}
		}

		// load all schemas
		loadNode(con, node, sql, Constants.NODE_SCHEMA, session.getDbData().getSchemaName());

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
		String sql = session.getDbData().getTableSchemaSQL();
		if (node.getInfo().getSchema() != null) {
			sql = updateSQL(sql, node.getInfo().getSchema());
		}

		return loadNode(con, node, sql, Constants.NODE_TABLE, session.getDbData().getTableName());
	}

	private static boolean loadViewNode(Session session, Connection con, TreeNode node) throws Exception {
		String sql = session.getDbData().getViewSchemaSQL();
		if (node.getInfo().getSchema() != null) {
			sql = updateSQL(sql, node.getInfo().getSchema());
		}

		return loadNode(con, node, sql, Constants.NODE_VIEW, session.getDbData().getViewName());
	}

	public static boolean loadNode(Connection con, TreeNode node, String sql, int nodeType, String colName)
			throws Exception {
		if (sql == null) {
			return false;
		}

		ResultSet rs = null;
		PreparedStatement stmt = null;
		Info info = null;
		TreeNode newNode = null;

		try {
			rs = (stmt = DBTools.getStatement(con, sql)).executeQuery();
			while (rs.next()) {
				info = new Info();
				info.setCatalog(node.getInfo().getCatalog());
				info.setSchema(node.getInfo().getSchema());
				info.setName(DBTools.getValue(rs, colName));
				info.setNodeType(nodeType);

				// update the node catalog or schema
				if (nodeType == Constants.NODE_CATALOG) {
					info.setCatalog(info.getName());
				} else if (nodeType == Constants.NODE_SCHEMA) {
					info.setSchema(info.getName());
				}

				newNode = new TreeNode(info);
				newNode.addToParent(node);
				if (nodeType == Constants.NODE_TABLE || nodeType == Constants.NODE_VIEW) {
					Info colsInfo = info.clone();
					colsInfo.setNodeType(Constants.NODE_TABLE_COLUMNS);
					colsInfo.setColName(Constants.NAME_COLS);
					TreeNode colsNode = new TreeNode(colsInfo);
					colsNode.add(newNode);
					colsNode.setLeaf(false);
				}
			}
		} finally {
			DBTools.close(stmt, rs);
		}

		return true;
	}

	private static String updateSQL(String sql, String name) {
		if (name == null) {
			return sql;
		} else {
			return sql.replaceFirst("\\?", name);
		}
	}
}