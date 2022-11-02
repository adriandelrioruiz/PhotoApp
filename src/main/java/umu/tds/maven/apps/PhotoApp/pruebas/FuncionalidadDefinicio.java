package umu.tds.maven.apps.PhotoApp.pruebas;

import java.util.List;
import java.util.Map;

import umu.tds.maven.apps.PhotoApp.modelo.Post;

public abstract class FuncionalidadDefinicio {
	
	// CONTROLADOR
	
		// Cambiar foto de perfil
		public abstract void changeProfilePic(String newPhoto);
		// Cambiar bio
		public abstract void changeBio(String newBio);
		// Cambiar contraseña
		public abstract void changePassword(String newPassword);
		// Compartir foto, habrá que mandar una notificación a los seguidores. Desta
		// foto habrá tb que extraer los hashtags. 
		public abstract void sharePhoto(String photo, String comment);
		// Follow user
		public abstract void followUser(String userName);
		// Darle me gusta a una foto de un usuario
		public abstract void like(String photo, String userName);
		// Obtener los hashtags que contienen una palabra
		public abstract Map<String, Integer> getHashtags(String search);
		// Comentar en una foto
		public abstract void comment(String photo, String userName);
		// Obtener últimas 10 fotos en las que te han dado me gusta
		public abstract void lastTenPhotos();
		// Eliminar foto
		public abstract void deletePhoto(String photo);
		// Obtener publicaciones
		public abstract List<Post> getPosts();
		// Cambiar foto, presentación o contraseña
		public abstract void changeUserInformation(String password, String profilePic, String bio);
		
	// MODELO
		
	// PERSISTENCIA
		
		// Eliminar foto de un usuario
		public abstract void deletePhoto(String photo, String username);
		
		
		
		
				
				
}
