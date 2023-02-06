package umu.tds.maven.apps.PhotoApp.vista;

/*import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal {

	private JFrame frmVentanaPrincipal;
	
	public VentanaPrincipal() {
		initialize();
	}


	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
	}
	
	public void initialize() {
		frmVentanaPrincipal = new JFrame();
		frmVentanaPrincipal.setTitle("AppVideo- Ventana Principal");
		frmVentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = (JPanel) frmVentanaPrincipal.getContentPane();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout());
		
		JLabel labelCompartirCoche = new JLabel("Bienvenidos a AppVideo");
		labelCompartirCoche.setFont(new Font("Arial", Font.PLAIN, 30));
		labelCompartirCoche.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelCompartirCoche, BorderLayout.CENTER);

		frmVentanaPrincipal.pack();
	}

}*/
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class VentanaPrincipal {
	public static final int WINDOW_WIDTH=800;
	public static final int WINDOW_HEIGHT=800;
	private JPanel contenedor;
	private Menu menu;
	private JFrame ventana=new JFrame();
	public VentanaPrincipal() {
		ventana.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor=(JPanel) ventana.getContentPane();
		//Menu
		menu=new Menu(this.getJFrame(),contenedor);
		/*--pantalla de abajo---*/
		ScrollScreen scroll= new ScrollScreen();
		ventana.add(scroll);
		ventana.setVisible(true);
	}
	public static void main(String[] args) {
		VentanaPrincipal ini=new VentanaPrincipal();
	}
	
	public static void fixSize(JComponent c , int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
	
	public JFrame getJFrame() {
		return ventana;
	}
	
	public static void setButton(JButton boton) {
		boton.setBackground(null);
		boton.setBorderPainted(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
