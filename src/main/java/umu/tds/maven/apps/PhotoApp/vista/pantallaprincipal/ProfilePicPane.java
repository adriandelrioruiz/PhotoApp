package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ProfilePicPane extends JPanel {

	private BufferedImage image;

	public ProfilePicPane(JFrame frame, String imagePath) {
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {

		int width = 96;
		int height = 96;
		int x = 103;
		int y = 10;

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(image, 0, 0, width, height, this);
		g2d.setClip(x, y, width, height);
		g2d.drawOval(x, y, width, height);

	}
}
