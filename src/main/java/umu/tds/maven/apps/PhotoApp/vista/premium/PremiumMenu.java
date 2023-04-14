package umu.tds.maven.apps.PhotoApp.vista.premium;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPopupMenu;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;
import umu.tds.maven.apps.PhotoApp.vista.topLikes.TopLikesFrame;

public class PremiumMenu extends JPopupMenu{
	private static final long serialVersionUID = 1L;
	private static final int FRAME_PREMIUM_HEIGHT = 150;
	private static final int FRAME_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 20;
	private JButton pdf ,excel,topLikes;
	
	public PremiumMenu() {
		this.setSize(FRAME_WIDTH, FRAME_PREMIUM_HEIGHT);
		initialize();
	}
	
	private void initialize() {
		pdf = new JButton("Generar PDF");
		pdf.setForeground(Color.white);
		pdf.setBackground(ViewConstants.APP_GREEN_COLOR);
		
		LoggedFrame.fixSize(pdf, FRAME_WIDTH, BUTTON_HEIGHT);
		this.add(pdf);
		
		excel = new JButton("Generar EXCEL");
		excel.setForeground(Color.white);
		excel.setBackground(ViewConstants.APP_GREEN_COLOR);
		
		LoggedFrame.fixSize(excel, FRAME_WIDTH, BUTTON_HEIGHT);
		this.add(excel);
		
		topLikes = new JButton("Top Me Gusta");
		topLikes.setForeground(Color.white);
		topLikes.setBackground(ViewConstants.APP_GREEN_COLOR);
		
		LoggedFrame.fixSize(topLikes, FRAME_WIDTH, BUTTON_HEIGHT);
		this.add(topLikes);
		pdf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PhotoAppController.getInstance().generatePDF("C:\\Users\\elcrio\\Desktop\\TDS");
			}
			
        });
		excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PhotoAppController.getInstance().generateExcel("C:\\Users\\elcrio\\Desktop\\TDS");
			}
        });
		topLikes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				topLikes();
			}

			
        });
	}

	public void topLikes() {
		new TopLikesFrame();
	}
}
