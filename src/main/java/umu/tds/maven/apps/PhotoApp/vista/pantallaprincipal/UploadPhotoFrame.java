package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
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

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

@SuppressWarnings("serial")
public class UploadPhotoFrame extends JFrame {

	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;

	private JFileChooser fileChooser;

	public UploadPhotoFrame() {
		fileChooser = new JFileChooser();
		initialize();
	}

	private void initialize() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		JEditorPane editorPane = new JEditorPane();
		getContentPane().add(editorPane, BorderLayout.CENTER);
		//editorPane.setPreferredSize(new Dimension(400, 200));
		editorPane.setContentType("text/html");
		editorPane.setText(
				"<h1>Agregar Foto</h1><p>An&iacute;mate a compartir una foto con tus amigos. <br> Puedes arrastrar el fichero aqu&iacute;"
				+ ". </p>");
		editorPane.setEditable(false);
		editorPane.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						validarImagen(file.getPath());
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
					validarImagen(fileChooser.getSelectedFile().toString());
				}
			}
		});
		getContentPane().add(seleccionarArchivoButton, BorderLayout.SOUTH);
	}
	

	private void validarImagen(String path) {
		// Cerramos la ventana
		dispose();
		if (!(path.endsWith(".png") || path.endsWith(".gif") || path.endsWith(".jpeg") || path.endsWith(".jpg") || path.endsWith(".bmp"))) {
			mostrarMensajeError();
			return;
		}
		
		Image image = Toolkit.getDefaultToolkit().getImage(path);
		
		@SuppressWarnings("unused")
		ShowNewImagePane sip = new ShowNewImagePane(path, image);
		
	}
	
	private void mostrarMensajeError() {
		// Mostramos mensaje de error
		JOptionPane.showMessageDialog(null, "Introduce una imagen válida", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
	}
}