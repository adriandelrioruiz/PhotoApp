package umu.tds.maven.apps.PhotoApp.vista;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class ViewConstants {
	
	// Nombre ventanas
	static final String WINDOWS_TITLE = "PhotoApp";

	// Colores
	static final Color APP_GREEN_COLOR = new Color(64, 128, 128);
	
	// Fuentes
	static final String APP_FONT = "Microsoft Sans Serif";
	
	// Register and Login window
	static final String USER_DEFAULT_TEXT = "nombre de usuario";
	static final String EMAIL_DEFAULT_TEXT = "email";
	static final String USEM_DEFAULT_TEXT = "nombre de usuario o email";
	static final String PASSWORD_DEFAULT_TEXT = "contrasena";
	static final String FULLNAME_DEFAULT_TEXT = "nombre completo";
	static final String DESCRIBE_YOURSELF_TEXT = "Descríbete";
	static final String CHOOSE_AN_IMAGE_TEXT = "Seleccionar imagen";
	static final String NOT_REGISTERED_TEXT = "¿Aún no te has registrado?";
	static final String ALREADY_REGISTERED_TEXT = "¿Ya tienes una cuenta?";
	static final String REGISTER_TEXT = "Registrarse";
	static final String LOGIN_TEXT = "Login";
	
	// SetBioFrame
	static final int BIO_LENGTH = 200;
	static final String BIO_DIALOG_LENGHT_EXCEEDED = "Máximo " + String.valueOf(BIO_LENGTH)+" caracteres";
	static final String BIO_DEFAULT_TEXT = "Descríbete (" + BIO_DIALOG_LENGHT_EXCEEDED + ")";
	
	//
	static final String RUTA_FOTOS="C:\\Users\\elcrio\\git\\PhotoApp\\img\\";
	 static ImageIcon getIcon(int width,int height,String filename ) {
		Image image;
		image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\elcrio\\git\\PhotoApp\\img\\"+filename);
		image = Toolkit.getDefaultToolkit().getImage(ViewConstants.RUTA_FOTOS+filename);
		// Escalar la imagen a un tamaño específico
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		// Crear un ImageIcon a partir de la imagen escalada
		return new ImageIcon(image);
	}		
	
	
}
