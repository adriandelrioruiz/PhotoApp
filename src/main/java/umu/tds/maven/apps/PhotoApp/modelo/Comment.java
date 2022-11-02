package umu.tds.maven.apps.PhotoApp.modelo;

public class Comment extends DomainObject {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
