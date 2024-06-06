package com.jqtools.powersql.obj;

public class ExecuteInfo {
	private String sqls[] = null;
	private Session session = null;

	/**
	 * @return the sqls
	 */
	public String[] getSqls() {
		return sqls;
	}

	/**
	 * @param sqls the sqls to set
	 */
	public void setSqls(String[] sqls) {
		this.sqls = sqls;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

}
