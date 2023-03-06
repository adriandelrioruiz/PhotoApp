package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;

/**
 * Usaremos esta clase para almacenar los usuarios de la base de datos al
 * iniciar la aplicación, además meteremos cada usuario nuevo que se registre
 * aquí
 */

public class UserRepository {

	// Patrón singleton
	private static UserRepository instance;
	private static FactoriaDAO factory;

	private HashMap<String, User> usersByUsername;
	private HashMap<Integer, User> usersById;

	// Devuelve la única instancia (Singleton)
	public static UserRepository getInstance() {
		if (instance == null)
			instance = new UserRepository();
		return instance;
	}

	// Constructor con el que extraemos todos los usuarios de la persistencia y los
	// añadimos al repositorio
	private UserRepository() {
		usersByUsername = new HashMap<>();
		usersById = new HashMap<>();

		try {
			factory = FactoriaDAO.getInstance();

			// Recuperamos todos los usuarios de la base de datos
			List<User> users = factory.getUserDAO().getAllUsers();
			// Ordenamos sus posts por fecha
			users.stream().forEach((u) -> u.sortPosts());

			// Los introducimos en nuestro mapa
			for (User user : users)
				usersById.put(user.getCode(), user);
			for (User user : users)
				usersByUsername.put(user.getUserName(), user);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// Método para añadir un usuario al repositorio
	public void addUser(User user) {
		usersById.put(user.getCode(), user);
		usersByUsername.put(user.getUserName(), user);
	}

	// Método para eliminar un usuario
	public void deleteUser(User user) {
		// Si eliminamos un usuario, tenemos que eliminar todos sus posts
		user.getPhotos().stream().forEach((p -> PostRepository.getInstance().deletePhoto(p.getCode())));
		user.getAlbums().stream().forEach((a -> PostRepository.getInstance().deleteAlbum(a.getCode())));
		usersById.remove(user.getCode());
	}

	// Método que devuelve un usuario dado su nombre de usuario
	public User getUserByUsername(String userName) {
		return usersByUsername.get(userName);
	}

	public User getUserByEmail(String email) {
		User user = null;
		Collection<User> allUsers = usersByUsername.values();
		for (User u : allUsers) {
			if (u.getEmail().equals(email))
				user = u;
		}
		return user;
	}

	// Método para devolver un usuario a partir de su id
	public User getUser(int id) {
		return usersById.get(id);
	}

	// Método que devuelve todos los usuarios cuyo username contiene una cadena
	// dada
	public List<User> getUsersByUserNameContaining(String userNameSubset) {
		return usersByUsername.keySet().stream().filter((u) -> u.contains(userNameSubset))
				.map((u) -> usersByUsername.get(u)).toList();
	}

	// Método que devuelve todos los usuarios cuyo nombre contiene una cadena dada
	public List<User> getUsersByNameStartingWith(String nameSubSet) {
		return usersByUsername.values().stream().filter((u) -> u.getFullName().contains(nameSubSet)).toList();
	}

	// Método que devuelve todos los usuarios cuyo email contiene una cadena dada
	public List<User> getUsersByEmailContaining(String emailSubset) {
		return usersByUsername.values().stream().filter((u) -> u.getEmail().contains(emailSubset)).toList();
	}

}
