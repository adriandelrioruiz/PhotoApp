package umu.tds.maven.apps.PhotoApp.pruebas;

import javax.swing.JFrame;

import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowMyUploadedAlbumFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedAlbumFrame;

public class PruebaVista extends JFrame{
	
	/*
	public PruebaVista() {
		setSize(new Dimension(500,540));
		User user = new User("Adrian del Rio", "adri@gmail", "adriandelrio", "password", new Date(), false, "myPhoto", "myBio");
		MyProfilePane ap = new MyProfilePane(user);
	
		add(ap);
		setVisible(true);

	}*/

	public static void main(String[] args) {
		// PON TU RUTA AQUI
		/*
		ViewConstants.RUTA_FOTOS = "C:\\Users\\adria\\eclipse-workspace\\PhotoApp2\\img\\";
		LoggedFrame lf = new LoggedFrame();
		lf.setLocationRelativeTo(null);
		lf.setDefaultCloseOperation(EXIT_ON_CLOSE);*/
		ShowMyUploadedAlbumFrame showPhoto = new ShowMyUploadedAlbumFrame(8481, 8533);
	}

}
