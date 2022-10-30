package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.List;

public class Album extends Publication {
	
	private List<Photo> photos;
	
	public List<Photo> getPhotos() {
		return Collections.unmodifiableList(photos);
	}
}
