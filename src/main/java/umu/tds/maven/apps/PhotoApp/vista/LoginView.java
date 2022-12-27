package umu.tds.maven.apps.PhotoApp.vista;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;

public class LoginView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String USER_OR_EMAIL_DEFAULT_TEXT = "username or email";
	private static final String PASSWORD_DEFAULT_TEXT = "password";
	
	private LoginView frame;
	private JPanel contentPane;
	private JTextField usernameOrEmail;
	private JTextField password;
	private PhotoAppController controller;
	
	// Ventana de registro
	private RegisterView registerView;


	/**
	 * Create the frame.
	 */
	public LoginView() {
		getContentPane().setBackground(Color.WHITE);
		controller = PhotoAppController.getInstance();
		frame = this;
		initialize();
	}
	
	private void initialize() {
		
		setTitle("PhotoApp");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 306, 390);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		createTitlePane();
		createLoginPane();
		createRegisterPane();
		
	}
	
	// Método para crear el panel que llevará el nombre de la aplicación
	private void createTitlePane() {
		JPanel northPanel = new JPanel();
		northPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		
		// Añadimos etiqueta que mostrará en grande el nombre de la aplicación
		JLabel lblNewLabel = new JLabel("PhotoApp");
		lblNewLabel.setForeground(new Color(64, 128, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		northPanel.add(lblNewLabel);
		
	}
	
	// Método para crear el panel que llevará el login
	private void createLoginPane() {
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);
		
		// Text field para el nombre de usuario o email
		usernameOrEmail = new JTextField(USER_OR_EMAIL_DEFAULT_TEXT);
		usernameOrEmail.setForeground(Color.GRAY);
		usernameOrEmail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		usernameOrEmail.setBounds(35, 47, 219, 31);
		centerPanel.add(usernameOrEmail);
		usernameOrEmail.setColumns(10);
		usernameOrEmail.setFocusable(false);
		addTextFieldHandler(usernameOrEmail, USER_OR_EMAIL_DEFAULT_TEXT);
		
		
		
		// Text field para la contraseña
		password = new JPasswordField(PASSWORD_DEFAULT_TEXT);
		password.setForeground(Color.GRAY);
		password.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		password.setBounds(35, 88, 219, 31);
		centerPanel.add(password);
		password.setColumns(10);
		password.setFocusable(false);
		addTextFieldHandler(password, PASSWORD_DEFAULT_TEXT);
		
		// Botón de login
		JButton loginButton = new JButton("Login");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(64, 128, 128));
		loginButton.setBounds(35, 141, 219, 31);
		centerPanel.add(loginButton);
		loginButton.requestFocus();
		
		
	}
	
	// Método para crear el panel que llevará el registro
	private void createRegisterPane() {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		
		// Añadimos etiqueta para indicar si el usuario se quiere registrar
		JLabel haveAccountLabel = new JLabel("Don´t have an account?");
		haveAccountLabel.setLocation(EXIT_ON_CLOSE, ABORT);
		haveAccountLabel.setForeground(Color.GRAY);
		haveAccountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		southPanel.add(haveAccountLabel);
		
		// Añadimos el botón de registro
		JLabel registerLabel = new JLabel("	Register");
		registerLabel.setForeground(new Color(64, 128, 128));
		registerLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		addRegisterLabelHandler(registerLabel);
		southPanel.add(registerLabel);
		
		
	}
	
	private void addRegisterLabelHandler(JLabel registerLabel) {
		registerLabel.addMouseListener(new MouseAdapter() {
			// Para que cuando el ratón pase por encima de "Register" se ponga el cursor de la mano
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				// Cuando se ponga el cursor encima de el botón de Register, crearemos la ventana
				if (registerView == null)
					registerView = new RegisterView(frame);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			// Para que al clicar se abra la ventana de registro
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				registerView.setVisible(true);
			}
		});

	}
	
	private void addLoginButtonHandler(JButton loginButton) {
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	private void addTextFieldHandler(JTextField textField, String defaultText) {
		
		
		textField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setFocusable(true);
				textField.grabFocus();
				if (textField.getText().equals(defaultText))
					textField.setText("");
			}
		});
		
		textField.addFocusListener(new FocusAdapter() {
			
			
			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().equals("")) {
					textField.setText(defaultText);
				}
			}
		});
	}
}
