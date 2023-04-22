package umu.tds.maven.apps.PhotoApp.pruebas;

import java.util.Date;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
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
		deleteAllDatabase();//System.exit(0);
		PhotoAppController.getInstance().registerUser("Adrian del Rio", "adri@gmail", "adriandelrio", "password",
				new Date(), ViewConstants.RUTA_FOTOS_USER+"animal.jpeg", "myBio");
		/*
		 * PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan@gmail",
		 * "juanhdz", "password", new Date(), "PhotoJuan", "BioJuan");
		 * PhotoAppController.getInstance().registerUser("Juan Hernandez",
		 * "juan2@gmail", "juanhdz2", "password", new Date(), "PhotoJuan", "BioJuan");
		 */
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan3@gmail", "juanhdz3", "password",
				new Date(), ViewConstants.RUTA_FOTOS_USER+"animal2.jpeg", "BioJuan");
		PhotoAppController.getInstance().login("adri@gmail", "password");
		PhotoAppController.getInstance().follow("juanhdz3");
		PhotoAppController.getInstance().unFollow("juanhdz3");
		PhotoAppController.getInstance().follow("juanhdz3");
		//adrian añade fotos
		Photo p1 = PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		Photo p2 = PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		Photo p3 = PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		Photo p4 = PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje9", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje1.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje2.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje3.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje5.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje6.jpeg");
		
		
		PhotoAppController.getInstance().unLogin();
		PhotoAppController.getInstance().login("juan3@gmail", "password");//se logea juan
		PhotoAppController.getInstance().generateExcel("C:\\Users\\adria\\OneDrive\\Escritorio");
		PhotoAppController.getInstance().addPhoto("paisaje2", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje3", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje4", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje1", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje2", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje8", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addPhoto("paisaje7", "foto", ViewConstants.RUTA_FOTOS_USER+"paisaje9.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje1.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje2.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje3.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje5.jpeg");
		PhotoAppController.getInstance().addAlbum("ALBUM", "HOLA SOY UN ALBUM", ViewConstants.RUTA_FOTOS_USER+"paisaje6.jpeg");
		
		
		PhotoAppController.getInstance().addPhoto("paisaje2", "astonishing",ViewConstants.RUTA_FOTOS_USER+"paisaje3.jpeg");

		//PhotoAppController.getInstance().deletePhoto(p.getCode());

		//PhotoAppController.getInstance().deleteAlbum(a.getCode());

		// List<Integer> photos = PhotoAppController.getInstance().getPhotos();

		//List<Photo> top = PhotoAppController.getInstance().getTopPhotosByLikes();
		// PhotoAppController.getInstance().comment(post, "hola soy juan comentando");
		//PhotoAppController.getInstance().search("hermana ines");
		PhotoAppController.getInstance().follow("adriandelrio");
		
		PhotoAppController.getInstance().like(p1.getCode());
		PhotoAppController.getInstance().like(p1.getCode());
		PhotoAppController.getInstance().like(p1.getCode());
		PhotoAppController.getInstance().like(p1.getCode());
		PhotoAppController.getInstance().like(p2.getCode());
		PhotoAppController.getInstance().like(p2.getCode());
		PhotoAppController.getInstance().like(p2.getCode());
		PhotoAppController.getInstance().like(p3.getCode());
		PhotoAppController.getInstance().like(p3.getCode());
		PhotoAppController.getInstance().like(p4.getCode());
		PhotoAppController.getInstance().unLogin();
		
		PhotoAppController.getInstance().login("adri@gmail", "password");
		List<Photo> top = PhotoAppController.getInstance().getTopPhotosByLikes();
		
		for (Photo p : top) {
			if(p.getCode()==p1.getCode()) {
				System.out.println("p1 esta en la lista");
			}
			if(p.getCode()==p2.getCode()) {
				System.out.println("p2 esta en la lista");
			}
			if(p.getCode()==p3.getCode()) {
				System.out.println("p3 esta en la lista");
			}
			if(p.getCode()==p4.getCode()) {
				System.out.println("p4 esta en la lista");
			}
		}
		// PhotoAppController.getInstance().getFeed();
		
		LoggedFrame fr=new LoggedFrame();
		fr.setVisible(true);

	}
}
