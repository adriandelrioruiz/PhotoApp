package umu.tds.maven.apps.PhotoApp.vista.premium;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

@SuppressWarnings("serial")
public class DescuentoFrame extends JFrame {
	
	public DescuentoFrame() {
		initialize();
		setVisible(true);
	}

	private void initialize() {

        setSize(320, 130);
        setLocationRelativeTo(null);
        
        double discount = PhotoAppController.getInstance().getDiscount();
        JLabel premiumLabel = new JLabel("<html><center>Hazte premium y<br>desbloquea nuevas funcionalidades por " + String.valueOf(discount) + "$</center></html>");
        premiumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton premiumButton = new JButton("Hazte premium");
        premiumButton.setSize(premiumButton.getPreferredSize().width, premiumButton.getHeight());
        premiumButton.setForeground(Color.white);
        premiumButton.setBackground(ViewConstants.APP_GREEN_COLOR);
        premiumButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PhotoAppController.getInstance().changeToPremium();
				dispose();
			}
		});
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.add(premiumLabel);
        panel.add(premiumButton);
        add(panel);
        
	}
}
