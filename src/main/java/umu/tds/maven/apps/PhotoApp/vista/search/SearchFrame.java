package umu.tds.maven.apps.PhotoApp.vista.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.Post;
import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedPhotoFrame;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

/** Esta clase muestra todos los objetos que se han encontrado a partir de un query */

@SuppressWarnings("serial")
public class SearchFrame extends JFrame {
	
	// Ancho y alto del frame
	static final int FRAME_WIDTH = 334;
	static final int FRAME_HEIGHT = 420;
	
	// Panel que contendrá todos los paneles de cada elemento encontrado
	private JPanel searchListPane;
	private LoggedFrame loggedFrame;
	// JScrollPane
	private JScrollPane scrollPane;
	
	// Resultados de la búsqueda
	private List<DomainObject> resultados;
	
	public SearchFrame(String query,LoggedFrame logged) {
		this.resultados = PhotoAppController.getInstance().search(query);
		this.loggedFrame=logged;
		if (!resultados.isEmpty()) {
			initialize();
		}
		else sinResultados(query);
	}
	
	private void initialize() {
		// Crear panel que contendrá los paneles
		searchListPane = new JPanel();
		searchListPane.setLayout(new BoxLayout(searchListPane, BoxLayout.Y_AXIS));
        
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle("Resultados: " + String.valueOf(resultados.size()));
		setLocationRelativeTo(null);
		setResizable(false);
		
		// Vemos si se trata de una lista de posts o de usuarios
		if (resultados.get(0) instanceof User) {
			// Añadimos todos los usuarios
			for (int i = 0; i < resultados.size(); i++) {
				User u = (User) resultados.get(i);
				ShowUserSearchPane panel = new ShowUserSearchPane(u.getCode(), this);
	            panel.setBackground(Color.LIGHT_GRAY);
	            searchListPane.add(panel);
			}
		}
			
		else {
			if (resultados.get(0) instanceof Post) {
			// Añadimos todos los usuarios
				for (int i = 0; i < resultados.size(); i++) {
					Post p = (Post) resultados.get(i);
					ShowPostSearchPane panel = new ShowPostSearchPane(p.getCode(), this);
		            panel.setBackground(Color.LIGHT_GRAY);
		            searchListPane.add(panel);
				}
			}else dispose();
		}
		// Crear JScrollPane y añadir el panelList
        scrollPane = new JScrollPane(searchListPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Añadir el JScrollPane al JFrame
        getContentPane().add(scrollPane);
		
		setVisible(true);
	}
	protected void ChangeToOtherProfile(int userId) {
		this.loggedFrame.changeToOtherProfile(userId);
		// Cerramos el frame
		this.dispose();
	}
	protected void showOtherPost(int postId) {
			int userId=PhotoAppController.getInstance().getId(PhotoAppController.getInstance().getOwnerOfPhoto(postId));
			new ShowOtherUploadedPhotoFrame(userId,postId);
			// Cerramos el frame
			this.dispose();
			
		}
	private void sinResultados(String query) {
		JOptionPane.showMessageDialog(this, "No se han encontrado resultados para \""+query+"\"");
	}
}
