package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

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
	
	private JButton btnFollow;
	
	User userSearch;

	public OthersProfilePane(User user, User userSearch) {
		super(user);
		this.userSearch = userSearch;
	}
	
	protected void createNorthPanel() {
		
		super.createNorthPanel();
		
		btnFollow = new JButton();
		
		// Según se siga o no al usuario, se mostrará una cosa u otra
		if (!controller.isFollowed(userSearch.getUserName())) {
			btnFollow.setText("Seguir");
			addFollowButtonListener(btnFollow);
		}
		else {
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
	

	
}
