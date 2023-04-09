package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import umu.tds.maven.apps.PhotoApp.modelo.Comment;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

public class CommentPanel extends JPanel{
	/**
	 * 
	 */
	private static final int PANEL_WIDTH = 260;
	private static final int PANEL_HEIGHT = 70;
	private static final long serialVersionUID = 1L;
	private Comment comentario;
	private JTextArea commentTxtArea;
	public CommentPanel(Comment c) {
		this.comentario=c;
		initialize();
	}

	private void initialize() {
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		//this.setMaximumSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.setMinimumSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		String text=this.comentario.getText();
		String user=comentario.getUser().getUserName();
		String c="-"+user+"-ha comentado: \""+text+"\"";
		commentTxtArea = new JTextArea();
		commentTxtArea.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		commentTxtArea.setEditable(false);
		commentTxtArea.setBorder(null);
		commentTxtArea.setForeground(Color.BLACK);
		commentTxtArea.setBackground(null);
		commentTxtArea.setLineWrap(true);
		commentTxtArea.setWrapStyleWord(true);
		commentTxtArea.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 14));
		commentTxtArea.setText(c);
		add(commentTxtArea);
		
	}
}
