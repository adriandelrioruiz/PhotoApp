package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Date;

public class Photo extends Post {
	
	private String path;

	public Photo(String title, Date date, String description, int likes, String path, User user) {
		super(title, date, description, likes, user);
		this.path = path;
	}
	
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
