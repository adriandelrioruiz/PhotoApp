package umu.tds.maven.apps.PhotoApp.modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import umu.tds.maven.apps.PhotoApp.persistencia.UserAdapterTDS;

/** Esta clase abstracta sirve para englobar a todos los objetos del modelo
 * de dominio, puesto que todos tienen atributos y m�todos en com�n */

public abstract class DomainObject {
	
	protected int code;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
}
