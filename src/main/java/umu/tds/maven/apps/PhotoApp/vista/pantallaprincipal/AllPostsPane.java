package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

/** Clase que define el panel que mostrará todas las fotos y álbumes de un usuario concreto. */
public class AllPostsPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int MAX_IMAGES_PER_PAGE = 9;
	private static final int INITIAL_PAGE = 1;

	private ArrayList<Image> photos = new ArrayList<>();
	private HashMap<Image, List<Image>> albums = new HashMap<>();

	// Para saber el número de páginas que habrá en la galería de álbumes o fotos
	private int numPhotosPage;
	private int numAlbumsPage;
	
	// Para saber si las imágenes se pueden borrar o no
	private boolean deletable;

	// Para saber en que página de la galería de álbumes o fotos estamos
	private PageCounter actualPhotosPage = new PageCounter();
	private PageCounter actualAlbumsPage = new PageCounter();

	// Paneles que contendrá las imágenes de las fotos o los albumes
	private JPanel centerPane;
	private JPanel photosPane;
	private JPanel albumsPane;
	private JPanel photosOrAlbumsPane;

	// Panel que contendrá los botones para cambiar entre una galería y otra
	private JPanel changeGalleryPane;

	private JButton seePhotosButton;
	private JButton seeAlbumsButton;
	private JButton addPhotoToAlbumButton;

	public AllPostsPane(List<String> photos, List<List<String>> albums, boolean deletable) {
		// Creamos la lista de fotos
		photos.stream().forEach((p) -> {
			try {
				this.photos.add(ImageIO.read(new File(p)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Creamos la lista de álbumes
		for (List<String> album : albums) {
			// Tomamos la clave del mapa de álbumes la primera foto del mismo
			Image key = null;
			try {
				key = ImageIO.read(new File(album.get(0)));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Creamos una lista con todas las fotos del álbum
			List<Image> photosInAlbum = new ArrayList<>();
			album.stream().forEach((p) -> {
				try {
					photosInAlbum.add(ImageIO.read(new File(p)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			this.albums.put(key, photosInAlbum);
		}
		
		this.deletable = deletable;

		// Calculamos el número de páginas
		setLayout(new BorderLayout());
		numPhotosPage = (int) Math.ceil((double) this.photos.size() / MAX_IMAGES_PER_PAGE);
		numAlbumsPage = (int) Math.ceil((double) this.albums.keySet().size() / MAX_IMAGES_PER_PAGE);

		// Creamos el panel centrals
		createCenterPane();
	}

	// Método para crear el panel central que contendrá las fotos/álbumes
	private void createCenterPane() {
		centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());
		add(centerPane, BorderLayout.CENTER);

		// Creamos el panel que contiene los botones de cambio de galerías
		createChangeGalleryPane();
		// Creamos las galerías
		createPhotosOrAlbumsPane();
	}

	// Método para crear el panel que contendrá los botones de cambio de fotos a
	// álbumes.
	private void createChangeGalleryPane() {
		changeGalleryPane = new JPanel();
		FlowLayout layout = new FlowLayout(0, 10, 0);
		layout.setAlignment(FlowLayout.CENTER);
		changeGalleryPane.setLayout(layout);
		centerPane.add(changeGalleryPane, BorderLayout.NORTH);
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
				changeToSeePhotosPane();
			}
		});

		seeAlbumsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeToSeeAlbumsPane();
			}
		});

		addPhotoToAlbumButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Mostrar frame de añadir foto
			}
		});

	}

	// Panel variable que contendrá la galería de fotos o álbumes
	private void createPhotosOrAlbumsPane() {
		photosOrAlbumsPane = new JPanel(new CardLayout());
		centerPane.add(photosOrAlbumsPane, BorderLayout.CENTER);

		// Creamos el panel de la galería de las fotos
		photosPane = new JPanel(new BorderLayout());
		photosOrAlbumsPane.add(photosPane, "photos");
		JPanel imagesPaneForPhotos = createImagesPane(photos, photosPane, actualPhotosPage);
		photosPane.add(imagesPaneForPhotos, BorderLayout.CENTER);
		photosPane.add(new MovePagePane(photos, imagesPaneForPhotos, actualPhotosPage, numPhotosPage),
				BorderLayout.SOUTH);

		// Creamos el panel de la galería los álbumes
		albumsPane = new JPanel(new BorderLayout());
		photosOrAlbumsPane.add(albumsPane, "albums");
		List<Image> firstAlbumsPhoto = new ArrayList<>(albums.keySet());
		JPanel imagesPaneForAlbums = createImagesPane(firstAlbumsPhoto, albumsPane, actualAlbumsPage);
		albumsPane.add(imagesPaneForAlbums, BorderLayout.CENTER);
		albumsPane.add(new MovePagePane(firstAlbumsPhoto, imagesPaneForAlbums, actualAlbumsPage, numAlbumsPage),
				BorderLayout.SOUTH);
		albumsPane.setVisible(false);

	}

	private JPanel createImagesPane(List<Image> images, JPanel imagesPane, PageCounter pc) {
		JPanel photosPane = new JPanel();
		photosPane.setLayout(new FlowLayout(0, 0, 0));
		photosPane.setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, 500));
		showPage(images, photosPane, pc);
		return photosPane;
	}

	// Método para mostrar una página de fotos o álbumes
	private void showPage(List<Image> images, JPanel imagesPane, PageCounter actualPage) {
		for (int i = (actualPage.getPageCount() - 1) * MAX_IMAGES_PER_PAGE; i < Math.min(images.size(),
				actualPage.getPageCount() * MAX_IMAGES_PER_PAGE); i++)
			addImage(images.get(i), imagesPane);
		revalidate();
		repaint();
	}

	// Para pintar una imagen 
	public void addImage(Image image, JPanel panel) {
		JLabel imageIcon = new JLabel(new ImageIcon(
				image.getScaledInstance(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 3 - 4, 120, Image.SCALE_SMOOTH)));
		
		// Añadimos el listener para que aparezca la foto
		imageIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unused")
				ShowUploadedImagePane sip = new ShowUploadedImagePane(image);
			}
		});
		
		// Añadimos el menú contextual en caso de que sea una foto nuestra
		if (deletable) {
			JPopupMenu menuContextual = new JPopupMenu(); 
			JMenuItem deletePhoto = new JMenuItem("Delete"); 
			deletePhoto.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					panel.removeAll();
					// Eliminar la imagen en la galería que esté visible
					if (photosPane.isVisible()) {
						photos.remove(image);
						showPage(photos, panel, actualPhotosPage);
					}
					
					else if (albumsPane.isVisible()) {
						albums.remove(image);
						showPage(new ArrayList<>(albums.keySet()), panel, actualAlbumsPage);
					}
						
					
				}
			});
			
			menuContextual.add(deletePhoto); 
			imageIcon.setComponentPopupMenu(menuContextual);
		}
		
		
		// Añadimos el label al panel
		panel.add(imageIcon);
		
	}

	@SuppressWarnings("serial")
	// Panel para el paso de fotos o álbumes de la galería
	class MovePagePane extends JPanel {

		private JLabel leftIconLabel;
		private JLabel rightIconLabel;

		public MovePagePane(List<Image> images, JPanel imagesPane, PageCounter actualPage, int numPages) {
			super();
			setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, 70));
			setLayout(null);

			Image rightImage;
			Image leftImage;
			try {
				rightImage = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "flecha_derecha.png"));
				leftImage = ImageIO.read(new File(ViewConstants.RUTA_FOTOS + "flecha_izquierda.png"));

				rightIconLabel = new JLabel(new ImageIcon(rightImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				rightIconLabel.setSize(100, 30);
				rightIconLabel.setName("right");
				rightIconLabel.setLocation(400, 10);
				if (images.size() > MAX_IMAGES_PER_PAGE)
					add(rightIconLabel);

				leftIconLabel = new JLabel(new ImageIcon(leftImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				leftIconLabel.setName("left");
				leftIconLabel.setSize(100, 30);
				leftIconLabel.setLocation(238, 10);
				add(leftIconLabel);
				leftIconLabel.setVisible(false);
				
				rightIconLabel.addMouseListener(new ButtonListener(images, imagesPane, actualPage, numPages, rightIconLabel, leftIconLabel));
				leftIconLabel.addMouseListener(new ButtonListener(images, imagesPane, actualPage, numPages, rightIconLabel, leftIconLabel));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Handler para el botón que pasa a la siguiente página de la galería
	class ButtonListener extends MouseAdapter {

		PageCounter actualPage;
		List<Image> images;
		JPanel imagesPane;
		int numPages;
		JLabel rightIconLabel;
		JLabel leftIconLabel;

		public ButtonListener(List<Image> images, JPanel imagesPane, PageCounter actualPage, int numPages, JLabel rightIconLabel, JLabel leftIconLabel) {
			this.actualPage = actualPage;
			this.images = images;
			this.imagesPane = imagesPane;
			this.numPages = numPages;
			this.rightIconLabel = rightIconLabel;
			this.leftIconLabel = leftIconLabel;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(((JLabel)e.getSource()).getName());
			
			JLabel label = (JLabel)e.getSource();
			// Si se ha pulsado el botón de la derecha
			if (label.getName().equals("right")) {
				// Pasar de página
				actualPage.forward();
				if (actualPage.getPageCount() >= numPages) {
					label.setVisible(false);
				}
				leftIconLabel.setVisible(true);
			}
			
			// Si se ha pulsado el botón de la izquierda
			else if (label.getName().equals("left")) {
				// Retroceder página
				actualPage.backward();
				if (actualPage.getPageCount() <= 1) {
					label.setVisible(false);
				}
				rightIconLabel.setVisible(true);
			}
			
			imagesPane.removeAll();
			showPage(images, imagesPane, actualPage);
		}

		public void mouseEntered(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	
	// Para mostrar las fotos
	private void changeToSeePhotosPane() {
		// Cambiamos botones del panel changeGalleryPane
		seeAlbumsButton.setVisible(true);
		seePhotosButton.setVisible(false);
		addPhotoToAlbumButton.setVisible(false);

		// Cambiamos panel photosOrAlbumsPanel gracias al CardLayout
		CardLayout cl = (CardLayout) photosOrAlbumsPane.getLayout();
		cl.show(photosOrAlbumsPane, "photos");
		revalidate();
		repaint();
	}

	// Para mostrar los álbumes
	private void changeToSeeAlbumsPane() {
		// Cambiamos botones del panel changeGalleryPane
		seeAlbumsButton.setVisible(false);
		seePhotosButton.setVisible(true);
		addPhotoToAlbumButton.setVisible(false);

		// Cambiamos panel photosOrAlbumsPanel gracias al CardLayout
		CardLayout cl = (CardLayout) photosOrAlbumsPane.getLayout();
		cl.show(photosOrAlbumsPane, "albums");
		revalidate();
		repaint();
	}

	private void changeToAddPhotoPane() {

	}
	

	
	// Para llevar el contador de la página de las galerías
	class PageCounter {
		
		private int pageCount = INITIAL_PAGE;
		
		public int getPageCount() {
			return pageCount;
		}
		
		// Método para pasar de página
		public void forward() {
			pageCount++;
		}
		
		// Método para retroceder página
		public void backward() {
			pageCount--;
		}
	}

}
