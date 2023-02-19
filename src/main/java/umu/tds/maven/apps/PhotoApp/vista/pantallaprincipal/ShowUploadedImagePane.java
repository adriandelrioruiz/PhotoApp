package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class ShowUploadedImagePane extends ShowImagePane {
	
	private static final String DEFAULT_COMMENT_TEXT = "Ingrese un comentario";

	public ShowUploadedImagePane(Image image) {
		super(image);
	}

	@Override
	protected void createExitButtonPane() {
		exitButtonPane = new JPanel();
		exitButtonPane.setAlignmentX(CENTER_ALIGNMENT);
		JButton botonAceptar = new JButton();
		exitButtonPane.add(botonAceptar);
		botonAceptar.setBackground(ViewConstants.APP_GREEN_COLOR);
		botonAceptar.setText("Salir");
		botonAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		exitButtonPane.setAlignmentX(CENTER_ALIGNMENT);
		JButton botonComentar = new JButton();
		exitButtonPane.add(botonComentar);
		botonComentar.setBackground(ViewConstants.APP_GREEN_COLOR);
		botonComentar.setText("Comentar");
		botonComentar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!comment.getText().equals(DEFAULT_COMMENT_TEXT) && !comment.getText().isEmpty()) {
					// Comentar en el controlador
					dispose();
				}
			}
		});
		
		
		getContentPane().add(exitButtonPane, BorderLayout.SOUTH);
	}
	
	@Override
	protected void createImageAndCommentPane() {
		super.createImageAndCommentPane();
		comment.setText(DEFAULT_COMMENT_TEXT);
	}
	
	@Override
	protected void createImageTitlePane() {}


}
