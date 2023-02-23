package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

/** Clase para mostrar un álbum que está subido. Permite comentar en cada una de las fotos */

@SuppressWarnings("serial")
public abstract class ShowUploadedAlbumFrame extends ShowUploadedPostFrame {
	
	// Número de fotos del álbum
	private int nOfPhotos;
	
	// JLabel para mostrar la imagen
	private JLabel imageLabel;
	// Botón para pasar a la derecha en la lista e fotos del álbum
	private JButton goRightButton;
	// Botón para pasar a la izquierda en la lista e fotos del álbum
	private JButton goLeftButton;
	
	// Lista de ids de las fotos del álbum
	protected List<Integer> photoIdList;
	// Lista de imágenes para ir cambiando el label
	protected List<ImageIcon> imageList;
	
	// Contador para saber en que foto estamos actualmente
	protected int photoCounter;

	public ShowUploadedAlbumFrame(int userId, int postId) {
		super(userId, postId);
		
		// Guardamos la lista de los PhotoId del album
		photoIdList = controller.getAlbums(userId);
		imageList = new LinkedList<>();
		nOfPhotos = photoIdList.size() - 1;
		
		// Ponemos el photoCounte a 0 pues empezaremos en la primera foto del álbum
		photoCounter = 0;
		
		initialize();
	}
	
	@Override
	protected void initialize() {
		
		// Creamos la lista de imágenes
		for (int id : photoIdList) {
			// Recuperamos el path de la foto
			String path = controller.getPath(id);
			// Creamos la imagen
			try {
				Image image = ImageIO.read(new File(path));
				ImageIcon postImage = new ImageIcon(image.getScaledInstance((int)WEST_PANEL_DIMENSION.getWidth() - 20, (int)WEST_PANEL_DIMENSION.getHeight() - 40, DO_NOTHING_ON_CLOSE));
				imageList.add(postImage);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		super.initialize();
		
		setVisible(true);
	}
	
	@Override
	protected void createWestPane() {
		super.createWestPane();
		
		// Creamos el Label
		imageLabel = new JLabel(imageList.get(photoCounter));
		imageLabel.setSize(330, 330);
		imageLabel.setLocation(20, 10);
		imageLabel.setLayout(null);
		westPane.add(imageLabel);
		
		// Creamos el botón para pasar a la derecha
		try {
			Image image = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "flecha_derecha.png"));
			ImageIcon rightButtonImage = new ImageIcon(image.getScaledInstance(DEFAULT_ICON_BUTTON_WIDTH, DEFAULT_ICON_BUTTON_HEIGHT, DO_NOTHING_ON_CLOSE));
			// Lo añadimos al panel oeste
			goRightButton = new JButton(rightButtonImage);
			goRightButton.setSize(DEFAULT_ICON_BUTTON_WIDTH, DEFAULT_ICON_BUTTON_HEIGHT);
			goRightButton.setLocation(252, 340);
			goRightButton.setName("right");
			goRightButton.setLayout(null);
			// Lo ponemos visible sólo si hay más de una página
			if (nOfPhotos <= 0)
				goRightButton.setVisible(false);
			
			westPane.add(goRightButton);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Creamos el botón para pasar a la izquierda
		try {
			Image image = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "flecha_izquierda.png"));
			ImageIcon leftButtonImage = new ImageIcon(image.getScaledInstance(DEFAULT_ICON_BUTTON_WIDTH, DEFAULT_ICON_BUTTON_HEIGHT, DO_NOTHING_ON_CLOSE));
			// Lo añadimos al panel oeste
			goLeftButton = new JButton(leftButtonImage);
			goLeftButton.setSize(DEFAULT_ICON_BUTTON_WIDTH, DEFAULT_ICON_BUTTON_HEIGHT);
			goLeftButton.setLocation(100, 340);
			goLeftButton.setName("left");
			goLeftButton.setLayout(null);
			// Lo ponemos no visible ya que estamos en la primera página
			goLeftButton.setVisible(false);
			westPane.add(goLeftButton);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void addListeners() {
		super.addListeners();
		
		// Añadimos los listeners de los botones para pasar de página
		goRightButton.addActionListener(new ChangePhotoButtonListener(goRightButton));
		goLeftButton.addActionListener(new ChangePhotoButtonListener(goLeftButton));
	}
	
	// Listener 
	class ChangePhotoButtonListener implements ActionListener {
		
		JButton button;
		
		public ChangePhotoButtonListener(JButton button) {
			
			this.button = button;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// Si se ha pulsado el botón de la derecha
			if (button.getName().equals("right")) {
				// Pasar de página
				photoCounter++;
				// Cambiamos la imagen
				if (photoCounter >= nOfPhotos) {
					goRightButton.setVisible(false);
				}
				goLeftButton.setVisible(true);
			}

			// Si se ha pulsado el botón de la izquierda
			else if (button.getName().equals("left")) {
				// Retroceder
				photoCounter--;
				if (photoCounter <= 0) {
					goLeftButton.setVisible(false);
				}
				goRightButton.setVisible(true);
			}
			
			// Cambiamos la imagen
			imageLabel.setIcon(imageList.get(photoCounter));
		}
	}

	@Override
	protected void comment() {
		// Comentamos en la foto en la que se esté actualmente
		if (controller.comment(photoIdList.get(photoCounter), commentTxtArea.getText())) {
			JButton btnAceptar = new JButton("Aceptar");
			JOptionPane.showMessageDialog(btnAceptar, "El registro se ha completado con éxito");
			commentTxtArea.setText(DEFAULT_COMMENT_TEXT);
		}

	}
	
	
}
