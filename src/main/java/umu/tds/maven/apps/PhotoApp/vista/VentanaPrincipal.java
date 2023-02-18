package umu.tds.maven.apps.PhotoApp.vista;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
public class VentanaPrincipal extends JFrame {
	public static final int WINDOW_WIDTH=800;
	public static final int WINDOW_HEIGHT=800;
	private JPanel contenedor;
	private JPanel bottomPanel=new JPanel();
	private Menu menu;
	protected PhotoAppController controller;
	public VentanaPrincipal() {
		 
		this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		contenedor=(JPanel) this.getContentPane();
		//controller = PhotoAppController.getInstance();
		//Menu
		menu=new Menu(this,contenedor);
		/*--pantalla de abajo---*/
		
		//controller.getProfilePic();
		
		ScrollScreen scroll= new ScrollScreen();
		SetBottomPanel(scroll);
		contenedor.add(bottomPanel);
		//this.add(scroll);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		VentanaPrincipal ini=new VentanaPrincipal();
	}
	
	public static void fixSize(JComponent c , int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
	public void SetBottomPanel(JComponent component) {
		bottomPanel.removeAll();
		bottomPanel.add(component);	
	}
	public static void setButton(JButton boton) {
		boton.setBackground(null);
		boton.setBorderPainted(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
