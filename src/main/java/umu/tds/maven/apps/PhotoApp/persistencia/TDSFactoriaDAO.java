package umu.tds.maven.apps.PhotoApp.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {

	@Override
	public IUserAdapterDAO getUserDAO() {
		return UserAdapterTDS.getInstance();
	}
	
	@Override
	public IPostAdapterDAO getPostDAO() {
		return PostAdapterTDS.getInstance();
	}
	
	
}
