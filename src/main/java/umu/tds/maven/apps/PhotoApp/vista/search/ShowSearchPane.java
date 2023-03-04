package umu.tds.maven.apps.PhotoApp.vista.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

/**
 * Clase para representar cada objeto encontrado en la búsqueda. Es abstracta
 * porque de ella heredarán las clases para mostrar un post o un usuario. Esta
 * clase engloba la funcionalidad de ambas
 */

@SuppressWarnings("serial")
public abstract class ShowSearchPane extends JPanel {

	// Tamaño de los paneles

	// Panel oeste que contendrá la imagen
	protected JPanel westPane;

	// Panel este que contendrá la información del post o usuario
	protected JPanel eastPane;

	// Imagen que contendrá cada panel para mostrar bien la foto del usuario o del
	// post
	protected JLabel image;

	// Label para mostrar el nombre de usuario
	protected JLabel userNameLabel;
	
	// Frame en el que está el panel
	protected SearchFrame frame;

	public ShowSearchPane(SearchFrame frame) {
		this.frame = frame;
	}

	protected void initialize() {

		setLayout(new BorderLayout());
		setSize(300, 100);

		createWestPane();
		createEastPane();
		addListeners();
	}

	protected void createWestPane() {
		// Configuramos el panel oeste
		westPane = new JPanel();
		westPane.setLayout(null);
		westPane.setBackground(Color.WHITE);

		image = new JLabel();
		image.setLocation(10, 10);
		image.setSize(90, 90);
		image.setLocation(westPane.getX() + 5, westPane.getY() + 5);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		image.setBorder(border);

		westPane.add(image);

		westPane.setPreferredSize(new Dimension(100, 100));
		add(westPane, BorderLayout.WEST);
	}

	protected void createEastPane() {
		// Configuramos el panel este
		eastPane = new JPanel();
		eastPane.setLayout(new BoxLayout(eastPane, BoxLayout.Y_AXIS));
		eastPane.setBackground(Color.WHITE);

		userNameLabel = new JLabel();
		userNameLabel.setAlignmentX(CENTER_ALIGNMENT);
		userNameLabel.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 12));
		Border margin = BorderFactory.createEmptyBorder(10, 0, 0, 0);
		userNameLabel.setBorder(margin);
		eastPane.add(userNameLabel);

		eastPane.setPreferredSize(new Dimension(200, 100));
		add(eastPane, BorderLayout.EAST);

	}

	protected void addListeners() {
		image.addMouseListener(new ShowProfileHandler());
	}

	protected class ShowProfileHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// Mostramos el perfil
			
			// Cerramos el frame
			frame.dispose();
		}

	}
}
