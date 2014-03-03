package edu.asupoly.ser422.lab4.model;

/**
 * 	An immutable object representing a comment on a news story
 * @author kevinagary
 *
 */
public class CommentBean implements java.io.Serializable {
	private static final long serialVersionUID = 8241629811258814033L;

	private final String userId;
	private final String comment;
	private final int newsItemId;

	public CommentBean(NewsItemBean nib, String userid, String comment) {
		this.newsItemId = nib.getItemId();
		this.userId = userid;
		this.comment = comment;
	}

	private CommentBean(int nid, String userid, String comment)
	{
		this.newsItemId = nid;
		this.userId = userid;
		this.comment = comment;
	}
	public String getUserId() {
		return this.userId;
	}

	public String getComment() {
		return this.comment;
	}

	public int getNewsItemId() {
		return this.newsItemId;
	}
}
