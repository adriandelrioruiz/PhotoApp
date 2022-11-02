package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.List;

public class Album extends Post {
	
	private List<Photo> photos;
	
	public List<Photo> getPhotos() {
		return Collections.unmodifiableList(photos);
	}
}
