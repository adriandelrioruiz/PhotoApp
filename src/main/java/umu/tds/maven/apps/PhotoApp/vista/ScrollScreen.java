package umu.tds.maven.apps.PhotoApp.vista;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;



import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Post;

public class ScrollScreen extends JScrollPane{
	
	public ScrollScreen() {
		JPanel panel = new JPanel();
		VentanaPrincipal.fixSize(panel, VentanaPrincipal.WINDOW_WIDTH, VentanaPrincipal.WINDOW_HEIGHT-Menu.MENU_HEIGHT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		anadirPublicaciones(panel);
		this.setViewportView(panel);
		VentanaPrincipal.fixSize(this, VentanaPrincipal.WINDOW_WIDTH, VentanaPrincipal.WINDOW_HEIGHT-Menu.MENU_HEIGHT);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	
	public void anadirPublicaciones(JPanel panel) {
		
		//REQUEST THE LAST POST TO THE CONTROLLER
		//ADD THE PUBLICATIONS TO THE PANEL
		List<Post> feed =PhotoAppController.getInstance().getFeed();
		//PASAR DE lIST A SCROLL PANEL
		panel.add(new Publicacion("SPK.png", "Pikachu", 15));
		panel.add(new Publicacion("SMV.png", "Mujer maravilla", 50));
		panel.add(new Publicacion("SSH.png", "Shongoku", 100));
		panel.add(new Publicacion("raro.png", "Emoji", 10));
		panel.add(new Publicacion("SPK.png", "Pikachu", 15));
		panel.add(new Publicacion("SMV.png", "Mujer maravilla", 50));
		panel.add(new Publicacion("SSH.png", "Shongoku", 100));
	}
}
