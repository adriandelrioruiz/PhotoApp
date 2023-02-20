package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.loginregistro.EditRegisterFrame;

@SuppressWarnings("serial")
public class MyProfilePane extends AbstractProfilePane {
	
	private JButton btnEditProfile;

	public MyProfilePane() {
		super();
	}
	

	
	protected void createNorthPanel() {
		
		super.createNorthPanel();
		
		btnEditProfile = new JButton("Editar perfil");
		btnEditProfile.setBackground(ViewConstants.APP_GREEN_COLOR);
		btnEditProfile.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_btnEditProfile = new GridBagConstraints();
		gbc_btnEditProfile.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditProfile.gridx = 2;
		gbc_btnEditProfile.gridy = 3;
		northPanel.add(btnEditProfile, gbc_btnEditProfile);
		
		addListeners();
	}
	
	@Override
	protected void addListeners() {
		addEditProfileButtonListener(btnEditProfile);
	}
	
	protected void addEditProfileButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditRegisterFrame frame = new EditRegisterFrame();
				
			}
		});
	}

	
	@Override
	protected void createCenterPanel() {
		centerPanel = new AllPostsPane(photos, albums, true);
		add(centerPanel, BorderLayout.CENTER);
	}



	@Override
	protected String getProfilePic() {
		return controller.getProfilePic();
	}



	@Override
	protected int getNumOfPosts() {
		return controller.getPhotos().size() + controller.getAlbums().size();
	}



	@Override
	protected String getUserName() {
		return controller.getUsername();
	}



	@Override
	protected String getFullName() {
		return controller.getFullName();
	}



	@Override
	protected int getFollowers() {
		return controller.getFollowers();
	}



	@Override
	protected int getFollowed() {
		return controller.getFollowed();
	}



	@Override
	protected void initializePhotos() {
		photos = controller.getPhotos();
		albums = controller.getAlbums();
	}

	
}
