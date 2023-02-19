package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class AllPostsPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int MAX_IMAGES_PER_PAGE = 9;
	private ArrayList<Image> images = new ArrayList<>();
	private int numPages;
	private int actualPage;

	// Panel que contendrá las imágenes de las fotos o los albumes
	private JPanel imagesPane;
	// Panel que contendrá dos botones para ir desplazándose por la galería de imágenes
	private JPanel movePagePane;
	// Panel que contendrá los label "fotos" y "albumes" para cambiar entre una galería y otra
	private JPanel changeGalleryPane;

	private JLabel leftIconLabel;
	private JLabel rightIconLabel;
	
	private JButton seePhotosButton;
	private JButton seeAlbumsButton;
	private JButton addPhotoToAlbumButton;
	

	public AllPostsPane() {
		setLayout(new BorderLayout());
		addImages();
		numPages = (int) Math.ceil((double) images.size() / MAX_IMAGES_PER_PAGE);
		actualPage = 1;

		createImagesPane();
		createMovePagePane();
		createChangeGalleryPane();
	}
	
	private void createChangeGalleryPane() {
		changeGalleryPane = new JPanel();
		FlowLayout layout = new FlowLayout(0, 10, 0);
		layout.setAlignment(FlowLayout.CENTER);
		changeGalleryPane.setLayout(layout);
		add(changeGalleryPane, BorderLayout.NORTH);
		changeGalleryPane.setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, 50));
		
		// Botón para ver las fotos
		seePhotosButton = new JButton("VER FOTOS");
		seePhotosButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		seePhotosButton.setVisible(false);
		changeGalleryPane.add(seePhotosButton);
		
		// Botón para ver los álbumes
		seeAlbumsButton = new JButton("VER ALBUMES");
		seeAlbumsButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		changeGalleryPane.add(seeAlbumsButton);
		
		// Botón para añadir foto a álbum
		addPhotoToAlbumButton = new JButton("AÑADIR FOTO A ALBUM");
		addPhotoToAlbumButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		addPhotoToAlbumButton.setVisible(false);
		changeGalleryPane.add(addPhotoToAlbumButton);
		
		seePhotosButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				seeAlbumsButton.setVisible(false);
				addPhotoToAlbumButton.setVisible(false);
				// Mostrar fotos
			}
		});
		
		
	}

	private void createImagesPane() {
		imagesPane = new JPanel();
		imagesPane.setLayout(new FlowLayout(0, 0, 0));
		add(imagesPane, BorderLayout.CENTER);
		imagesPane.setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, 500));

		showPage();
	}

	private void createMovePagePane() {
		movePagePane = new JPanel();
		add(movePagePane, BorderLayout.SOUTH);
		movePagePane.setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, 70));
		movePagePane.setLayout(null);

		Image rightImage;
		Image leftImage;
		try {
			rightImage = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "flecha_derecha.png"));
			leftImage = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "flecha_izquierda.png"));

			rightIconLabel = new JLabel(new ImageIcon(rightImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			rightIconLabel.setSize(100, 30);
			rightIconLabel.setLocation(400, 10);
			rightIconLabel.addMouseListener(new RightButtonListener());
			if (images.size() > MAX_IMAGES_PER_PAGE)
				movePagePane.add(rightIconLabel);

			leftIconLabel = new JLabel(new ImageIcon(leftImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			leftIconLabel.addMouseListener(new LeftButtonListener());
			leftIconLabel.setSize(100, 30);
			leftIconLabel.setLocation(238, 10);
			movePagePane.add(leftIconLabel);
			leftIconLabel.setVisible(false);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Método para mostrar una página de fotos o álbumes
	private void showPage() {
		for (int i = (actualPage - 1) * MAX_IMAGES_PER_PAGE; i < Math.min(images.size(),
				actualPage * MAX_IMAGES_PER_PAGE); i++)
			addImage(images.get(i), imagesPane);
	}

	public void addImage(Image image, JPanel panel) {
		panel.add(new JLabel(new ImageIcon(
				image.getScaledInstance(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 3 - 4, 120, Image.SCALE_SMOOTH))));
		revalidate();
		repaint();
	}

	class RightButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			actualPage++;
			if (actualPage >= numPages) {
				rightIconLabel.setVisible(false);
			}
			leftIconLabel.setVisible(true);
			imagesPane.removeAll();
			showPage();
		}

		public void mouseEntered(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	class LeftButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			actualPage--;
			if (actualPage <= 1) {
				leftIconLabel.setVisible(false);
			}
			rightIconLabel.setVisible(true);
			imagesPane.removeAll();
			showPage();
		}

		public void mouseEntered(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	// TODO borrar
	private void addImages() {
		try {
			String[] matriz = new String[8];
			matriz[0] = ViewConstants.RUTA_FOTOS + "default-profpic.png";
			matriz[1] = ViewConstants.RUTA_FOTOS + "flecha_derecha.png";
			matriz[2] = ViewConstants.RUTA_FOTOS + "flecha_izquierda.png";
			matriz[3] = ViewConstants.RUTA_FOTOS + "icon_lupa.png";
			matriz[4] = ViewConstants.RUTA_FOTOS + "icon_tres_lineas.png";
			matriz[5] = ViewConstants.RUTA_FOTOS + "icono_comentario.png";
			matriz[6] = ViewConstants.RUTA_FOTOS + "icono_like.png";
			matriz[7] = ViewConstants.RUTA_FOTOS + "iconUploadPhoto.png";
		
			Random r = new Random();
			for (int i = 0; i < 30; i++)
				images.add(ImageIO.read(new File(matriz[r.nextInt(matriz.length)])));
			


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class FotosAlbumesLabelHandler extends MouseAdapter {
		
		// Queremos que al cambiar el texto se cambie de fotos a albumes y viceversa
		@Override
		public void mouseClicked(MouseEvent e) {
			// Si el texto está en "fotos", cambiar a "álbumes"
			if (fotosAlbumesLabel.getText().equals("FOTOS")) {
				fotosAlbumesLabel.setText("ALBUMES");
				
			
			
			
		}
		
		// Queremos que el cursor sea tipo mano para dar la impresión de botón
		public void mouseEntered(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
}
