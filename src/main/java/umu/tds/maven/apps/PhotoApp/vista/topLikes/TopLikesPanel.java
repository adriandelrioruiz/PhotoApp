package umu.tds.maven.apps.PhotoApp.vista.topLikes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;

public class TopLikesPanel extends JPanel{

	private Photo photo;
	private JLabel west,east;
	private static final int PANEL_WIDTH=288;
	public TopLikesPanel(Photo p) {
		this.photo=p;
		initialize();
	}


	private void initialize() {
		// TODO Auto-generated method stub
		//
		this.setPreferredSize(new Dimension(PANEL_WIDTH,90));
		this.setMaximumSize(new Dimension(PANEL_WIDTH,90));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		createWestPane();
		createEastPane();
	}
	private void createEastPane() {
		east=new JLabel();
		//PhotoAppController.getInstance().getLikes(photo.getCode())
		east.setSize(PANEL_WIDTH-90, 90);
		int likes = PhotoAppController.getInstance().getLikes(photo.getCode());
		east.setText(" "+likes+" likes");
		this.add(east);
	}


	private void createWestPane() {
		west = new JLabel();
		west.setSize(90, 90);
		
		try {
			String path = PhotoAppController.getInstance().getPath(photo.getCode());
			Image image = ImageIO.read(new File(path));
			ImageIcon icon = new ImageIcon(
			image.getScaledInstance((int) this.west.getWidth(), (int) this.west.getHeight(), 0));
			// Lo añadimos al panel oeste
			this.west.setIcon(icon);
		}catch (IOException e) {
			e.printStackTrace();
		}
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		west.setBorder(border);
		this.add(west);
	}
}
