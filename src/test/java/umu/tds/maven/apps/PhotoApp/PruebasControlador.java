package umu.tds.maven.apps.PhotoApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import umu.tds.maven.apps.PhotoApp.controlador.Codes;
import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Album;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.persistencia.AlbumAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.CommentAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.NotificationAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.PhotoAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PruebasControlador {

	static PhotoAppController controller;
	
	static Photo p;


	public static void deleteAllDatabase() {
		UserAdapterTDS.getInstance().deleteAll();
		PhotoAdapterTDS.getInstance().deleteAll();
		NotificationAdapterTDS.getInstance().deleteAll();
		AlbumAdapterTDS.getInstance().deleteAll();
		CommentAdapterTDS.getInstance().deleteAll();
	}

	@Test
	public void prueba0inicializar() {
		deleteAllDatabase();
		controller = PhotoAppController.getInstance();
	}

	@Test
	public void prueba1Registro() {

		System.out.println("1");
		ArrayList<Codes> codesEsperados = new ArrayList<>();
		ArrayList<Codes> codesReales = new ArrayList<>();

		codesEsperados.add(Codes.OK);
		codesEsperados.add(Codes.INVALID_USERNAME);
		codesEsperados.add(Codes.INVALID_EMAIL);

		codesReales.add(controller.registerUser("a", "email1", "username", "a", new Date(), "a", "a"));
		codesReales.add(controller.registerUser("a", "email2", "username", "a", new Date(), "a", "a"));
		codesReales.add(controller.registerUser("a", "email1", "username2", "a", new Date(), "a", "a"));

		assertEquals("resultado pruebas registro", codesEsperados, codesReales);
	}

	@Test
	public void prueba2Login() {
		ArrayList<Codes> codesEsperados = new ArrayList<>();
		ArrayList<Codes> codesReales = new ArrayList<>();

		codesEsperados.add(Codes.INCORRECT_EMAIL_USERNAME);
		codesEsperados.add(Codes.INCORRECT_PASSWORD);
		codesEsperados.add(Codes.OK);

		codesReales.add(controller.login("invalido", "a"));
		codesReales.add(controller.login("username", "invalido"));
		codesReales.add(controller.login("username", "a"));

		assertEquals("resultado pruebas login", codesEsperados, codesReales);
	}

	@Test
	public void prueba3AddPhoto() {

		p = controller.addPhoto("titulo", "descripcion", "path");
		String path = controller.getPath(p.getCode());

		assertEquals("resultado pruebas add photo", path, "path");

	}

	@Test
	public void prueba4AddPhotoTooManyHashtags() {

		Photo p = controller.addPhoto("titulo",
				"#1 #2 #2 #1 #2 #2 #1 #2 #2 #1 #2 #2 #1 #2 #2 #1 #2 #2 #1 #2 #2 #1 #2 #2 #1 #2 #2 #1 #2 #2", "path");
		
		assertNull(p);
	}
	
	@Test
	public void prueba5AddPhotoLongHashtag() {
		Photo p = controller.addPhoto("titulo", "#aoierhiaerohhhhiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii", "path");
		
		assertNull(p);
	}
	
	@Test
	public void prueba6AddAlbum() {
		Album a = controller.addAlbum("title", "descripcion", "path");
		
		assertEquals("descripcion", controller.getPostDescription(a.getCode()));
	}
	
	@Test
	public void prueba7LikePhoto() {
		controller.like(p.getCode());
		
		assertEquals(1, controller.getLikes(p.getCode()));
		
	}
	
	@Test
	public void prueba8FollowUser() {
		controller.unLogin();
		controller.registerUser("a", "email3", "username3", "a", new Date(), "a", "a");
		controller.login("username", "a");
		controller.follow("username3");
		assertEquals(1, controller.getFollowed(p.getUser().getCode()));
	}
	
	@Test
	public void prueba9UnFollowUser() {
		controller.unFollow("username3");
		assertEquals(0, controller.getFollowed(p.getUser().getCode()));
	}
	
	

}
