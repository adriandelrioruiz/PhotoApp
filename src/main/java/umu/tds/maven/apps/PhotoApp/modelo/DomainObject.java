package umu.tds.maven.apps.PhotoApp.modelo;


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
