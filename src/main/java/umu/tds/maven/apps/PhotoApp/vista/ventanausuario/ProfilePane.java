package umu.tds.maven.apps.PhotoApp.vista.ventanausuario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

@SuppressWarnings("serial")
public abstract class ProfilePane extends JPanel {

	protected PhotoAppController controller;

	protected JFrame frame;
	protected JPanel northPanel;
	protected JPanel centerPanel;

	protected JLabel lblNFollowers;

	// Id del usuario
	int userId;

	public ProfilePane(int userId) {
		this.controller = PhotoAppController.getInstance();
		this.userId = userId;
	}

	protected void initialize() {
		setLayout(new BorderLayout());

		createNorthPanel();
		createCenterPanel();

		addListeners();
	}

	protected void createNorthPanel() {
		northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		northPanel.setPreferredSize(new Dimension(ViewConstants.LOGGEDFRAME_WINDOW_WIDTH, 200));
		GridBagLayout gbl_northPanel = new GridBagLayout();
		gbl_northPanel.rowHeights = new int[] { 30, 20, 20, 30 };
		gbl_northPanel.columnWidths = new int[] { 100, 120, 120, 100 };
		northPanel.setLayout(gbl_northPanel);

		// Creamos la imagen foto de perfil
		try {
			Image profilePic = ImageIO.read(new File(getProfilePic()));
			JLabel lblProfilePic = new JLabel(
					new ImageIcon(profilePic.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
			GridBagConstraints gbc_lblProfilePic = new GridBagConstraints();
			gbc_lblProfilePic.gridheight = 4;
			gbc_lblProfilePic.insets = new Insets(0, 0, 5, 5);
			gbc_lblProfilePic.gridx = 0;
			gbc_lblProfilePic.gridy = 0;
			gbc_lblProfilePic.weightx = 1.0;
			northPanel.add(lblProfilePic, gbc_lblProfilePic);
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		JLabel lblEmail = new JLabel(getUserName());
		lblEmail.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		lblEmail.setBounds(50, 50, 50, 50);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 0;
		gbc_lblEmail.gridwidth = 2;
		gbc_lblEmail.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(lblEmail, gbc_lblEmail);

		JLabel lblPosts = new JLabel(String.valueOf(getNumOfPosts()));
		lblPosts.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosts.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_lblPosts = new GridBagConstraints();
		gbc_lblPosts.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosts.gridx = 1;
		gbc_lblPosts.gridy = 1;
		gbc_lblPosts.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(lblPosts, gbc_lblPosts);

		JLabel nPosts = new JLabel("publicaciones");
		nPosts.setHorizontalAlignment(SwingConstants.CENTER);
		nPosts.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_nPosts = new GridBagConstraints();
		gbc_nPosts.insets = new Insets(0, 0, 5, 5);
		gbc_nPosts.gridx = 1;
		gbc_nPosts.gridy = 2;
		gbc_nPosts.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(nPosts, gbc_nPosts);

		JLabel lblFollowers = new JLabel("seguidores");
		lblFollowers.setHorizontalAlignment(SwingConstants.CENTER);
		lblFollowers.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_lblFollowers = new GridBagConstraints();
		gbc_lblFollowers.insets = new Insets(0, 0, 5, 5);
		gbc_lblFollowers.gridx = 2;
		gbc_lblFollowers.gridy = 2;
		gbc_lblFollowers.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(lblFollowers, gbc_lblFollowers);

		lblNFollowers = new JLabel(String.valueOf(getFollowers()));
		lblNFollowers.setHorizontalAlignment(SwingConstants.CENTER);
		lblNFollowers.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_lblNFollowers = new GridBagConstraints();
		gbc_lblNFollowers.insets = new Insets(0, 0, 5, 5);
		gbc_lblNFollowers.gridx = 2;
		gbc_lblNFollowers.gridy = 1;
		gbc_lblNFollowers.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(lblNFollowers, gbc_lblNFollowers);

		JLabel lblFollowed = new JLabel("seguidos");
		lblFollowed.setHorizontalAlignment(SwingConstants.CENTER);
		lblFollowed.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_lblFollowed = new GridBagConstraints();
		gbc_lblFollowed.insets = new Insets(0, 0, 5, 0);
		gbc_lblFollowed.gridx = 3;
		gbc_lblFollowed.gridy = 2;
		gbc_lblFollowed.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(lblFollowed, gbc_lblFollowed);

		JLabel lblNFollowed = new JLabel(String.valueOf(getFollowed()));
		lblNFollowed.setHorizontalAlignment(SwingConstants.CENTER);
		lblNFollowed.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_lblNFollowed = new GridBagConstraints();
		gbc_lblNFollowed.insets = new Insets(0, 0, 5, 0);
		gbc_lblNFollowed.gridx = 3;
		gbc_lblNFollowed.gridy = 1;
		gbc_lblNFollowed.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(lblNFollowed, gbc_lblNFollowed);

		JLabel lblFullName = new JLabel(getFullName());
		lblFullName.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		GridBagConstraints gbc_lblFullName = new GridBagConstraints();
		gbc_lblFullName.gridx = 1;
		gbc_lblFullName.gridy = 4;
		gbc_lblFullName.gridwidth = 3;
		gbc_lblFullName.fill = GridBagConstraints.HORIZONTAL;
		northPanel.add(lblFullName, gbc_lblFullName);

	}

	protected abstract void addListeners();

	protected String getProfilePic() {
		return controller.getProfilePic(userId);
	}

	protected int getNumOfPosts() {
		return controller.getPhotos(userId).size() + controller.getAlbums(userId).size();
	}

	protected String getUserName() {
		return controller.getUserName(userId);
	}

	protected String getFullName() {
		return controller.getFullName(userId);
	}

	protected int getFollowers() {
		return controller.getFollowers(userId);
	}

	protected int getFollowed() {
		return controller.getFollowed(userId);
	}

	protected abstract void createCenterPanel();

}
