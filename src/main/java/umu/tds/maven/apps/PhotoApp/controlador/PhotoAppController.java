package umu.tds.maven.apps.PhotoApp.controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import umu.tds.maven.apps.PhotoApp.modelo.Album;
import umu.tds.maven.apps.PhotoApp.modelo.Comment;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.Notification;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.modelo.Post;
import umu.tds.maven.apps.PhotoApp.modelo.PostRepository;
import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.modelo.UserRepository;
import umu.tds.maven.apps.PhotoApp.persistencia.AlbumAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.IAlbumAdapterDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.ICommentAdapterDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.INotificationAdapterDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.IPhotoAdapterDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.IUserAdapterDAO;
import umu.tds.maven.apps.PhotoApp.persistencia.PhotoAdapterTDS;
import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;

public class PhotoAppController {

	// Premium
	private static final boolean DEFAULT_PREMIUM = false;
	private static final int PREMIUM_PRICE = 10;
	private static final double LIKES_DISCOUNT_FACTOR = 0.001;
	private static final int YOUNG_AGE_DISCOUNT = 18;
	private static final int OLD_AGE_DISCOUNT = 65;
	private static final double AGE_DISCOUNT = 0.25;
	private static final int NUMBER_OF_TOP_PHOTOS = 10;
	
	// única instancia (singleton)
	private static PhotoAppController onlyInstance;

	// Usuario que se inicializará al hacer login
	User user;

	// Repositorios (catálogos)
	private UserRepository userRepository;
	private PostRepository postRepository;

