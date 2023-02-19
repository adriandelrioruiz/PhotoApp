package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

public class DescuentoFrame extends JFrame {
	public DescuentoFrame() {
		this.setSize(350,350);
		this.setLocationRelativeTo(null);
		JPanel conteiner= (JPanel) this.getContentPane();
		conteiner.setLayout(new BoxLayout(conteiner, BoxLayout.Y_AXIS));
		JLabel firstPanel=new JLabel("HAZTE PREMIUM");
		centrarTexto(firstPanel);
		JLabel secondPanel=new JLabel("CON ESTE NUEVO DESCUENTO");
		centrarTexto(secondPanel);
		Double descuento=PhotoAppController.getInstance().getDiscount();
		JLabel thirdPanel=new JLabel(descuento.toString()+" €");
		centrarTexto(thirdPanel);
		JButton pagar=new JButton("PAGAR");
		LoggedFrame.fixSize(pagar,500,50);
		pagar.setHorizontalAlignment(SwingConstants.CENTER);
		conteiner.add(firstPanel);
		conteiner.add(secondPanel);
		conteiner.add(thirdPanel);
		conteiner.add(pagar);
		pagar.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  PhotoAppController.getInstance().changeToPremium();
		    	  cerrar();
		      }

			
		    });
		this.setVisible(true);
	}
private void centrarTexto(JLabel component) {
	LoggedFrame.fixSize(component,350,50);
	component.setHorizontalAlignment(SwingConstants.CENTER);
}
private void cerrar() {
		this.setVisible(false);
		
	}
}
