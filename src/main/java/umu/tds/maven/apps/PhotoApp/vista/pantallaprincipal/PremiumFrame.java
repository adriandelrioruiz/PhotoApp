package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class PremiumFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_HEIGHT = 50;
	private static final int FRAME_PREMIUM_HEIGHT = 150;
	private static final int FRAME_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 20;
	private LoggedFrame frame;
	public PremiumFrame(LoggedFrame fr) {
		this.frame=fr;
		this.setSize(FRAME_WIDTH, FRAME_PREMIUM_HEIGHT);
		this.setLocation(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - FRAME_WIDTH, 0);
		initialize();
		

	}
	private void initialize() {
		// TODO Auto-generated method stub
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contenedor = (JPanel) this.getContentPane();
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
		JButton pdf = new JButton("Generar PDF");
		LoggedFrame.fixSize(pdf, FRAME_WIDTH, BUTTON_HEIGHT);
		contenedor.add(pdf);
		JButton excel = new JButton("Generar EXCEL");
		LoggedFrame.fixSize(excel, FRAME_WIDTH, BUTTON_HEIGHT);
		contenedor.add(excel);
		JButton topLikes = new JButton("Top Me Gusta");
		LoggedFrame.fixSize(topLikes, FRAME_WIDTH, BUTTON_HEIGHT);
		contenedor.add(topLikes);
		
		this.setVisible(true);
	}

}
