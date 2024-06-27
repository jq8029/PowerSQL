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

			con = DBTools.getConnection(session.getDbInfo());

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
			return loadSchemaNode(session, con, node);
		}

		return true;
	}

	private static boolean loadSchemaNode(Session session, Connection con, TreeNode node) throws Exception {
		String sql = session.getDbData().getSchemaAllSQL();

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
				info.setSchema(DBTools.getValue(rs, Constants.MY_SCHEMA));
				info.setName(info.getSchema());
				newNode = new TreeNode(info);
				node.addToParent(newNode);
			}
		} finally {
			DBTools.close(stmt, rs);
		}

		return true;
	}

	private static boolean loadTableNode(Session session, Connection con, TreeNode node) throws Exception {
		String sql = session.getDbData().getSchemaAllSQL();

		if (sql == null) {
			return false;
		}

		return true;
	}
}
