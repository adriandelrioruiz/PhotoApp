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
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class AlbumAdapterTDS extends AdapterTDS implements IAlbumAdapterDAO {
	
	public static final String ALBUM = "album";
	public static final String TITLE = "title";
	public static final String DATE = "date";
	public static final String DESCRIPTION = "description";
	public static final String LIKES = "likes";
	public static final String HASHTAGS = "hashtags";
	public static final String COMMENTS = "comments";
	public static final String USER = "user";
	public static final String PHOTOS = "photos";
	

	private static AlbumAdapterTDS instance;
	private SimpleDateFormat dateFormat;
	
	public static AlbumAdapterTDS getInstance() {
		if (instance == null)
			instance = new AlbumAdapterTDS();
		return instance;
	}
	
	public AlbumAdapterTDS() {	
		super();
		dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	}
	
	@Override
	protected Entidad objectToEntity(DomainObject o) {
		Entidad ePhoto = new Entidad();
		Album album = (Album) o;

		ePhoto.setNombre(ALBUM);
		ePhoto.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(TITLE, album.getTitle()), new Propiedad(DATE, dateFormat.format(album.getDate())),
						new Propiedad(DESCRIPTION, album.getDescription()), new Propiedad(LIKES, String.valueOf(album.getLikes())),
						new Propiedad(HASHTAGS, getStringFromHashtags(album.getHashtags())),
						new Propiedad(COMMENTS, CommentAdapterTDS.getInstance().getCodesFromComments(album.getComments())),
						new Propiedad(PHOTOS, PhotoAdapterTDS.getInstance().getCodesFromAllPhotos(album.getPhotos())))));

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
		user = UserAdapterTDS.getInstance().getUser(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(en, USER))); 
		
		
		// Recuperamos los atributos que son listas 
		List<String> hashtags = getHashtagsFromString(servPersistencia.recuperarPropiedadEntidad(en, HASHTAGS));
		List<Comment> comments = CommentAdapterTDS.getInstance().getCommentsFromCodes(servPersistencia.recuperarPropiedadEntidad(en, COMMENTS));
		List<Photo> photos = PhotoAdapterTDS.getInstance().getAllPhotosFromCodes(servPersistencia.recuperarPropiedadEntidad(en, PHOTOS));
	
		Album album = new Album(title, date, description, user);
		album.setLikes(likes);
		album.setHashtags(hashtags);
		album.setComments(comments);
		album.setPhotos(photos);
		album.setCode(en.getId());
		return album;
		
	}
	

	@Override
	public Album getAlbum(int code) {
		Entidad eAlbum;
		Album album = null;

		// Recuperamos la entidad
		eAlbum = servPersistencia.recuperarEntidad(code);
		// Convertimos la entidad en un objeto usuario
		try {
			album = (Album) entityToObject(eAlbum);
			
		} catch (NullPointerException e) {
			System.out.println("El photo con el id " + code + " no está registrado");
		}
		return album;
	}
	
	@Override
	public void addAlbum(Album album) {
		// Si el photo ya está registrado, no se registra
		Entidad eAlbum = null;
		try {
			eAlbum = servPersistencia.recuperarEntidad(album.getCode());
		} catch (NullPointerException e) {
		}
		if (eAlbum != null)
			return;
		
		// Creamos entidad photo
		eAlbum = objectToEntity(album);
		// registrar entidad photo
		eAlbum = servPersistencia.registrarEntidad(eAlbum);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		album.setCode(eAlbum.getId());
		
	}
	
	@Override
	public void deleteAlbum(Album album) {
		if (album != null ) {
			Entidad eAlbum = servPersistencia.recuperarEntidad(album.getCode());
			// Borramos todas las fotos del álbum
			List<Photo> photos = PhotoAdapterTDS.getInstance().getAllPhotosFromCodes(servPersistencia.recuperarPropiedadEntidad(eAlbum, PHOTOS));
			photos.stream().forEach((p)->PhotoAdapterTDS.getInstance().deletePhoto(p.getCode()));
			servPersistencia.borrarEntidad(eAlbum);
		}
	}
	
	@Override
	public void updateAlbum(Album album, String attribute) {
		Entidad eAlbum = servPersistencia.recuperarEntidad(album.getCode());
		List<Propiedad> properties = new LinkedList<>();
		for (Propiedad p : eAlbum.getPropiedades()) {
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
			property.setValor(String.valueOf(album.getLikes()));
			break;
		case COMMENTS:
			property.setValor(CommentAdapterTDS.getInstance().getCodesFromComments(album.getComments()));
			break;
		case TITLE:
			property.setValor(album.getTitle());
			break;
		case DESCRIPTION:
			property.setValor(album.getDescription());
			break;
		case PHOTOS:
			property.setValor(PhotoAdapterTDS.getInstance().getCodesFromAllPhotos(album.getPhotos()));
			
		}
		
		servPersistencia.modificarPropiedad(property);
		
		
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
	
	String getCodesFromAllAlbums(List<Album> albums) {
		String codes = "";
		for (Album album : albums) {
			codes += album.getCode() + " ";
		}
		return codes.trim();
	}
	
	List<Album> getAllAlbumsFromCodes(String codes) {
		List<Album> albumList = new LinkedList<>();
		if (codes != null && !codes.isEmpty()) {
			StringTokenizer strTok = new StringTokenizer(codes, " ");
			AlbumAdapterTDS adapter = getInstance();
			while (strTok.hasMoreTokens()) {
				albumList.add(adapter.getAlbum(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return albumList;
	}


	public List<Album> getAllAlbums() {
		List<Entidad> entities = servPersistencia.recuperarEntidades(ALBUM);

		List<Album> albums = new LinkedList<Album>();
		for (Entidad eAlbum : entities) {
			albums.add(getAlbum(eAlbum.getId()));
		}

		return albums;
	}
	

}
