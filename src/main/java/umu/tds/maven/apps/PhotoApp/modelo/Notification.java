package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Date;

public class Notification extends DomainObject {

	private final Date date;
	private final Post post;
	private final boolean isAlbum;
	public Notification(Date date, Post post,boolean isAlbum) {
		this.date = date;
		this.post = post;
		this.isAlbum=isAlbum;
	}

	public Date getDate() {
		return date;
	}

	public Post getPost() {
		return post;
	}
	
	public boolean getisAlbum() {
		return isAlbum;
	}

}
