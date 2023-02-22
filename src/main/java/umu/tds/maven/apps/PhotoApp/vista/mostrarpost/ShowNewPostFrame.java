package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")

/** Esta clase hereda de ShowPostFrame y se utiliza para mostrar un post que va a ser subido*/

public abstract class ShowNewPostFrame extends ShowPostFrame {
	
	// Path del nuevo post que se va a subir
	private String path;
	
	// JTextField para introducir el título de la foto o el álbum
	protected JTextField txtTitulo;
	
	// JLabel que contiene la imagen del post que se va a subir
	private JLabel imageLabel;

	public ShowNewPostFrame(int userId, String path) {
		super(userId);
		
		this.path = "C:\\Users\\adria\\eclipse-workspace\\PhotoApp2\\img\\flecha_derecha.png";
		
		// Añadimos la imagen al panel oeste
		addImage();
	}
	
	protected void initialize() {
		
	}
	
	@Override
	protected void createNorthPane() {
		super.createNorthPane();
		
		// Creamos el JTextField para introducir el título del post que queremos subir
		txtTitulo = new JTextField();
		txtTitulo.setBorder(BorderFactory.createCompoundBorder(
				txtTitulo.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
		northPane.setLayout(new BorderLayout());
		northPane.add(txtTitulo, BorderLayout.CENTER);
		
	}
	
	// Método para añadir la imagen del post que vamos a subir
	private void addImage() {
		
		
		try {
			Image image = ImageIO.read(new File(path));
			ImageIcon postImage = new ImageIcon(image.getScaledInstance((int)WEST_PANEL_DIMENSION.getWidth(), (int)WEST_PANEL_DIMENSION.getHeight(), DO_NOTHING_ON_CLOSE));
			// Lo añadimos al panel oeste
			imageLabel = new JLabel(postImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		westPane.add(imageLabel, BorderLayout.CENTER);
	}
	
	

}
