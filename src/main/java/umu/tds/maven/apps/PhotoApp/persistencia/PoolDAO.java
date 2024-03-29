package umu.tds.maven.apps.PhotoApp.persistencia;

/*Esta clase implementa un pool para los adaptadores que lo necesiten*/

import java.util.Hashtable;

public class PoolDAO {
	private static PoolDAO instance;
	private Hashtable<Integer, Object> pool;

	private PoolDAO() {
		pool = new Hashtable<Integer, Object>();
	}

	public static PoolDAO getInstance() {
		if (instance == null)
			instance = new PoolDAO();
		return instance;

	}

	public Object getObject(int id) {
		return pool.get(id);
	} // devuelve null si no encuentra el objeto

	public void addObject(int id, Object obj) {
		pool.put(id, obj);
	}

	public boolean contains(int id) {
		return pool.containsKey(id);
	}
}
