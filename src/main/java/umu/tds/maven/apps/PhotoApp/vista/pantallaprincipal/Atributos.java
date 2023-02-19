package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import org.w3c.dom.events.MouseEvent;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class Atributos extends JPanel {

	private static final String COMMENT_ICON = "icono_comentario.png";
	private static final String LIKE_ICON = "icono_like.png";
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_WIDTH = 60;
	/**
	 * Necesito: dividir en dos el jpanel el de arriba:iconos de like y comentario y
	 * likes el de abajo:foto de perfil de usuario con su nombre necesito saber la
	 * foto
	 */
	private static final long serialVersionUID = 1L;
	private JButton likeButton, commentButton, userfoto;
	private JLabel megustas, userimagen;
	private int likes;

	public Atributos(String usuario, int likes) {
		this.likes = likes;
		fixSize(this, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - PostPane.IMAGE_WIDTH, PostPane.PUBLICACION_HEIGHT);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
		// TOP PANEL
		JPanel topPanel = new JPanel();
		fixSize(topPanel, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - PostPane.IMAGE_WIDTH,
				PostPane.PUBLICACION_HEIGHT / 2);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		JButton likeButton = new JButton(ViewConstants.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, LIKE_ICON));
		// likeButton.setPreferredSize(new Dimension(60,BUTTON_HEIGHT));
		fixSize(likeButton, BUTTON_WIDTH, BUTTON_HEIGHT);
		LoggedFrame.setButton(likeButton);
		topPanel.add(likeButton);
		JButton commentButton = new JButton(ViewConstants.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, COMMENT_ICON));
		fixSize(commentButton, BUTTON_WIDTH, BUTTON_HEIGHT);
		LoggedFrame.setButton(commentButton);
		topPanel.add(commentButton);
		megustas = new JLabel(likes + " Me gustas");
		topPanel.add(megustas);
		this.add(topPanel, BorderLayout.NORTH);
		// bottomPanel
		JPanel bottomPanel = new JPanel();
		fixSize(bottomPanel, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - PostPane.IMAGE_WIDTH,
				PostPane.PUBLICACION_HEIGHT / 2);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

		userimagen = new JLabel(ViewConstants.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, COMMENT_ICON));
		userimagen.setText(usuario);
		bottomPanel.add(userimagen);
		this.add(bottomPanel, BorderLayout.SOUTH);

		likeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sumarLikes();
			}
		});
		commentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comentario();
			}
		});
	}

	private void comentario() {
		// Mostrar una ventana de di�logo para escribir un comentario
		String comment = JOptionPane.showInputDialog("Ingrese un comentario:");
		System.out.println("Comentario: " + comment);
	}

	private void sumarLikes() {
		likes++;
		megustas.setText(likes + " Me gustas");
	}

	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
	}

}
