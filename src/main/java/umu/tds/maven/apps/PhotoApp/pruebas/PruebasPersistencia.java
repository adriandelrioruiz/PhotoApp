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
<<<<<<< HEAD
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;
import umu.tds.maven.apps.PhotoApp.vista.search.SearchFrame;
=======

>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
public class PruebasPersistencia {

	public static void deleteAllDatabase() {
		UserAdapterTDS.getInstance().deleteAll();
		PhotoAdapterTDS.getInstance().deleteAll();
		NotificationAdapterTDS.getInstance().deleteAll();
		AlbumAdapterTDS.getInstance().deleteAll();
		CommentAdapterTDS.getInstance().deleteAll();
	}

	public static void main(String[] args) {
<<<<<<< HEAD
		//deleteAllDatabase();System.exit(0);
		PhotoAppController.getInstance().registerUser("Adrian del Rio", "adri@gmail", "adriandelrio", "password", new Date(), "myPhoto", "myBio");
		/*PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan@gmail", "juanhdz", "password", new Date(), "PhotoJuan", "BioJuan");
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan2@gmail", "juanhdz2", "password", new Date(), "PhotoJuan", "BioJuan");*/
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan3@gmail", "juanhdz3", "password", new Date(), "PhotoJuan", "BioJuan");
=======
		deleteAllDatabase();System.exit(0);
		PhotoAppController.getInstance().registerUser("Adrian del Rio", "adri@gmail", "adriandelrio", "password",
				new Date(), "myPhoto", "myBio");
		/*
		 * PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan@gmail",
		 * "juanhdz", "password", new Date(), "PhotoJuan", "BioJuan");
		 * PhotoAppController.getInstance().registerUser("Juan Hernandez",
		 * "juan2@gmail", "juanhdz2", "password", new Date(), "PhotoJuan", "BioJuan");
		 */
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan3@gmail", "juanhdz3", "password",
				new Date(), "PhotoJuan", "BioJuan");
>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
		PhotoAppController.getInstance().login("adri@gmail", "password");
		PhotoAppController.getInstance().follow("juanhdz3");
		PhotoAppController.getInstance().unFollow("juanhdz3");
		PhotoAppController.getInstance().follow("juanhdz3");
		PhotoAppController.getInstance().unLogin();
		PhotoAppController.getInstance().login("juan3@gmail", "password");
<<<<<<< HEAD
		//PhotoAppController.getInstance().generateExcel("C:\\Users\\adria\\OneDrive\\Escritorio");
		
		
		Photo p = PhotoAppController.getInstance().addPhoto("foto", "HOLA SOY UNa fto", "pathconcomment");
		
		Album a = PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", "pathconcomment");
		
		PhotoAppController.getInstance().addPhotoToAlbum("ola", "asd", "asdasd", a.getCode());
		
		//PhotoAppController.getInstance().deletePhoto(p.getCode());
		
		PhotoAppController.getInstance().deleteAlbum(a.getCode());
		
		//List<Integer> photos = PhotoAppController.getInstance().getPhotos();
		
=======
		// PhotoAppController.getInstance().generateExcel("C:\\Users\\adria\\OneDrive\\Escritorio");

		Photo p = PhotoAppController.getInstance().addPhoto("foto", "HOLA SOY UNa fto", "pathconcomment");

		Album a = PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", "pathconcomment");

		PhotoAppController.getInstance().addPhotoToAlbum("ola", "asd", "asdasd", a.getCode());

		PhotoAppController.getInstance().deletePhoto(p.getCode());

		PhotoAppController.getInstance().deleteAlbum(a.getCode());

		// List<Integer> photos = PhotoAppController.getInstance().getPhotos();

>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
		List<Photo> top = PhotoAppController.getInstance().getTopPhotosByLikes();
		// PhotoAppController.getInstance().comment(post, "hola soy juan comentando");
		PhotoAppController.getInstance().search("hermana ines");
		PhotoAppController.getInstance().unLogin();
		PhotoAppController.getInstance().login("adri@gmail", "password");
<<<<<<< HEAD
		//PhotoAppController.getInstance().getFeed();	
		new SearchFrame("adri");
		
=======
		// PhotoAppController.getInstance().getFeed();

>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
	}
}
