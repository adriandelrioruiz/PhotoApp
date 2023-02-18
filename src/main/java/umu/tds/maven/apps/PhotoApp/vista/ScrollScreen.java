package umu.tds.maven.apps.PhotoApp.vista;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;



import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Post;

public class ScrollScreen extends JScrollPane{
	
	public ScrollScreen() {
		/*		//Lista de valores
		List<Post> feed =PhotoAppController.getInstance().getFeed();
		DefaultListModel<Elemento> model = new DefaultListModel<Elemento>();
		model.addElement(new Elemento("SPK.png", "Pikachu", 15, 25, 90));
		model.addElement(new Elemento("SMV.png", "Mujer maravilla", 50, 85, 85));
		model.addElement(new Elemento("SSH.png", "Shongoku", 100, 60, 10));
		model.addElement(new Elemento("raro.png", "Emoji", 10, 5, 15));
		lista.setModel(model);
		lista.setCellRenderer(new ElementoListRenderer());
		
		JScrollPane scroll = new JScrollPane(lista);*/
		List<Post> feed = PhotoAppController.getInstance().getFeed();
		System.out.println("---- "+feed.get(0).getTitle());
		JList<Post> lista = new JList<>(feed.toArray(new Post[0]));
		lista.setCellRenderer(new PublicacionRenderer());
		/*JPanel panel = new JPanel();
		VentanaPrincipal.fixSize(panel, VentanaPrincipal.WINDOW_WIDTH, VentanaPrincipal.WINDOW_HEIGHT-Menu.MENU_HEIGHT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		anadirPublicaciones(panel);
		this.setViewportView(panel);
		VentanaPrincipal.fixSize(this, VentanaPrincipal.WINDOW_WIDTH, VentanaPrincipal.WINDOW_HEIGHT-Menu.MENU_HEIGHT);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);*/
	}
	
	
	public void anadirPublicaciones(JPanel panel) {
		
		//REQUEST THE LAST POST TO THE CONTROLLER
		//ADD THE PUBLICATIONS TO THE PANEL
		List<Post> feed =PhotoAppController.getInstance().getFeed();
		for(Post p:feed) {
			panel.add(new Publicacion(p));
		}
		//PASAR DE lIST A SCROLL PANEL
		/*panel.add(new Publicacion("icono_comentario.png", "Pikachu", 15));
		panel.add(new Publicacion("icono_comentario.png", "Mujer maravilla", 50));
		panel.add(new Publicacion("icono_comentario.png", "Shongoku", 100));
		panel.add(new Publicacion("icono_comentario.png", "Emoji", 10));
		panel.add(new Publicacion("icono_comentario.png", "Pikachu", 15));
		panel.add(new Publicacion("icono_comentario.png", "Mujer maravilla", 50));
		panel.add(new Publicacion("icono_comentario.png", "Shongoku", 100));*/
	}
}
