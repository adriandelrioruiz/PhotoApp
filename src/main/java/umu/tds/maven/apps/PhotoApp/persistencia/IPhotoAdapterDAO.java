package umu.tds.maven.apps.PhotoApp.persistencia;

import java.util.List;

import umu.tds.maven.apps.PhotoApp.modelo.Photo;

public interface IPhotoAdapterDAO {
	public Photo getPhoto(int code);
	public void addPhoto(Photo photo);
	public void deletePhoto(int code);
	public void updatePhoto(Photo photo, String attribute);
	public List<Photo> getAllPhotos();
}
