package umu.tds.maven.apps.PhotoApp.vista.notificacion;


import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.maven.apps.PhotoApp.modelo.Notification;

public class NotificationPane extends JPanel{
	
	
	public NotificationPane(Notification n) {
		JLabel nombre =new JLabel();
		//n.getPhoto()
		//NOMBRE HA SUBIDO UNA FOTO/ALBUM
		JLabel fecha =new JLabel(n.getDate().toString());
		
	}
}
