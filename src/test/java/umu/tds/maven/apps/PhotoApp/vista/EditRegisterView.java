package umu.tds.maven.apps.PhotoApp.vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import umu.tds.maven.apps.PhotoApp.controlador.Codes;

@SuppressWarnings("serial")
public class EditRegisterView extends AbstractRegisterView {
	
	// Valores de los datos que tenía el usuario
	private String username;
	private String fullName;
	private String email;
	private String password;
	private String bio;
	private String profilePic;
	
	// Botones para salir o actualizar los campos
	private JButton btnExit;
	private JButton btnUpdate;
	
	

	public EditRegisterView(String username, String fullName, String email, String password, String bio,
			String profilePic) {
		super();
		this.username = username;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.bio = bio;
		this.profilePic = profilePic;
		bioFrame = new SetBioFrame(bio);
		
		initialize();
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
				if (!txtPassword.getText().equals(controller.getPassword()))
					controller.changePassword(txtPassword.getText());
				if (!profilePic.equals(controller.getProfilePic()))
					controller.changeProfilePic(profilePic);
				if (!bioFrame.getBio().equals(controller.getBio()))
					controller.changeBio(bioFrame.getBio());
				
			}
		});
	}

	
}
