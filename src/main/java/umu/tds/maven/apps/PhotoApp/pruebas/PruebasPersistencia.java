package umu.tds.maven.apps.PhotoApp.pruebas;

import java.util.Date;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.PostAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;

public class PruebasPersistencia {
	
	public static void deleteAllUsers() {
		FactoriaDAO factory = null;
		try {
			factory = FactoriaDAO.getInstance();
			
			// Recuperamos todos los usuarios de la base de datos
			List<User> users = factory.getUserDAO().getAllUsers();
			// Los eliminamos a todos
			users.stream().forEach((u) -> UserAdapterTDS.getInstance().deleteUser(u));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		

		//PruebasPersistencia.deleteAllUsers();
		PhotoAppController.getInstance().registerUser("Adrian del Rio", "adri@gmail", "adriandelrio", "password", new Date(), "myPhoto", "myBio");
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan@gmail", "juanhdz", "password", new Date(), "PhotoJuan", "BioJuan");
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan2@gmail", "juanhdz2", "password", new Date(), "PhotoJuan", "BioJuan");
		PhotoAppController.getInstance().registerUser("Juan Hernandez", "juan3@gmail", "juanhdz3", "password", new Date(), "PhotoJuan", "BioJuan");
		PhotoAppController.getInstance().login("adri@gmail", "password");
		PhotoAppController.getInstance().follow("juanhdz");
		PhotoAppController.getInstance().follow("juanhdz2");
		PhotoAppController.getInstance().follow("juanhdz3");
		
		
	}
}

