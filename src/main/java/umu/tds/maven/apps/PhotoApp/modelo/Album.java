package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Album extends Post {
	
	private List<Photo> photos;
	
	public Album(String title, Date date, String description, int likes) {
		super(title, date, description, likes);
		photos = new LinkedList<>();
	}
	
	public List<Photo> getPhotos() {
		return Collections.unmodifiableList(photos);
	}
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
}
