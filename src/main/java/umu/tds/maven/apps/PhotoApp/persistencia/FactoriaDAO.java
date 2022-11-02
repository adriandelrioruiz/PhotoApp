package umu.tds.maven.apps.PhotoApp.persistencia;


//Define una factoria abstracta que devuelve todos los DAO de la aplicacion

public abstract class FactoriaDAO {
	private static FactoriaDAO instance;
	
	public static final String DAO_TDS = "umu.tds.maven.apps.PhotoApp.persistencia.TDSFactoriaDAO";
		
	/** 
	 * Crea un tipo de factoria DAO.
	 * Solo existe el tipo TDSFactoriaDAO
	 */
	
	public static FactoriaDAO getInstance(String tipo) throws Exception {
		if (instance == null)
			try { instance =(FactoriaDAO) Class.forName(tipo).getDeclaredConstructor().newInstance();
			} catch (Exception e) {	
				e.printStackTrace();
			} 
		return instance;
	}


	public static FactoriaDAO getInstance() throws Exception {
			if (instance == null) return getInstance (FactoriaDAO.DAO_TDS);
					else return instance;
		}

	/* Constructor */
	protected FactoriaDAO (){}
		
		
	// Metodos factoria que devuelven adaptadores que implementen estos interfaces
	public abstract IUserAdapterDAO getUserDAO();
	public abstract IPostAdapterDAO getPostDAO();


}
