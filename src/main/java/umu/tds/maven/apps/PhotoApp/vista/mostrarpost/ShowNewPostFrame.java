package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.eventoscomunes.SetDefaultTextListener;
import umu.tds.maven.apps.PhotoApp.vista.eventoscomunes.SetEmptyTextListener;

@SuppressWarnings("serial")

/**
 * Esta clase hereda de ShowPostFrame y se utiliza para mostrar un post que va a
 * ser subido
 */

public abstract class ShowNewPostFrame extends ShowPostFrame {

	protected static final String DEFAULT_COMMENT_TEXT = "Añade una descripción...";

	// Path del nuevo post que se va a subir
	protected String path;

	// JTextField para introducir el título de la foto o el álbum
	protected JTextField txtTitulo;

	// JLabel que contiene la imagen del post que se va a subir
	private JLabel imageLabel;

	// JButton para compartir el post
	protected JButton shareButton;

	public ShowNewPostFrame(int userId, String path) {
		super(userId);

		this.path = path;

		initialize();

	}

	@Override
	protected void initialize() {

		super.initialize();

	}

	@Override
	protected void createNorthPane() {
		super.createNorthPane();

		// Creamos el JTextField para introducir el título del post que queremos subir
		txtTitulo = new JTextField();
		txtTitulo.setFocusable(false);
		txtTitulo.setBorder(BorderFactory.createCompoundBorder(txtTitulo.getBorder(),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		northPane.setLayout(new BorderLayout());
		northPane.add(txtTitulo, BorderLayout.CENTER);

	}

	@Override
	protected void createWestPane() {

		super.createWestPane();

		try {
			Image image = ImageIO.read(new File(path));
			ImageIcon postImage = new ImageIcon(image.getScaledInstance((int) WEST_PANEL_DIMENSION.getWidth() - 20,
					(int) WEST_PANEL_DIMENSION.getHeight() - 40, DO_NOTHING_ON_CLOSE));
			// Lo añadimos al panel oeste
			imageLabel = new JLabel(postImage);
			imageLabel.setSize(330, 363);
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

		commentTxtArea.setText(DEFAULT_COMMENT_TEXT);

	}

	@Override
	protected void createSouthPane() {
		super.createSouthPane();

		shareButton = new JButton("Compartir");
		shareButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		southPane.add(shareButton);

	}

	@Override
	protected void addListeners() {
		super.addListeners();

		commentTxtArea.addMouseListener(new SetEmptyTextListener(DEFAULT_COMMENT_TEXT, commentTxtArea));
		commentTxtArea.addFocusListener(new SetDefaultTextListener(DEFAULT_COMMENT_TEXT, commentTxtArea));
	}
}
