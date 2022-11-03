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

public abstract class PostAdapterTDS extends AdapterTDS implements IPostAdapterDAO {
	
	private static final String POST = "post";
	private static final String TITLE = "title";
	private static final String DATE = "date";
	private static final String DESCRIPTION = "description";
	private static final String LIKES = "likes";
	private static final String HASHTAGS = "hashtags";
	private static final String COMMENTS = "comments";
	

	private SimpleDateFormat dateFormat;
	
	
	public PostAdapterTDS() {	
		super();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Override
	protected Entidad objectToEntity(DomainObject o) {
		Entidad ePost = new Entidad();
		Post post = (Post) o;

		ePost.setNombre(POST);
		ePost.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(TITLE, post.getTitle()), new Propiedad(DATE, dateFormat.format(post.getDate())),
						new Propiedad(DESCRIPTION, post.getDescription()), new Propiedad(LIKES, String.valueOf(post.getLikes())))));

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
		
		// Creamos el objeto Post a partir de los atributos recuperados de la persistencia
		Post post = new Post(title, date, description, likes);
		// Le damos el código que le ha asignado la base de datos a nuestro post;
		post.setCode(en.getId());
		
		PoolDAO.getInstance().addObject(en.getId(), post);
		
		// Recuperamos los atributos que son listas 
		/*List<String> hashtags = getAllStringsFromCodes(servPersistencia.recuperarPropiedadEntidad(en, HASHTAGS));
		List<Comment> comments = getAllCommentsFromCodes(servPersistencia.recuperarPropiedadEntidad(en, COMMENTS));
	
		
		post.setHashtags(hashtags);
		post.setComments(comments);*/

		return post;
	}
	
	

	@Override
	public Post getPost(int code) {
		Entidad ePost;
		Post post = null;

		// Recuperamos la entidad
		ePost = servPersistencia.recuperarEntidad(code);
		// Convertimos la entidad en un objeto usuario
		try {
			post = (Post) entityToObject(ePost);
			
		} catch (NullPointerException e) {
			System.out.println("El post con el id " + code + " no está registrado");
		}
		return post;
	}
	
	@Override
	public void addPost(Post post) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deletePost(int code) {
		// TODO Auto-generated method stub
		
	}
	
	/*// TODO ver si puedo reutilizar código y reescribir en AdapterTDS
	// Usamos esta función para obtener posts a través de un string con varios códigos
	static List<Post> getAllPostsFromCodes(String codes) {
		List<Post> postList = new LinkedList<Post>();
		if (codes != null) {
			StringTokenizer strTok = new StringTokenizer(codes, " ");
			PostAdapterTDS adapter = getInstance();
			while (strTok.hasMoreTokens()) {
				postList.add(adapter.getPost(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return postList;
	}*/
	


	public List<Post> getAllPosts() {
		List<Entidad> entities = servPersistencia.recuperarEntidades(POST);

		List<Post> users = new LinkedList<Post>();
		for (Entidad ePost : entities) {
			users.add(getPost(ePost.getId()));
		}

		return users;
	}

}
