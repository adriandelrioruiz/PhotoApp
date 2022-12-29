package umu.tds.maven.apps.PhotoApp.vista;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import umu.tds.maven.apps.PhotoApp.controlador.Codes;
import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

public class LoginView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JTextField txtUsernameOrEmail;
	private JTextField txtPassword;
	private PhotoAppController controller;
	
	// JLabels para los campos inválidos
	private JLabel lblEmptyEmUs;
	private JLabel lblInvalidEmUs;
	private JLabel lblEmptyPassword;
	private JLabel lblInvalidPassword;
	


	/**
	 * Create the frame.
	 */
	public LoginView() {
		getContentPane().setBackground(Color.WHITE);
		controller = PhotoAppController.getInstance();
		initialize();
	}
	
	private void initialize() {
		
		setTitle(ViewConstants.WINDOWS_TITLE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 306, 390);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		createTitlePane();
		createLoginPane();
		createRegisterPane();
		
		setVisible(true);
		
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
		lblNewLabel.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 32));
		northPanel.add(lblNewLabel);
		
	}
	
	// Método para crear el panel que llevará el login
	private void createLoginPane() {
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);
		
		// Text field para el nombre de usuario o email
		txtUsernameOrEmail = new JTextField(ViewConstants.USEM_DEFAULT_TEXT);
		txtUsernameOrEmail.setForeground(Color.GRAY);
		txtUsernameOrEmail.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtUsernameOrEmail.setBounds(35, 47, 219, 31);
		centerPanel.add(txtUsernameOrEmail);
		txtUsernameOrEmail.setColumns(10);
		txtUsernameOrEmail.setFocusable(false);
		addTextFieldHandler(txtUsernameOrEmail, ViewConstants.USEM_DEFAULT_TEXT);
		
		// Text field para la contraseña
		txtPassword = new JPasswordField(ViewConstants.PASSWORD_DEFAULT_TEXT);
		txtPassword.setForeground(Color.GRAY);
		txtPassword.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtPassword.setBounds(35, txtUsernameOrEmail.getY() + txtUsernameOrEmail.getHeight() + 20, 219, 31);
		centerPanel.add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.setFocusable(false);
		addTextFieldHandler(txtPassword, ViewConstants.PASSWORD_DEFAULT_TEXT);
		
		// Botón de login
		JButton loginButton = new JButton(ViewConstants.LOGIN_TEXT);
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(64, 128, 128));
		loginButton.setBounds(35, txtPassword.getY() + txtPassword.getHeight() + 20, 219, 31);
		centerPanel.add(loginButton);
		addLoginButtonHandler(loginButton);
		
		// JLabels para tratar los campos inválidos
		lblEmptyEmUs = new JLabel("Introduce un email o un nombre de usuario");
		lblEmptyEmUs.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyEmUs.setForeground(Color.RED);
		lblEmptyEmUs.setBounds(35, 78, 219, 13);
		centerPanel.add(lblEmptyEmUs);

		lblInvalidEmUs = new JLabel("El email o nombre de usuario no está registrado");
		lblInvalidEmUs.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblInvalidEmUs.setForeground(Color.RED);
		lblInvalidEmUs.setBounds(35, 78, 247, 13);
		centerPanel.add(lblInvalidEmUs);
		
		lblEmptyPassword = new JLabel("Introduce una contraseña");
		lblEmptyPassword.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyPassword.setForeground(Color.RED);
		lblEmptyPassword.setBounds(35, txtPassword.getY() + txtPassword.getHeight(), 180, 13);
		centerPanel.add(lblEmptyPassword);
		
		lblInvalidPassword = new JLabel("Contraseña incorrecta");
		lblInvalidPassword.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblInvalidPassword.setForeground(Color.RED);
		lblInvalidPassword.setBounds(35, txtPassword.getY() + txtPassword.getHeight(), 180, 13);
		centerPanel.add(lblInvalidPassword);
		
		hideErrors();
	}
	
	
	private void hideErrors() {
		lblEmptyEmUs.setVisible(false);
		lblInvalidEmUs.setVisible(false);
		lblEmptyPassword.setVisible(false);
		lblInvalidPassword.setVisible(false);

		Border border = new JTextField().getBorder();
		txtUsernameOrEmail.setBorder(border);
		txtPassword.setBorder(border);

	}
	
	// Método para crear el panel que llevará el registro
	private void createRegisterPane() {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		
		// Añadimos etiqueta para indicar si el usuario se quiere registrar
		JLabel haveAccountLabel = new JLabel(ViewConstants.NOT_REGISTERED_TEXT);
		haveAccountLabel.setLocation(EXIT_ON_CLOSE, ABORT);
		haveAccountLabel.setForeground(Color.GRAY);
		haveAccountLabel.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		southPanel.add(haveAccountLabel);
		
		// Añadimos el botón de registro
		JLabel registerLabel = new JLabel(ViewConstants.REGISTER_TEXT);
		registerLabel.setForeground(new Color(64, 128, 128));
		registerLabel.setFont(new Font(ViewConstants.APP_FONT, Font.BOLD, 13));
		addRegisterLabelHandler(registerLabel);
		southPanel.add(registerLabel);
			
	}
	
	private void addRegisterLabelHandler(JLabel registerLabel) {
		registerLabel.addMouseListener(new MouseAdapter() {
			// Para que cuando el ratón pase por encima de "Register" se ponga el cursor de la mano
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			// Para que al clicar se abra la ventana de registro
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				new RegisterView();
			}
		});

	}
	
	private boolean checkFields() {

		boolean fieldsOkay = true;
		hideErrors();
		

		if (txtUsernameOrEmail.getText().isEmpty() || txtUsernameOrEmail.getText().equals(ViewConstants.USEM_DEFAULT_TEXT)) {
			lblEmptyEmUs.setVisible(true);
			txtUsernameOrEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txtPassword.getText().isEmpty() || txtPassword.getText().equals(ViewConstants.PASSWORD_DEFAULT_TEXT)) {
			lblEmptyPassword.setVisible(true);
			txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		return fieldsOkay;

	}
	
	private void addLoginButtonHandler(JButton loginButton) {
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean fieldsOkay = checkFields();
				
				if (!fieldsOkay)
					return;
				
				// Intentamos logearnos
				Codes Codes = controller.login(txtUsernameOrEmail.getText(), txtPassword.getText());
				
				switch(Codes) {
				
				case INCORRECT_EMAIL_USERNAME:
					lblInvalidEmUs.setVisible(true);
					txtUsernameOrEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
					break;
				case INCORRECT_PASSWORD:
					lblInvalidPassword.setVisible(true);
					txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
					break;
					
				default:
					// Cerramos la ventana de login
					dispose();
					// Abrimos el perfil
					new ProfileView();
					break;
					
				}
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
