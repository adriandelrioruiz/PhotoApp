package umu.tds.maven.apps.PhotoApp.vista.notificacion;


import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.Album;
import umu.tds.maven.apps.PhotoApp.modelo.Notification;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedAlbumFrame;
import umu.tds.maven.apps.PhotoApp.vista.mostrarpost.ShowOtherUploadedPhotoFrame;

public class NotificationPane extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String STRALBUM="Ha añadido un nuevo Album";
	private static final String STRPHOTO="Ha añadido una nueva Foto";
	private JLabel nombre;
	private JLabel str; 
	private JLabel fecha;
	private Notification noti;
	private NotificationFrame frame;
	public NotificationPane(NotificationFrame frame,Notification n) {
		this.noti=n;
		this.frame=frame;
		initialize();
		this.addListeners();
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}
	private void initialize() {
		nombre =new JLabel(PhotoAppController.getInstance().getOwnerOfPhoto(noti.getPost().getCode()));
		nombre.setAlignmentX(LEFT_ALIGNMENT);
		setLabel(nombre);
		if(noti.getisAlbum()) {
			 str =new JLabel(STRALBUM);
			 str.setAlignmentX(CENTER_ALIGNMENT);
			 setLabel(str);
		}else {
			str=new JLabel(STRPHOTO);
		}
		
		fecha =new JLabel("["+noti.getDate().toString()+"]");
		nombre.setAlignmentX(RIGHT_ALIGNMENT);
		setLabel(fecha);
		this.add(nombre);
		this.add(str);
		this.add(fecha);
	}
	private void visualizarPost() {
		//PhotoAppController.getInstance().
		if(noti.getPost() instanceof Album) {
			Album a=(Album) noti.getPost();
			ShowOtherUploadedAlbumFrame other= new ShowOtherUploadedAlbumFrame(a.getUser().getCode(),a.getCode());
		}else {
			Photo p=(Photo) noti.getPost();
			ShowOtherUploadedPhotoFrame other= new ShowOtherUploadedPhotoFrame(p.getUser().getCode(),p.getCode());
		}
		//CERRAR EL FRAME
		frame.cerrar();
	}
	private void setLabel(JLabel label) {
		
		label.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 12));
		Border margin = BorderFactory.createEmptyBorder(10, 0, 0, 0);
		label.setBorder(margin);
	}
	protected void addListeners() {
		this.addMouseListener(new ShowNotificationHandler(this));
		
	}
	protected class ShowNotificationHandler extends MouseAdapter {
		private NotificationPane panel;

		public  ShowNotificationHandler(NotificationPane panel) {
			this.panel=panel;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			this.panel.visualizarPost();
		}
	}
	
}
