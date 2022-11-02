package umu.tds.maven.apps.PhotoApp.persistencia;

import umu.tds.maven.apps.PhotoApp.modelo.Post;

public interface IPostAdapterDAO {
	public Post getPost(int code);
}
