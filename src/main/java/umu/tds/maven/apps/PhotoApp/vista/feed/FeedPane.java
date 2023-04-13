package umu.tds.maven.apps.PhotoApp.vista.feed;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.MenuPane;

public class FeedPane extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	
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
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// Añadimos los PostPanes a partir de la lista de ids
		anadirPostPanes();
	}
	
		

	public void anadirPostPanes() {
		for (Integer id : photoIds) {
			this.panel.add(new PostPane(id,this));
		}
		
		//JScrollPane sp = new JScrollPane(panel);
		this.setViewportView(panel);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
	}
	public void changeOtherProfile(int UserId) {
		this.frame.changeToOtherProfile(UserId);
	}
}
