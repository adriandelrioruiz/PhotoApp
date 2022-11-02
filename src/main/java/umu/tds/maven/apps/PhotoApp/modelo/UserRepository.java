package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;


/** Usaremos esta clase para almacenar los usuarios de la base de datos al iniciar la aplicación, además
 * meteremos cada usuario nuevo que se registre aquí */

public class UserRepository {
	
	// Patrón singleton
	private static UserRepository instance;
	// TODO quitar esto?
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
			//users.stream().forEach((u) -> UserAdapterTDS.getInstance().deleteUser(u));
			
			
			// Los introducimos en nuestro mapa
			for (User user : users)
				usersByUsername.put(user.getUserName(), user);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Método para añadir un usuario al repositorio
	public void registerUser(User user) {
		usersByUsername.put(user.getUserName(), user);
	}
	
	// Método para eliminar un usuario
	public void removeUser(User user) {
		usersByUsername.remove(user.getUserName());
	}
	
	
	// Método que devuelve un usuario dado su nombre de usuario
	public User getUserByUsername(String userName) {
		return usersByUsername.get(userName);
	}
	
	public User getUserByEmail(String email) {
		User user = null;
		Collection<User> allUsers = usersByUsername.values();
		for(User u : allUsers) {
			if (u.getEmail().equals(email))
				user = u;
		}
		return user;
	}
	

	
	
	
}
