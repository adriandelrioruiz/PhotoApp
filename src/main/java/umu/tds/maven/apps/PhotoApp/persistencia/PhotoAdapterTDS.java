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
import umu.tds.maven.apps.PhotoApp.modelo.Comment;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class PhotoAdapterTDS extends AdapterTDS implements IPhotoAdapterDAO {
	
	public static final String PHOTO = "photo";
	public static final String TITLE = "title";
	public static final String DATE = "date";
	public static final String DESCRIPTION = "description";
	public static final String LIKES = "likes";
	public static final String HASHTAGS = "hashtags";
	public static final String COMMENTS = "comments";
	public static final String USER = "user";
	public static final String PATH = "path";
	

	private static PhotoAdapterTDS instance;
	private SimpleDateFormat dateFormat;
	
	public static PhotoAdapterTDS getInstance() {
		if (instance == null)
			instance = new PhotoAdapterTDS();
		return instance;
	}
	
	public PhotoAdapterTDS() {	
		super();
		dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	}
	
	@Override
	protected Entidad objectToEntity(DomainObject o) {
		Entidad ePhoto = new Entidad();
		Photo photo = (Photo) o;

		ePhoto.setNombre(PHOTO);
		ePhoto.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(TITLE, photo.getTitle()), new Propiedad(DATE, dateFormat.format(photo.getDate())),
						new Propiedad(DESCRIPTION, photo.getDescription()), new Propiedad(LIKES, String.valueOf(photo.getLikes())),
						new Propiedad(HASHTAGS, getStringFromHashtags(photo.getHashtags())),
						new Propiedad(COMMENTS, CommentAdapterTDS.getInstance().getCodesFromComments(photo.getComments())),
						new Propiedad(PATH, photo.getPath()))));

		return ePhoto;
	}
	
	
	@Override
	protected DomainObject entityToObject(Entidad en) {

		// Si el objeto está en el Pool, lo devolvemos directamente
		if (PoolDAO.getInstance().contains(en.getId()))
			return (DomainObject) PoolDAO.getInstance().getObject(en.getId());

		// Estos serán los atributos del Photo que queremos recuperar
		String title;
		Date date;
		String description;
		int likes;
		User user;
		
		// Recuperamos los atributos de Photo de la persistencia
		title = servPersistencia.recuperarPropiedadEntidad(en, TITLE);
		date = null;
		try {
			date = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(en, DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		description = servPersistencia.recuperarPropiedadEntidad(en, DESCRIPTION);
		likes = Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(en, LIKES));
		String path = servPersistencia.recuperarPropiedadEntidad(en, PATH); 
		user = UserAdapterTDS.getInstance().getUser(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(en, USER))); 
		
		
		// Recuperamos los atributos que son listas 
		List<String> hashtags = getHashtagsFromString(servPersistencia.recuperarPropiedadEntidad(en, HASHTAGS));
		List<Comment> comments = CommentAdapterTDS.getInstance().getCommentsFromCodes(servPersistencia.recuperarPropiedadEntidad(en, COMMENTS));
	
		Photo photo = new Photo(title, date, description, path, user);
		photo.setLikes(likes);
		photo.setHashtags(hashtags);
		photo.setComments(comments);
		photo.setCode(en.getId());
		return photo;
		
	}
	

	@Override
	public Photo getPhoto(int code) {
		Entidad ePhoto;
		Photo photo = null;

		// Recuperamos la entidad
		ePhoto = servPersistencia.recuperarEntidad(code);
		// Convertimos la entidad en un objeto usuario
		try {
			photo = (Photo) entityToObject(ePhoto);
			
		} catch (NullPointerException e) {
			System.out.println("El photo con el id " + code + " no está registrado");
		}
		return photo;
	}
	
	@Override
	public void addPhoto(Photo photo) {
		// Si el photo ya está registrado, no se registra
		Entidad ePhoto = null;
		try {
			ePhoto = servPersistencia.recuperarEntidad(photo.getCode());
		} catch (NullPointerException e) {
		}
		if (ePhoto != null)
			return;
		
		// Creamos entidad photo
		ePhoto = objectToEntity(photo);
		// registrar entidad photo
		ePhoto = servPersistencia.registrarEntidad(ePhoto);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		photo.setCode(ePhoto.getId());
		
	}
	
	@Override
	public void deletePhoto(int code) {
		Entidad ePhoto = servPersistencia.recuperarEntidad(code);
		Photo photo = (Photo) entityToObject(ePhoto);
		// Si eliminamos una foto, tenemos que eliminar sus comentarios
		photo.getComments().stream().forEach((c)->CommentAdapterTDS.getInstance().deleteComment(c));
		servPersistencia.borrarEntidad(ePhoto);
		
	}
	
	@Override
	public void updatePhoto(Photo photo, String attribute) {
		Entidad ePhoto = servPersistencia.recuperarEntidad(photo.getCode());
		List<Propiedad> properties = new LinkedList<>();
		for (Propiedad p : ePhoto.getPropiedades()) {
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
			property.setValor(String.valueOf(photo.getLikes()));
			break;
		case COMMENTS:
			property.setValor(CommentAdapterTDS.getInstance().getCodesFromComments(photo.getComments()));
			break;
		case TITLE:
			property.setValor(photo.getTitle());
			break;
		case DESCRIPTION:
			property.setValor(photo.getDescription());
			break;
			
		}
		
		servPersistencia.modificarPropiedad(property);
		
		
	}
	
	// TODO ver si puedo reutilizar código y reescribir en AdapterTDS
	// Usamos esta función para obtener photos a través de un string con varios códigos
	List<Photo> getAllPhotosFromCodes(String codes) {
		List<Photo> photoList = new LinkedList<>();
		if (codes != null && !codes.isEmpty()) {
			StringTokenizer strTok = new StringTokenizer(codes, " ");
			PhotoAdapterTDS adapter = getInstance();
			while (strTok.hasMoreTokens()) {
				photoList.add(adapter.getPhoto(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return photoList;
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
	
	String getCodesFromAllPhotos(List<Photo> photos) {
		String codes = "";
		for (Photo photo : photos) {
			codes += photo.getCode() + " ";
		}
		return codes.trim();
	}


	public List<Photo> getAllPhotos() {
		List<Entidad> entities = servPersistencia.recuperarEntidades(PHOTO);

		List<Photo> photos = new LinkedList<Photo>();
		for (Entidad ePhoto : entities) {
			photos.add(getPhoto(ePhoto.getId()));
		}

		return photos;
	}
	

}
