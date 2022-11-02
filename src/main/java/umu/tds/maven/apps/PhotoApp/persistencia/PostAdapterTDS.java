package umu.tds.maven.apps.PhotoApp.persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.Post;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class PostAdapterTDS extends AdapterTDS implements IPostAdapterDAO {
	
	private static final String POST = "post";
	private static final String TITLE = "title";
	private static final String DATE = "date";
	private static final String DESCRIPTION = "description";
	private static final String LIKES = "likes";
	private static final String HASHTAGS = "hashtags";
	private static final String COMMENTS = "comments";
	
	private static PostAdapterTDS instance;
	private SimpleDateFormat dateFormat;
	
	public static PostAdapterTDS getInstance() {
		if (instance == null)
			instance = new PostAdapterTDS();
		return instance;
	}
	
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post getPost(int code) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// TODO ver si puedo reutilizar código y reescribir en AdapterTDS
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
	}
	
	static String getCodesFromAllPosts(List<Post> posts) {
		String codes = "";
		for (Post post : posts) {
			codes += post.getCode() + " ";
		}
		return codes.trim();
	}

}
