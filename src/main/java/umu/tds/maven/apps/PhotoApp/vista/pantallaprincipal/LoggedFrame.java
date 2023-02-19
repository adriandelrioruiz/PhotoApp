package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;


public class LoggedFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// El centerPane podra ir variando su contenido entre myProfilePane y feedPane
	private JPanel centerPane;
	private MyProfilePane myProfilePane;
	private FeedPane feedPane;
	
	private MenuPane menuPane;

	public LoggedFrame() {
		initialize();
		
	}
	
	private void initialize() {
		setResizable(false);
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

	    feedPane = new FeedPane();
	    centerPane.add(feedPane, "feed"); // Agrega el panel feedPane al panel secundario con el nombre "feed"

	    myProfilePane = new MyProfilePane();
	    centerPane.add(myProfilePane, "profile"); // Agrega el panel myProfilePane al panel secundario con el nombre "profile"
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
		cl.show(centerPane, "feed"); // Muestra el panel "feed"
		revalidate();
		repaint();
	}
	
	public void changeToProfilePanel() {
		CardLayout cl = (CardLayout) centerPane.getLayout();
		cl.show(centerPane, "profile"); // Muestra el panel "feed"
		revalidate();
		repaint();
	}
}
