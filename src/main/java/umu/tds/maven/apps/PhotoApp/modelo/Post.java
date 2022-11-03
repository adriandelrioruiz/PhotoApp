package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// TODO Cambiar Post por Post
public class Post extends DomainObject {

	private String title;
	private Date date;
	private String description;
	private int likes;
	private List<String> hashtags;
	private List<Comment> comments;
	
	public Post(String title, Date date, String description, int likes) {
		this.title = title;
		this.date = date;
		this.description = description;
		this.likes = likes;
		hashtags = new LinkedList<>();
		comments = new LinkedList<>();
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<String> getHashtags() {
		return Collections.unmodifiableList(hashtags);
	}
	
	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}
	
	
	@Override
	public String toString() {
		return title;
	}

}
