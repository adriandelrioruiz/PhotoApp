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
			
			// Recuperamos todos los posts de la base de datos
			List<Post> posts = factory.getPostDAO().getAllPosts();
			/*posts.stream().forEach((u) -> PostAdapterTDS.getInstance().deletePost(u));
			System.exit(0);*/
			
			// Los introducimos en nuestro mapa
			for (Post post : posts)
				postsById.put(post.getCode(), post);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Método para añadir un post al repositorio
	public void addPost(Post post) {
		postsById.put(post.getCode(), post);
	}
	
	// Método para eliminar un post
	public void deletePost(Post post) {
		postsById.remove(post.getCode());
	}
	
	// Método para obtener las últimas 10 fotos de los usuarios a los que sigue un usuario
	public List<Post> getFeed(List<User> followed) {
		// Obtenemos todos los posts de los seguidos por el usuario ordenados por fecha en orden ascendente
		List<Post> postsOfFollowed = postsById.values().stream().filter((p)->followed.contains(p.getUser())).sorted().toList();
		// Nos quedamos con los últimos 10, o en caso de que haya menos de 10, con todos ellos
		if (postsOfFollowed.size() <= 10)
			return postsOfFollowed;
		List<Post> postsOfFeed = new LinkedList<>();
		// Vamos añadiendo los posts
		for(int i = postsOfFollowed.size() - POSTS_IN_FEED - 1; i < postsOfFollowed.size() - 1; i++) {
			postsOfFeed.add(postsOfFollowed.get(i));
		}
		return postsOfFeed;
	}
	
}
