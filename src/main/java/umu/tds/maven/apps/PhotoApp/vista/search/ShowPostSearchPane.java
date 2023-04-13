package umu.tds.maven.apps.PhotoApp.vista.search;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class ShowPostSearchPane extends ShowSearchPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int postId;
	private JLabel likesPost;

	public ShowPostSearchPane(int postId, SearchFrame frame) {
		super(frame);
		this.postId = postId;
		initialize();
	}

	@Override
	protected void createEastPane() {
		super.createEastPane();
		// mostrar likes,nombre de user,
		userNameLabel.setText(PhotoAppController.getInstance().getOwnerOfPhoto(postId));
		int likes = PhotoAppController.getInstance().getLikes(postId);
		// String user=PhotoAppController.getInstance().getOwnerOfPhoto(postId);
		likesPost = new JLabel(likes + " likes");
		likesPost.setAlignmentX(CENTER_ALIGNMENT);
		likesPost.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 10));
		Border margin = BorderFactory.createEmptyBorder(20, 0, 0, 0);
		likesPost.setBorder(margin);
		eastPane.add(likesPost);
	}

	@Override
	protected void createWestPane() {
		super.createWestPane();

		// Obtenemos el path de la foto de perfil del usuario
		try {
			String path = PhotoAppController.getInstance().getPath(postId);
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

	protected void addListeners() {
		this.addMouseListener(new ShowPostHandler(frame));

	}

	protected class ShowPostHandler extends MouseAdapter {
		private SearchFrame frame;

		public ShowPostHandler(SearchFrame frame) {
			this.frame = frame;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// Mostramos el perfil
			frame.showOtherPost(postId);
		}

	}

}
