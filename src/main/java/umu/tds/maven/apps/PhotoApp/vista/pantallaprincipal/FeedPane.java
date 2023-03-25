package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class FeedPane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Lista de fotos por id
	private List<Integer> photoIds;
	
	// Controlador
	private PhotoAppController controller;

<<<<<<< HEAD
=======
	// Lista de fotos por id
	private List<Integer> photoIds;

	// Controlador
	private PhotoAppController controller;

>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
	public FeedPane(List<Integer> photoIds) {
		this.controller = PhotoAppController.getInstance();
		this.photoIds = photoIds;
		setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, MenuPane.MENU_HEIGHT));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
<<<<<<< HEAD
		
		// Añadimos los PostPanes a partir de la lista de ids
		anadirPostPanes(panel);
		
=======

		// Añadimos los PostPanes a partir de la lista de ids
		anadirPostPanes(panel);

>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
		JScrollPane sp = new JScrollPane(this);
		sp.setViewportView(panel);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(sp);
	}

	public void anadirPostPanes(JPanel panel) {
		for (Integer id : photoIds) {
			add(new PostPane(controller.getPath(id), controller.getOwnerOfPhoto(id), controller.getLikes(id)));
		}
	}
}
