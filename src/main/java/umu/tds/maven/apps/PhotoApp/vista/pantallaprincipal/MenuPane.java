package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pulsador.IEncendidoListener;
import pulsador.Luz;
import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.notificacion.NotificationFrame;
import umu.tds.maven.apps.PhotoApp.vista.premium.DescuentoFrame;
import umu.tds.maven.apps.PhotoApp.vista.premium.PremiumMenu;
import umu.tds.maven.apps.PhotoApp.vista.search.SearchFrame;

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

	private JTextField txtSearch;
	private JButton uploadButton, lupaButton, userButton, premiumButton, titulo,notiButton;
	private LoggedFrame frame;

	private PhotoAppController controller;

	// PARTE DE ARRIBA DE LA PANTALLA
	public MenuPane(LoggedFrame frame) {
		this.controller = PhotoAppController.getInstance();
		this.frame = frame;
		initialize();
	}

	private void initialize() {

		setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, MENU_HEIGHT));
		this.setBackground(Color.WHITE);

		// Nombre de la aplicación
		titulo = new JButton("PhotoTDS");
		titulo.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 22));
		this.setButton(titulo, 0, TITULO_WIDTH, MENU_HEIGHT);

		// Botón para subir foto
		uploadButton = new JButton(
				this.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, ViewConstants.RUTA_FOTOS + "iconUploadPhoto.png"));
		this.setButton(uploadButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 2 - 100, BUTTON_WIDTH, BUTTON_HEIGHT);

		// Buscar
		txtSearch = new JTextField();
		txtSearch.setAlignmentX(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 2 - 50);
		LoggedFrame.fixSize(txtSearch, SEARCH_WIDTH, SEARCH_HEIGHT);
		txtSearch.setText("Search");
		txtSearch.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 12));
		txtSearch.setEditable(true);
		txtSearch.setOpaque(true);
		txtSearch.setFocusable(false);

		txtSearch.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!txtSearch.isFocusable()) {
					txtSearch.setFocusable(true);
					txtSearch.grabFocus();
					txtSearch.setText("");
				}
			}
		});
		

		this.add(txtSearch);
		txtSearch.setColumns(10);
		// Bonton lupa
		lupaButton = new JButton(this.getIcon(BUTTON_WIDTH, SEARCH_HEIGHT, ViewConstants.RUTA_FOTOS + "icon_lupa.png"));
		this.setButton(lupaButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 2 + 50, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		// FOTO USER

		userButton = new JButton(
				this.getIcon(USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT, controller.getProfilePic(controller.getId())));
		this.setButton(userButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - 100, USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT);
		//BOTON NOTIFICATION
		notiButton = new JButton(
				this.getIcon(USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT, ViewConstants.RUTA_FOTOS + "icon_notifications.png"));
		this.setButton(notiButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - 150, USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT);
		// BOTON PREMIUN
		premiumButton = new JButton(
				this.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, ViewConstants.RUTA_FOTOS + "icon_tres_lineas.png"));
		setButton(premiumButton, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - 50, BUTTON_WIDTH, BUTTON_HEIGHT);

		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Mostrar una ventana de diálogo para SUBIR FOTO
				@SuppressWarnings("unused")
				UploadPhotoFrame cargar = new UploadPhotoFrame(UploadPhotoFrame.ADD_ALBUM);
			}
		});
		lupaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = txtSearch.getText();
				System.out.println("query: " + query);
				new SearchFrame(query,frame);
			}
		});
		userButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeToProfilePanel();
			}
		});

		premiumButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (PhotoAppController.getInstance().isPremium(controller.getId())) {
					PremiumMenu menu= new PremiumMenu();
					menu.show(premiumButton, 0,0);
				} else {
					new DescuentoFrame();
				}

			}

		
		});
		notiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Mostrar notificaciones
				new NotificationFrame();
			}
		});
		titulo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.changeToFeedPanel();
			}
		});
		
		// Añadimos el componente luz
		Luz luz = new Luz();
		luz.setBounds(0, 0, 15, 15);
		luz.addEncendidoListener(new IEncendidoListener() {
			
			@Override
			public void enteradoCambioEncendido(EventObject arg0) {
				String path;
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
				if (fileChooser.getSelectedFile() != null) {
					path = fileChooser.getSelectedFile().toString();
					controller.cargarFotos(path);
				}		
			}
		});
		this.add(luz);
	}
	
	private ImageIcon getIcon(int width, int height, String path) {
		Image image;
		image = Toolkit.getDefaultToolkit().getImage(path);
		// Escalar la imagen a un tamaño específico
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		// Crear un ImageIcon a partir de la imagen escalada
		return new ImageIcon(image);
	}

	/*
	 * private void setComponent(JComponent component, int x, int y, int width, int
	 * height) { component.setAlignmentX(x); LoggedFrame.fixSize(component,
	 * TITULO_WIDTH, MENU_HEIGHT); this.add(component); }
	 * 
	 * private void mostrarMenu(ActionEvent e) { // PopupMenu menu = new
	 * PopupMenu(); JPopupMenu menu = new JPopupMenu(); JMenuItem cut = new
	 * JMenuItem("Cut"); JMenuItem copy = new JMenuItem("Copy"); JMenuItem paste =
	 * new JMenuItem("Paste");
	 * 
	 * // menu.add(open); menu.add(cut); menu.add(copy); menu.add(paste); //
	 * menu.show(e.getComponent(), e.getX(), e.getY()); }
	 */

	private void setButton(JButton boton, int x, int width, int height) {
		this.add(boton);
		boton.setBounds(x, 150, width, height);
		boton.setAlignmentX(x);
		// LoggedFrame.fixSize(boton,width,height);
		boton.setBackground(null);
		boton.setBorderPainted(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	public void updateProfilePic() {
		userButton.setIcon(getIcon(USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT, controller.getProfilePic(controller.getId())));
		revalidate();
		repaint();
	}

}