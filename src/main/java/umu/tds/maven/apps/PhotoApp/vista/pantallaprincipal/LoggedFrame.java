package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.ventanausuario.MyProfilePane;
import umu.tds.maven.apps.PhotoApp.vista.ventanausuario.OthersProfilePane;
import umu.tds.maven.apps.PhotoApp.vista.ventanausuario.ProfilePane;

public class LoggedFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// El centerPane podra ir variando su contenido entre myProfilePane y feedPane
	private JPanel centerPane;
	private ProfilePane myProfilePane;
	private FeedPane feedPane;

	// Controlador
	private PhotoAppController controller;

	private MenuPane menuPane;

	private static LoggedFrame lf;

	public LoggedFrame() {
		lf = this;
		controller = PhotoAppController.getInstance();
		initialize();
	}

	public static LoggedFrame getInstance() {
		return lf;
	}

	private void initialize() {
		setResizable(true);
		setLayout(new BorderLayout());
		setSize(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, ViewConstants.LOGGEDFRAME_WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		createNorthPane();
		createCenterPane();

		setVisible(true);
	}

	// Método para crear el panel norte que contendrá el MenuPane
	private void createNorthPane() {
		menuPane = new MenuPane(this);
		getContentPane().add(menuPane, BorderLayout.NORTH);
	}

	// Método para crear el panel centro que contendrá los paneles variables
	private void createCenterPane() {
		centerPane = new JPanel(new CardLayout()); // Crea un panel secundario
		getContentPane().add(centerPane, BorderLayout.CENTER); // Agrega el panel secundario al contenedor principal

		feedPane = new FeedPane(controller.getFeed(),this);
		centerPane.add(feedPane, "feed"); // Agrega el panel feedPane al panel secundario con el nombre "feed"

		myProfilePane = new MyProfilePane(controller.getId());
		centerPane.add(myProfilePane, "profile"); // Agrega el panel myProfilePane al panel secundario con el nombre
													// "profile"
		myProfilePane.setVisible(false); // Lo deja no visible
	}

	public static void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}

	public void SetBottomPanel(JComponent component) {
		this.add(component);
	}

	public static void setButton(JButton boton) {
		boton.setBackground(null);
		boton.setBorderPainted(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	// Método para cambiar el panel sur
	public void changeToFeedPanel() {
		CardLayout cl = (CardLayout) centerPane.getLayout();
		cl.show(centerPane, "feed");
		revalidate();
		repaint();
	}

	public void changeToProfilePanel() {
		CardLayout cl = (CardLayout) centerPane.getLayout();
		cl.show(centerPane, "profile");
		revalidate();
		repaint();
	}

	// Para que se actualice la vista del perfil en caso de que se suba una nueva
	// foto
	public void updateProfile() {
		centerPane.remove(myProfilePane);
		myProfilePane = new MyProfilePane(controller.getId());
		centerPane.add(myProfilePane, "profile"); // Agrega el panel myProfilePane al panel secundario con el nombre
													// "profile"
		changeToProfilePanel();
		revalidate();
		repaint();
	}
	public void changeToOtherProfile(int userId) {
		centerPane.remove(myProfilePane);
		myProfilePane = new OthersProfilePane(userId,controller.getId());
		centerPane.add(myProfilePane, "profile"); // Agrega el panel myProfilePane al panel secundario con el nombre
													// "profile"
		changeToProfilePanel();
		revalidate();
		repaint();
	}
	public void changeTopLikes() {
		controller.getTopPhotosByLikes();
		centerPane.remove(myProfilePane);
		//myProfilePane = new OthersProfilePane(userId,controller.getId());
		centerPane.add(myProfilePane, "profile"); // Agrega el panel myProfilePane al panel secundario con el nombre
													// "profile"
		changeToProfilePanel();
		revalidate();
		repaint();
	}
	
	// Para que se actualice la foto de perfil en caso de que se suba una nueva foto
	public void updateProfilePic() {
		updateProfile();
		// Recreamos en northPane con el menuPane con la foto de perfil nueva
		menuPane.updateProfilePic();
	}
}
