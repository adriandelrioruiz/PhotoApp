package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class MenuPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int TITULO_WIDTH = 250;
	private static final int BUTTON_WIDTH = 30;
	private static final int BUTTON_HEIGHT = 30;
	private static final int USER_PHOTO_HEIGHT = 50;
	private static final int USER_PHOTO_WIDTH = 50;
	public static final int MENU_HEIGHT = 90;
	private static final int SEARCH_HEIGHT = 25;
	private static final int SEARCH_WIDTH = 100;
	
	private JTextField txtTexto;
	private JButton uploadButton, lupaButton, userButton, premiumButton, titulo;
	private ImageIcon image;
	private LoggedFrame frame;

	// PARTE DE ARRIBA DE LA PANTALLA
	public MenuPane(LoggedFrame frame) {
		this.frame = frame;
		initialize();
	}

	private void initialize() {

		setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, MENU_HEIGHT));
		this.setBackground(Color.WHITE);

		// Nombre de la aplicación
		titulo = new JButton("PhotoTDS");
		titulo.setFont(new Font("Book Antiqua", Font.BOLD, 22));
		this.setButton(titulo, 0, TITULO_WIDTH, MENU_HEIGHT);

		// Botón para subir foto
		uploadButton = new JButton(this.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, "iconUploadPhoto.png"));
		this.setButton(uploadButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 2 - 100, BUTTON_WIDTH, BUTTON_HEIGHT);

		// Buscar
		txtTexto = new JTextField();
		txtTexto.setAlignmentX(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 2 - 50);
		LoggedFrame.fixSize(txtTexto, SEARCH_WIDTH, SEARCH_HEIGHT);
		txtTexto.setText("Search");
		txtTexto.setFont(new Font("Book Antiqua", Font.BOLD, 12));
		txtTexto.setEditable(true);
		txtTexto.setOpaque(true);
		this.add(txtTexto);
		txtTexto.setColumns(10);
		// Bonton lupa
		lupaButton = new JButton(this.getIcon(BUTTON_WIDTH, SEARCH_HEIGHT, "icon_lupa.png"));
		this.setButton(lupaButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 2 + 50, BUTTON_WIDTH, BUTTON_HEIGHT);
		// FOTO USER
		// PhotoAppController.getInstance().getProfilePic();
		// image=this.getIcon(USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT, "raro.png");
		userButton = new JButton(this.getIcon(USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT, "default-profpic.png"));
		this.setButton(userButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - 100, USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT);
		// BOTON PREMIUN
		premiumButton = new JButton(this.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, "icon_tres_lineas.png"));
		setButton(premiumButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - 50, BUTTON_WIDTH, BUTTON_HEIGHT);

		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Mostrar una ventana de diálogo para SUBIR FOTO
				UploadPhotoFrame cargar = new UploadPhotoFrame();
			}
		});
		lupaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = txtTexto.getText();
				System.out.println("query: " + query);
				new SearchFrame(query);
			}
		});
		userButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = new User("Adrian del Rio", "adri@gmail", "adriandelrio", "password", new Date(), false, "myPhoto", "myBio");
				frame.changeToProfilePanel();
			}
		});

		premiumButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (PhotoAppController.getInstance().isPremium()) {
					new PremiumFrame();// llamar a la clase ventana premium para mostrar frame
				} else {
					new DescuentoFrame();
				}

			}
		});
		
		titulo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeToFeedPanel();
			}
		});
	}

	private ImageIcon getIcon(int width, int height, String filename) {
		Image image;
		image = Toolkit.getDefaultToolkit().getImage(ViewConstants.RUTA_FOTOS + filename);
		// Escalar la imagen a un tamaño específico
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		// Crear un ImageIcon a partir de la imagen escalada
		return new ImageIcon(image);
	}

	private void setComponent(JComponent component, int x, int y, int width, int height) {
		component.setAlignmentX(x);
		LoggedFrame.fixSize(component, TITULO_WIDTH, MENU_HEIGHT);
		this.add(component);
	}

	private void mostrarMenu(ActionEvent e) {
		// PopupMenu menu = new PopupMenu();
		JPopupMenu menu = new JPopupMenu();
		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");

		// menu.add(open);
		menu.add(cut);
		menu.add(copy);
		menu.add(paste);
		// menu.show(e.getComponent(), e.getX(), e.getY());
	}

	private void setButton(JButton boton, int x, int width, int height) {
		this.add(boton);
		boton.setBounds(x, 150, width, height);
		boton.setAlignmentX(x);
		// LoggedFrame.fixSize(boton,width,height);
		boton.setBackground(null);
		boton.setBorderPainted(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

}