import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

public class PremiumWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_HEIGHT=50;
	private static final int FRAME_PREMIUM_HEIGHT=150;
	private static final int FRAME_WIDTH=150;
	private static final int BUTTON_HEIGHT=20;
	public PremiumWindow(String usuario) {
		if(PhotoAppController.getInstance().isPremium()) {
			this.setSize(FRAME_WIDTH,FRAME_PREMIUM_HEIGHT);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel contenedor=(JPanel) this.getContentPane();
			contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
			JButton pdf=new JButton("Generar PDF");
			VentanaPrincipal.fixSize(pdf,FRAME_WIDTH,BUTTON_HEIGHT);
			contenedor.add(pdf);
			JButton excel=new JButton("Generar EXCEL");
			VentanaPrincipal.fixSize(excel,FRAME_WIDTH,BUTTON_HEIGHT);
			contenedor.add(excel);
			JButton topLikes=new JButton("Top Me Gusta");
			VentanaPrincipal.fixSize(topLikes,FRAME_WIDTH,BUTTON_HEIGHT);
			contenedor.add(topLikes);
			
		}else {
			this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel contenedor=(JPanel) this.getContentPane();
			contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
			JButton premium=new JButton("Premium");
			VentanaPrincipal.fixSize(premium,FRAME_WIDTH,BUTTON_HEIGHT);
			contenedor.add(premium);
		}
		this.setLocation(VentanaPrincipal.WINDOW_WIDTH-FRAME_WIDTH, 0);
		this.setVisible(true);
	}
	
	
}
