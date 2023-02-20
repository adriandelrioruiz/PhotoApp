package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.hpsf.Array;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

@SuppressWarnings("serial")
public class ShowNewImagePane extends ShowImagePane {

	private static final String DEFAULT_COMMENT_TEXT = "Ingrese una descripción (Opcional)";
	private static final String DEFAULT_TITLE_TEXT = "Ingrese un título a la imagen";

	// JTextField para poner el título
	private JTextField tituloField;
	
	// Para saber si se trata de un álbum
	boolean isAlbum;

	public ShowNewImagePane(String path, Image image, boolean isAlbum) {
		super(image);
		this.path = path;
		this.isAlbum = isAlbum;
	}

	@Override
	protected void createImageTitlePane() {
		imageTitlePane = new JPanel();
		imageTitlePane.setAlignmentX(CENTER_ALIGNMENT);
		tituloField = new JTextField(DEFAULT_TITLE_TEXT);
		imageTitlePane.add(tituloField);
		tituloField.setFocusable(false);
		getContentPane().add(imageTitlePane);
		tituloField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!tituloField.isFocusable()) {
					tituloField.setFocusable(true);
					tituloField.grabFocus();
					tituloField.setText("");
				}
			}
		});
	}

	@Override
	protected void createImageAndCommentPane() {
		super.createImageAndCommentPane();
		comment.setText(DEFAULT_COMMENT_TEXT);
	}

	@Override
	protected void createExitButtonPane() {
		exitButtonPane = new JPanel();
		exitButtonPane.setAlignmentX(CENTER_ALIGNMENT);
		JButton botonSubirFoto = new JButton();
		exitButtonPane.add(botonSubirFoto);
		botonSubirFoto.setText("Subir foto");
		botonSubirFoto.setBackground(ViewConstants.APP_GREEN_COLOR);
		botonSubirFoto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tituloField.getText().equals(DEFAULT_TITLE_TEXT) || tituloField.getText().isEmpty())
					JOptionPane.showMessageDialog(new JButton("Aceptar"), "Introduce un título a tu foto");
				else {
					String descripcion;
					if (comment.equals(DEFAULT_COMMENT_TEXT))
						descripcion = "";
					else
						descripcion = comment.getText();
					
					// Distinguimos entre un álbum y una foto
					if (!isAlbum)
						controller.addPhoto(tituloField.getText(), descripcion, path);
					else {
						List<String> paths = new ArrayList<>();
						paths.add(path);
						controller.addAlbum(tituloField.getText(), descripcion, paths);
					}
					LoggedFrame.getInstance().updateProfile();
					dispose();
				}
					
			}
		});

		getContentPane().add(exitButtonPane, BorderLayout.SOUTH);
	}



}
