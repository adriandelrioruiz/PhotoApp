package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowNewAlbumFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowNewPhotoFrame;

@SuppressWarnings("serial")
public class UploadPhotoFrame extends JFrame {

	public static final byte ADD_PHOTO = 1;
	public static final byte ADD_ALBUM = 2;

	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;

	private static final String PHOTO_DESCRIPTION_TEXT = "<h1>Agregar Foto</h1><p>An&iacute;mate a compartir una foto con tus amigos. <br> Puedes arrastrar el fichero aqu&iacute;"
			+ ". </p>";
	private static final String ALBUM_DESCRIPTION_TEXT = "<h1>Agregar Album</h1><p>An&iacute;mate a compartir un álbum con tus amigos. <br> "
			+ "Escoge la foto que será la portada de tu álbum. Puedes arrastrar el fichero aqu&iacute;. <br>"
			+ "Podrás añadir más fotos al álbum desde tu perfil" + ". </p>";
	private static final String ADD_PHOTO_TO_ALBUM_TEXT = "";

	private JFileChooser fileChooser;

	// Para saber si se trata de un álbum o de una foto
	private byte frameType;

	public UploadPhotoFrame(byte frameType) {
		this.frameType = frameType;
		fileChooser = new JFileChooser();
		initialize();
	}

	private void initialize() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		JEditorPane editorPane = new JEditorPane();
		getContentPane().add(editorPane, BorderLayout.CENTER);
		// editorPane.setPreferredSize(new Dimension(400, 200));
		editorPane.setContentType("text/html");
		switch (frameType) {
		case ADD_PHOTO:
			editorPane.setText(PHOTO_DESCRIPTION_TEXT);
			break;
		case ADD_ALBUM:
			editorPane.setText(ALBUM_DESCRIPTION_TEXT);
			break;
		}

		editorPane.setEditable(false);
		editorPane.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						String path = file.getPath();
						validarImagen(path);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		setVisible(true);

		JButton seleccionarArchivoButton = new JButton("Seleccionar desde archivo");
		seleccionarArchivoButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		seleccionarArchivoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String absolutePath = fileChooser.getSelectedFile().toString();
					validarImagen(absolutePath);
				}
			}
		});
		getContentPane().add(seleccionarArchivoButton, BorderLayout.SOUTH);
	}

	@SuppressWarnings("unused")
	private void validarImagen(String path) {
		// Cerramos la ventana
		dispose();
		if (!isValidImageFormat(path)) {
			JOptionPane.showMessageDialog(null, "Introduce una imagen válida", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		switch (frameType) {
		case ADD_PHOTO:
			ShowNewPhotoFrame newPhoto = new ShowNewPhotoFrame(PhotoAppController.getInstance().getId(), path);
			break;
		case ADD_ALBUM:
			ShowNewAlbumFrame newAlbum = new ShowNewAlbumFrame(PhotoAppController.getInstance().getId(), path);
			break;
		}

	}

	// Para saber si una imagen tiene un formato válido
	public static boolean isValidImageFormat(String path) {
		return (path.endsWith(".png") || path.endsWith(".gif") || path.endsWith(".jpeg") || path.endsWith(".jpg")
				|| path.endsWith(".bmp"));
	}

}