package umu.tds.maven.apps.PhotoApp.controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import fotos.ComponenteCargadorFotos;
import fotos.Foto;
import fotos.FotosEvent;
import fotos.IBuscadorFotos;
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
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Phrase;

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
			user = null;
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

	public boolean follow(String userNameFollowed) {
		if (user == null)
			return false;
		// Si no se sigue ya al usuario se seguirá
		if (!user.getFollowed().stream().anyMatch((u) -> u.getUserName().equals(userNameFollowed))) {
			// Obtenemos el objeto user del usuario seguido
			User userFollowed = userRepository.getUserByUsername(userNameFollowed);
			if (userFollowed == null) {
				System.out.println("El usuario con el username " + userNameFollowed + " no existe");
				return false;
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
			return true;
		} else {
			System.out.println("Ya sigues al usuario " + userNameFollowed);
			return false;
		}

	}

	public boolean unFollow(String userNameUnfollowed) {
		if (user == null)
			return false;
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
			return true;
		} else {
			System.out.println("No sigues al usuario " + userNameUnfollowed);
			return false;
		}

	}

	// Método para añadir una foto
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
			for (String hashtag : hashtags)
				photo.addHashtag(hashtag);

			photoAdapter.addPhoto(photo);
			postRepository.addPhoto(photo);

			user.addPhoto(photo);
			// añadir la foto en la persistencia del usuario
			userAdapter.updateUser(user, UserAdapterTDS.PHOTOS);
			// Habrá que mandar una notificación a todos los seguidores
			user.getFollowers().stream().forEach((u) -> notify(u, notification));

			System.out.println("El usuario " + user.getUserName() + " ha añadido el post " + title);
		}

		catch (InvalidHashtagException e) {
			e.showDialog();
			return null;
		}

		return photo;
	}

	// Método para añadir
	public Codes addPhotoToAlbum(String title, String description, String path, int albumId) {
		if (user == null)
			return null;

		Date now = new Date();
		Photo photo = new Photo(title, now, description, path, user);

		// Añadimos la foto al photoAdapter
		photoAdapter.addPhoto(photo);

		// Metemos la foto en el repositorio y nos devuelve el álbum al que pertenece
		Album album = postRepository.addPhotoToAlbum(photo, albumId);

		// Actualizamos foto y album en usuario en la persistencia
		userAdapter.updateUser(user, UserAdapterTDS.ALBUMS);

		// Si el álbum del usuario no contenía es foto, la añadimos
		user.addPhotoToAlbum(photo, albumId);

		// Miramos si se ha excedido el número de fotos, en cuyo caso el repositorio
		// devolverá null
		if (album == null) {
			// Tendremos que borrar la foto que acabamos de meter en la persistencia
			photoAdapter.deletePhoto(photo.getCode());
			return Codes.NUM_OF_PHOTOS_IN_ALBUM_EXCEEDED;
		}

		// Actualizamos el álbum den albumAdapter
		albumAdapter.updateAlbum(album, AlbumAdapterTDS.PHOTOS);

		return Codes.OK;

	}

	// Método para añadir un álbum
	public Album addAlbum(String title, String description, String path) {
		if (user == null)
			return null;

		// Creamos el álbum
		Date now = new Date();
		Album album = new Album(title, now, description, user);
		Photo photo = new Photo(title, now, description, path, user);
		album.addPhoto(photo);

		try {
			// Extraer hashtags y meter en la foto
			List<String> hashtags = getHashtagsFromDescription(description);
			// Añadimos los hashtags al objeto Post
			for (String hashtag : hashtags)
				album.addHashtag(hashtag);

			// Registramos la foto y el álbum
			photoAdapter.addPhoto(photo);
			albumAdapter.addAlbum(album);
			postRepository.addAlbum(album);

			// Añadimos el album al usuario
			user.addAlbum(album);

			// añadir la foto en la persistencia del usuario
			userAdapter.updateUser(user, UserAdapterTDS.ALBUMS);

			System.out.println("El usuario " + user.getUserName() + " ha añadido el album " + title);
		}

		catch (InvalidHashtagException e) {
			e.showDialog();
		}

		return album;
	}

	// Método para eliminar un post
	public void deletePhoto(int id) {

		Post post = postRepository.deletePhoto(id);

		if (post instanceof Photo) {
			photoAdapter.deletePhoto(id);
			user.removePhoto(id);
			userAdapter.updateUser(user, UserAdapterTDS.PHOTOS);
		} else {
			Album album = (Album) post;
			// Si el álbum solo tenía una foto, borramos el álbum
			if (album.getPhotos().size() == 0) {
				albumAdapter.deleteAlbum(album.getCode());
				user.removeAlbum(album.getCode());
			}

			// Si el álbum tenía más de una foto, borramos la foto y actualizamos el álbum
			else {
				user.deletePhotoFromAlbum(id, album.getCode());
				photoAdapter.deletePhoto(id);
				albumAdapter.updateAlbum(album, AlbumAdapterTDS.PHOTOS);
			}

		}

		System.out.println("El usuario " + user.getUserName() + " ha eliminado el post " + post.getTitle());
	}

	// Método para eliminar un álbum
	public void deleteAlbum(int id) {
		if (user == null)
			return;

		postRepository.deleteAlbum(id);
		albumAdapter.deleteAlbum(id);

		// Eliminamos el álbum de su usuario
		user.removeAlbum(id);

		// Actualizamos el usuario en la base de datos
		userAdapter.updateUser(user, UserAdapterTDS.ALBUMS);

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

	// Método para darle like a un post
	public void like(int postId) {
		if (user == null)
			return;

		// Obtenemos el post
		Post post = postRepository.getPost(postId);
		// Modificamos los likes en el objeto post, que será una foto o un álbum
		post.like();

		// Cambiamos el objeto en la persistencia según sea foto o álbum
		if (post instanceof Photo)
			photoAdapter.updatePhoto((Photo) post, PhotoAdapterTDS.LIKES);
		else {
			// Cambiamos los likes del álbum
			albumAdapter.updateAlbum((Album) post, AlbumAdapterTDS.LIKES);
			// Cambiamos los likes de todas sus fotos
			((Album) post).getPhotos().stream().forEach((p) -> photoAdapter.updatePhoto(p, PhotoAdapterTDS.LIKES));
		}

	}


	// Método para comentar en un post
	public boolean comment(int id, String commentText) {
		// Creamos el objeto comentario, que tendrá como usuario comentador a nuestro
		// usuario
		Comment comment = new Comment(commentText, user);
		// Recuperamos el objeto Photo
		Photo photo = (Photo) postRepository.getPost(id);
		// Comprobamos que no sea nulo
		if (photo == null)
			return false;
		// Modificamos nuestro objeto photo
		photo.addComment(comment);
		// Añadimos el comentario a la persistencia
		commentAdapter.addComment(comment);
		// Modificamos la foto de la persistencia
		photoAdapter.updatePhoto(photo, PhotoAdapterTDS.COMMENTS);

		return true;

	}

	// Método para hacer una búsqueda. Devuelve una lista de objetos de dominio
	public List<DomainObject> search(String search) {
		List<DomainObject> objetos = new LinkedList<>();

		// Comprobamos si el query empieza por hashtag, en ese caso habrá que busar
		// posts
		if (search.startsWith("#")) {
			objetos.addAll(postRepository.getPostsByHashtagsContaining(search));
		}

		// Si no, buscamos usuarios
		else {
			// Primero buscamos si hay usuarios a partir del userName
			objetos.addAll(userRepository.getUsersByUserNameContaining(search));
			// Luego buscamos si los hay a partir del name
			objetos.addAll(userRepository.getUsersByNameStartingWith(search));
			// Luego buscamos si los hay a partir del email
			objetos.addAll(userRepository.getUsersByEmailContaining(search));
		}

		// Eliminamos duplicados
		Set<DomainObject> conjuntoUnico = new HashSet<>(objetos);
		List<DomainObject> listaUnica = new ArrayList<>(conjuntoUnico);

		return listaUnica;
	}

	// -------------------- FUNCIONALIDAD PREMIUM ----------------a
	// Método para hacerse premium
	public void changeToPremium() {
		// Aquí es donde se realizaría el pago, pero esto queda fuera de los que se pide
		// en la especificación
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

		LocalDate date = LocalDate.of(user.getDateOfBirth().getYear(), user.getDateOfBirth().getMonth(),
				user.getDateOfBirth().getDay());
		Period age = Period.between(date, LocalDate.now());
		int yearsOfUser = age.getYears();

		if (yearsOfUser <= YOUNG_AGE_DISCOUNT || yearsOfUser >= OLD_AGE_DISCOUNT)
			return (1 - AGE_DISCOUNT) * PREMIUM_PRICE;

		return PREMIUM_PRICE;
	}

	public double getDiscount() {
		return Math.min(getDiscountByAge(), getDiscountByLikes());
	}

	public List<Photo> getTopPhotosByLikes() {
		return user.getPhotos().stream().sorted(new PhotoComparatorByLikes()).limit(NUMBER_OF_TOP_PHOTOS).toList();
	}

	// TODO
	public void generatePDF() {
		 	Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream("seguidores.pdf"));
	        document.open();
	        PdfPTable table = new PdfPTable(3); // 3 columnas
	        PdfPCell cell1 = new PdfPCell(new Phrase("Nombre"));//nombre usuario
	        PdfPCell cell2 = new PdfPCell(new Phrase("Email"));//email usuario
	        PdfPCell cell3 = new PdfPCell(new Phrase("Descripción"));//descripción usuario
	        table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        for (User user : user.getFollowers()) {
		        table.addCell(user.getUserName());
		        table.addCell(user.getEmail());
		        table.addCell(user.getBio());
	        }
	        document.add(table);
	        document.close();
	}

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

	// Método para obtener los últimos 10 posts que han publicado los usuarios a los
	// que sigues
	public List<Integer> getFeed() {
		if (user == null)
			return null;
		return postRepository.getFeed(user.getFollowed()).stream().map((p) -> p.getCode()).toList();

	}

	// Obtener mi id
	public int getId() {
		return user.getCode();
	}

	// Método para devolver el id de un usuario a partir de su username
	public int getId(String username) {
		return userRepository.getUserByUsername(username).getCode();
	}

	// Obtener lista de fotos de un usuario, incluyendo las que incluyen los álbumes
	public List<Integer> getPhotos(int id) {
		List<Integer> photos = new ArrayList<>();
		User user = userRepository.getUser(id);
		photos.addAll(user.getPhotos().stream().map((p) -> p.getCode()).toList());
		List<Album> albums = user.getAlbums();
		for (Album a : albums) {
			photos.addAll(a.getPhotos().stream().map((p) -> p.getCode()).toList());
		}
		return photos;
	}

	// Ver si un usuario es premium
	public boolean isPremium(int id) {
		return userRepository.getUser(id).isPremium();
	}

	// Obtener el nombre de usuario de un usuario
	public String getUserName(int id) {
		return userRepository.getUser(id).getUserName();
	}

	// Obtener el nombre completo de un usuario
	public String getFullName(int id) {
		return userRepository.getUser(id).getFullName();
	}

	// Obtener el número de seguidores de un usuario
	public int getFollowers(int id) {
		return userRepository.getUser(id).getFollowers().size();
	}

	// Obtener el número de seguidos de un usuario
	public int getFollowed(int id) {
		return userRepository.getUser(id).getFollowed().size();
	}

	// Obtener el email de un usuario
	public String getEmail(int id) {
		return userRepository.getUser(id).getEmail();
	}

	// Obtener la contraseña de un usuario
	public String getPassword(int id) {
		return userRepository.getUser(id).getPassword();
	}

	// Obtener la bio de un usuario
	public String getBio(int id) {
		return userRepository.getUser(id).getBio();
	}

	// Obtener foto de perfil de un usuario
	public String getProfilePic(int id) {
		return userRepository.getUser(id).getProfilePic();
	}

	// Obtener la lista de albumes de un usuario
	public List<Integer> getAlbums(int id) {
		return userRepository.getUser(id).getAlbums().stream().map((p) -> p.getCode()).toList();
	}
	
	// Obtener la lista de notificaciones de un usuario
	public List<Notification> getNotifications(int id) {
		return userRepository.getUser(id).getNotifications();
	}

	// Ver si un usuario user1 tiene a user2 como seguidor
	public boolean isFollowed(int idUser1, int idUser2) {
		return userRepository.getUser(idUser1).getFollowers().stream().anyMatch((u) -> u.getCode() == idUser2);
	}

	// Obtener el path de una foto a partir de su id
	public String getPath(int id) {
		Post post = postRepository.getPost(id);

		// Si es una foto devolvemos su path
		if (post instanceof Photo)
			return ((Photo) post).getPath();

		// Si es un álbum devolvemos el path de su primera foto
		else
			return ((Album) post).getPhotos().get(0).getPath();
	}

	// Obtener el nombre de usuario del propietario de una foto a partir de su id
	public String getOwnerOfPhoto(int id) {
		return postRepository.getPost(id).getUser().getUserName();
	}

	// Obtener los likes de un post a partir de su id
	public int getLikes(int id) {
		Post p = postRepository.getPost(id);
		return p.getLikes();
	}

	// Obtener todas las fotos de un album
	public List<Integer> getPhotosOfAlbum(int albumId) {
		return postRepository.getPhotosOfAlbum(albumId);
	}

	// Obtener el título de una foto
	public String getPostTitle(int photoId) {
		return postRepository.getPost(photoId).getTitle();
	}

	// Obtener el título de una foto
	public String getPostDescription(int photoId) {
		return postRepository.getPost(photoId).getDescription();
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
	
	// Para el componente cargador de fotos
	public void cargarFotos(String path) {
		ComponenteCargadorFotos cargador = new ComponenteCargadorFotos();
		cargador.addHayFotosListener(new IBuscadorFotos() {
			
			@Override
			public void hayFotos(EventObject arg) {
				// TODO cargar las fotos
				FotosEvent event = (FotosEvent) arg;
				List<Foto> fotos = event.getFotos();
				// Vamos añadiendo todas las fotos
				for (Foto foto : fotos) {
					addPhoto(foto.getTitulo(), foto.getDescripcion(), foto.getPath());
					LoggedFrame.getInstance().updateProfile();
				}
			}
		});
		
		cargador.setArchivoFotos(path);
	}

}
