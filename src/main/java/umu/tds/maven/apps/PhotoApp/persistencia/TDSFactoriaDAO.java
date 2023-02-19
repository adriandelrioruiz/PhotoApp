package umu.tds.maven.apps.PhotoApp.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {

	@Override
	public IUserAdapterDAO getUserDAO() {
		return UserAdapterTDS.getInstance();
	}
	
	@Override
	public IPhotoAdapterDAO getPhotoDAO() {
		return PhotoAdapterTDS.getInstance();
	}
	
	@Override
	public IAlbumAdapterDAO getAlbumDAO() {
		return AlbumAdapterTDS.getInstance();
	}

	@Override
	public ICommentAdapterDAO getCommentDAO() {
		return CommentAdapterTDS.getInstance();
	}

	@Override
	public INotificationAdapterDAO getNotificationDAO() {
		return NotificationAdapterTDS.getInstance();
	}
	
	
}
