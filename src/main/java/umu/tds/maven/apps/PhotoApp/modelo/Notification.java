package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Date;

public class Notification extends DomainObject {

	private final Date date;
	private final Photo photo;
	
	public Notification(Date date, Photo photo) {
		this.date = date;
		this.photo = photo;
	}
	
	public Date getDate() {
		return date;
	}

	public Photo getPhoto() {
		return photo;
	}	

}
