package umu.tds.maven.apps.PhotoApp.vista.constantes;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class ViewConstants {

	// Window titles
	public static final String WINDOWS_TITLE = "PhotoApp";

	// Colors
	public static final Color APP_GREEN_COLOR = new Color(0, 128, 128);

	// Fonts
	public static final String APP_FONT = "Microsoft Sans Serif";

	// Register and Login windows
	public static final String USER_DEFAULT_TEXT = "nombre de usuario";
	public static final String EMAIL_DEFAULT_TEXT = "email";
	public static final String USEM_DEFAULT_TEXT = "nombre de usuario o email";
	public static final String PASSWORD_DEFAULT_TEXT = "contrasena";
	public static final String FULLNAME_DEFAULT_TEXT = "nombre completo";
	public static final String DESCRIBE_YOURSELF_TEXT = "Descríbete";
	public static final String CHOOSE_AN_IMAGE_TEXT = "Seleccionar imagen";
	public static final String NOT_REGISTERED_TEXT = "¿Aún no te has registrado?";
	public static final String ALREADY_REGISTERED_TEXT = "¿Ya tienes una cuenta?";
	public static final String REGISTER_TEXT = "Registrarse";
	public static final String LOGIN_TEXT = "Login";

	// SetBioFrame
	public static final int BIO_LENGTH = 200;
	public static final String BIO_DIALOG_LENGHT_EXCEEDED = "Máximo " + String.valueOf(BIO_LENGTH) + " caracteres";
	public static final String BIO_DEFAULT_TEXT = "Descríbete (" + BIO_DIALOG_LENGHT_EXCEEDED + ")";

	// LoggedFrame
	public static final int LOGGEDFRAME_WINDOW_WIDTH = 800;
	public static final int LOGGEDFRAME_WINDOW_HEIGHT = 800;

	//
	public static String RUTA_FOTOS = "C:\\Users\\adria\\eclipse-workspace\\PhotoApp\\img\\";
	public static String RUTA_FOTOS_USER = "C:\\Users\\adria\\eclipse-workspace\\PhotoApp\\img_users\\";
	public static ImageIcon getIcon(int width, int height, String filename) {
		Image image;
		image = Toolkit.getDefaultToolkit().getImage(ViewConstants.RUTA_FOTOS + filename);
		// Scale the image to a specific size
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		// Create an ImageIcon from the scaled image
		return new ImageIcon(image);
	}
}
