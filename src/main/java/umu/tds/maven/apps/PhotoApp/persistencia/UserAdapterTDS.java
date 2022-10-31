package umu.tds.maven.apps.PhotoApp.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class UserAdapterTDS implements IUserAdapterDAO {

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

	private Entidad userToEntity(User user) {
		Entidad eUser = new Entidad();

		eUser.setNombre("user");
		eUser.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(FULLNAME, user.getFullName()), new Propiedad(USERNAME, user.getFullName()),
						new Propiedad(EMAIL, user.getEmail()), new Propiedad(PASSWORD, user.getPassword()),
						new Propiedad(DATE, dateFormat.format(user.getDateOfBirth())),
						new Propiedad(PREMIUM, String.valueOf(user.isPremium())),
						new Propiedad(PROFILEPIC, user.getProfilePic()), new Propiedad(BIO, user.getBio()))));

		return eUser;
	}

	private User entityToUser(Entidad eUser) {

		// Estos serán los atributos del User que queremos recuperar
		String fullName;
		String userName;
		String email;
		String password;
		Date date;
		boolean isPremium;
		String profilePic;
		String bio;

		// Recuperamos los atributos de User de la persistencia
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

		// Creamos el objeto User a partir de los atributos recuperados de la
		// persistencia
		User user = new User(fullName, userName, email, password, date, isPremium, profilePic, bio, null, null);
		// Le damos el código que le ha asignado la base de datos a nuestro usuario;
		user.setCode(eUser.getId());

		return user;

	}

	public void registerUser(User user) {
		Entidad eUser = null;
		try {
			eUser = servPersistencia.recuperarEntidad(user.getCode());
		} catch (NullPointerException e) {
		}
		if (eUser != null)
			return;

		// Crear entidad venta
		eUser = userToEntity(user);
		// registrar entidad user
		eUser = servPersistencia.registrarEntidad(eUser);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		user.setCode(eUser.getId());
	}

	public User getUser(int code) {

		Entidad eUser;

		// Recuperamos la entidad
		eUser = servPersistencia.recuperarEntidad(code);
		// Convertimos la entidad en un objeto usuario
		User user = entityToUser(eUser);

		return user;
	}

	// Para eliminar de la base de datos y el repositorio a un usuario concreto
	public void deleteUser(User user) {

		Entidad eUser = servPersistencia.recuperarEntidad(user.getCode());
		servPersistencia.borrarEntidad(eUser);

	}

	// A partir del nombre de usuario o email, se busca al usuario en el repositorio
	public User userExists(String usernameOrEmail) {

		List<Entidad> entities = servPersistencia.recuperarEntidades(USERNAME);
		List<User> users = new ArrayList<>();
		// Intentamos buscar el usuario por el campo username

		return null;
	}

	public List<User> getAllUsers() {
		List<Entidad> entities = servPersistencia.recuperarEntidades(USERNAME);

		List<User> users = new LinkedList<User>();
		for (Entidad eUser : entities) {
			users.add(getUser(eUser.getId()));
		}

		return users;
	}

}
