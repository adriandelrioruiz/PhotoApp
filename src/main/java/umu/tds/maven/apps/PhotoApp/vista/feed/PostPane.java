package umu.tds.maven.apps.PhotoApp.vista.feed;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedPhotoFrame;

public class PostPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int IMAGE_WIDTH = 220;
	public static final int PUBLICACION_HEIGHT = 100;
	private FeedPane pane;
	private int id;
	private JLabel lbimagen;
	private Atributos atributos;
	private PhotoAppController controller=PhotoAppController.getInstance();
	// private Image image;
	// Es necesario saber foto,me gustas,propietario
	public PostPane(int id,FeedPane f) {
		this.id=id;
		this.pane=f;
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
		atributos = new Atributos(id,this);
		this.add(lbimagen);
		this.add(atributos, BorderLayout.WEST);
		
		
	}
	
	private void cargarImagen() {
		try {
			Image image = (ImageIO.read(new File(controller.getPath(id)))).getScaledInstance(IMAGE_WIDTH, PUBLICACION_HEIGHT,
					Image.SCALE_SMOOTH);
			ImageIcon i= new ImageIcon(image);
			lbimagen.setIcon(i);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void changeOtherProfile(int Userid) {
		this.pane.changeOtherProfile(Userid);
	}
	private void mostrarFoto() {
		if(controller.getPost(id) instanceof Photo) {
			JFrame frame = new ShowOtherUploadedPhotoFrame(controller.getId(controller.getOwnerOfPhoto(id)),id);
			frame.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	                atributos.actualizarLikes();
	            }
	            @Override
	            public void windowClosing(WindowEvent e) {
	                atributos.actualizarLikes();
	            }
	        });
			/* public void windowOpened(WindowEvent e) {}
			  public void windowClosing(WindowEvent e) {}
			  public void windowClosed(WindowEvent e) {}
			  public void windowActivated(WindowEvent e) {}
			  public void windowDeactivated(WindowEvent e) {}*/
		}
	}
	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}
}
