package umu.tds.maven.apps.PhotoApp.persistencia;

import java.util.List;

import umu.tds.maven.apps.PhotoApp.modelo.Album;

public interface IAlbumAdapterDAO {
	public Album getAlbum(int code);
	public void addAlbum(Album album);
	public void deleteAlbum(Album album);
	public void updateAlbum(Album album, String attribute);
	public List<Album> getAllAlbums();
}