	// Adaptadores
	private IUserAdapterDAO userAdapter;
	private IPhotoAdapterDAO photoAdapter;
	private ICommentAdapterDAO commentAdapter;
	private INotificationAdapterDAO notificationAdapter;
	private IAlbumAdapterDAO albumAdapter;

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
		postRepository = PostRepository.getInstance();
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
		photoAdapter = factory.getPhotoDAO();
		albumAdapter = factory.getAlbumDAO();
		commentAdapter = factory.getCommentDAO();
		notificationAdapter = factory.getNotificationDAO();
	}

	// Método para registrar a un usuario en la base de datos

	public Codes registerUser(String fullName, String email, String userName, String password, Date date,
			String profilePic, String bio) {

		// Miramos si el userName ya está cogido
		if (userRepository.getUserByUsername(userName) != null) {
			// TODO quitar el print
			System.out.println("Fallo al Registrarse: El userName " + userName + " ya está cogido");
			return Codes.INVALID_USERNAME;
		}

		// Miramos si el email ya está cogido
		if (userRepository.getUserByEmail(email) != null) {
			// TODO quitar el print
			System.out.println("Fallo al Registrarse: El email " + email + " ya está cogido");
			return Codes.INVALID_EMAIL;
		}

		// En otro caso, podremos registrar al usuario en la persistencia y en el
		// repositorio

		User user = new User(fullName, email, userName, password, date, DEFAULT_PREMIUM, profilePic, bio);

		userAdapter.addUser(user);
		userRepository.addUser(user);

		// TODO quitar el print
		System.out.println("El usuario " + userName + " se ha registrado con éxito!");
		return Codes.OK;
	}

	public Codes login(String usernameOrEmail, String password) {
		if (user != null)
			return Codes.ALREADY_LOGGED;
		// Comprobamos en el repositorio si el usuario existe por nombre de usuario o
		// email
		user = userRepository.getUserByUsername(usernameOrEmail);
		if (user == null)
			user = userRepository.getUserByEmail(usernameOrEmail);
		if (user == null) {
			// TODO quitar el print
			System.out.println("email o username incorrecto");
			return Codes.INCORRECT_EMAIL_USERNAME;
		}

		// Si no es null es porque existe. Vemos si la contraseña es correcta
		if (!password.equals(user.getPassword())) {
			System.out.println("contraseña incorrecta");
			return Codes.INCORRECT_PASSWORD;
		}

		// TODO quitar el print
		System.out.println("El usuario " + usernameOrEmail + " se ha logeado con éxito");
		return Codes.OK;
	}
	
	// Método para unLogearse TODO ver si lo quito
	public void unLogin() {
		user = null;
	}
	

	public void follow(String userNameFollowed) {
		if (user == null)
			return;
		// Si no se sigue ya al usuario se seguirá
		if (!user.getFollowed().stream().anyMatch((u) -> u.getUserName().equals(userNameFollowed))) {
			// Obtenemos el objeto user del usuario seguido
			User userFollowed = userRepository.getUserByUsername(userNameFollowed);
			if (userFollowed == null) {
				System.out.println("El usuario con el username " + userNameFollowed + " no existe");
				return;
			}
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
	
	public void unFollow(String userNameUnfollowed) {
		if (user == null)
			return;
		// Si se sigue ya al usuario se dejará de seguir
		if (user.getFollowed().stream().anyMatch((u) -> u.getUserName().equals(userNameUnfollowed))) {
			// Obtenemos el objeto user del usuario seguido
			User userUnfollowed = userRepository.getUserByUsername(userNameUnfollowed);
			// Añadimos seguido a nuestro usuario
			user.removeFollowed(userUnfollowed);
			// Añadimos seguidor al usuario seguido
			userUnfollowed.removeFollower(user);

			// Modificamos los datos en persistencia
			userAdapter.updateUser(user, UserAdapterTDS.FOLLOWED);
			userAdapter.updateUser(userUnfollowed, UserAdapterTDS.FOLLOWERS);

			// No hace falta modificar los datos en el repositorio porque los objetos
			// usuario son tomados del mismo, así que ya se han modificado en esta función
			
			System.out.println("Ya no sigues a " + userNameUnfollowed);
		}
		else
			System.out.println("No sigues al usuario " + userNameUnfollowed);
	}
	
	// Método para añadir una foto TODO Hace falta q devuelva el post??????????
	public Photo addPhoto(String title, String description, String path) {
		if (user == null)
			return null;
		
		Date now = new Date();
		Photo photo = new Photo(title, now, description, path, user);
		Notification notification = new Notification(now, photo);
		photo.setNotification(notification);
		
		try {
			// Extraer hashtags y meter en la foto
			List<String> hashtags = getHashtagsFromDescription(description);
			// Añadimos los hashtags al objeto Post
			for(String hashtag : hashtags)
				photo.addHashtag(hashtag);
			
			photoAdapter.addPhoto(photo);
			postRepository.addPost(photo);
			
			user.addPhoto(photo);
			// añadir la foto en la persistencia del usuario
			// TODO cambiar para que no se añada una foto individual cuando pertenece a un álbum
			userAdapter.updateUser(user, UserAdapterTDS.PHOTOS);
			// Habrá que mandar una notificación a todos los seguidores
			user.getFollowers().stream().forEach((u)->notify(u,notification));
			
			
			System.out.println("El usuario " + user.getUserName() + " ha añadido el post " + title);
		}
		
		catch (InvalidHashtagException e) {
			e.showDialog();
		}
			
			return photo;
	}
	
	
	public Album addAlbum(String title, String description, List<String> paths) {
		if (user == null)
			return null;
		
		List<Photo> photos = new LinkedList<>();
		
		// Vamos añadiendo cada foto
		paths.stream().forEach((p) -> photos.add(addPhoto(title, description, p)));

		// Creamos el álbum
		Album album = new Album(title, null, description, user);
		album.setPhotos(photos);
		
		try {
			// Extraer hashtags y meter en la foto
			List<String> hashtags = getHashtagsFromDescription(description);
			// Añadimos los hashtags al objeto Post
			for(String hashtag : hashtags)
				album.addHashtag(hashtag);
			
			albumAdapter.addAlbum(album);
			postRepository.addPost(album);
			
			user.addAlbum(album);
			// añadir la foto en la persistencia del usuario
			userAdapter.updateUser(user, UserAdapterTDS.ALBUMS);
			
			System.out.println("El usuario " + user.getUserName() + " ha añadido el post " + title);
		}
		
		catch (InvalidHashtagException e) {
			e.showDialog();
		}
			
			return album;
	}	
	
	// Método para actualizar la bio
	public void changeBio(String newBio) {
		if (user == null)
			return;
		// Cambiamos nuestro objeto usuario
		user.setBio(newBio);
		// Actualizamos la persistencia
		userAdapter.updateUser(user, UserAdapterTDS.BIO);
	}
	
	public void changeProfilePic(String newProfilePicPath) {
		if (user == null)
			return;
		// Cambiamos nuestro objeto usuario
		user.setProfilePic(newProfilePicPath);
		// Actualizamos la persistencia
		userAdapter.updateUser(user, UserAdapterTDS.PROFILEPIC);
	}
	
	public void changePassword(String newPassword) {
		if (user == null)
			return;
		// Cambiamos nuestro objeto usuario
		user.setPassword(newPassword);
		// Actualizamos la persistencia
		userAdapter.updateUser(user, UserAdapterTDS.PASSWORD);
	}
	
	// Método para eliminar un post
	public void deletePost(Post post) {
		if (user == null)
			return;

		postRepository.deletePost(post);
		
		if (post instanceof Photo) {
			photoAdapter.deletePhoto(post.getCode());
			user.removePhoto((Photo)post);
			userAdapter.updateUser(user, UserAdapterTDS.PHOTOS);
		}
		else {
			albumAdapter.deleteAlbum((Album)post);
			user.removeAlbum((Album)post);
			userAdapter.updateUser(user, UserAdapterTDS.ALBUMS);
		}
		
		System.out.println("El usuario " + user.getUserName() + " ha eliminado el post " + post.getTitle());
	}
	
	
	
	public List<Post> getAllPosts() {
		if (user == null)
			return null;
		LinkedList<Post> posts = new LinkedList<>();
		posts.addAll(user.getPhotos());
		posts.addAll(user.getAlbums());
		
		return posts;
	}
	
	// Método para darle like a un post
	public void like(Post post) {
		if (user == null)
			return;
		// Modificamos los likes en el objeto post, que será una foto o un álbum
		post.like();
			
		// Cambiamos el objeto en la persistencia según sea foto o álbum
		if (post instanceof Photo)
			photoAdapter.updatePhoto((Photo)post, PhotoAdapterTDS.LIKES);
		else {
			// Cambiamos los likes del álbum
			albumAdapter.updateAlbum((Album)post, AlbumAdapterTDS.LIKES);
			// Cambiamos los likes de todas sus fotos
			((Album)post).getPhotos().stream().forEach((p) -> photoAdapter.updatePhoto(p, PhotoAdapterTDS.LIKES));
		}
			
	}
	
	// Método para quitarle el like a un post
	public void unlike(Post post) {
		if (user == null)
			return;
		// Modificamos los likes en el objeto post, que será una foto o un álbum
		post.unlike();
			
		// Cambiamos el objeto en la persistencia según sea foto o álbum
		if (post instanceof Photo)
			photoAdapter.updatePhoto((Photo)post, PhotoAdapterTDS.LIKES);
		else {
			// Cambiamos los likes del álbum
			albumAdapter.updateAlbum((Album)post, AlbumAdapterTDS.LIKES);
			// Cambiamos los likes de todas sus fotos
			((Album)post).getPhotos().stream().forEach((p) -> photoAdapter.updatePhoto(p, PhotoAdapterTDS.LIKES));
		}
	}
	
	// Método para comentar en un post
	public void comment(Post post, String commentText) {
		if (user == null)
			return;
		// Creamos el objeto comentario, que tendrá como usuario comentador a nuestro usuario
		Comment comment = new Comment(commentText, user);
		// Modificamos nuestro objeto post
		post.addComment(comment);
		// Añadimos el comentario a la persistencia
		commentAdapter.addComment(comment);
		// Modificamos el post de la persistencia según sea foto o álbum
		if (post instanceof Photo)
			photoAdapter.updatePhoto((Photo)post, PhotoAdapterTDS.COMMENTS);
		else {
			// Cambiamos los comentarios del álbum
			albumAdapter.updateAlbum((Album)post, AlbumAdapterTDS.COMMENTS);
			// Cambiamos los comentarios de todas sus fotos
			((Album)post).getPhotos().stream().forEach((p) -> photoAdapter.updatePhoto(p, PhotoAdapterTDS.COMMENTS));
		}
	}
	
	// Método para hacer una búsqueda. Devuelve una lista de objetos de dominio
	public List<DomainObject> search(String search) {
		if (user == null)
			return null;
		List<DomainObject> objetos = new LinkedList<>();
		// Primero buscamos si hay usuarios a partir del userName
		objetos.addAll(userRepository.getUsersByUserNameContaining(search));
		// Luego buscamos si 
		objetos.addAll(userRepository.getUsersByNameStartingWith(search));
		objetos.addAll(userRepository.getUsersByEmailContaining(search));
		//objetos.addAll(userRepository.getPostsByHashtagsContaining(search));
		
		return objetos;
	}
	
	
	// -------------------- FUNCIONALIDAD PREMIUM ----------------
	// TODO
	
	// Método para hacerse premium
	public void changeToPremium() {
		if (user == null)
			return;
		// Aquí es donde se realizaría el pago, pero esto queda fuera de los que se pide en la especificación
		user.setPremium(true);
	}
	
	public void changeToNotPremium() {
		if (user == null)
			return;
		user.setPremium(false);
	}
	
	private double getDiscountByLikes() {
		if (user == null)
			return -1;
		int likes = 0;
		for (Photo p : user.getPhotos())
			likes += p.getLikes();
		

		if (likes > 500)
			return 0.5 * PREMIUM_PRICE;
		
		return (1 - LIKES_DISCOUNT_FACTOR * likes) * PREMIUM_PRICE;
		
	}
	
	private double getDiscountByAge() {
		if (user == null)
			return -1;
		LocalDate date = LocalDate.of(user.getDateOfBirth().getYear(), user.getDateOfBirth().getMonth(), user.getDateOfBirth().getDay());
	    Period age = Period.between(date, LocalDate.now());
	    int yearsOfUser = age.getYears();

	    
	    if (yearsOfUser <= YOUNG_AGE_DISCOUNT || yearsOfUser >= OLD_AGE_DISCOUNT)
	    	return (1 - AGE_DISCOUNT) * PREMIUM_PRICE;
	    
	    return PREMIUM_PRICE;
 
	}
	
	public double getDiscount() {
		if (user == null)
			return -1;
		return Math.min(getDiscountByAge(), getDiscountByLikes());
	}
	
	public List<Photo> getTopPhotosByLikes() {
		if (user == null)
			return null;
		return user.getPhotos().stream().sorted(new PhotoComparatorByLikes()).limit(NUMBER_OF_TOP_PHOTOS).toList();
	}
	
	// TODO
	public void generatePDF() {
		
	}
	
	// TODO
	public boolean generateExcel(String path) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("lista-seguidores");
		
		// Contador de filas
		int i = 0;
		// Creamos una fila para cada seguidor
		for (User user : user.getFollowers()) {
			Row row = sheet.createRow(i);
			
			Cell cellName = row.createCell(0, CellType.STRING);
			Cell cellEmail = row.createCell(1, CellType.STRING);
			Cell cellBio = row.createCell(2, CellType.STRING);
			
			cellName.setCellValue(user.getUserName());
			cellEmail.setCellValue(user.getEmail());
			cellBio.setCellValue(user.getBio());
			
			i++;
		}
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(path + "\\lista-seguidores.xlsx");
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;

	}
	
	// -------------------------------------------------------------
	
	// Método para obtener los últimos 10 posts que han publicado los usuarios a los que sigues
	public List<Post> getFeed() {
		if (user == null)
			return null;
		return postRepository.getFeed(user.getFollowed());
	}

	// Obtener número de seguidores
	public int getFollowers() {
		if (user == null)
			return -1;
		return user.getFollowers().size();
	}

	// Obtener número de seguidos
	public int getFollowed() {
		if (user == null)
			return -1;
		return user.getFollowed().size();
	}

	// Obtener nombre de usuario
	public String getUsername() {
		if (user == null)
			return null;
		return user.getUserName();
	}
	// Obtener nombre de usuario
	public String getUsername(User user) {
			if (user == null)
				return null;
			return user.getUserName();
		}
	// Obtener nombre completo
	public String getFullName() {
		if (user == null)
			return null;
		return user.getFullName();
	}
	
	public boolean isFollowed(String userNameFollowed) {
		return user.getFollowed().stream().anyMatch((u) -> u.getUserName().equals(userNameFollowed));
	}
	
	// Obtener email
	public String getEmail() {
		return user.getEmail();
	}
	
	// Obtener contraseña
	public String getPassword() {
		if (user == null)
			return null;
		return user.getPassword();
	}

	// Obtener foto de perfil
	public String getProfilePic() {
		if (user == null)
			return null;
		return user.getProfilePic();
	}
	// Obtener foto de perfil
		public String getProfilePic(User user) {
			if (user == null)
				return null;
			return user.getProfilePic();
		}
	// Obtener foto de perfil
	public String getBio() {
		if (user == null)
			return null;
		return user.getBio();
	}
	
	// Ver si un usuario es premium
	public boolean isPremium() {
		if (user == null)
			return false;
		return user.isPremium();
	}
	
	// TODO borrar
	public User getLoggedUser() {
		return user;
	}
	
	
	/* Funciones privadas */
	// Función para extraer los hashtags
	List<String> getHashtagsFromDescription(String description) {
		List<String> hashtags = new LinkedList<>();
		
		StringTokenizer strTok = new StringTokenizer(description, " ");
		while (strTok.hasMoreTokens()) {
			String nxtElement = (String) strTok.nextElement();
			if (nxtElement.startsWith("#"))
				hashtags.add(nxtElement.substring(1));
		}
		
		return hashtags;
	}
	
	// Mandar una notificación a un usuario
	private void notify(User user, Notification notification) {
		if (user == null)
			return;
		// Añadimos la notificación a la persistencia
		notificationAdapter.addNotification(notification);
		// Añadimos la notificación al usuario
		user.addNotification(notification);
		// Volcamos los cambios del usuario en la persistencia del usuario
		userAdapter.updateUser(user, UserAdapterTDS.NOTIFICATIONS);
	}

}
