package com.jqtools.powersql.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.obj.ColumnInfo;
import com.jqtools.powersql.obj.ConstraintInfo;
import com.jqtools.powersql.obj.IndexInfo;
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
			child = (TreeNode) node.getChildAt(i).getChildAt(1);
			rc = rc && loadNode(con, child, session.getDbData().getIndexSQL(child.getInfo()), Constants.NODE_INDEX);
			child = (TreeNode) node.getChildAt(i).getChildAt(2);
			rc = rc && loadNode(con, child, session.getDbData().getConstraintSQL(child.getInfo()),
					Constants.NODE_CONSTRAINT);
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
		} catch (Exception ex) {
			MessageLogger.info("sql = " + sql);
			MessageLogger.error(ex);
			throw ex;
		} finally {
			DBTools.close(stmt, rs);
		}

		return true;
	}

	private static TreeNode loadNode(ResultSet rs, TreeNode node, int nodeType) {
		if (nodeType == Constants.NODE_COLUMN) {
			return loadColumnNode(rs, node, nodeType);
		} else if (nodeType == Constants.NODE_INDEX) {
			return loadIndexNode(rs, node, nodeType);
		} else if (nodeType == Constants.NODE_CONSTRAINT) {
			return loadConstraintNode(rs, node, nodeType);
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
			// add table/view columns node
			Info subInfo = info.clone();
			subInfo.setNodeType(Constants.NODE_TABLE_COLUMNS);
			TreeNode subNode = new TreeNode(subInfo);
			subNode.addToParent(newNode);
			subNode.setLeaf(false);

			if (nodeType == Constants.NODE_TABLE) {
				// add table indexes node
				subInfo = info.clone();
				subInfo.setNodeType(Constants.NODE_TABLE_INDEXES);
				subNode = new TreeNode(subInfo);
				subNode.addToParent(newNode);
				subNode.setLeaf(false);

				// add table constraints node
				subInfo = info.clone();
				subInfo.setNodeType(Constants.NODE_TABLE_CONSTRAINTS);
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

	private static TreeNode loadIndexNode(ResultSet rs, TreeNode node, int nodeType) {
		IndexInfo info = new IndexInfo();
		info.setCatalog(node.getInfo().getCatalog());
		info.setSchema(node.getInfo().getSchema());
		info.setName(node.getInfo().getName());
		info.setNodeType(Constants.NODE_INDEX_KEY);
		info.setIndexName(DBTools.getValue(rs, Constants.INDEX_NAME));
		info.setType(DBTools.getValue(rs, Constants.INDEX_TYPE));
		info.setColumnName(DBTools.getValue(rs, Constants.COL_NAME));
		info.setAscOrDesc(DBTools.getValue(rs, Constants.INDEX_ASC_DESC));
		info.setNonUnique(DBTools.getBooleanValue(rs, Constants.INDEX_NON_UNIQUE, true));
		info.setOrdinalPosition(DBTools.getIntValue(rs, Constants.COL_ORDINAL_POSITION, 0));

		TreeNode indexNode = null;
		IndexInfo childInfo = null;
		for (int i = 0; i < node.getChildCount(); i++) {
			childInfo = (IndexInfo) ((TreeNode) node.getChildAt(i)).getInfo();
			if (Tools.isEqual(childInfo.getName(), info.getName())
					&& Tools.isEqual(childInfo.getIndexName(), info.getIndexName())) {
				indexNode = (TreeNode) node.getChildAt(i);
				break;
			}
		}
		if (indexNode == null) {
			indexNode = new TreeNode(info.clone());
			indexNode.getInfo().setNodeType(nodeType);
			indexNode.addToParent(node);
		}

		TreeNode newNode = new TreeNode(info);
		newNode.addToParent(indexNode);

		return newNode;
	}

	private static TreeNode loadConstraintNode(ResultSet rs, TreeNode node, int nodeType) {
		ConstraintInfo info = new ConstraintInfo();
		info.setCatalog(node.getInfo().getCatalog());
		info.setSchema(node.getInfo().getSchema());
		info.setName(node.getInfo().getName());
		info.setNodeType(Constants.NODE_CONSTRAINT_KEY);
		info.setConstraintName(DBTools.getValue(rs, Constants.CONSTRAINT_NAME));
		info.setConstraintType(DBTools.getValue(rs, Constants.CONSTRAINT_TYPE));
		info.setColumnName(DBTools.getValue(rs, Constants.COL_NAME));
		info.setOrdinalPosition(DBTools.getIntValue(rs, Constants.COL_ORDINAL_POSITION, 0));
		info.setReferenceSchema(DBTools.getValue(rs, Constants.REF_SCHEMA));
		info.setReferenceTable(DBTools.getValue(rs, Constants.REF_TABLE_NAME));
		info.setReferenceColName(DBTools.getValue(rs, Constants.REF_COLUMN_NAME));
		info.setReferencePosition(DBTools.getIntValue(rs, Constants.REF_POSITION, 0));

		TreeNode constraintNode = null;
		ConstraintInfo childInfo = null;
		for (int i = 0; i < node.getChildCount(); i++) {
			childInfo = (ConstraintInfo) ((TreeNode) node.getChildAt(i)).getInfo();
			if (Tools.isEqual(childInfo.getName(), info.getName())
					&& Tools.isEqual(childInfo.getConstraintName(), info.getConstraintName())) {
				constraintNode = (TreeNode) node.getChildAt(i);
				break;
			}
		}
		if (constraintNode == null) {
			constraintNode = new TreeNode(info.clone());
			constraintNode.getInfo().setNodeType(nodeType);
			constraintNode.addToParent(node);
		}

		TreeNode newNode = new TreeNode(info);
		newNode.addToParent(constraintNode);

		return newNode;
	}
}