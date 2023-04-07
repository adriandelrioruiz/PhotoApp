package umu.tds.maven.apps.PhotoApp.vista.feed;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

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
	private PostPane p;
	private JPanel northPanel, southPanel;
	private JButton likeButton, commentButton;
	private JLabel megustas, userimagen, username;
	private int likes, id;
	private String user;
	private PhotoAppController controller;

	public Atributos(int id, PostPane p) {
		this.controller = PhotoAppController.getInstance();
		this.id = id;
		this.user = controller.getOwnerOfPhoto(id);
		this.p = p;
		this.likes = controller.getPost(id).getLikes();
		fixSize(this, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - PostPane.IMAGE_WIDTH, PostPane.PUBLICACION_HEIGHT);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
		createNorthPanel();
		createSouthPanel();

		// TOP PANEL

		// bottomPanel

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
		userimagen.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// Mostrar perfil del user
				mostrarPerfil();
			}

		});
	}

	private void createSouthPanel() {
		southPanel = new JPanel();
		fixSize(southPanel, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - PostPane.IMAGE_WIDTH,
				PostPane.PUBLICACION_HEIGHT / 2);
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		userimagen = new JLabel();
		fixSize(userimagen, BUTTON_WIDTH, BUTTON_HEIGHT);
		String path = PhotoAppController.getInstance().getProfilePic(
				PhotoAppController.getInstance().getId(PhotoAppController.getInstance().getOwnerOfPhoto(id)));
		try {
			Image image = (ImageIO.read(new File(path))).getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT,
					Image.SCALE_SMOOTH);
			ImageIcon i = new ImageIcon(image);
			userimagen.setIcon(i);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		username = new JLabel(user);
		fixSize(username, BUTTON_WIDTH + 50, BUTTON_HEIGHT);
		southPanel.add(userimagen);
		southPanel.add(username);
		this.add(southPanel, BorderLayout.SOUTH);

	}

	private void createNorthPanel() {
		northPanel = new JPanel();
		fixSize(northPanel, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - PostPane.IMAGE_WIDTH,
				PostPane.PUBLICACION_HEIGHT / 2);
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		// boton like
		likeButton = new JButton(ViewConstants.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, LIKE_ICON));
		fixSize(likeButton, BUTTON_WIDTH, BUTTON_HEIGHT);
		LoggedFrame.setButton(likeButton);
		northPanel.add(likeButton);
		// boton comentario
		commentButton = new JButton(ViewConstants.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, COMMENT_ICON));
		fixSize(commentButton, BUTTON_WIDTH, BUTTON_HEIGHT);
		LoggedFrame.setButton(commentButton);
		northPanel.add(commentButton);
		megustas = new JLabel(likes + " Me gustas");
		northPanel.add(megustas);
		this.add(northPanel, BorderLayout.NORTH);

	}

	private void comentario() {
		// Mostrar una ventana de di�logo para escribir un comentario
		String comment = JOptionPane.showInputDialog("Ingrese un comentario:");
		if (!comment.isEmpty()) {
			controller.comment(id, comment);
			System.out.println("Comentario: " + comment);
		}
	}

	private void sumarLikes() {
		actualizarLikes();
		controller.like(id);
	}
	
	public void actualizarLikes() {
		likes++;
		megustas.setText(likes + " Me gustas");
	}

	private void mostrarPerfil() {
		this.p.changeOtherProfile(controller.getId(user));

	}

	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
	}

}
