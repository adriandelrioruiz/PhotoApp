package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Comment;

@SuppressWarnings("serial")
public class CommentFrame extends JFrame{
	// Ancho y alto del frame
		private static final int FRAME_WIDTH = 320;
		private static final int FRAME_HEIGHT = 420;

		// Panel que contendrá todos los paneles de cada elemento encontrado
		private JPanel commentPane;
		// JScrollPane
		private JScrollPane scrollPane;

		// Resultados de la búsqueda
		private List<Comment> resultados;

		public CommentFrame(int postId) {

			this.resultados = PhotoAppController.getInstance().getComments(postId);
			if (!resultados.isEmpty()) {
				initialize();
			} else
				sinResultados();
		}

		private void initialize() {
			// Crear panel que contendrá los paneles
			commentPane = new JPanel();
			commentPane.setLayout(new BoxLayout(commentPane, BoxLayout.Y_AXIS));
			commentPane.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setTitle("Comentarios");
			setLocationRelativeTo(null);
			setResizable(false);

			for (Comment c : resultados) {
				commentPane.add(new CommentPanel(c));
			}
			// Crear JScrollPane y añadir el panelList
			scrollPane = new JScrollPane(commentPane);
			scrollPane.setViewportView(commentPane);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			// Añadir el JScrollPane al JFrame
			getContentPane().add(scrollPane);

			setVisible(true);
		}

		private void sinResultados() {
			JOptionPane.showMessageDialog(this, "No hay ningún comentario");
		}

}
