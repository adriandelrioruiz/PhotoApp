package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import javax.swing.JButton;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

/** Esta clase sirve para mostrar una foto subida mía */

@SuppressWarnings("serial")
public class ShowMyUploadedPhotoFrame extends ShowUploadedPhotoFrame {
	
	// Botón para eliminar una foto
	private JButton deletePhotoButton;

	public ShowMyUploadedPhotoFrame(int userId, int photoId) {
		super(userId, photoId);
		
		setVisible(true);
	}
	
	@Override
	protected void createSouthPane() {

		super.createSouthPane();
		
		deletePhotoButton = new JButton("Eliminar foto");
		deletePhotoButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		southPane.add(deletePhotoButton);
	}
	
}
