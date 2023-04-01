package umu.tds.maven.apps.PhotoApp.vista.notificacion;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Notification;
import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;
import umu.tds.maven.apps.PhotoApp.vista.search.ShowUserSearchPane;

public class NotificationFrame extends JFrame{

	private static final int FRAME_WIDTH = 320;
	private static final int FRAME_HEIGHT = 420;
	private static final int PANEL_HEIGHT = 70;
	private List<Notification> notifications;
	// Panel que contendrá todos los paneles de cada elemento encontrado
	private JPanel notificationListPane;
	// JScrollPane
	private JScrollPane scrollPane;
	
	public NotificationFrame() {
			
			this.notifications = PhotoAppController.getInstance().getNotifications(PhotoAppController.getInstance().getId());
		
			if (!notifications.isEmpty()) {
				initialize();
			}
			else//No TIENES NOTIFICACIONES
				dispose();
		}
		
		private void initialize() {
			// Crear panel que contendrá los paneles
			notificationListPane = new JPanel();
			notificationListPane.setLayout(new BoxLayout(notificationListPane, BoxLayout.Y_AXIS));
	        
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setTitle("Notificaciones: " + String.valueOf(notifications.size()));
			setLocationRelativeTo(null);
			setResizable(false);
			// Añadimos todos los usuarios
			for (int i = 0; i < notifications.size(); i++) {
				//new panel.
				NotificationPane noti= new NotificationPane(this,(Notification) notifications.get(i));
				noti.setBackground(Color.LIGHT_GRAY);
				Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
				noti.setBorder(border);
				noti.setPreferredSize(new Dimension(FRAME_WIDTH,PANEL_HEIGHT));
			    notificationListPane.add(noti);
			}
			// Crear JScrollPane y añadir el panelList
	        scrollPane = new JScrollPane(notificationListPane);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	        // Añadir el JScrollPane al JFrame
	        getContentPane().add(scrollPane);
			
			setVisible(true);
			
		}
	protected void cerrar() {
		dispose();
	}
	

}
