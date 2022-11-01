package umu.tds.maven.apps.PhotoApp.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsernameOrEmail;
	private JTextField txtPassword;


	/**
	 * Create the frame.
	 */
	public LoginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsernameOrEmail = new JTextField();
		txtUsernameOrEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtUsernameOrEmail.setText("username or email");
		txtUsernameOrEmail.setBounds(125, 71, 176, 39);
		contentPane.add(txtUsernameOrEmail);
		txtUsernameOrEmail.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("password");
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtPassword.setColumns(10);
		txtPassword.setBounds(125, 122, 176, 39);
		contentPane.add(txtPassword);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(169, 175, 85, 21);

		contentPane.add(loginButton);
		
		JButton registerButton = new JButton("Register");
	
		registerButton.setBounds(169, 221, 85, 21);
		contentPane.add(registerButton);
		
		// Añadimos los manejadores de los botones
		addLoginButtonHandler(loginButton);
		addRegisterButtonHandler(registerButton);
		
		// Centramos la ventana
		setLocationRelativeTo(null);
	}
	
	private void addLoginButtonHandler(JButton loginButton) {
		loginButton.addActionListener(new LoginButtonActionListener());
	}
	
	private void addRegisterButtonHandler(JButton registerButton) {
		registerButton.addActionListener(new RegisterButtonActionListener(this));
	}

	
	class RegisterButtonActionListener implements ActionListener {
		
		JFrame frame;

		public RegisterButtonActionListener(JFrame frame) {
			this.frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// Ocultamos la ventana de login que es la que hemos pasado al constructor
			frame.setVisible(false);
			// Creamos una ventana de registro y la mostramos
			RegisterView registerFrame = new RegisterView();
			registerFrame.setVisible(true);
		}
		
	}
	
	class LoginButtonActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// Si los campos no están vacíos, intentamos hacer un login con el controlador
			if(!txtUsernameOrEmail.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
				boolean loginSuccessful = PhotoAppController.getInstance().login(txtUsernameOrEmail.getText(), getName());
				if (!loginSuccessful) {
					// Crear el JDialog
					System.out.println("NOLOGIN");
				}
				
				else
					System.out.println("LOGIN");
				
			}
		}
	}
}
