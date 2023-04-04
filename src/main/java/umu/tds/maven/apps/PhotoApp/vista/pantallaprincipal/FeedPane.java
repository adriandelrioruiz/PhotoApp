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
	private LoggedFrame frame;
	public FeedPane(List<Integer> photoIds,LoggedFrame fr) {
		this.photoIds = photoIds;
		this.frame=fr;
		initialize();
		
	}
	private void initialize() {
		
		setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, MenuPane.MENU_HEIGHT));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// Añadimos los PostPanes a partir de la lista de ids
		anadirPostPanes(panel);

		JScrollPane sp = new JScrollPane(this);
		sp.setViewportView(panel);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(sp);
	}
	
	

	public void anadirPostPanes(JPanel panel) {
		for (Integer id : photoIds) {
			add(new PostPane(id,this ));
		}
	}
	public void changeOtherProfile(int UserId) {
		this.frame.changeToOtherProfile(UserId);
	}
}
