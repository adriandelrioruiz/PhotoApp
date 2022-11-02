package umu.tds.maven.apps.PhotoApp.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.Post;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class UserAdapterTDS extends AdapterTDS implements IUserAdapterDAO {

	public static final String USER = "user";
	public static final String FULLNAME = "fullName";
	public static final String USERNAME = "userName";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String DATE = "date";
	public static final String PREMIUM = "premium";
	public static final String PROFILEPIC = "profilePic";
	public static final String BIO = "bio";
	public static final String POSTS = "posts";
	public static final String FOLLOWERS = "followers";
	public static final String FOLLOWED = "followed";

	private static UserAdapterTDS instance;
	
	private SimpleDateFormat dateFormat;

	public static UserAdapterTDS getInstance() {
		if (instance == null)
			instance = new UserAdapterTDS();
		return instance;
	}

	private UserAdapterTDS() {
		super();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	protected Entidad objectToEntity(DomainObject o) {
		User user =(User) o;
		Entidad eUser = new Entidad();

		eUser.setNombre(USER);
		eUser.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(FULLNAME, user.getFullName()), new Propiedad(USERNAME, user.getUserName()),
						new Propiedad(EMAIL, user.getEmail()), new Propiedad(PASSWORD, user.getPassword()),
						new Propiedad(DATE, dateFormat.format(user.getDateOfBirth())),
						new Propiedad(PREMIUM, String.valueOf(user.isPremium())),
						new Propiedad(PROFILEPIC, user.getProfilePic()), new Propiedad(BIO, user.getBio()), 
						new Propiedad(POSTS, PostAdapterTDS.getCodesFromAllPosts(user.getPosts())),
						new Propiedad(FOLLOWERS, getCodesFromAllUsers(user.getFollowers())),
						new Propiedad(FOLLOWED, getCodesFromAllUsers(user.getFollowed())))));

		return eUser;
	}

	@Override
	protected DomainObject entityToObject(Entidad en) {
		
		// Si el objeto está en el Pool, lo devolvemos directamente
		if (PoolDAO.getInstance().contains(en.getId()))
			return (DomainObject) PoolDAO.getInstance().getObject(en.getId());

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
		fullName = servPersistencia.recuperarPropiedadEntidad(en, FULLNAME);
		userName = servPersistencia.recuperarPropiedadEntidad(en, USERNAME);
		email = servPersistencia.recuperarPropiedadEntidad(en, EMAIL);
		password = servPersistencia.recuperarPropiedadEntidad(en, PASSWORD);
		date = null;
		try {
			date = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(en, DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		isPremium = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(en, PREMIUM));
		profilePic = servPersistencia.recuperarPropiedadEntidad(en, PROFILEPIC);
		bio = servPersistencia.recuperarPropiedadEntidad(en, BIO);
		
		// Creamos el objeto User a partir de los atributos recuperados de la persistencia
		User user = new User(fullName, email, userName, password, date, isPremium, profilePic, bio);
		// Le damos el código que le ha asignado la base de datos a nuestro usuario;
		user.setCode(en.getId());
		
		PoolDAO.getInstance().addObject(en.getId(), user);
		
		// Recuperamos los atributos que son listas 
		List<Post> posts = PostAdapterTDS.getAllPostsFromCodes(servPersistencia.recuperarPropiedadEntidad(en, POSTS));
		List<User> followers = getAllUsersFromCodes(servPersistencia.recuperarPropiedadEntidad(en, FOLLOWERS));
		List<User> followed = getAllUsersFromCodes(servPersistencia.recuperarPropiedadEntidad(en, FOLLOWED));
		
		user.setPosts(posts);
		user.setFollowers(followers);
		user.setFollowed(followed);

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
		eUser = objectToEntity(user);
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
		User user = (User) entityToObject(eUser);

		return user;
	}

	// Para eliminar de la base de datos y el repositorio a un usuario concreto
	public void deleteUser(User user) {

		Entidad eUser = servPersistencia.recuperarEntidad(user.getCode());
		servPersistencia.borrarEntidad(eUser);

	}
	
	// TODO hacerlo así o un poco más eficiente
	// Para modificar un usuario
	public void updateUser(User user, String attribute) {
		Entidad eUser = servPersistencia.recuperarEntidad(user.getCode());
		List<Propiedad> properties = new LinkedList<>();
		for (Propiedad p : eUser.getPropiedades()) {
			if (p.getNombre().equals(attribute))
				properties.add(p);
		}
		//List<Propiedad> properties = eUser.getPropiedades().stream().filter((p) -> p.getNombre().equals(attribute)).toList();
		// si la lista es vacía es que no hay nada con esa propiedad
		if (properties.isEmpty())
			return;
		
		// En otro caso, modificamos la propiedad
		Propiedad property = properties.get(0);
		
		switch (attribute) {
		
		case PROFILEPIC:
			property.setValor(user.getProfilePic());
			break;
		case BIO:
			property.setValor(user.getBio());
			break;
		case PASSWORD:
			property.setValor(user.getPassword());
			break;
		case POSTS:
			property.setValor(PostAdapterTDS.getCodesFromAllPosts(user.getPosts()));
			break;
		case FOLLOWERS:
			property.setValor(getCodesFromAllUsers(user.getFollowers()));
			break;
		case FOLLOWED:
			property.setValor(getCodesFromAllUsers(user.getFollowed()));
			break;
			
		}
		
		servPersistencia.modificarPropiedad(property);
		
		
	}


	public List<User> getAllUsers() {
		List<Entidad> entities = servPersistencia.recuperarEntidades(USER);

		List<User> users = new LinkedList<User>();
		for (Entidad eUser : entities) {
			users.add(getUser(eUser.getId()));
		}

		return users;
	}
	
	// Usamos esta función para obtener users a través de un string con varios códigos
	static List<User> getAllUsersFromCodes(String codes) {
		List<User> usersList = new LinkedList<User>();
		if (codes != null) {
			StringTokenizer strTok = new StringTokenizer(codes, " ");
			UserAdapterTDS adapter = getInstance();
			while (strTok.hasMoreTokens()) {
				usersList.add(adapter.getUser(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return usersList;
	}
	
	static String getCodesFromAllUsers(List<User> users) {
		String codes = "";
		for (User user : users) {
			codes += user.getCode() + " ";
		}
		return codes.trim();
	}
	

	

}
