package umu.tds.maven.apps.PhotoApp.persistencia;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;

/** Utilizamos esta clase abstracta para recoger todos los atributos y métodos que los adaptadores TDS tienen
 * en común */

public abstract class AdapterTDS {
	
	protected static ServicioPersistencia servPersistencia;
	protected AdapterTDS instance;
	
	
	
	public AdapterTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
		
	protected String obtenerCodigosVentas(List<DomainObject> domainObjectList) {
		String aux = "";
		for (DomainObject o : domainObjectList) {
			aux += o.getCode() + " ";
		}
		return aux.trim();
	}
	
	protected abstract Entidad objectToEntity(DomainObject o);
	protected abstract DomainObject entityToObject(Entidad en);
}
