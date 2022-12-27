package umu.tds.maven.apps.PhotoApp.pruebas;

import java.util.Date;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.PhotoAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;

public class PruebasPersistencia {
	
	
	public static void main(String[] args) {
		
		UserAdapterTDS.getInstance().deleteAllUsers();
		PhotoAdapterTDS.getInstance().deleteAllPosts();
		//PruebasPersistencia.deleteAllUsers();
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
		PhotoAppController.getInstance().addPhoto("fotoConComent1", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().addPhoto("fotoConComent2", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().addPhoto("fotoConComent3", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().addPhoto("fotoConComent4", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		PhotoAppController.getInstance().addPhoto("fotoConComent5", "hola me llamo #juan y mi #hermana se llama #ines", "pathconcomment");
		//PhotoAppController.getInstance().comment(post, "hola soy juan comentando");
		PhotoAppController.getInstance().search("hermana ines");
		PhotoAppController.getInstance().unLogin();
		PhotoAppController.getInstance().login("adri@gmail", "password");
		PhotoAppController.getInstance().getFeed();

		
		
		
		
	}
}

