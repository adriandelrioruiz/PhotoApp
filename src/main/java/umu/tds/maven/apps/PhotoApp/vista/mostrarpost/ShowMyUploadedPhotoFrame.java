package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

/** Esta clase sirve para mostrar una foto subida mía */

@SuppressWarnings("serial")
public class ShowMyUploadedPhotoFrame extends ShowUploadedPhotoFrame {
<<<<<<< HEAD
	
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
	
	@Override
	protected void addListeners() {

		super.addListeners();
		
		// Añadimos el listener para eliminar una foto
		deletePhotoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deletePhoto(postId);
				JButton btnAceptar = new JButton("Aceptar");
				JOptionPane.showMessageDialog(btnAceptar, "La foto se ha eliminado con éxito");
				LoggedFrame.getInstance().updateProfile();
				dispose();
			}
		});
	}
	
=======

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

	@Override
	protected void addListeners() {

		super.addListeners();

		// Añadimos el listener para eliminar una foto
		deletePhotoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deletePhoto(postId);
				JButton btnAceptar = new JButton("Aceptar");
				JOptionPane.showMessageDialog(btnAceptar, "La foto se ha eliminado con éxito");
				LoggedFrame.getInstance().updateProfile();
				dispose();
			}
		});
	}

>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
}
