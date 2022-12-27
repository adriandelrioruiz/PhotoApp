package umu.tds.maven.apps.PhotoApp.persistencia;

import umu.tds.maven.apps.PhotoApp.modelo.Comment;

public interface ICommentAdapterDAO {
	public Comment getComment(int id);
	public void addComment(Comment comment);
	public void deleteComment(Comment comment);
}
