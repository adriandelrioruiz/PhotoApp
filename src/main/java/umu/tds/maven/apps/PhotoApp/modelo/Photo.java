package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Date;

public class Photo extends Post{

	public Photo(String title, Date date, String description, int likes) {
		super(title, date, description, likes);
	}

	private String path;
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
