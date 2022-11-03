package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Album extends Post {
	
	public Album(String title, Date date, String description, int likes) {
		super(title, date, description, likes);
	}

	private List<Photo> photos;
	
	public List<Photo> getPhotos() {
		return Collections.unmodifiableList(photos);
	}
}
