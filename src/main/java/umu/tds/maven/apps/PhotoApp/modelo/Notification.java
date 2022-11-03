package umu.tds.maven.apps.PhotoApp.modelo;

import java.time.LocalDateTime;

public class Notification extends DomainObject {

	private LocalDateTime date;
	
	public Notification() {
		this.date = LocalDateTime.now();
	}

}
