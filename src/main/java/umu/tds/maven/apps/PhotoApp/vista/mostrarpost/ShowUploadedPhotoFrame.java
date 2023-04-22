package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

/** Clase para mostrar una foto que ya está subida, permite comentar en ella */

@SuppressWarnings("serial")
public abstract class ShowUploadedPhotoFrame extends ShowUploadedPostFrame {

	// JLabel para mostrar la foto
	private JLabel imageLabel;

	// JLabel para mostrar el número de likes de la foto
	protected JLabel nLikes;

	public ShowUploadedPhotoFrame(int userId, int photoId) {
		super(userId, photoId);
		initialize();
		verComentarios.addMouseListener(new ShowCommentsHandler(photoId));
	}

	@Override
	protected void createWestPane() {

		super.createWestPane();

		try {
			Image image = ImageIO.read(new File(controller.getPath(postId)));
			ImageIcon postImage = new ImageIcon(image.getScaledInstance((int) WEST_PANEL_DIMENSION.getWidth() - 20,
					(int) WEST_PANEL_DIMENSION.getHeight() - 60, DO_NOTHING_ON_CLOSE));
			// Lo añadimos al panel oeste
			imageLabel = new JLabel(postImage);
			imageLabel.setSize(330, 340);
			imageLabel.setLocation(20, 10);
			imageLabel.setLayout(null);
		} catch (IOException e) {

			e.printStackTrace();
		}

		westPane.add(imageLabel);
	}

	@Override
	protected void createEastPane() {
		super.createEastPane();
		// Añadimos el Label
		nLikes = new JLabel(DEFAULT_NLIKES_TEXT + controller.getLikes(postId));
		nLikes.setBackground(Color.LIGHT_GRAY);
		nLikes.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 14));
		nLikes.setLocation(eastPane.getX() + 100, commentButton.getY() + commentButton.getHeight() + 20);
		nLikes.setSize(200, 20);
		eastPane.add(nLikes);
	}

	@Override
	protected void comment() {
		if (controller.comment(postId, commentTxtArea.getText())) {
			JButton btnAceptar = new JButton("Aceptar");
			JOptionPane.showMessageDialog(btnAceptar, "El comentario se ha registrado con éxito");
			commentTxtArea.setText(DEFAULT_COMMENT_TEXT);
		}
	}
	protected class ShowCommentsHandler extends MouseAdapter {
		private int postId;

		public  ShowCommentsHandler(int id) {
			this.postId=id;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			new CommentFrame(postId);
		}
	}

}
