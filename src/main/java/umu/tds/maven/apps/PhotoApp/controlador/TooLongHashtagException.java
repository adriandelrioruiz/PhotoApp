package umu.tds.maven.apps.PhotoApp.controlador;

@SuppressWarnings("serial")
public class TooLongHashtagException extends InvalidHashtagException {
	
	@Override
	public void showDialog() {
		System.out.println("too long hashtag");
	}
}
