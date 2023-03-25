package umu.tds.maven.apps.PhotoApp.main;

import java.awt.EventQueue;

import umu.tds.maven.apps.PhotoApp.vista.loginregistro.LoginFrame;
<<<<<<< HEAD

=======
>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git

public class Launcher {
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
<<<<<<< HEAD
					
					//LoginView ventana = new LoginView();
=======

					// LoginView ventana = new LoginView();
>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git
					LoginFrame ventana = new LoginFrame();
					// Hacemos visible la ventana
					ventana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
