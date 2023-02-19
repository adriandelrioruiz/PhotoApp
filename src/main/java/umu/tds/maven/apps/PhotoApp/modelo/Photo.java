package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Date;

public class Photo extends Post {

	// Longitud máxima de caracteres de un hashtag
	public static final byte MAX_HASHTAG_LENGTH = 15;
	// Número máximo de hashtags
	public static final byte MAX_HASHTAGS = 4;

	private String path;
	private Notification notification;

	public Photo(String title, Date date, String description, String path, User user) {
		super(title, date, description, user);
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Notification getNotification() {
		return notification;
	}
	
	public void setNotification(Notification notification) {
		this.notification = notification;
	}

}
