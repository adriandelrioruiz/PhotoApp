package umu.tds.maven.apps.PhotoApp.persistencia;

import java.util.List;

import umu.tds.maven.apps.PhotoApp.modelo.Album;

public interface IAlbumAdapterDAO {
	public Album getAlbum(int code);

	public void addAlbum(Album album);
<<<<<<< HEAD
	public void deleteAlbum(int code);
=======

	public void deleteAlbum(int code);

>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
	public void updateAlbum(Album album, String attribute);

	public List<Album> getAllAlbums();
}
