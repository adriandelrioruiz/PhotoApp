package umu.tds.maven.apps.PhotoApp.vista.ventanausuario;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowMyUploadedAlbumFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowMyUploadedPhotoFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedAlbumFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedPhotoFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowUploadedAlbumFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowUploadedPhotoFrame;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

/**
 * Clase que define el panel que mostrará todas las fotos y álbumes de un
 * usuario concreto.
 */
public class AllPostsPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int MAX_IMAGES_PER_PAGE = 9;
	private static final int INITIAL_PAGE = 1;

	// Para distinguir la galería
	private static final byte PHOTOS_GALLERY = 1;
	private static final byte ALBUMS_GALLERY = 2;

	private ArrayList<Image> photos = new ArrayList<>();
	private HashMap<Image, List<Image>> albums = new HashMap<>();

	private List<Integer> photosId;
	private List<Integer> albumsId;

	// Para saber el número de páginas que habrá en la galería de álbumes o fotos
	private int numPhotosPage;
	private int numAlbumsPage;

	// Para saber si las imágenes se pueden borrar o no
	private boolean deletable;

	// Id del usuario que se muestra el perfil
	private int userId;

	// Para saber en que página de la galería de álbumes o fotos estamos
	private PageCounter actualPhotosPage = new PageCounter();
	private PageCounter actualAlbumsPage = new PageCounter();

	// Panel que contendrá las imagenes en sí
	private JPanel imagesPaneForPhotos;
	private JPanel imagesPaneForAlbums;

	// Paneles que contendrá las imágenes de las fotos o los albumes
	private JPanel centerPane;
	private JPanel photosPane;
	private JPanel albumsPane;
	private JPanel photosOrAlbumsPane;

	// Panel que contendrá los botones para cambiar entre una galería y otra
	private JPanel changeGalleryPane;

	private JButton seePhotosButton;
	private JButton seeAlbumsButton;

	// Controlador
	private PhotoAppController controller = PhotoAppController.getInstance();

	public AllPostsPane(int userId, boolean deletable) {

		this.photosId = controller.getPhotos(userId);
		this.albumsId = controller.getAlbums(userId);

		this.userId = userId;

		// Creamos la lista de fotos
		photosId.stream().forEach((p) -> {
			try {
				this.photos.add(ImageIO.read(new File(controller.getPath(p))));
			} catch (IOException e) {

				e.printStackTrace();
			}
		});

		// Creamos la lista de álbumes
		for (int albumId : albumsId) {
			// Tomamos la lista de fotos a partir del id del album
			List<Integer> albumPhotos = controller.getPhotosOfAlbum(albumId);

			// Tomamos la clave como la primera foto del album
			Image key = null;
			try {
				key = ImageIO.read(new File(controller.getPath(albumId)));
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			// Creamos una lista con todas las fotos del álbum
			List<Image> photosInAlbum = new ArrayList<>();
			albumPhotos.stream().forEach((p) -> {
				try {
					photosInAlbum.add(ImageIO.read(new File(controller.getPath(p))));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			});

			// Añadimos la entrada al mapa
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
		seePhotosButton = new JButton("Ver fotos");
		seePhotosButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		seePhotosButton.setForeground(Color.white);
		seePhotosButton.setVisible(false);
		changeGalleryPane.add(seePhotosButton);

		// Botón para ver los álbumes
		seeAlbumsButton = new JButton("Ver álbumes");
		seeAlbumsButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		seeAlbumsButton.setForeground(Color.white);
		changeGalleryPane.add(seeAlbumsButton);

		

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


	}

	// Panel variable que contendrá la galería de fotos o álbumes
	private void createPhotosOrAlbumsPane() {
		photosOrAlbumsPane = new JPanel(new CardLayout());
		centerPane.add(photosOrAlbumsPane, BorderLayout.CENTER);

		// Creamos el panel de la galería de las fotos
		photosPane = new JPanel(new BorderLayout());
		photosOrAlbumsPane.add(photosPane, "photos");
		imagesPaneForPhotos = createImagesPane(PHOTOS_GALLERY);
		photosPane.add(imagesPaneForPhotos, BorderLayout.CENTER);
		photosPane.add(new MovePagePane(PHOTOS_GALLERY), BorderLayout.SOUTH);

		// Creamos el panel de la galería los álbumes
		albumsPane = new JPanel(new BorderLayout());
		photosOrAlbumsPane.add(albumsPane, "albums");
		imagesPaneForAlbums = createImagesPane(ALBUMS_GALLERY);
		albumsPane.add(imagesPaneForAlbums, BorderLayout.CENTER);
		albumsPane.add(new MovePagePane(ALBUMS_GALLERY), BorderLayout.SOUTH);
		albumsPane.setVisible(false);

		List<Image> firstAlbumsPhoto = new ArrayList<>(albums.keySet());
		showPage(photos, imagesPaneForPhotos, actualPhotosPage, PHOTOS_GALLERY);
		showPage(firstAlbumsPhoto, imagesPaneForAlbums, actualAlbumsPage, ALBUMS_GALLERY);

	}

	private JPanel createImagesPane(byte gallery) {

		JPanel photosPane = new JPanel();
		photosPane.setLayout(new FlowLayout(0, 0, 0));
		photosPane.setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, 500));

		return photosPane;
	}

	// Método para mostrar una página de fotos o álbumes
	private void showPage(List<Image> images, JPanel imagesPane, PageCounter actualPage, byte gallery) {
		List<Integer> listOfPosts = null;
		if (gallery == ALBUMS_GALLERY)
			listOfPosts = albumsId;
		else if (gallery == PHOTOS_GALLERY)
			listOfPosts = photosId;

		for (int i = (actualPage.getPageCount() - 1) * MAX_IMAGES_PER_PAGE; i < Math.min(images.size(),
				actualPage.getPageCount() * MAX_IMAGES_PER_PAGE); i++)
			addImage(images.get(i), listOfPosts.get(i), imagesPane, gallery);
		revalidate();
		repaint();
	}

	// Para pintar una imagen
	public void addImage(Image image, int imageId, JPanel panel, byte gallery) {
		JLabel imageIcon = new JLabel(new ImageIcon(
				image.getScaledInstance(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH / 3 - 4, 120, Image.SCALE_SMOOTH)));

		// Añadimos el listener para que aparezca la foto o el álbum

		if (gallery == PHOTOS_GALLERY)
			imageIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					@SuppressWarnings("unused")
					ShowUploadedPhotoFrame frame;
					// Si es un perfil que puede borrar fotos, será MyProfile
					if (deletable)
						frame = new ShowMyUploadedPhotoFrame(userId, imageId);
					// Si no, será OthersProfile
					else
						frame = new ShowOtherUploadedPhotoFrame(userId, imageId);
				}
			});
		else
			imageIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					@SuppressWarnings("unused")
					ShowUploadedAlbumFrame frame;
					// Si es un perfil que puede borrar fotos, será MyProfile
					if (deletable)
						frame = new ShowMyUploadedAlbumFrame(userId, imageId);
					// Si no, será OthersProfile
					else
						frame = new ShowOtherUploadedAlbumFrame(userId, imageId);
				}
			});

		// Añadimos el menú contextual en caso de que sea una foto o álbum nuestro
		if (deletable) {
			JPopupMenu menuContextual = new JPopupMenu();
			JMenuItem deleteMenuItem = new JMenuItem("Delete");
			// Si es una foto
			if (gallery == PHOTOS_GALLERY)
				deleteMenuItem.addActionListener(new ActionListener() {
	
					@Override
					public void actionPerformed(ActionEvent e) {
						panel.removeAll();
						controller.deletePhoto(imageId);
						LoggedFrame.getInstance().updateProfile();
	
					}
				});
			
			// Si es un álbum
			else {
				deleteMenuItem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						panel.removeAll();
						controller.deleteAlbum(imageId);
						LoggedFrame.getInstance().updateProfile();
					}
				});
			}

			menuContextual.add(deleteMenuItem);
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

		public MovePagePane(byte gallery) {
			super();
			setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, 70));
			setLayout(null);

			List<Image> images;

			// Distinguimos foto de álbum
			if (gallery == PHOTOS_GALLERY) {
				images = photos;
			}

			else {
				List<Image> firstAlbumsPhoto = new ArrayList<>(albums.keySet());
				images = firstAlbumsPhoto;
			}

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

				rightIconLabel.addMouseListener(new ButtonListener(rightIconLabel, leftIconLabel, gallery));
				leftIconLabel.addMouseListener(new ButtonListener(rightIconLabel, leftIconLabel, gallery));

			} catch (IOException e) {
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
		byte gallery;

		public ButtonListener(JLabel rightIconLabel, JLabel leftIconLabel, byte gallery) {

			this.gallery = gallery;

			// Distinguimos foto de álbum
			if (gallery == PHOTOS_GALLERY) {
				this.images = photos;
				this.actualPage = actualPhotosPage;
				this.numPages = numPhotosPage;
				this.imagesPane = imagesPaneForPhotos;
			}

			else {
				List<Image> firstAlbumsPhoto = new ArrayList<>(albums.keySet());
				this.images = firstAlbumsPhoto;
				this.actualPage = actualAlbumsPage;
				this.numPages = numAlbumsPage;
				this.imagesPane = imagesPaneForAlbums;
			}

			this.rightIconLabel = rightIconLabel;
			this.leftIconLabel = leftIconLabel;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(((JLabel) e.getSource()).getName());

			JLabel label = (JLabel) e.getSource();
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
			showPage(images, imagesPane, actualPage, gallery);
		}

		public void mouseEntered(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	// Para mostrar las fotos
	private void changeToSeePhotosPane() {
		// Cambiamos botones del panel changeGalleryPane
		seeAlbumsButton.setVisible(true);
		seePhotosButton.setVisible(false);

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

		// Cambiamos panel photosOrAlbumsPanel gracias al CardLayout
		CardLayout cl = (CardLayout) photosOrAlbumsPane.getLayout();
		cl.show(photosOrAlbumsPane, "albums");
		revalidate();
		repaint();
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
