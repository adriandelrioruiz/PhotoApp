package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import umu.tds.maven.apps.PhotoApp.controlador.Codes;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.UploadPhotoFrame;

/** Clase para mostrar un álbum mío */

@SuppressWarnings("serial")
public class ShowMyUploadedAlbumFrame extends ShowUploadedAlbumFrame {

	// Botón para eliminar álbum
	private JButton deleteAlbumButton;
	// Botón para añadir foto a álbum
	private JButton addPhotoToAlbumButton;
	// TODO Botón para eliminar foto de álbum

	public ShowMyUploadedAlbumFrame(int userId, int postId) {
		super(userId, postId);

	}

	@Override
	protected void createEastPane() {
		super.createEastPane();

		// Creamos el botón para comentar
		try {
			Image image = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "iconUploadPhoto.png"));
			ImageIcon addPhotoImage = new ImageIcon(image.getScaledInstance(DEFAULT_ICON_BUTTON_WIDTH,
					DEFAULT_ICON_BUTTON_HEIGHT, DO_NOTHING_ON_CLOSE));
			// Lo añadimos al panel oeste
			addPhotoToAlbumButton = new JButton(addPhotoImage);
			addPhotoToAlbumButton.setSize(DEFAULT_ICON_BUTTON_WIDTH, DEFAULT_ICON_BUTTON_HEIGHT);
			addPhotoToAlbumButton.setLocation(commentButton.getX() - DEFAULT_ICON_BUTTON_WIDTH - 10,
					commentButton.getY());
			addPhotoToAlbumButton.setLayout(null);
			eastPane.add(addPhotoToAlbumButton);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void createSouthPane() {
		super.createSouthPane();

		deleteAlbumButton = new JButton("Eliminar álbum");
		deleteAlbumButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		southPane.add(deleteAlbumButton);
	}

	@Override
	protected void addListeners() {
		super.addListeners();

		// Añadimos el listener para borrar el álbum
		deleteAlbumButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deleteAlbum(postId);
				JButton btnAceptar = new JButton("Aceptar");
				JOptionPane.showMessageDialog(btnAceptar, "El álbum se ha eliminado con éxito");
				LoggedFrame.getInstance().updateProfile();
				dispose();
			}
		});

		// Añadimos el listener para añadir foto al álbum
		addPhotoToAlbumButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String absolutePath = "";
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					absolutePath = fileChooser.getSelectedFile().toString();
					if (!UploadPhotoFrame.isValidImageFormat(absolutePath)) {
						JOptionPane.showMessageDialog(null, "Introduce una imagen válida", "Mensaje",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

				}
				// Añadimos la foto al álbum con el mismo título y descripción que el álbum
				Codes exit = controller.addPhotoToAlbum(controller.getPostTitle(postId),
						controller.getPostDescription(postId), absolutePath, postId);

				// éxito
				if (exit == Codes.OK) {
					JButton btnAceptar = new JButton("Aceptar");
					JOptionPane.showMessageDialog(btnAceptar, "La foto se ha añadido con éxito");
					LoggedFrame.getInstance().updateProfile();
					dispose();
				}

				if (exit == Codes.NUM_OF_PHOTOS_IN_ALBUM_EXCEEDED) {
					JButton btnAceptar = new JButton("Aceptar");
					JOptionPane.showMessageDialog(btnAceptar, "El álbum ha excedido el máximo de fotos");
					dispose();
				}

			}
		});
	}

}
