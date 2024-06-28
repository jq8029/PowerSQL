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

			// load all database nodes
			loadCatalogNode(session, con, session.getDbNode());
		} catch (Throwable e) {
			MessageLogger.error(e);

			return false;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
			}
		}

		return true;
	}

	private static boolean loadCatalogNode(Session session, Connection con, TreeNode node) throws Exception {
		if (session.getDbData().getCatalogAllSQL() == null) {
			// load schemas if there is no catalog
			return loadSchemaNode(session, con, node);
		}

		// load all catalogs
		return loadNode(con, node, session.getDbData().getCatalogAllSQL(), Constants.NODE_CATALOG,
				Constants.MY_CATALOG);
	}

	private static boolean loadSchemaNode(Session session, Connection con, TreeNode node) throws Exception {
		String sql = session.getDbData().getSchemaAllSQL();

		if (sql == null) {
			return false;
		}

		// load all schemas
		loadNode(con, node, session.getDbData().getTableSchemaSQL(), Constants.NODE_SCHEMA, Constants.MY_SCHEMA);

		// load tables and views for each schema
		for (int i = 0; i < node.getChildCount(); i++) {
			TreeNode child = (TreeNode) node.getChildAt(i);

			// load tables
			TreeNode tables = new TreeNode(Constants.NAME_TABLES, Constants.NODE_TABLES);
			tables.addToParent(child);
			tables.getInfo().setCatalog(node.getInfo().getCatalog());
			tables.getInfo().setSchema(node.getInfo().getSchema());
			loadTableNode(session, con, tables);

			// load views
			TreeNode views = new TreeNode(Constants.NAME_VIEWS, Constants.NODE_VIEWS);
			views.addToParent(child);
			views.getInfo().setCatalog(node.getInfo().getCatalog());
			views.getInfo().setSchema(node.getInfo().getSchema());
			loadViewNode(session, con, views);
		}

		return true;
	}

	private static boolean loadTableNode(Session session, Connection con, TreeNode node) throws Exception {
		return loadNode(con, node, session.getDbData().getTableSchemaSQL(), Constants.NODE_TABLE, Constants.MY_TABLE);
	}

	private static boolean loadViewNode(Session session, Connection con, TreeNode node) throws Exception {
		return loadNode(con, node, session.getDbData().getViewSchemaSQL(), Constants.NODE_VIEW, Constants.MY_VIEW);
	}

	private static boolean loadNode(Connection con, TreeNode node, String sql, int nodeType, String colName)
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
				newNode = new TreeNode(info);
				node.addToParent(newNode);
			}
		} finally {
			DBTools.close(stmt, rs);
		}

		return true;
	}
}
