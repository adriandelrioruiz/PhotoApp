package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import javax.swing.JLabel;

/** Clase para mostrar una ventana con un post que ya está subido */

@SuppressWarnings("serial")
public abstract class ShowUploadedPostFrame extends ShowPostFrame {
	
	// Id del post que queramos mostrar
	protected int postId;
	
	// Label para mostrar el título del post
	private JLabel titleLabel;

	public ShowUploadedPostFrame(int userId, int postId) {
		super(userId);
		
		this.postId = postId;
		
		initialize();
	}
	
	@Override
	protected void initialize() {
		
		super.initialize();
	}
	
	
	@Override
	protected void createNorthPane() {
		super.createNorthPane();
		titleLabel = new JLabel(controller.getPhotoTitle(postId));
		northPane.add(titleLabel);
	}
	
	
	
}
