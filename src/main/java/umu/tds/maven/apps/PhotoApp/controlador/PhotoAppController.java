package umu.tds.maven.apps.PhotoApp.controlador;

import java.util.Date;
import java.util.LinkedList;

import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.modelo.Post;
import umu.tds.maven.apps.PhotoApp.modelo.UserRepository;
import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.IUserAdapterDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;

public class PhotoAppController {

	private static final boolean DEFAULT_PREMIUM = false;

	// única instancia (singleton)
	private static PhotoAppController onlyInstance;

	// Usuario que se inicializará al hacer login
	User user;

	// Repositorios (catálogos)
	private UserRepository userRepository;

	// Adaptadores
	private IUserAdapterDAO userAdapter;

	public static PhotoAppController getInstance() {
		if (onlyInstance == null)
			onlyInstance = new PhotoAppController();
		return onlyInstance;
	}

	// Constructor con el que se inicializan los repositorios
	public PhotoAppController() {
		initializeAdapters();
		initializeRepositories();
	}

	// Método para inicializar los repositorios (catálogos)
	private void initializeRepositories() {
		userRepository = UserRepository.getInstance();
	}

	// Método para inicializar los adaptadores
	private void initializeAdapters() {
		FactoriaDAO factory = null;
		try {
			factory = FactoriaDAO.getInstance(FactoriaDAO.DAO_TDS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		userAdapter = factory.getUserDAO();
	}

	// Método para registrar a un usuario en la base de datos

	public boolean registerUser(String fullName, String email, String userName, String password, Date date,
			String profilePic, String bio) {

		// Miramos si el userName ya está cogido
		if (userRepository.getUserByUsername(userName) != null) {
			// TODO quitar el print
			System.out.println("Fallo al Registrarse: El userName " + userName + " ya está cogido");
			return false;
		}

		// Miramos si el email ya está cogido
		if (userRepository.getUserByEmail(email) != null) {
			// TODO quitar el print
			System.out.println("Fallo al Registrarse: El email " + email + " ya está cogido");
			return false;
		}

		// En otro caso, podremos registrar al usuario en la persistencia y en el
		// repositorio

		User user = new User(fullName, email, userName, password, date, DEFAULT_PREMIUM, profilePic, bio);

		userAdapter.registerUser(user);
		userRepository.registerUser(user);

		// TODO quitar el print
		System.out.println("El usuario " + userName + " se ha registrado con éxito!");
		return true;
	}

	public boolean login(String usernameOrEmail, String password) {
		// Comprobamos en el repositorio si el usuario existe por nombre de usuario o
		// email
		user = userRepository.getUserByUsername(usernameOrEmail);
		if (user == null)
			user = userRepository.getUserByEmail(usernameOrEmail);
		if (user == null) {
			// TODO quitar el print
			System.out.println("email o username incorrecto");
			return false;
		}

		// Si no es null es porque existe. Vemos si la contraseña es correcta
		if (!password.equals(user.getPassword())) {
			System.out.println("contraseña incorrecta");
			return false;
		}

		// TODO quitar el print
		System.out.println("El usuario " + usernameOrEmail + " se ha logeado con éxito");
		return true;
	}

	public void follow(String userNameFollowed) {
		// Si no se sigue ya al usuario se seguirá
		if (!user.getFollowed().stream().anyMatch((u) -> u.getUserName().equals(userNameFollowed))) {
			// Obtenemos el objeto user del usuario seguido
			User userFollowed = userRepository.getUserByUsername(userNameFollowed);
			// Añadimos seguido a nuestro usuario
			user.addFollowed(userFollowed);
			// Añadimos seguidor al usuario seguido
			userFollowed.addFollower(user);

			// Modificamos los datos en persistencia
			userAdapter.updateUser(user, UserAdapterTDS.FOLLOWED);
			userAdapter.updateUser(userFollowed, UserAdapterTDS.FOLLOWERS);

			// No hace falta modificar los datos en el repositorio porque los objetos
			// usuario son tomados del mismo, así que ya se han modificado en esta función
			
			System.out.println("Ahora sigues a " + userNameFollowed);
		}
		else
			System.out.println("Ya sigues al usuario " + userNameFollowed);

	}

	// Obtener número de seguidores
	public int getFollowers() {
		return user.getFollowers().size();
	}

	// Obtener número de seguidos
	public int getFollowed() {
		return user.getFollowed().size();
	}

	// Obtener nombre de usuario
	public String getUsername() {
		return user.getUserName();
	}

	// Obtener nombre completo
	public String getFullName() {
		return user.getFullName();
	}

	// Obtener foto de perfil
	public String getProfilePic() {
		return user.getProfilePic();
	}

}
