package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.controlador.InvalidHashtagException;
import umu.tds.maven.apps.PhotoApp.controlador.TooLongHashtagException;
import umu.tds.maven.apps.PhotoApp.controlador.TooManyHashtagsException;

public abstract class Post extends DomainObject implements Comparable<Post> {
	
	// Longitud máxima de caracteres de un hashtag
	public static final byte MAX_HASHTAG_LENGTH = 15;
	// Número máximo de hashtags
	public static final byte MAX_HASHTAGS = 4;

	private String title;
	private Date date;
	private String description;
	private User user;
	private int likes;
	private List<String> hashtags;
	private List<Comment> comments;
	
	public Post(String title, Date date, String description, User user) {
		this.title = title;
		this.date = date;
		this.description = description;
		this.user = user;
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
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public List<String> getHashtags() {
		return Collections.unmodifiableList(hashtags);
	}
	
	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}
	
	public void addHashtag(String hashtag) throws InvalidHashtagException {
		if (hashtag.length() > MAX_HASHTAG_LENGTH)
			throw new TooLongHashtagException();
		if (hashtags.size() > MAX_HASHTAGS)
			throw new TooManyHashtagsException();
		hashtags.add(hashtag);
	}
	
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public int compareTo(Post o) {
		return this.date.compareTo(o.date);
	}
	
	// Para dar like y quitar like a un post
	
	public void like() {
		likes++;
	}
	public void unlike() {
		likes--;
	}

}
