package umu.tds.maven.apps.PhotoApp.vista;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.toedter.calendar.JCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Canvas;

public class RegisterView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String USER_DEFAULT_TEXT = "username";
	private static final String EMAIL_DEFAULT_TEXT = "email";
	private static final String PASSWORD_DEFAULT_TEXT = "password";
	private static final String FULLNAME_DEFAULT_TEXT = "full name";
	private static final String BIO_DEFAULT_TEXT = "tell us something about you";
	
	private RegisterView frame;
	private JPanel contentPane;
	private JTextField txUsername;
	private JTextField txtPassword;
	private PhotoAppController controller;
	// TODO quitar atributos
	private LoginView loginView;
	private JTextField txtEmail;
	private JTextField txtFullName;


	/**
	 * Create the frame.
	 */
	public RegisterView(LoginView loginView) {
		this.loginView = loginView;
		getContentPane().setBackground(Color.WHITE);
		controller = PhotoAppController.getInstance();
		frame = this;
		initialize();
	}
	
	private void initialize() {
		setTitle("PhotoApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 725);
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
	private void createRegisterPane() {
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());
		
		
		JPanel center = new JPanel();
		center.setLayout(null);
		centerPanel.add(center, BorderLayout.CENTER);
		
		// Text field para el nombre de usuario o email
		txUsername = new JTextField(USER_DEFAULT_TEXT);
		txUsername.setForeground(Color.GRAY);
		txUsername.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		txUsername.setBounds(35, 159, 219, 31);
		center.add(txUsername);
		txUsername.setColumns(10);
		txUsername.setFocusable(false);
		addTextFieldHandler(txUsername, USER_DEFAULT_TEXT);
		
		// Text field para la contraseña
		txtPassword = new JPasswordField(PASSWORD_DEFAULT_TEXT);
		txtPassword.setForeground(Color.GRAY);
		txtPassword.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		txtPassword.setBounds(35, 241, 219, 31);
		center.add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.setFocusable(false);
		addTextFieldHandler(txtPassword, PASSWORD_DEFAULT_TEXT);
		
		// Botón de login
		JButton loginButton = new JButton("Register");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(64, 128, 128));
		loginButton.setBounds(35, 485, 219, 31);
		center.add(loginButton);
		
		txtEmail = new JTextField(EMAIL_DEFAULT_TEXT);
		txtEmail.setForeground(Color.GRAY);
		txtEmail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		txtEmail.setFocusable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(35, 200, 219, 31);
		center.add(txtEmail);
		addTextFieldHandler(txtEmail, EMAIL_DEFAULT_TEXT);
		
		txtFullName = new JTextField(FULLNAME_DEFAULT_TEXT);
		txtFullName.setForeground(Color.GRAY);
		txtFullName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		txtFullName.setColumns(10);
		txtFullName.setBounds(35, 118, 219, 31);
		center.add(txtFullName);
		addTextFieldHandler(txtFullName, FULLNAME_DEFAULT_TEXT);
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(35, 300, 219, 150);
		center.add(calendar);
		
		ProfilePicPane north = new ProfilePicPane(this, "img/default-profpic.png");
		north.setBounds(0, 0, 296, 108);
		center.add(north);
		north.setLayout(null);
		
		
	}
	
	// Método para crear el panel que llevará el registro
	private void createLoginPane() {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		
		// Añadimos etiqueta para indicar si el usuario se quiere registrar
		JLabel haveAccountLabel = new JLabel("Already have an account?");
		haveAccountLabel.setLocation(EXIT_ON_CLOSE, ABORT);
		haveAccountLabel.setForeground(Color.GRAY);
		haveAccountLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		southPanel.add(haveAccountLabel);
		
		// Añadimos el botón de registro
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setForeground(new Color(64, 128, 128));
		loginLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		addLoginLabelHandler(loginLabel);
		southPanel.add(loginLabel);
		
	}
	
	private void addLoginLabelHandler(JLabel registerLabel) {
		registerLabel.addMouseListener(new MouseAdapter() {
			// Para que cuando el ratón pase por encima de "Register" se ponga el cursor de la mano
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			// Para que al clicar se abra la ventana de registro
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				loginView.setVisible(true);
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
