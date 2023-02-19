package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
		
		JFrame frame = new JFrame();
		frame.setSize(700, 420);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		JTextField titulo = new JTextField();
		LoggedFrame.fixSize(titulo, 700, 50);
		titulo.setText("Añade titulo a tu Foto");
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(titulo);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		File selectedFile = fileChooser.getSelectedFile();
		Image image = Toolkit.getDefaultToolkit().getImage(path);
		// Escalar la imagen a un tamaño específico
		image = image.getScaledInstance(340, 300, Image.SCALE_SMOOTH);
		JLabel label = new JLabel(new ImageIcon(image));
		panel.add(label);
		JTextField description = new JTextField();
		LoggedFrame.fixSize(description, 340, 300);
		description.setText("Añade un comentario a tu Foto");
		panel.add(description);
		frame.getContentPane().add(panel);
		JButton boton = new JButton("Subir Foto");
		boton.setBackground(ViewConstants.APP_GREEN_COLOR);
		LoggedFrame.fixSize(boton, 200, 30);
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(boton);
		frame.setVisible(true);
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//SubirFoto(path, description.getText(), titulo.getText());
				frame.dispose();
			}
		});
	}
	
	private void mostrarMensajeError() {
		// Mostramos mensaje de error
		JOptionPane.showMessageDialog(null, "Introduce una imagen válida", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
	}
}