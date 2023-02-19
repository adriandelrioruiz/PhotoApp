package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase del modelo de dominio para representar a un Usuario de la PhotoApp
 */

public class User extends DomainObject {

	private String fullName;
	private String userName;
	private String email;
	private String password;
	private Date dateOfBirth;
	private List<User> followers;
	private List<User> followed;
	private List<Photo> photos;
	private List<Album> albums;
	private List<Notification> notifications;
	private boolean isPremium;
	private String profilePic;
	private String bio;

	public User(String fullName, String email, String userName, String password, Date dateOfBirth, boolean isPremium,
			String profilePic, String bio) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.profilePic = profilePic;
		this.dateOfBirth = dateOfBirth;
		this.followers = new LinkedList<>();
		this.followed = new LinkedList<>();
		this.notifications = new LinkedList<>();
		this.photos = new LinkedList<>();
		this.albums = new LinkedList<>();
		this.isPremium = isPremium;
		this.bio = bio;

	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<User> getFollowers() {
		return Collections.unmodifiableList(followers);
	}

	public List<User> getFollowed() {
		return Collections.unmodifiableList(followed);
	}

	public List<Photo> getPhotos() {
		return Collections.unmodifiableList(photos);
	}
	
	public List<Album> getAlbums() {
		return Collections.unmodifiableList(albums);
	}

	public List<Notification> getNotifications() {
		return Collections.unmodifiableList(notifications);
	}


	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public void addFollower(User newFollower) {
		followers.add(newFollower);
	}

	public void addFollowed(User newFollowed) {
		followed.add(newFollowed);
	}

	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	public void addAlbum(Album album) {
		albums.add(album);
	}

	public void addNotification(Notification notification) {
		notifications.add(notification);
	}

	public void removeFollower(User followerUser) {
		followers.remove(followerUser);
	}

	public void removeFollowed(User followedUser) {
		followed.remove(followedUser);
	}
	
	public void removePhoto(Photo photo) {
		photos.remove(photo);
	}

	public void removeAlbum(Album album) {
		albums.remove(album);
	}
	
	public void removeNotification(int code) {
		for (Notification n : notifications)
			if (n.getCode() == code) {
				notifications.remove(n);
				return;
			}
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public void setFollowed(List<User> followed) {
		this.followed = followed;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	// Método para ordenar los photos por fecha de publicación
	// TODO cambiar este método o no
	public void sortPosts() {
		Collections.sort(photos);
		Collections.sort(albums);
	}

	@Override
	public String toString() {
		return userName;
	}

}
