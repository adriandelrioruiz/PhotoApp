package umu.tds.maven.apps.PhotoApp.persistencia;


import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;

/** Utilizamos esta clase abstracta para recoger todos los atributos y métodos que los adaptadores TDS tienen
 * en común */

public abstract class AdapterTDS {
	
	protected static ServicioPersistencia servPersistencia;
	
	
	public AdapterTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	
	/* TODO
	protected String getCodesFromObjects(List<DomainObject> objects) {
		String codes = "";
		for (DomainObject o : objects) {
			codes += o.getCode() + " ";
		}
		return codes.trim();
		
	}
	
	protected List<DomainObject> getObjectsFromCodes(String codes) {
		List<DomainObject> objecstList = new LinkedList<DomainObject>();
		if (codes != null) {
			StringTokenizer strTok = new StringTokenizer(codes, " ");
			while (strTok.hasMoreTokens()) {
				DomainObject o = getObject(strTok);
				objecstList.add(o);
			}
		}
		return objecstList;
	}
	
	
	protected abstract DomainObject getObject(StringTokenizer strTok);*/
	
	protected abstract Entidad objectToEntity(DomainObject o);
	protected abstract DomainObject entityToObject(Entidad en);
}
