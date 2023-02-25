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

/** Clase utilizada para mostrar una foto de otro usuario */

@SuppressWarnings("serial")
public class ShowOtherUploadedAlbumFrame extends ShowUploadedAlbumFrame {

	// Botón para dar like a la foto actual
	private JButton likePhotoButton;
	
	// Botón para dar like al álbum
	private JButton likeAlbumButton;

	public ShowOtherUploadedAlbumFrame(int userId, int photoId) {
			super(userId, photoId);
			
			initialize();
			
			setVisible(true);
		}

	@Override
	protected void createSouthPane() {
		super.createSouthPane();

		// Creamos el botón para dar like a la foto actual
		likeAlbumButton = new JButton("Dar like al álbum");
		likeAlbumButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		southPane.add(likeAlbumButton);
	}
	
	@Override
	protected void createEastPane() {
		super.createEastPane();
		
		// Creamos el botón para dar like al álbum
		try {
			Image image = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "icono_like.png"));
			ImageIcon likePhotoImage = new ImageIcon(image.getScaledInstance(DEFAULT_ICON_BUTTON_WIDTH,
					DEFAULT_ICON_BUTTON_HEIGHT, DO_NOTHING_ON_CLOSE));
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

		// Añadimos el listener para dar like a la foto actual
		likePhotoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Damos like a la foto
				controller.like(photoIdList.get(photoCounter));
				nPhotoLikes.setText(DEFAULT_NLIKES_TEXT + controller.getLikes(photoIdList.get(photoCounter))); 
				JButton btnAceptar = new JButton("Aceptar");
				JOptionPane.showMessageDialog(btnAceptar, "Has dado like a la foto");
				commentTxtArea.setText(DEFAULT_COMMENT_TEXT);
			}
		});
		
		// Añadimos el listener para dar like al álbum
		likeAlbumButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Damos like al album
				controller.like(postId);
				
				// Actualizamos la JLabel que muestra los likes del álbum
				nAlbumLikes.setText(DEFAULT_NALBUMLIKES_TEXT + controller.getLikes(postId));
				
				// Actualizamos la JLabel que muestra los likes de la foto
				nPhotoLikes.setText(DEFAULT_NLIKES_TEXT + controller.getLikes(photoIdList.get(photoCounter)));
				
				// Mostramos ventana de diálogo
				JButton btnAceptar = new JButton("Aceptar");
				JOptionPane.showMessageDialog(btnAceptar, "Has dado like al álbum");
			}
		});
	}

}
