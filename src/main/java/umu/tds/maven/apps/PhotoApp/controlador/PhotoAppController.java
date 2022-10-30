package umu.tds.maven.apps.PhotoApp.controlador;

import java.util.Date;

import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.IUserAdapterDAO;

public class PhotoAppController {
	
	private static final boolean DEFAULT_PREMIUM = false;
	
	private static PhotoAppController onlyInstance;
	
	private IUserAdapterDAO userAdapter;
	
	public static PhotoAppController getInstance() {
		if (onlyInstance == null)
			onlyInstance = new PhotoAppController();
		return onlyInstance;
	}
	
	public PhotoAppController() {
		initializeAdapters();
	}
	
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
	
	public void registerUser(String fullName, String userName, String email, String password, Date date, String profilePic, String bio) {
		User user = new User(fullName, userName, email, password, date, DEFAULT_PREMIUM, profilePic, bio, null, null);
		userAdapter.registerUser(user);
	}
	
	public boolean login(String usernameOrEmail, String password) {
		return false;
	}
	
	
	
}
