package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Album;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedAlbumFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedPhotoFrame;

public class PostPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int IMAGE_WIDTH = 220;
	public static final int PUBLICACION_HEIGHT = 100;
	private int id;
	private JLabel lbimagen;
	private PhotoAppController controller=PhotoAppController.getInstance();
	// private Image image;
	// Es necesario saber foto,me gustas,propietario
	public PostPane(int id) {
		this.id=id;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		fixSize(this, ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, PUBLICACION_HEIGHT);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		// this.setBorder(new TitledBorder(nombre));
		lbimagen = new JLabel();
		cargarImagen();
		
		fixSize(lbimagen, IMAGE_WIDTH, PUBLICACION_HEIGHT);
		lbimagen.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// MOSTRAR EL FRAME DE ALBUM O FOTO 
				mostrarFoto();
			}
		});
		JPanel atributos = new Atributos(id);
		this.add(lbimagen);
		this.add(atributos, BorderLayout.WEST);
		
	}
	
	private void cargarImagen() {
		try {
			Image image = (ImageIO.read(new File(controller.getPath(id)))).getScaledInstance(IMAGE_WIDTH, PUBLICACION_HEIGHT,
					Image.SCALE_SMOOTH);
			lbimagen.setIcon((Icon)image);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void mostrarFoto() {
		//TO-DO si es un Album TO-DO
		if(controller.getPost(id) instanceof Album) {
			new ShowOtherUploadedAlbumFrame(controller.getId(controller.getOwnerOfPhoto(id)),id);
		}
		//SI ES UNA FOTO
		if(controller.getPost(id) instanceof Photo) {
			new ShowOtherUploadedPhotoFrame(controller.getId(controller.getOwnerOfPhoto(id)),id);
		}
	}
	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}
}
