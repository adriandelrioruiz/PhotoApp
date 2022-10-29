package umu.tds.maven.apps.PhotoApp;

import java.util.ArrayList;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class Launcher {
	public static void main(String[] args) {
		System.out.println("hello world");
		System.out.println("ola");
		
		ServicioPersistencia servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		Entidad eProducto = new Entidad();
		eProducto = servPersistencia.registrarEntidad(eProducto);
	}
}
