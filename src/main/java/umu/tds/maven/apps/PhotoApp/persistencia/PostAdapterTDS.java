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
import umu.tds.maven.apps.PhotoApp.modelo.Album;
import umu.tds.maven.apps.PhotoApp.modelo.Comment;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.modelo.Post;

public class PostAdapterTDS extends AdapterTDS implements IPostAdapterDAO {
	
	public static final String POST = "post";
	public static final String TITLE = "title";
	public static final String DATE = "date";
	public static final String DESCRIPTION = "description";
	public static final String LIKES = "likes";
	public static final String HASHTAGS = "hashtags";
	public static final String COMMENTS = "comments";
	public static final String PHOTOS = "photos";
	

	private static PostAdapterTDS instance;
	private SimpleDateFormat dateFormat;
	
	public static PostAdapterTDS getInstance() {
		if (instance == null)
			instance = new PostAdapterTDS();
		return instance;
	}
	
	public PostAdapterTDS() {	
		super();
		dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	}
	
	@Override
	protected Entidad objectToEntity(DomainObject o) {
		Entidad ePost = new Entidad();
		Post post = (Post) o;

		ePost.setNombre(POST);
		ePost.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(TITLE, post.getTitle()), new Propiedad(DATE, dateFormat.format(post.getDate())),
						new Propiedad(DESCRIPTION, post.getDescription()), new Propiedad(LIKES, String.valueOf(post.getLikes())),
						new Propiedad(HASHTAGS, getStringFromHashtags(post.getHashtags())),
						new Propiedad(COMMENTS, CommentAdapterTDS.getInstance().getCodesFromComments(post.getComments())),
						new Propiedad(PHOTOS, getPathsFromPost(post)))));

		return ePost;
	}
	
	
	@Override
	protected DomainObject entityToObject(Entidad en) {

		// Si el objeto está en el Pool, lo devolvemos directamente
		if (PoolDAO.getInstance().contains(en.getId()))
			return (DomainObject) PoolDAO.getInstance().getObject(en.getId());

		// Estos serán los atributos del Post que queremos recuperar
		String title;
		Date date;
		String description;
		int likes;
		
		
		// Recuperamos los atributos de Post de la persistencia
		title = servPersistencia.recuperarPropiedadEntidad(en, TITLE);
		date = null;
		try {
			date = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(en, DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		description = servPersistencia.recuperarPropiedadEntidad(en, DESCRIPTION);
		likes = Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(en, LIKES));
		
		
		// Recuperamos los atributos que son listas 
		List<String> hashtags = getHashtagsFromString(servPersistencia.recuperarPropiedadEntidad(en, HASHTAGS));
		List<Comment> comments = CommentAdapterTDS.getInstance().getCommentsFromCodes(servPersistencia.recuperarPropiedadEntidad(en, COMMENTS));
		List<String> paths = getListOfPathsFromPaths(servPersistencia.recuperarPropiedadEntidad(en, PHOTOS)); 
	
		
		// Si hay más de un path se trata de un álbum
		if (paths.size() > 1) {
			/*Album album = (Album) post;
			List<Photo> photos = new LinkedList<>();
			for (String path : paths) {
				Photo photo = (Photo) post;
				photo.setPath(path);
				photos.add(photo);
			}
			album.setPhotos(photos);*/
			return null;
		}
		// En otro caso, se trata de una foto
		else {
			Photo photo = new Photo(title, date, description, likes, paths.get(0));
			photo.setHashtags(hashtags);
			photo.setComments(comments);
			photo.setCode(en.getId());
			return photo;
		}

	}
	
	

	@Override
	public Photo getPost(int code) {
		Entidad ePost;
		Photo post = null;

		// Recuperamos la entidad
		ePost = servPersistencia.recuperarEntidad(code);
		// Convertimos la entidad en un objeto usuario
		try {
			post = (Photo) entityToObject(ePost);
			
		} catch (NullPointerException e) {
			System.out.println("El post con el id " + code + " no está registrado");
		}
		return post;
	}
	
	@Override
	public void addPost(Post post) {
		// Si el post ya está registrado, no se registra
		Entidad ePost = null;
		try {
			ePost = servPersistencia.recuperarEntidad(post.getCode());
		} catch (NullPointerException e) {
		}
		if (ePost != null)
			return;
		
		// Creamos entidad post
		ePost = objectToEntity(post);
		// registrar entidad post
		ePost = servPersistencia.registrarEntidad(ePost);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		post.setCode(ePost.getId());
		
	}
	
	@Override
	public void deletePost(Post post) {
		if (post != null ) {
			Entidad ePost = servPersistencia.recuperarEntidad(post.getCode());
			
			servPersistencia.borrarEntidad(ePost);
		}
	}
	
	@Override
	public void updatePost(Post post, String attribute) {
		Entidad ePost = servPersistencia.recuperarEntidad(post.getCode());
		List<Propiedad> properties = new LinkedList<>();
		for (Propiedad p : ePost.getPropiedades()) {
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
		
		case LIKES:
			property.setValor(String.valueOf(post.getLikes()));
			break;
		case COMMENTS:
			property.setValor(CommentAdapterTDS.getInstance().getCodesFromComments(post.getComments()));
			break;
		case TITLE:
			property.setValor(post.getTitle());
			break;
		case DESCRIPTION:
			property.setValor(post.getDescription());
			break;
			
		}
		
		servPersistencia.modificarPropiedad(property);
		
		
	}
	
	// TODO ver si puedo reutilizar código y reescribir en AdapterTDS
	// Usamos esta función para obtener posts a través de un string con varios códigos
	List<Post> getAllPostsFromCodes(String codes) {
		List<Post> postList = new LinkedList<>();
		if (codes != null && !codes.isEmpty()) {
			StringTokenizer strTok = new StringTokenizer(codes, " ");
			PostAdapterTDS adapter = getInstance();
			while (strTok.hasMoreTokens()) {
				postList.add(adapter.getPost(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return postList;
	}
	
	String getStringFromHashtags(List<String> listOfHashtags) {
		String hashtags = "";
		for (String hashtag : listOfHashtags) {
			hashtags += hashtag + " ";
		}
		return hashtags.trim();
	}
	
	List<String> getHashtagsFromString(String hashtags) {
		List<String> listOfHashtags = new LinkedList<>();
		if (hashtags != null) {
			StringTokenizer strTok = new StringTokenizer(hashtags, " ");
			while (strTok.hasMoreTokens()) {
				listOfHashtags.add((String) strTok.nextElement());
			}
		}
		return listOfHashtags;
	}
	
	String getCodesFromAllPosts(List<Post> posts) {
		String codes = "";
		for (Post post : posts) {
			codes += post.getCode() + " ";
		}
		return codes.trim();
	}

	String getPathsFromPost(Post post) {
		Photo photo = null;
		Album album = null;
		List<String> listOfPaths = new LinkedList<>();
		
		if (post instanceof Photo) {
			photo = (Photo) post;
			listOfPaths = List.of(photo.getPath());
		}
		else {
			album = (Album) post;
			listOfPaths = album.getPhotos().stream().map((p) -> p.getPath()).toList();
		}
		
		String codes = "";
		for (String path : listOfPaths) {
			codes += path + " ";
		}
		return codes.trim();
		
	}
	
	List<String> getListOfPathsFromPaths(String paths) {
		List<String> pathList = new LinkedList<>();
		if (paths != null) {
			StringTokenizer strTok = new StringTokenizer(paths, " ");
			while (strTok.hasMoreTokens()) {
				pathList.add((String) strTok.nextElement());
			}
		}
		return pathList;
	}
	


	public List<Post> getAllPosts() {
		List<Entidad> entities = servPersistencia.recuperarEntidades(POST);

		List<Post> posts = new LinkedList<Post>();
		for (Entidad ePost : entities) {
			posts.add(getPost(ePost.getId()));
		}

		return posts;
	}
	

}
