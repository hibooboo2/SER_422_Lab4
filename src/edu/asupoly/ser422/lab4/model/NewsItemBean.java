package edu.asupoly.ser422.lab4.model;

import java.util.ArrayList;
import java.util.Date;

public class NewsItemBean {
	private static int nextId = 1;

	private int itemId;
	private String itemTitle;
	private String itemStory;
	private Date itemDate;
	private final String reporterId;
	private ArrayList<CommentBean> comments = new ArrayList<CommentBean>();

	// This constructor is used for a new news item
	public NewsItemBean(String title, String story, String rid) {
		this(nextId++, title, story, rid);
	}

	public NewsItemBean(){
		this.itemTitle = "The Title";
		this.itemStory = "Story now incoming";
		this.itemDate = null;
		this.reporterId= "ADAM";
		this.comments = null;
	}

	// This constructor is used for an existing, i.e. coming from datastore
	public NewsItemBean(int id, String title, String story, String rid) {
		this.itemTitle = title;
		this.itemStory = story;
		this.reporterId = rid;
		this.itemDate = new Date();
		this.itemId = id;
	}

	public int getItemId() {
		return this.itemId;
	}
	public String getReporterId() {
		return this.reporterId;
	}

	public void setItemTitle(String itemTitle, String rid) {
		if (rid.equals(this.reporterId)) {
			this.itemTitle = itemTitle;
		}
	}

	public String getItemTitle() {
		return this.itemTitle;
	}

	public void setItemStory(String itemStory, String rid) {
		if (rid.equals(this.reporterId)) {
			this.itemStory = itemStory;
			this.setItemDate(new Date());
		}
	}

	public String getItemStory() {
		return this.itemStory;
	}

	private void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}

	public Date getItemDate() {
		return this.itemDate;
	}

	public void addComment(CommentBean cb) {
		this.comments.add(cb);
	}
	public CommentBean[] getComments() {
		return this.comments.toArray(new CommentBean[0]);
	}
}
