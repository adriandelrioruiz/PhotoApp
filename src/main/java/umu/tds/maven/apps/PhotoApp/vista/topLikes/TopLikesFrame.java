package umu.tds.maven.apps.PhotoApp.vista.topLikes;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;

/**
 * Esta clase muestra todos los objetos que se han encontrado a partir de un
 * query
 */

@SuppressWarnings("serial")
public class TopLikesFrame extends JFrame {

	// Ancho y alto del frame
	private static final int FRAME_WIDTH = 320;
	private static final int FRAME_HEIGHT = 420;

	// Panel que contendrá todos los paneles de cada elemento encontrado
	private JPanel topListPane;
	// JScrollPane
	private JScrollPane scrollPane;

	// Resultados de la búsqueda
	private List<Photo> resultados;

	public TopLikesFrame() {

		this.resultados = PhotoAppController.getInstance().getTopPhotosByLikes();
		if (!resultados.isEmpty()) {
			initialize();
		} else
			sinResultados();
	}

	private void initialize() {
		// Crear panel que contendrá los paneles
		topListPane = new JPanel();
		topListPane.setLayout(new BoxLayout(topListPane, BoxLayout.Y_AXIS));

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("TopLikes");
		setLocationRelativeTo(null);
		setResizable(false);

		for (Photo p : resultados) {
			TopLikesPanel panel = new TopLikesPanel(p);
			panel.setBackground(Color.LIGHT_GRAY);
			topListPane.add(panel);
		}
		// Crear JScrollPane y añadir el panelList
		scrollPane = new JScrollPane(topListPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// Añadir el JScrollPane al JFrame
		getContentPane().add(scrollPane);

		setVisible(true);
	}

	private void sinResultados() {
		JOptionPane.showMessageDialog(this, "No has subido ninguna foto");
	}
}
