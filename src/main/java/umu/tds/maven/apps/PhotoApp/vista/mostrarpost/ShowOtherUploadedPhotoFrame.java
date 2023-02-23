package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

/** Clase utilizada para mostrar un álbum de otro usuario */

@SuppressWarnings("serial")
public class ShowOtherUploadedPhotoFrame extends ShowUploadedPhotoFrame{
	
	// Botón para dar like a la foto
	private JButton likePhotoButton;

	public ShowOtherUploadedPhotoFrame(int userId, int photoId) {
		super(userId, photoId);
		
		initialize();
		
		setVisible(true);
	}
	
	@Override
	protected void createEastPane() {
		super.createEastPane();
		
		// Creamos el botón para dar like a la foto
		try {
			Image image = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "icono_like.png"));
			ImageIcon likePhotoImage = new ImageIcon(image.getScaledInstance(DEFAULT_ICON_BUTTON_WIDTH, DEFAULT_ICON_BUTTON_HEIGHT, DO_NOTHING_ON_CLOSE));
			// Lo añadimos al panel oeste
			likePhotoButton = new JButton(likePhotoImage);
			likePhotoButton.setSize(DEFAULT_ICON_BUTTON_WIDTH, DEFAULT_ICON_BUTTON_HEIGHT);
			likePhotoButton.setLocation(commentButton.getX() - DEFAULT_ICON_BUTTON_WIDTH - 10, commentButton.getY());
			likePhotoButton.setLayout(null);
			eastPane.add(likePhotoButton);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void addListeners() {
		super.addListeners();
		
		// Añadimos el listener para dar like
		likePhotoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Damos like a la foto
				controller.like(postId);
				JButton btnAceptar = new JButton("Aceptar");
				JOptionPane.showMessageDialog(btnAceptar, "Has dado like a la foto");
				commentTxtArea.setText(DEFAULT_COMMENT_TEXT);
			}
		});
	}

}
