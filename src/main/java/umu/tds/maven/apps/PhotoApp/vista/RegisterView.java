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

import com.toedter.calendar.JCalendar;

import umu.tds.maven.apps.PhotoApp.controlador.Code;
import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

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
	private JCalendar calendar;

	// JLabels para los campos inválidos
	private JLabel lblEmptyFullName;
	private JLabel lblEmptyUsername;
	private JLabel lblInvalidUsername;
	private JLabel lblEmptyEmail;
	private JLabel lblInvalidEmail;
	private JLabel lblEmptyPassword;

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

		txtFullName = new JTextField(FULLNAME_DEFAULT_TEXT);
		txtFullName.setForeground(Color.GRAY);
		txtFullName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		txtFullName.setFocusable(false);
		txtFullName.setColumns(10);
		txtFullName.setBounds(35, 118, 219, 31);
		center.add(txtFullName);
		addTextFieldHandler(txtFullName, FULLNAME_DEFAULT_TEXT);

		// Text field para el nombre de usuario o email
		txUsername = new JTextField(USER_DEFAULT_TEXT);
		txUsername.setForeground(Color.GRAY);
		txUsername.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		txUsername.setBounds(35, txtFullName.getY() + 55, 219, 31);
		center.add(txUsername);
		txUsername.setColumns(10);
		txUsername.setFocusable(false);
		addTextFieldHandler(txUsername, USER_DEFAULT_TEXT);

		txtEmail = new JTextField(EMAIL_DEFAULT_TEXT);
		txtEmail.setForeground(Color.GRAY);
		txtEmail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		txtEmail.setFocusable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(35, txUsername.getY() + 55, 219, 31);
		center.add(txtEmail);
		addTextFieldHandler(txtEmail, EMAIL_DEFAULT_TEXT);

		// Text field para la contraseña
		txtPassword = new JPasswordField(PASSWORD_DEFAULT_TEXT);
		txtPassword.setForeground(Color.GRAY);
		txtPassword.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		txtPassword.setBounds(35, txtEmail.getY() + 55, 219, 31);
		center.add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.setFocusable(false);
		addTextFieldHandler(txtPassword, PASSWORD_DEFAULT_TEXT);

		// Botón de login
		JButton registerButton = new JButton("Register");
		registerButton.setForeground(Color.WHITE);
		registerButton.setBackground(new Color(64, 128, 128));
		registerButton.setBounds(35, 516, 219, 31);
		center.add(registerButton);
		addRegisterButtonHandler(registerButton);

		calendar = new JCalendar();
		calendar.setBounds(35, txtPassword.getY() + 60, 219, 150);
		center.add(calendar);

		ProfilePicPane north = new ProfilePicPane(this, "img/default-profpic.png");
		north.setBounds(0, 0, 296, 108);
		center.add(north);
		north.setLayout(null);

		lblEmptyFullName = new JLabel("Introduce tu nombre completo");
		lblEmptyFullName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmptyFullName.setForeground(Color.RED);
		lblEmptyFullName.setBounds(35, txtFullName.getY() + txtFullName.getHeight(), 152, 13);
		center.add(lblEmptyFullName);

		lblEmptyUsername = new JLabel("Introduce un nombre de usuario");
		lblEmptyUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmptyUsername.setForeground(Color.RED);
		lblEmptyUsername.setBounds(35, txUsername.getY() + txUsername.getHeight(), 180, 13);
		center.add(lblEmptyUsername);

		lblInvalidUsername = new JLabel("El nombre de usuario ya existe");
		lblInvalidUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInvalidUsername.setForeground(Color.RED);
		lblInvalidUsername.setBounds(35, txUsername.getY() + txUsername.getHeight(), 180, 13);
		center.add(lblInvalidUsername);

		lblEmptyEmail = new JLabel("Introduce un email");
		lblEmptyEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmptyEmail.setForeground(Color.RED);
		lblEmptyEmail.setBounds(35, txtEmail.getY() + txtEmail.getHeight(), 180, 13);
		center.add(lblEmptyEmail);

		lblInvalidEmail = new JLabel("El email ya está registrado");
		lblInvalidEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInvalidEmail.setForeground(Color.RED);
		lblInvalidEmail.setBounds(35, txtEmail.getY() + txtEmail.getHeight(), 180, 13);
		center.add(lblInvalidEmail);

		lblEmptyPassword = new JLabel("Introduce una contraseña");
		lblEmptyPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmptyPassword.setForeground(Color.RED);
		lblEmptyPassword.setBounds(35, txtPassword.getY() + txtPassword.getHeight(), 180, 13);
		center.add(lblEmptyPassword);

		hideErrors();

	}

	private void hideErrors() {
		lblEmptyFullName.setVisible(false);
		lblEmptyUsername.setVisible(false);
		lblInvalidUsername.setVisible(false);
		lblEmptyEmail.setVisible(false);
		lblInvalidEmail.setVisible(false);
		lblEmptyPassword.setVisible(false);

		Border border = new JTextField().getBorder();
		txtFullName.setBorder(border);
		txUsername.setBorder(border);
		txtEmail.setBorder(border);
		txtPassword.setBorder(border);
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
			// Para que cuando el ratón pase por encima de "Register" se ponga el cursor de
			// la mano
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

	private boolean checkFields() {

		boolean fieldsOkay = true;
		hideErrors();

		if (txtFullName.getText().isEmpty() || txtFullName.getText().equals(FULLNAME_DEFAULT_TEXT)) {
			lblEmptyFullName.setVisible(true);
			txtFullName.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txUsername.getText().isEmpty() || txUsername.getText().equals(USER_DEFAULT_TEXT)) {
			lblEmptyUsername.setVisible(true);
			txUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txtEmail.getText().isEmpty() || txtEmail.getText().equals(EMAIL_DEFAULT_TEXT)) {
			lblEmptyEmail.setVisible(true);
			txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txtPassword.getText().isEmpty() || txtPassword.getText().equals(PASSWORD_DEFAULT_TEXT)) {
			lblEmptyPassword.setVisible(true);
			txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		return fieldsOkay;

	}

	private void addRegisterButtonHandler(JButton button) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Comprobamos que los campos sean válidos
				boolean fieldsAreValid = checkFields();
				
				if (!fieldsAreValid)
					return;
				
				// Intentamos registrar el nuevo user con los datos
				Code code = controller.registerUser(txtFullName.getText(), txtEmail.getText(), 
						txUsername.getText(), txtPassword.getText(), 
						calendar.getDate(), "", "");
				
				switch(code) {
				
				case INVALID_USERNAME:
				{
					lblEmptyUsername.setVisible(true);
					txUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
					break;
				}
				
				case INVALID_EMAIL:
				{
					lblEmptyEmail.setVisible(true);
					txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
					break;
				}
				
				default:
				{
					System.out.println("register okkkkk");
					break;
				}
				
				
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
