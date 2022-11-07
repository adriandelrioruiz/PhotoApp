package umu.tds.maven.apps.PhotoApp.persistencia;

import java.util.List;

import umu.tds.maven.apps.PhotoApp.modelo.Post;

public interface IPostAdapterDAO {
	public Post getPost(int code);
	public void addPost(Post post);
	public void deletePost(Post post);
	public void updatePost(Post post, String attribute);
	public List<Post> getAllPosts();
}
