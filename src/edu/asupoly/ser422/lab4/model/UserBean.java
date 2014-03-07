package edu.asupoly.ser422.lab4.model;

import java.util.ArrayList;

public class UserBean implements java.io.Serializable {
	private static final long serialVersionUID = -1408433159918540612L;

	public enum Role {
		GUEST, SUBSCRIBER, REPORTER;

		@Override
		public String toString() {
			switch(this) {
				case GUEST: return "Guest";
				case SUBSCRIBER: return "Subscriber";
				case REPORTER: return "Reporter";
				default: throw new IllegalArgumentException();
			}
		}
	}

	private final String userId;
	private final String passwd;
	private final Role role;

	private final ArrayList<Integer>	favArticles	= new ArrayList<Integer>();

	public UserBean(String id, String passwd, Role r) {
		this.userId = id;
		this.passwd = passwd;
		this.role = r;
	}
	public UserBean(UserBean user, Role newRole) {
		this.userId = user.userId;
		this.passwd = user.passwd;
		this.role = newRole;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public Role getRole() {
		return this.role;
	}

	@Override
	public String toString() {
		return "User " + this.userId + " of Role " + this.role.toString();
	}

	@Override
	public Object clone() {
		return new UserBean(this, this.role);
	}
	/**
	 * @return the favArticles
	 */
	public ArrayList<Integer> getFavArticles()
	{

		return favArticles;
	}

}
