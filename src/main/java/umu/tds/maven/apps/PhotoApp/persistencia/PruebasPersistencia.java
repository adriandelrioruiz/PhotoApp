package umu.tds.maven.apps.PhotoApp.persistencia;

import java.util.Date;

import umu.tds.maven.apps.PhotoApp.modelo.User;

public class PruebasPersistencia {
	public static void main(String[] args) {
		User user = new User("adrian", "adriandelrio", "adri@gmail", "yuiii", new Date(), false, "foto", "biooooo", null,
				null);
		
		UserAdapterTDS ada = UserAdapterTDS.getInstance();
		ada.registerUser(user);
		System.out.println(ada.getUser(user.getCode()).getEmail());
		System.out.println(ada.getUser(user.getCode()).getPassword());
		System.out.println(ada.getUser(user.getCode()).getBio());
		System.out.println(ada.getUser(user.getCode()).getFullName());
		System.out.println(ada.getUser(user.getCode()).getDateOfBirth().toString());
		System.out.println(ada.getUser(user.getCode()).getProfilePic());	
	}
}
