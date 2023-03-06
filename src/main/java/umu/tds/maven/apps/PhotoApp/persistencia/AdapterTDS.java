package umu.tds.maven.apps.PhotoApp.persistencia;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;

/**
 * Utilizamos esta clase abstracta para recoger todos los atributos y métodos
 * que los adaptadores TDS tienen en común
 */

public abstract class AdapterTDS {

	protected static ServicioPersistencia servPersistencia;

	public AdapterTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	protected abstract Entidad objectToEntity(DomainObject o);

	protected abstract DomainObject entityToObject(Entidad en);
}
