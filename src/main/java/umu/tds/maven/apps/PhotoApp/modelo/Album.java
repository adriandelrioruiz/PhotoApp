package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Album extends Post {

	private List<Photo> photos;
	private Notification notification;

	public Album(String title, Date date, String description, User user) {
		super(title, date, description, user);
		photos = new LinkedList<>();
	}

	public List<Photo> getPhotos() {
		return Collections.unmodifiableList(photos);
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public void addPhoto(Photo photo) {
		photos.add(photo);
	}

	public void removePhoto(int id) {
		List<Photo> photo = photos.stream().filter((p) -> p.getCode() == id).toList();
		if (!photo.isEmpty())
			photos.remove(photo.get(0));
	}

	public void like() {
		// Damos like al propio álbum
		super.like();

		// Damos like a cada una de las fotos que lo conforman
		photos.stream().forEach((p) -> p.like());

	}
	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
}
