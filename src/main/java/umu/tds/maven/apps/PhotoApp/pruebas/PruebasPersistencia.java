package umu.tds.maven.apps.PhotoApp.pruebas;

import java.util.Date;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;
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
		PhotoAppController.getInstance().registerUser("asd", "awe", "awawd", "adaw", new Date(), "asd", "Awdaw");
		PhotoAppController.getInstance().registerUser("asd", "awe", "as", "adaw", new Date(), "asd", "Awdaw");
		
		PhotoAppController.getInstance().login("awe", "adaw");
		PhotoAppController.getInstance().login("awe", "adaddaa");
		
	}
}

