package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class FeedPane extends JPanel {

	private static final long serialVersionUID = 1L;

	public FeedPane() {
		setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, MenuPane.MENU_HEIGHT));
		JPanel panel = new JPanel();	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		anadirPostPanees(panel);
		
		JScrollPane sp = new JScrollPane(this);
		sp.setViewportView(panel);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(sp);
	}

	public void anadirPostPanees(JPanel panel) {

		panel.add(new PostPane("icono_comentario.png", "Pikachu", 15));
		panel.add(new PostPane("icono_comentario.png", "Mujer maravilla", 50));
		panel.add(new PostPane("icono_comentario.png", "Shongoku", 100));
		panel.add(new PostPane("icono_comentario.png", "Emoji", 10));
		panel.add(new PostPane("icono_comentario.png", "Pikachu", 15));
		panel.add(new PostPane("icono_comentario.png", "Mujer maravilla", 50));
		panel.add(new PostPane("icono_comentario.png", "Shongoku", 100));
	}
}
