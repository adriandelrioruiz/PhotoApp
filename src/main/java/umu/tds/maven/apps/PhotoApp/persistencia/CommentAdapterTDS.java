package umu.tds.maven.apps.PhotoApp.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import umu.tds.maven.apps.PhotoApp.modelo.Comment;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class CommentAdapterTDS extends AdapterTDS implements ICommentAdapterDAO {
	
	public static final String COMMENT = "comment";
	public static final String TEXT = "text";
	public static final String USER = "user";
	
	private static CommentAdapterTDS instance;
	
	public static CommentAdapterTDS getInstance() {
		if (instance == null)
			instance = new CommentAdapterTDS();
		return instance;
	}
	
	public CommentAdapterTDS() {
		super();
	}

	@Override
	protected Entidad objectToEntity(DomainObject o) {
		Comment comment =(Comment) o;
		Entidad eComment = new Entidad();

		eComment.setNombre(COMMENT);
		eComment.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(TEXT, comment.getText()),
						new Propiedad(USER, UserAdapterTDS.getInstance().getCodesFromAllUsers(Arrays.asList(comment.getUser()))))));

		return eComment;
	}

	@Override
	protected DomainObject entityToObject(Entidad en) {
		// Estos serán los atributos del Comment que queremos recuperar
		String text;
		User user;
		
		// Recuperamos los atributos de Comment de la persistencia
		text = servPersistencia.recuperarPropiedadEntidad(en, TEXT);

		// Recuperamos al usuario
		user = UserAdapterTDS.getInstance().getUser(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(en, USER)));
		
		// Creamos el objeto Comment a partir de los atributos recuperados de la persistencia
		Comment comment = new Comment(text, user);
		comment.setCode(en.getId());

		return comment;
	}
	
	@Override
	public Comment getComment(int id) {
		
		Entidad eComment;

		// Recuperamos la entidad
		eComment = servPersistencia.recuperarEntidad(id);
		// Convertimos la entidad en un objeto usuario
		Comment comment = (Comment) entityToObject(eComment);

		return comment;
	}

	@Override
	public void addComment(Comment comment) {
		
		// Si el comentario ya está registrado, no se registra
		Entidad eComment = null;
		try {
			eComment = servPersistencia.recuperarEntidad(comment.getCode());
		} catch (NullPointerException e) {
		}
		if (eComment != null)
			return;

		// Crear entidad comentario
		eComment = objectToEntity(comment);
		// registrar entidad user
		eComment = servPersistencia.registrarEntidad(eComment);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		comment.setCode(eComment.getId());
		
	}

	@Override
	public void deleteComment(int id) {
		// TODO Auto-generated method stub
		
	}
	
	public String getCodesFromComments(List<Comment> comments) {
		String codes = "";
		for (Comment comment : comments) {
			codes += comment.getCode() + " ";
		}
		return codes.trim();
	}
	
	public List<Comment> getCommentsFromCodes(String codes) {
		List<Comment> commentList = new LinkedList<>();
		if (codes != null && !codes.isEmpty()) {
			StringTokenizer strTok = new StringTokenizer(codes, " ");
			CommentAdapterTDS adapter = getInstance();
			while (strTok.hasMoreTokens()) {
				commentList.add(adapter.getComment(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return commentList;
	}
	

}
