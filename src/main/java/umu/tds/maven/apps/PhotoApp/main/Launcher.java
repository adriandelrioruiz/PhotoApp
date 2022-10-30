package umu.tds.maven.apps.PhotoApp.main;

import java.awt.EventQueue;
import java.util.Date;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.LoginView;


public class Launcher {
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView ventana = new LoginView();
					PhotoAppController.getInstance().registerUser("prepu", "awe", "awawd", "adaw", new Date(), "asd", "Awdaw");
					
					
					ventana.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
