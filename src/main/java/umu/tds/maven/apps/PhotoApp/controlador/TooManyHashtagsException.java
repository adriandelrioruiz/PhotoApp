package umu.tds.maven.apps.PhotoApp.controlador;

@SuppressWarnings("serial")
public class TooManyHashtagsException extends InvalidHashtagException {

	@Override
	public void showDialog() {
		System.out.println("Too many hashtags");
	}
}
