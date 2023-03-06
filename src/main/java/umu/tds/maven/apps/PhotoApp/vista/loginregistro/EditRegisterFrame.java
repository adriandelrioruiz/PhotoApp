package umu.tds.maven.apps.PhotoApp.vista.loginregistro;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.LoggedFrame;

@SuppressWarnings("serial")
public class EditRegisterFrame extends AbstractRegisterFrame {

	// Valores de los datos que tenía el usuario
	private String username;
	private String fullName;
	private String email;
	private String password;
	private String bio;
	// Botones para salir o actualizar los campos
	private JButton btnExit;
	private JButton btnUpdate;

	public EditRegisterFrame() {
		super();
		this.username = controller.getUserName(controller.getId());
		this.fullName = controller.getFullName(controller.getId());
		this.email = controller.getEmail(controller.getId());
		this.password = controller.getPassword(controller.getId());
		this.bio = controller.getBio(controller.getId());
		this.profilePic = controller.getProfilePic(controller.getId());
		bioFrame = new SetBioFrame(bio);

		initialize();
	}

	@Override
	protected void initialize() {
		super.initialize();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	protected void setBounds() {
		setBounds(100, 100, 310, 550);
		setLocationRelativeTo(null);
	}

	@Override
	protected void createRegisterPane() {
		super.createRegisterPane();
	}

	@Override
	protected void addListeners() {
		super.addListeners();
		addUpdateButtonHandler(btnUpdate);
	}

	@Override
	protected void createSouthPane() {
		super.createSouthPane();

		btnUpdate = new JButton("Actualizar");
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setBackground(ViewConstants.APP_GREEN_COLOR);
		southPanel.add(btnUpdate);

		btnExit = new JButton("Salir");
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(ViewConstants.APP_GREEN_COLOR);
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		southPanel.add(btnExit);
	}

	@Override
	protected void setTextFieldsDefault() {
		txtFullName.setText(fullName);
		txtUsername.setText(username);
		txtEmail.setText(email);
		txtPassword.setText(password);
	}

	private void addUpdateButtonHandler(JButton button) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Comprobamos que los campos sean válidos
				boolean fieldsAreValid = checkFields();

				if (!fieldsAreValid)
					return;

				// Si los datos son diferentes, los cambiamos
				if (!txtPassword.getText().equals(controller.getPassword(controller.getId())))
					controller.changePassword(txtPassword.getText());
				if (!profilePic.equals(controller.getProfilePic(controller.getId())))
					controller.changeProfilePic(profilePic);
				if (!bioFrame.getBio().equals(controller.getBio(controller.getId())))
					controller.changeBio(bioFrame.getBio());

				// Actualizamos la vista
				LoggedFrame.getInstance().updateProfilePic();
				dispose();

			}
		});
	}

}
