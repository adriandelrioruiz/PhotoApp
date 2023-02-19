package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;

public class PostRepository {
	
	// Número de posts que queremos en la feed
	public static final int POSTS_IN_FEED = 10;
	// Patrón singleton
	private static PostRepository instance;
	// TODO quitar esto?
	private static FactoriaDAO factory;
	
	private HashMap<Integer,Post> postsById;
	
	// Devuelve la única instancia (Singleton)
	public static PostRepository getInstance() {
		if (instance == null)
			instance = new PostRepository();
		return instance;
	}
	
	// Constructor con el que extraemos todos los posts de la persistencia y los añadimos al repositorio
	private PostRepository() {
		postsById = new HashMap<>();
		
		try {
			factory = FactoriaDAO.getInstance();
			
			
			// Recuperamos todas las fotos y las las introducimos en nuestro mapa
			factory.getPhotoDAO().getAllPhotos().stream().forEach((p) -> postsById.put(p.getCode(), p));
			
			// postsById.values().stream().forEach((u) -> factory.getPhotoDAO().deletePhoto((Photo)u)); System.exit(0);
			
			// Recuperamos todos los álbumes
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Método para añadir un post al repositorio
	// TODO cambiar por un metodo addAlbum y addPhoto
	public void addPost(Post post) {
		postsById.put(post.getCode(), post);
	}
	
	// Método para eliminar un post
	public void deletePost(Post post) {
		// Si es una foto, tenemos que eliminar las notificaciones que hacían referencia a ella
		if (post instanceof Photo)
			post.getUser().getFollowers().stream().forEach((u)->u.removeNotification(((Photo)post).getNotification().getCode()));
		postsById.remove(post.getCode());
	}
	
	// Método para recuperar una foto a partir de su id
	public Photo getPhoto(int id) {
		return (Photo)postsById.get(id);
	}
	
	// Método para obtener todas las fotos de un álbum
	public List<Integer> getPhotosOfAlbum(int albumId) {
		return ((Album)postsById.get(albumId)).getPhotos().stream().map((p)->p.getCode()).toList();
	}
	
	// Método para obtener las últimas 10 fotos de los usuarios a los que sigue un usuario
	public List<Photo> getFeed(List<User> followed) {
		// Obtenemos todos los posts de los seguidos por el usuario ordenados por fecha en orden ascendente
		List<Post> postsOfFollowed = postsById.values().stream().filter((p)->followed.contains(p.getUser())).sorted().toList();
		// Solo nos quedamos con las fotos
		List<Photo> photosOfFollowed = new LinkedList<>();
		for (Post p: postsOfFollowed)
			if (p instanceof Photo)
				photosOfFollowed.add((Photo)p);
		
		// Nos quedamos con los últimos 10, o en caso de que haya menos de 10, con todos ellos
		if (photosOfFollowed.size() <= POSTS_IN_FEED)
			return photosOfFollowed;
		List<Photo> photosOfFeed = new LinkedList<>();
		// Vamos añadiendo los posts
		for(int i = photosOfFollowed.size() - POSTS_IN_FEED - 1; i < photosOfFollowed.size() - 1; i++) {
			photosOfFeed.add(photosOfFollowed.get(i));
		}
		return photosOfFeed;
	}
	
}
