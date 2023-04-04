package umu.tds.maven.apps.PhotoApp.pruebas;

import java.util.Date;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Album;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.persistencia.AlbumAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.CommentAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.NotificationAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.PhotoAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

public class PruebasPersistencia {

	public static void deleteAllDatabase() {
		UserAdapterTDS.getInstance().deleteAll();
		PhotoAdapterTDS.getInstance().deleteAll();
		NotificationAdapterTDS.getInstance().deleteAll();
		AlbumAdapterTDS.getInstance().deleteAll();
		CommentAdapterTDS.getInstance().deleteAll();
	}

	public static void main(String[] args) {
		//deleteAllDatabase();System.exit(0);
		PhotoAppController.getInstance().registerUser("Adrian del Rio", "adri@gmail", "adriandelrio", "password",
				new Date(), ViewConstants.RUTA_FOTOS_USER+"\\animal.jpeg", "myBio");
		/*
		 * PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan@gmail",
		 * "juanhdz", "password", new Date(), "PhotoJuan", "BioJuan");
		 * PhotoAppController.getInstance().registerUser("Juan Hernandez",
		 * "juan2@gmail", "juanhdz2", "password", new Date(), "PhotoJuan", "BioJuan");
		 */
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan3@gmail", "juanhdz3", "password",
				new Date(), ViewConstants.RUTA_FOTOS_USER+"\\animal2.jpeg", "BioJuan");
		PhotoAppController.getInstance().login("adri@gmail", "password");
		PhotoAppController.getInstance().follow("juanhdz3");
		PhotoAppController.getInstance().unFollow("juanhdz3");
		PhotoAppController.getInstance().follow("juanhdz3");
		PhotoAppController.getInstance().unLogin();
		PhotoAppController.getInstance().login("juan3@gmail", "password");
		// PhotoAppController.getInstance().generateExcel("C:\\Users\\adria\\OneDrive\\Escritorio");

		Photo p = PhotoAppController.getInstance().addPhoto("paisaje", "HOLA SOY UNa fto", ViewConstants.RUTA_FOTOS_USER+"\\itza.jpeg");

		Album a = PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"\\paisaje1.jpeg");

		PhotoAppController.getInstance().addPhotoToAlbum("paisaje2", "astonishing",ViewConstants.RUTA_FOTOS_USER+"\\paisaje1.jpeg", a.getCode());

		//PhotoAppController.getInstance().deletePhoto(p.getCode());

		//PhotoAppController.getInstance().deleteAlbum(a.getCode());

		// List<Integer> photos = PhotoAppController.getInstance().getPhotos();

		//List<Photo> top = PhotoAppController.getInstance().getTopPhotosByLikes();
		// PhotoAppController.getInstance().comment(post, "hola soy juan comentando");
		//PhotoAppController.getInstance().search("hermana ines");
		PhotoAppController.getInstance().unLogin();
		PhotoAppController.getInstance().login("adri@gmail", "password");
		// PhotoAppController.getInstance().getFeed();
		
		LoggedFrame fr=new LoggedFrame();
		fr.setVisible(true);

	}
}
