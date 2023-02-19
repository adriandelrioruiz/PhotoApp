package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputAdapter;

import org.w3c.dom.events.MouseEvent;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class PostPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int IMAGE_WIDTH = 220;
	public static final int PUBLICACION_HEIGHT = 100;

	// private Image image;
	// Es necesario saber foto,me gustas,propietario
	public PostPane(String filename, String propietario, int likes) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		fixSize(this, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, PUBLICACION_HEIGHT);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		// this.setBorder(new TitledBorder(nombre));

		JLabel lbimagen = new JLabel();
		fixSize(lbimagen, IMAGE_WIDTH, PUBLICACION_HEIGHT);
		lbimagen.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// verFoto(foto);
				mostrarMenu(e);
			}
		});
		JPanel atributos = new Atributos(propietario, likes);
		this.add(lbimagen);
		this.add(atributos, BorderLayout.WEST);

	}

	private void verFoto(ImageIcon foto) {
		JFrame frame = new JFrame();
		frame.setSize(foto.getIconWidth(), foto.getIconHeight() + 30);
		JLabel label = new JLabel();
		label.setIcon(foto);
		frame.getContentPane().add(label);
		frame.setLocation(this.getX(), this.getY() + MenuPane.MENU_HEIGHT);
		// Mostrar la ventana
		frame.setVisible(true);

	}

	private void mostrarMenu(java.awt.event.MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		JLabel label = new JLabel();
		label.setIcon(ViewConstants.getIcon(300, 300, "icon_lupa.png"));
		menu.add(label);
		menu.show(e.getComponent(), 5, -5);
		// menu.show(e.getComponent(), e.getX()-50, e.getY()-50);
	}

	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}
}
