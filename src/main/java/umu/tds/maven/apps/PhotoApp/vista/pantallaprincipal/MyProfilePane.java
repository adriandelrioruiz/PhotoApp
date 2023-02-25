package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.loginregistro.EditRegisterFrame;

@SuppressWarnings("serial")
public class MyProfilePane extends AbstractProfilePane {
	
	private JButton btnEditProfile;

	public MyProfilePane(int userId) {
		super(userId);
		
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
				@SuppressWarnings("unused")
				EditRegisterFrame frame = new EditRegisterFrame();
				
			}
		});
	}

	@Override
	protected void createCenterPanel() {
		centerPanel = new AllPostsPane(userId, true);
		add(centerPanel, BorderLayout.CENTER);
	}

	
}
