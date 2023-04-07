package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;

public class PostRepository {

	// Número de posts que queremos en la feed
	public static final int MAX_PHOTOS_IN_ALBUM = 16;
	public static final int POSTS_IN_FEED = 10;
	// Patrón singleton
	private static PostRepository instance;
	private static FactoriaDAO factory;

	private HashMap<Integer, Photo> photosById;
	private HashMap<Integer, Album> albumsById;

	// Devuelve la única instancia (Singleton)
	public static PostRepository getInstance() {
		if (instance == null)
			instance = new PostRepository();
		return instance;
	}

	// Constructor con el que extraemos todos los posts de la persistencia y los
	// añadimos al repositorio
	private PostRepository() {
		photosById = new HashMap<>();
		albumsById = new HashMap<>();

		try {
			factory = FactoriaDAO.getInstance();

			// Recuperamos todas las fotos y álbumes y los introducimos en nuestro mapa
			factory.getPhotoDAO().getAllPhotos().stream().forEach((p) -> photosById.put(p.getCode(), p));
			factory.getAlbumDAO().getAllAlbums().stream().forEach((a) -> albumsById.put(a.getCode(), a));

			// Quitamos las fotos de la lista de fotos que pertenezcan a un álbum
			albumsById.values().stream()
					.forEach((a) -> a.getPhotos().stream().forEach((p) -> photosById.remove(p.getCode())));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para añadir una foto al repositorio
	public void addPhoto(Photo photo) {
		photosById.put(photo.getCode(), photo);
	}

	// Método para añadir un álbum al repositorio
	public void addAlbum(Album album) {
		// Añadimos el álbum a la lista de álbumes
		albumsById.put(album.getCode(), album);
	}

	// Método para añadir una foto a un álbum
	public Album addPhotoToAlbum(Photo photo, int idAlbum) {
		// Recuperamos el álbum y comprobamos que no se ha excedido el máximo de fotos
		Album a = albumsById.get(idAlbum);
		if (a.getPhotos().size() > MAX_PHOTOS_IN_ALBUM)
			return null;

		// Metemos la foto en el álbum dentro de la lista de álbumes
		a.addPhoto(photo);
		return a;

	}

	// Método para eliminar una foto
	public Post deletePhoto(int id) {
		// Miramos si es una foto normal
		Photo photo = photosById.remove(id);

		// Si no lo es, tendremos que mirar si pertenece a un álbum
		if (photo == null) {
			for (Album a : albumsById.values()) {
				for (Photo p : a.getPhotos()) {
					if (p.getCode() == id) {
						// Eliminamos la foto del álbum
						a.removePhoto(id);
						// Si el álbum se queda vacío, eliminamos el álbum y lo devolvemos sin la foto
						if (a.getPhotos().size() == 0) {
							deleteAlbum(a.getCode());
						}
						return a;
					}
				}
			}
		}

		// Si es una foto normal, tenemos que eliminar las notificaciones que hacían
		// referencia a ella
		photo.getUser().getFollowers().stream()
				.forEach((u) -> u.removeNotification((photo.getNotification().getCode())));
		// Eliminarmos la foto y la devolvemos
		return photo;

	}

	// Método para eliminar un álbum
	public Album deleteAlbum(int id) {
		Album a = albumsById.get(id);

		// Eliminamos el álbum en sí
		albumsById.remove(id);
		return a;
	}

	// Método para recuperar un post a partir de su id
	public Post getPost(int id) {
		// Miramos si es una foto
		Post p = photosById.get(id);

		// Miramos si la foto está en algún álbum
		for (Album a : albumsById.values()) {
			for (Photo ph : a.getPhotos()) {
				if (ph.getCode() == id)
					return ph;
			}
		}

		// Si no, miramos si es un álbum
		if (p == null)
			p = albumsById.get(id);

		return p;
	}

	// Método para obtener todas las fotos de un álbum
	public List<Integer> getPhotosOfAlbum(int albumId) {
		return (albumsById.get(albumId)).getPhotos().stream().map((p) -> p.getCode()).toList();
	}

	// Método para obtener las últimas 10 fotos de los usuarios a los que sigue un
	// usuario
	public List<Photo> getFeed(List<User> followed) {
		// Obtenemos todas las fotos de los seguidos por el usuario ordenados por fecha
		// en orden ascendente
		List<Photo> photosOfFollowed = photosById.values().stream().filter((p) -> followed.contains(p.getUser()))
				.sorted().toList();
		List<Album> albumOfFollowed = albumsById.values().stream().filter((p) -> followed.contains(p.getUser()))
				.sorted().toList();
		
		
		List<Photo> feed= new LinkedList<>();
		for (Photo p : photosOfFollowed)
			feed.add(p);
		for (Album a : albumOfFollowed)
			feed.addAll(a.getPhotos());

		// Nos quedamos con los últimos 10, o en caso de que haya menos de 10, con todos
		// ellos
		if (feed.size() <= POSTS_IN_FEED)
			return feed;
		
		// Vamos añadiendo los posts
		Collections.sort(feed, new PostDateComparator());
		List<Photo> ultimos10Posts = feed.subList(Math.max(feed.size() - 10, 0), feed.size());
		
		return ultimos10Posts;
	}

	public List<Post> getPostsByHashtagsContaining(String hashtagsSubset) {
		// Primero extraemos los hashtags de la búsqueda, pues puede haber varios
		List<String> hashtags = new LinkedList<>();

		StringTokenizer strTok = new StringTokenizer(hashtagsSubset, " ");
		while (strTok.hasMoreTokens()) {
			hashtags.add((String) strTok.nextElement());
		}
		// Esta será la lista de posts que contengan todos los hashtags
		List<Post> validPosts = new LinkedList<>();
		// Primero buscamos en los álbumes
		for (Post post : albumsById.values()) {
			if (post.getHashtags().containsAll(hashtags)) {
				validPosts.add(post);
			}
		}

		// Luego buscamos en las fotos
		for (Post post : photosById.values()) {
			if (post.getHashtags().containsAll(hashtags)) {
				validPosts.add(post);
			}
		}

		return validPosts;
	}
	 class PostDateComparator implements Comparator<Post> {
		    @Override
		    public int compare(Post post1, Post post2) {
		        return post1.getDate().compareTo(post2.getDate());
		    }
		}
}

