package umu.tds.maven.apps.PhotoApp.persistencia;

import java.util.List;

import umu.tds.maven.apps.PhotoApp.modelo.User;

public interface IUserAdapterDAO {
	public void addUser(User user);
	public User getUser(int code);
	public void deleteUser(int code);
	public List<User> getAllUsers();
	public void updateUser(User user, String attribute);
}
