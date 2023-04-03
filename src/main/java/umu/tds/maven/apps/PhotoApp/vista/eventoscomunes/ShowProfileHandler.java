package umu.tds.maven.apps.PhotoApp.vista.eventoscomunes;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

public class ShowProfileHandler extends MouseAdapter {
	private JFrame frame;
	private LoggedFrame loggedFame;
	
	public ShowProfileHandler(LoggedFrame loggedFame,JFrame frame) {
		this.frame=frame;
		this.loggedFame=loggedFame;
		
	}
	public void mouseClicked(MouseEvent e) {
		// Mostramos el perfil
		
		changeToOtherProfile(Id);
		// Cerramos el frame
		frame.dispose();
	}
}
