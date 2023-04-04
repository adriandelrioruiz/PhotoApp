package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.topLikes.TopLikesFrame;

public class PremiumMenu extends JPopupMenu{
	private static final long serialVersionUID = 1L;
	private static final int FRAME_HEIGHT = 50;
	private static final int FRAME_PREMIUM_HEIGHT = 150;
	private static final int FRAME_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 20;
	private LoggedFrame frame;
	private JButton pdf ,excel,topLikes;
	public PremiumMenu() {
		//this.frame=fr;
		this.setSize(FRAME_WIDTH, FRAME_PREMIUM_HEIGHT);
		//this.setLocation(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH - FRAME_WIDTH, 0);
		initialize();
		

	}
	private void initialize() {
		pdf = new JButton("Generar PDF");
		LoggedFrame.fixSize(pdf, FRAME_WIDTH, BUTTON_HEIGHT);
		this.add(pdf);
		excel = new JButton("Generar EXCEL");
		LoggedFrame.fixSize(excel, FRAME_WIDTH, BUTTON_HEIGHT);
		this.add(excel);
		topLikes = new JButton("Top Me Gusta");
		LoggedFrame.fixSize(topLikes, FRAME_WIDTH, BUTTON_HEIGHT);
		this.add(topLikes);
		pdf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PhotoAppController.getInstance().generatePDF("C:\\Users\\elcrio\\Desktop");
			}
        });
		excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PhotoAppController.getInstance().generateExcel("C:\\Users\\elcrio\\Desktop");
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
		// TODO Auto-generated method stub
		new TopLikesFrame();
	}
}
