package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.eventoscomunes.SetDefaultTextListener;
import umu.tds.maven.apps.PhotoApp.vista.eventoscomunes.SetEmptyTextListener;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

/** Clase para mostrar una ventana con un post que ya está subido */

@SuppressWarnings("serial")
public abstract class ShowUploadedPostFrame extends ShowPostFrame {

	protected static final String DEFAULT_COMMENT_TEXT = "Introduce un comentario...";
	protected static final String DEFAULT_NLIKES_TEXT = "Número de likes: ";
	protected static final String DEFAULT_NALBUMLIKES_TEXT = "Número de likes del álbum: ";

	protected static final int DEFAULT_ICON_BUTTON_WIDTH = 38;
	protected static final int DEFAULT_ICON_BUTTON_HEIGHT = DEFAULT_ICON_BUTTON_WIDTH;

	// Id del post que queramos mostrar
	protected int postId;

	// Botón para comentar en el post
	protected JButton commentButton;

	// Label para mostrar el título del post
	private JLabel titleLabel;

	public ShowUploadedPostFrame(int userId, int postId) {
		super(userId);

		this.postId = postId;
	}

	@Override
	protected void initialize() {

		super.initialize();
	}

	@Override
	protected void createNorthPane() {
		super.createNorthPane();
		titleLabel = new JLabel(controller.getPostTitle(postId));
		titleLabel.setForeground(Color.DARK_GRAY);
		titleLabel.setFont(new Font(ViewConstants.APP_FONT, Font.BOLD, 14));
		northPane.add(titleLabel);
	}

	@Override
	protected void createEastPane() {
		super.createEastPane();

		commentTxtArea.setText("Introduce un comentario...");
		commentTxtArea.setPreferredSize(new Dimension(commentTxtArea.getWidth(), 100));

		// Creamos el botón para comentar
		try {
			Image image = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "icono_comentario.png"));
			ImageIcon commentImage = new ImageIcon(image.getScaledInstance(DEFAULT_ICON_BUTTON_WIDTH,
					DEFAULT_ICON_BUTTON_HEIGHT, DO_NOTHING_ON_CLOSE));
			// Lo añadimos al panel oeste
			commentButton = new JButton(commentImage);
			commentButton.setSize(DEFAULT_ICON_BUTTON_WIDTH, DEFAULT_ICON_BUTTON_HEIGHT);
			commentButton.setLocation(252, 206);
			commentButton.setLayout(null);
			eastPane.add(commentButton);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void addListeners() {
		super.addListeners();

		commentTxtArea.addMouseListener(new SetEmptyTextListener(DEFAULT_COMMENT_TEXT, commentTxtArea));
		commentTxtArea.addFocusListener(new SetDefaultTextListener(DEFAULT_COMMENT_TEXT, commentTxtArea));

		commentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Si el texto del commentTxtArea no es vacío ni el por defecto, comentamos
				if (!commentTxtArea.getText().equals(DEFAULT_COMMENT_TEXT) && !commentTxtArea.getText().isEmpty())
					comment();
			}
		});
		
	}

	@Override
	protected void createWestPane() {
		super.createWestPane();

	}

	// Método usado para comentar, bien en una foto o en un álbum
	protected abstract void comment();

}
