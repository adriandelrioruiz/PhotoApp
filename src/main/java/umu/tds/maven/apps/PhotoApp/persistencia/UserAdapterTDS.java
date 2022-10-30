package umu.tds.maven.apps.PhotoApp.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class UserAdapterTDS implements IUserAdapterDAO{
	
	private static final String FULLNAME = "fullName";
	private static final String USERNAME = "userName";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String DATE = "date";
	private static final String PREMIUM = "premium";
	private static final String PROFILEPIC = "profilePic";
	private static final String BIO = "bio";

	public static ServicioPersistencia servPersistencia;
	private SimpleDateFormat dateFormat;

	public static UserAdapterTDS instance;

	public static UserAdapterTDS getInstance() {
		if (instance == null)
			instance = new UserAdapterTDS();
		return instance;
	}

	public UserAdapterTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public void registerUser(User user) {
		Entidad eUser = null;
		try {
			eUser = servPersistencia.recuperarEntidad(user.getCode());
		} catch (NullPointerException e) {}
		if (eUser != null)	return;


		// Crear entidad venta
		eUser = new Entidad();

		eUser.setNombre("user");
		eUser.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(FULLNAME, user.getFullName()),
						new Propiedad(USERNAME, user.getFullName()),
						new Propiedad(EMAIL, user.getEmail()),
						new Propiedad(PASSWORD, user.getPassword()),
						new Propiedad(DATE, dateFormat.format(user.getDateOfBirth())),
						new Propiedad(PREMIUM, String.valueOf(user.isPremium())),
						new Propiedad(PROFILEPIC, user.getProfilePic()),
						new Propiedad(BIO, user.getBio()))));
		// registrar entidad user
		eUser = servPersistencia.registrarEntidad(eUser);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		user.setCode(eUser.getId());
	}

	public User getUser(int code) {
	
		// si no, la recupera de la base de datos
		Entidad eUser;
		
		String fullName;
		String userName;
		String email;
		String password;
		Date date;
		boolean isPremium;
		String profilePic;
		String bio;

		// recuperar entidad
		eUser = servPersistencia.recuperarEntidad(code);

		// recuperar propiedades que no son objetos
		fullName = servPersistencia.recuperarPropiedadEntidad(eUser, FULLNAME);
		userName = servPersistencia.recuperarPropiedadEntidad(eUser, USERNAME);
		email = servPersistencia.recuperarPropiedadEntidad(eUser, EMAIL);
		password = servPersistencia.recuperarPropiedadEntidad(eUser, PASSWORD);
		date = null;
		try {
			date = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUser, DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		isPremium = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eUser, PREMIUM));
		profilePic = servPersistencia.recuperarPropiedadEntidad(eUser, PROFILEPIC);
		bio = servPersistencia.recuperarPropiedadEntidad(eUser, BIO);
		

		User user = new User(fullName, userName, email, password, date, isPremium, profilePic, bio, null, null);
		user.setCode(code);


		return user;
	}

	public void deleteUser(User user) {
		
		Entidad eUser = servPersistencia.recuperarEntidad(user.getCode());
		servPersistencia.borrarEntidad(eUser);
		
		
	}


}
