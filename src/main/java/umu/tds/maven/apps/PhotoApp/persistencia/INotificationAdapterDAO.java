package umu.tds.maven.apps.PhotoApp.persistencia;

import umu.tds.maven.apps.PhotoApp.modelo.Notification;

public interface INotificationAdapterDAO {
	public Notification getNotification(int code);

	public void addNotification(Notification notification);

	public void deleteNotification(int code);

	public int getNotificationCodeByPost(int postCode);
}
