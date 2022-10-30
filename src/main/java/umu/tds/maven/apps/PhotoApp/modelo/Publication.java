package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Publication {

	private int code;
	private String title;
	private Date date;
	private String description;
	private int likes;
	private List<String> hashtags;
	private List<Comment> comments;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
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

	public void setComentarios(List<Comment> comments) {
		this.comments = comments;
	}

	public List<String> getHashtags() {
		return Collections.unmodifiableList(hashtags);
	}
	
	@Override
	public String toString() {
		return title;
	}

}
