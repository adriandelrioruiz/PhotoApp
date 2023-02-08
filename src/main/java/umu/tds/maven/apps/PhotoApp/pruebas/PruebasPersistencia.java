package umu.tds.maven.apps.PhotoApp.pruebas;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.persistencia.CommentAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.NotificationAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.PhotoAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;
import umu.tds.maven.apps.PhotoApp.vista.Menu;
import umu.tds.maven.apps.PhotoApp.vista.ScrollScreen;
import umu.tds.maven.apps.PhotoApp.vista.*;

public class PruebasPersistencia {
	
	public static void deleteAllDatabase() {
		UserAdapterTDS.getInstance().deleteAll();
		PhotoAdapterTDS.getInstance().deleteAll();
		NotificationAdapterTDS.getInstance().deleteAll();
		CommentAdapterTDS.getInstance().deleteAll();
	}
	
	public static void main(String[] args) {
		JFrame ventana=new JFrame();
		
		ventana.setSize(700,600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image image;
		image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\elcrio\\git\\PhotoApp\\img\\icon_lupa.png");

		// Escalar la imagen a un tamaño específico
		image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		// Crear un ImageIcon a partir de la imagen escalada
		 ImageIcon imagei = new ImageIcon(image);
		 JButton b=new JButton(imagei);
		 ventana.getContentPane().add(b);
		ventana.setVisible(true);
	/*	VentanaPrincipal ini= new VentanaPrincipal();
		ini.setVisible(true);*/
		deleteAllDatabase();
		//System.exit(0);
		PhotoAppController.getInstance().registerUser("Adrian del Rio", "adri@gmail", "adriandelrio", "password", new Date(), "myPhoto", "myBio");
		/*PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan@gmail", "juanhdz", "password", new Date(), "PhotoJuan", "BioJuan");
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan2@gmail", "juanhdz2", "password", new Date(), "PhotoJuan", "BioJuan");*/
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan3@gmail", "juanhdz3", "password", new Date(), "PhotoJuan", "BioJuan");
		PhotoAppController.getInstance().login("adri@gmail", "password");
		PhotoAppController.getInstance().follow("juanhdz3");
		PhotoAppController.getInstance().unFollow("juanhdz3");
		PhotoAppController.getInstance().follow("juanhdz3");
		PhotoAppController.getInstance().unLogin();
		PhotoAppController.getInstance().login("juan3@gmail", "password");
		PhotoAppController.getInstance().generateExcel("C:\\Users\\adria\\OneDrive\\Escritorio");
		Photo photo = PhotoAppController.getInstance().addPhoto("fotoConComent1", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().addPhoto("fotoConComent2", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().addPhoto("fotoConComent3", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().addPhoto("fotoConComent4", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().addPhoto("fotoConComent5", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().deletePost(photo);
		List<Photo> top = PhotoAppController.getInstance().getTopPhotosByLikes();
		//PhotoAppController.getInstance().comment(post, "hola soy juan comentando");
		PhotoAppController.getInstance().search("hermana ines");
		PhotoAppController.getInstance().unLogin();
		PhotoAppController.getInstance().login("adri@gmail", "password");
		PhotoAppController.getInstance().getFeed();	
		//VentanaPrincipal ini=new VentanaPrincipal();
		
		
	}
}

