package umu.tds.maven.apps.PhotoApp.vista;

import java.util.List;

import javax.swing.JFrame;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;

public class BusquedaFrame extends JFrame{
	
	public BusquedaFrame(String query) {
		List<DomainObject> busqueda=PhotoAppController.getInstance().search(query);
		for (DomainObject d : busqueda) {
			
		}
		
		
	}

}
