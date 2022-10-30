package umu.tds.maven.apps.PhotoApp.persistencia;

import umu.tds.maven.apps.PhotoApp.modelo.User;

public interface IUserAdapterDAO {
	public void registerUser(User user);
	public User getUser(int code);
	public void deleteUser(User user);
}
