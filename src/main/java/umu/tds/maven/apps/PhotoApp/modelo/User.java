package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase del modelo de dominio para representar a un Usuario de la PhotoApp
 */

public class User {

	private int code;
	private String fullName;
	private String userName;
	private String email;
	private String password;
	private Date dateOfBirth;
	private List<User> followers;
	private List<User> followed;
	private boolean isPremium;
	private String profilePic;
	private String bio;

	public User(String fullName, String userName, String email, String password, Date dateOfBirth, boolean isPremium,
			String profilePic, String bio, LinkedList<User> followers, LinkedList<User> followed) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.bio = bio;
		this.profilePic = profilePic;
		this.dateOfBirth = dateOfBirth;
		this.followers = followers;
		this.followed = followed;
		this.isPremium = isPremium;

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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	@Override
	public String toString() {
		return userName;
	}

}
