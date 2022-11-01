package umu.tds.maven.apps.PhotoApp.controlador;

import java.util.Date;

import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.modelo.UserRepository;
import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.IUserAdapterDAO;

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
	
	public boolean registerUser(String fullName, String email, String userName, String password, Date date, String profilePic, String bio) {
		
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
		
		// En otro caso, podremos registrar al usuario en la persistencia y en el repositorio
		
		User user = new User(fullName, email, userName, password, date, DEFAULT_PREMIUM, profilePic, bio, null, null);
		
		userAdapter.registerUser(user);
		userRepository.registerUser(user);
		
		// TODO quitar el print
		System.out.println("El usuario " + userName + " se ha registrado con éxito!");
		return true;
	}
	
	public boolean login(String usernameOrEmail, String password) {
		// Comprobamos en el repositorio si el usuario existe por nombre de usuario o email
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
	
	
	
}
