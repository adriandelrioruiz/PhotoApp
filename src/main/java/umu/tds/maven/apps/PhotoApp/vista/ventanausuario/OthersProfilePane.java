package umu.tds.maven.apps.PhotoApp.vista.ventanausuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import umu.tds.maven.apps.PhotoApp.modelo.User;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.AllPostsPane;

@SuppressWarnings("serial")
public class OthersProfilePane extends ProfilePane {

	private static final String IS_FOLLOWED_TEXT = "Dejar de seguir";
	private static final String NOT_FOLLOWED_TEXT = "Seguir";

	private JButton btnFollow;

	// Id de mi usuario
	private int myId;

	User userSearch;

	public OthersProfilePane(int userId, int myId) {
		super(userId);

		this.myId = myId;

		initialize();
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	protected void createNorthPanel() {

		super.createNorthPanel();

		btnFollow = new JButton();

		// Según se siga o no al usuario, se mostrará una cosa u otra
		if (!controller.isFollowed(userId, myId)) {
			btnFollow.setText("Seguir");
			btnFollow.setBackground(ViewConstants.APP_GREEN_COLOR);
		} else {
			btnFollow.setText("Dejar de seguir");
			btnFollow.setBackground(Color.white);
		}

		btnFollow.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_btnEditProfile = new GridBagConstraints();
		gbc_btnEditProfile.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditProfile.gridx = 2;
		gbc_btnEditProfile.gridy = 3;
		northPanel.add(btnFollow, gbc_btnEditProfile);
	}

	@Override
	protected void addListeners() {
		addFollowButtonListener(btnFollow);
	}

	protected void addFollowButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Si no lo sigue
				if (btnFollow.getText().equals(NOT_FOLLOWED_TEXT)) {
					boolean ok = controller.follow(controller.getUserName(userId));
					if (ok) {
						btnFollow.setText(IS_FOLLOWED_TEXT);
						btnFollow.setBackground(Color.white);
						// Aumentamos el número de seguidores en la vista
						lblNFollowers.setText(String.valueOf(Integer.valueOf(lblNFollowers.getText()) + 1));
					}
				}

				// Si lo sigue
				else if (btnFollow.getText().equals(IS_FOLLOWED_TEXT)) {
					boolean ok = controller.unFollow(controller.getUserName(userId));
					if (ok) {
						btnFollow.setText(NOT_FOLLOWED_TEXT);
						btnFollow.setBackground(ViewConstants.APP_GREEN_COLOR);
						// Disminuimos el número de seguidores en la vista
						lblNFollowers.setText(String.valueOf(Integer.valueOf(lblNFollowers.getText()) - 1));
					}

				}

			}
		});
	}

	@Override
	protected void createCenterPanel() {
		centerPanel = new AllPostsPane(userId, false);
		add(centerPanel, BorderLayout.CENTER);
	}

}
