package umu.tds.maven.apps.PhotoApp.vista;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;



import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

public class DescuentoFrame extends JFrame {
	public DescuentoFrame() {
		this.setSize(350,250);
		this.setLocationRelativeTo(null); 
	    JPanel container=(JPanel) this.getContentPane();
	  	Double descuento=PhotoAppController.getInstance().getDiscount();
	    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		JLabel firstPanel=new JLabel("HAZTE PREMIUM ");
		
		centrarTexto(firstPanel);
		JLabel secondPanel=new JLabel("CON ESTE DESCUENTO");
		centrarTexto(secondPanel);
		JLabel thirdPanel=new JLabel(descuento.toString()+" €");
		centrarTexto(thirdPanel);
		JButton pagar=new JButton("PAGAR");
		VentanaPrincipal.fixSize(pagar,350,50);
		pagar.setHorizontalAlignment(SwingConstants.CENTER);
		pagar.setFont(new Font("Arial", Font.BOLD, 25));
		pagar.setBackground(Color.ORANGE);
		container.add(firstPanel);
		container.add(secondPanel);
		container.add(thirdPanel);
		container.add(pagar);
		pagar.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  PhotoAppController.getInstance().changeToPremium();
		    	  cerrar();
		      }

			
		    });
		Color color= new Color(50,180,0) ;
		container.setBackground(color);
		this.setVisible(true);
	}
private void centrarTexto(JLabel component) {
	VentanaPrincipal.fixSize(component,350,50);
	component.setBackground(null);
	component.setFont(new Font("Arial", Font.BOLD, 25));
	component.setHorizontalAlignment(SwingConstants.CENTER);
}
private void cerrar() {
		this.setVisible(false);
		
	}
}
