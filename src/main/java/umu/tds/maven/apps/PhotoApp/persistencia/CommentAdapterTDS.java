package umu.tds.maven.apps.PhotoApp.persistencia;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import umu.tds.maven.apps.PhotoApp.modelo.Comment;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.Post;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class CommentAdapterTDS extends AdapterTDS implements ICommentAdapterDAO {
	
	public static final String COMMENT = "comment";
	public static final String TEXT = "text";
	public static final String USER = "user";

	@Override
	protected Entidad objectToEntity(DomainObject o) {
		Comment comment =(Comment) o;
		Entidad eComment = new Entidad();

		eComment.setNombre(COMMENT);
		eComment.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(TEXT, comment.getText()),
						new Propiedad(USER, AdapterTDS.getCodesFromObjects(Arrays.asList(comment.getUser()))))));

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
	

}
