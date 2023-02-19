package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

@SuppressWarnings("serial")
public class OthersProfilePane extends AbstractProfilePane {

	private String userName;
	
	private JButton btnFollow;

	User userSearch;

	public OthersProfilePane(String userName) {
		super();
		this.userName = userName;
		// Coger las fotos y álbumes del controlador
		photos = null;
		albums = null;
	}

	protected void createNorthPanel() {

		super.createNorthPanel();

		btnFollow = new JButton();

		// Según se siga o no al usuario, se mostrará una cosa u otra
		if (!controller.isFollowed(userSearch.getUserName())) {
			btnFollow.setText("Seguir");
			addFollowButtonListener(btnFollow);
		} else {
			btnFollow.setText("Dejar de seguir");
			addUnfollowButtonListener(btnFollow);
		}
		btnFollow.setBackground(ViewConstants.APP_GREEN_COLOR);
		btnFollow.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_btnEditProfile = new GridBagConstraints();
		gbc_btnEditProfile.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditProfile.gridx = 2;
		gbc_btnEditProfile.gridy = 3;
		northPanel.add(btnFollow, gbc_btnEditProfile);
	}

	@Override
	protected void addListeners() {
		addEditProfileButtonListener(btnFollow);
	}

	protected void addEditProfileButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

	protected void addFollowButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.follow(userSearch.getUserName());
				btnFollow.setText("Dejar de seguir");
				addUnfollowButtonListener(btnFollow);
			}
		});
	}

	protected void addUnfollowButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.unFollow(userSearch.getUserName());
				btnFollow.setText("Seguir");
				addFollowButtonListener(btnFollow);
			}
		});
	}

	@Override
	protected void createCenterPanel() {
		centerPanel = new AllPostsPane(photos, albums, false);
		add(centerPanel, BorderLayout.CENTER);
	}

	@Override
	protected String getProfilePic() {
		return controller.getProfilePic(userName);
	}

	@Override
	protected int getNumOfPosts() {
		return controller.getPhotos(userName).size() + controller.getAlbums(userName).size();
	}

	@Override
	protected String getUserName() {
		return userName;
	}

	@Override
	protected String getFullName() {
		return controller.getFullName(userName);
	}

	@Override
	protected int getFollowers() {
		return controller.getFollowers(userName);
	}

	@Override
	protected int getFollowed() {
		return controller.getFollowed(userName);
	}

	@Override
	protected void initializePhotos() {
		photos = controller.getPhotos(userName);
		albums = controller.getAlbums(userName);
	}

}
