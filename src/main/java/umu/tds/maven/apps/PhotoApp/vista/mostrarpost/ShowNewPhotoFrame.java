package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.eventoscomunes.SetDefaultTextListener;
import umu.tds.maven.apps.PhotoApp.vista.eventoscomunes.SetEmptyTextListener;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

/**
 * Esta clase hereda de ShowNewPostFrame y se utiliza para mostrar una foto que
 * va a ser subida
 */

@SuppressWarnings("serial")
public class ShowNewPhotoFrame extends ShowNewPostFrame {

	private static final String DEFAULT_FRAME_NAME = "Subir una foto";

	private static final String DEFAULT_TITULO_TEXT = "Ponle un título a tu foto";

	public ShowNewPhotoFrame(int userId, String path) {
		super(userId, path);

	}

	@Override
	protected void initialize() {
		super.initialize();

		setTitle(DEFAULT_FRAME_NAME);
		setVisible(true);
	}

	@Override
	protected void createNorthPane() {
		super.createNorthPane();

		txtTitulo.setText(DEFAULT_TITULO_TEXT);
		txtTitulo.setForeground(Color.GRAY);
		txtTitulo.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 14));
	}

	@Override
	protected void addListeners() {
		super.addListeners();

		txtTitulo.addMouseListener(new SetEmptyTextListener(DEFAULT_TITULO_TEXT, txtTitulo));
		txtTitulo.addFocusListener(new SetDefaultTextListener(DEFAULT_TITULO_TEXT, txtTitulo));

		shareButton.addActionListener(new SharePhotoButtonListener());

	}

	class SharePhotoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Comprobamos que esten rellenos los campos
			if (!txtTitulo.getText().isEmpty() && !commentTxtArea.getText().isEmpty()
					&& !txtTitulo.getText().equals(DEFAULT_TITULO_TEXT)
					&& !commentTxtArea.getText().equals(DEFAULT_COMMENT_TEXT)) {

				controller.addPhoto(txtTitulo.getText(), commentTxtArea.getText(), path);
				JButton btnAceptar = new JButton("Aceptar");
				JOptionPane.showMessageDialog(btnAceptar, "La foto se ha subido con éxito");
				// Para refrescar la ventana
				LoggedFrame.getInstance().updateProfile();
				dispose();
			}
		}

	}

}
