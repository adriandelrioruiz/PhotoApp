package umu.tds.maven.apps.PhotoApp.main;


import java.awt.EventQueue;

import umu.tds.maven.apps.PhotoApp.vista.loginregistro.LoginFrame;


public class Launcher {
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//LoginView ventana = new LoginView();
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
