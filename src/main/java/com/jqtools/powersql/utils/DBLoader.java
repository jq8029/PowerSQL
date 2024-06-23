package com.jqtools.powersql.utils;

import java.sql.Connection;

import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.obj.TreeNode;

public class DBLoader {
	public static boolean loadDBNode(Session session) {
		if (session == null || session.getDbInfo() == null || session.getDbNode() == null
				|| session.getDbData() == null) {
			return false;
		}

		Connection conn = null;

		try {

			conn = DBTools.getConnection(session.getDbInfo());

			loadCatalogNode(session, conn, session.getDbNode());
		} catch (Throwable e) {
			MessageLogger.error(e);

			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}

		return true;
	}

	private static boolean loadCatalogNode(Session session, Connection conn, TreeNode node) {
		if (session.getDbData().getCatalogAllSQL() == null) {
			return loadSchemaNode(session, conn, node);
		}

		return true;
	}

	private static boolean loadSchemaNode(Session session, Connection conn, TreeNode node) {
		if (session.getDbData().getSchemaAllSQL() == null) {
			return false;
		}

		return true;
	}
}
