package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.HashMap;
import java.util.List;

import umu.tds.maven.apps.PhotoApp.persistencia.FactoriaDAO;

public class PostRepository {
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
	
}
