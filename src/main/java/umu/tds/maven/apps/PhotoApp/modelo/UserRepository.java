package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.HashMap;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;

/** Usaremos esta clase para almacenar los usuarios de la base de datos al iniciar la aplicación, además
 * meteremos cada usuario nuevo que se registre aquí */

public class UserRepository {
	
	// Patrón singleton
	private static UserRepository instance;
	
	private static FactoriaDAO factory;
	
	private HashMap<String,User> usersByUsername;
	
	// Devuelve la única instancia (Singleton)
	public static UserRepository getInstance() {
		if (instance == null)
			instance = new UserRepository();
		return instance;
	}
	
	// Constructor con el que extraemos todos los usuarios de la persistencia y los añadimos al repositorio
	private UserRepository() {
		usersByUsername = new HashMap<>();
		
		try {
			factory = FactoriaDAO.getInstance();
			
			// Recuperamos todos los usuarios de la base de datos
			List<User> users = factory.getUserDAO().getAllUsers();
			// Los introducimos en nuestro mapa
			for (User user : users)
				usersByUsername.put(user.getUserName(), user);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Método para añadir un usuario al repositorio
	public void addUser(User user) {
		usersByUsername.put(user.getUserName(), user);
	}
	
	// Método para eliminar un usuario
	public void removeUser(User user) {
		usersByUsername.remove(user.getUserName());
	}
	
	// Método para comprobar si un nombre de usuario ya está cogido
	public boolean userExists(User user) {
		return usersByUsername.containsKey(user.getUserName());
	}
	
	// Método que devuelve un usuario dado su nombre de usuario
	public User getUser(String userName) {
		return usersByUsername.get(userName);
	}
	
	
	
}
