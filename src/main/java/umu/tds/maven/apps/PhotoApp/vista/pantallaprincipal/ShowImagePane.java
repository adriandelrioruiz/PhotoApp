package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public abstract class ShowImagePane extends JFrame {
	
	protected JPanel imageTitlePane;
	protected JPanel imageAndCommentPane;
	protected JPanel exitButtonPane;
	
	protected JTextArea comment;
	
	protected Image image;
	
	public ShowImagePane(Image image) {
		this.image = image.getScaledInstance(340, 300, Image.SCALE_SMOOTH);
		setSize(700, 420);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		initialize();
		setVisible(true);
	}
	
	private void initialize() {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		createImageTitlePane();
		createImageAndCommentPane();
		createExitButtonPane();
	}
	
	protected abstract void createImageTitlePane();
	
	protected void createImageAndCommentPane() {
		imageAndCommentPane = new JPanel();
		imageAndCommentPane.setAlignmentX(CENTER_ALIGNMENT);
		imageAndCommentPane.setLayout(new BorderLayout());
		JLabel imageLabel = new JLabel(new ImageIcon(image));
		imageAndCommentPane.add(imageLabel, BorderLayout.WEST);
		
		// Crea el campo de texto con scroll
        comment = new JTextArea();
        comment.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(comment);
        scrollPane.setPreferredSize(new Dimension(300, 60)); // Ajusta el tamaño del scroll
        imageAndCommentPane.add(scrollPane, BorderLayout.EAST);
        
        comment.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!comment.isFocusable()) {
					comment.setFocusable(true);
					comment.grabFocus();
					comment.setText("");
				}
			}
		});
	
		
		getContentPane().add(imageAndCommentPane);
	}
	
	protected abstract void createExitButtonPane();
}
