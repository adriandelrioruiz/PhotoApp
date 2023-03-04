package umu.tds.maven.apps.PhotoApp.vista.search;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

/** Clase para mostrar un resultado de la búsqueda de un usuario */

@SuppressWarnings("serial")
public class ShowUserSearchPane extends ShowSearchPane {

	// Label para mostrar los seguidores del usuario
	private JLabel userFollowersLabel;

	// id del usuario
	private int userId;

	public ShowUserSearchPane(int userId, SearchFrame frame) {
		super(frame);
		this.userId = userId;

		initialize();
	}

	@Override
	protected void createEastPane() {
		super.createEastPane();
		
		userNameLabel.setText(PhotoAppController.getInstance().getUserName(userId));
		
		String numOfFollowers = String.valueOf(PhotoAppController.getInstance().getFollowers(userId));
		userFollowersLabel = new JLabel(numOfFollowers + " seguidores");
		userFollowersLabel.setAlignmentX(CENTER_ALIGNMENT);
		userFollowersLabel.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 10));
		Border margin = BorderFactory.createEmptyBorder(20, 0, 0, 0);
		userFollowersLabel.setBorder(margin);
		eastPane.add(userFollowersLabel);
	}

	@Override
	protected void createWestPane() {
		super.createWestPane();

		// Obtenemos el path de la foto de perfil del usuario
		try {
			String path = PhotoAppController.getInstance().getProfilePic(userId);
			Image image = ImageIO.read(new File(path));
			ImageIcon icon = new ImageIcon(
					image.getScaledInstance((int) this.image.getWidth() - 20, (int) this.image.getHeight() - 60, 0));
			// Lo añadimos al panel oeste
			this.image.setIcon(icon);
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
