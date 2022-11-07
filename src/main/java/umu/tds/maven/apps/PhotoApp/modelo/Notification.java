package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Date;

public class Notification extends DomainObject {

	private Date date;
	private Post post;
	
	public Notification(Date date, Post post) {
		this.date = date;
		this.post = post;
	}
	
	public Date getDate() {
		return date;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
