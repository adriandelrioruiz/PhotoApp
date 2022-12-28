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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.View;

import com.toedter.calendar.JCalendar;

import umu.tds.maven.apps.PhotoApp.controlador.Codes;
import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

public class ProfileView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private RegisterView frame;
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
	private JLabel lblInvalidProfilePic;
	
	// Para elegir foto de perfil
	private JFileChooser fileChooser;
	private JButton fileChooserButton;
	
	// Frame para escribir la bio
	private SetBioFrame bioFrame;
	private JButton btnDescribeYourself;

	/**
	 * Create the frame.
	 */
	public ProfileView() {
		getContentPane().setBackground(Color.WHITE);
		controller = PhotoAppController.getInstance();
		fileChooser = new JFileChooser();
		bioFrame = new SetBioFrame();
		initialize();
	}

	private void initialize() {
		setTitle(ViewConstants.WINDOWS_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 751);
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
		lblNewLabel.setForeground(ViewConstants.APP_GREEN_COLOR);
		lblNewLabel.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 32));
		northPanel.add(lblNewLabel);

	}

	// Método para crear el panel que llevará el login
	private void createRegisterPane() {
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());

		JPanel center = new JPanel();
		center.setBackground(Color.WHITE);
		center.setLayout(null);
		centerPanel.add(center, BorderLayout.CENTER);

		txtFullName = new JTextField(ViewConstants.FULLNAME_DEFAULT_TEXT);
		txtFullName.setForeground(Color.GRAY);
		txtFullName.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtFullName.setFocusable(false);
		txtFullName.setColumns(10);
		txtFullName.setBounds(35, 80, 219, 31);
		center.add(txtFullName);
		addTextFieldHandler(txtFullName, ViewConstants.FULLNAME_DEFAULT_TEXT);

		// Text field para el nombre de usuario o email
		txUsername = new JTextField(ViewConstants.USER_DEFAULT_TEXT);
		txUsername.setForeground(Color.GRAY);
		txUsername.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txUsername.setBounds(35, txtFullName.getY() + 55, 219, 31);
		center.add(txUsername);
		txUsername.setColumns(10);
		txUsername.setFocusable(false);
		addTextFieldHandler(txUsername, ViewConstants.USER_DEFAULT_TEXT);

		txtEmail = new JTextField(ViewConstants.EMAIL_DEFAULT_TEXT);
		txtEmail.setForeground(Color.GRAY);
		txtEmail.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtEmail.setFocusable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(35, txUsername.getY() + 55, 219, 31);
		center.add(txtEmail);
		addTextFieldHandler(txtEmail, ViewConstants.EMAIL_DEFAULT_TEXT);

		// Text field para la contraseña
		txtPassword = new JPasswordField(ViewConstants.PASSWORD_DEFAULT_TEXT);
		txtPassword.setForeground(Color.GRAY);
		txtPassword.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtPassword.setBounds(35, txtEmail.getY() + 55, 219, 31);
		center.add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.setFocusable(false);
		addTextFieldHandler(txtPassword, ViewConstants.PASSWORD_DEFAULT_TEXT);

		// Botón de registro
		JButton registerButton = new JButton(ViewConstants.REGISTER_TEXT);
		registerButton.setForeground(Color.WHITE);
		registerButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		registerButton.setBounds(35, 547, 219, 31);
		center.add(registerButton);
		addRegisterButtonHandler(registerButton);

		calendar = new JCalendar();
		calendar.setBounds(35, txtPassword.getY() + 60, 219, 150);
		center.add(calendar);

		/*ProfilePicPane north = new ProfilePicPane(this, "img/default-profpic.png");
		north.setBounds(0, 0, 296, 108);
		center.add(north);
		north.setLayout(null);*/
		
		fileChooserButton = new JButton(ViewConstants.CHOOSE_AN_IMAGE_TEXT);
		fileChooserButton.setForeground(Color.WHITE);
		fileChooserButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		fileChooserButton.setBounds(35, 21, 219, 36);
		center.add(fileChooserButton);
		addFileChooseButtonHandler(fileChooserButton);

		lblEmptyFullName = new JLabel("Introduce tu nombre completo");
		lblEmptyFullName.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyFullName.setForeground(Color.RED);
		lblEmptyFullName.setBounds(35, txtFullName.getY() + txtFullName.getHeight(), 152, 13);
		center.add(lblEmptyFullName);

		lblEmptyUsername = new JLabel("Introduce un nombre de usuario");
		lblEmptyUsername.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyUsername.setForeground(Color.RED);
		lblEmptyUsername.setBounds(35, txUsername.getY() + txUsername.getHeight(), 180, 13);
		center.add(lblEmptyUsername);

		lblInvalidUsername = new JLabel("El nombre de usuario ya existe");
		lblInvalidUsername.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblInvalidUsername.setForeground(Color.RED);
		lblInvalidUsername.setBounds(35, txUsername.getY() + txUsername.getHeight(), 180, 13);
		center.add(lblInvalidUsername);

		lblEmptyEmail = new JLabel("Introduce un email");
		lblEmptyEmail.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyEmail.setForeground(Color.RED);
		lblEmptyEmail.setBounds(35, txtEmail.getY() + txtEmail.getHeight(), 180, 13);
		center.add(lblEmptyEmail);

		lblInvalidEmail = new JLabel("El email ya está registrado");
		lblInvalidEmail.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblInvalidEmail.setForeground(Color.RED);
		lblInvalidEmail.setBounds(35, txtEmail.getY() + txtEmail.getHeight(), 180, 13);
		center.add(lblInvalidEmail);

		lblEmptyPassword = new JLabel("Introduce una contraseña");
		lblEmptyPassword.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyPassword.setForeground(Color.RED);
		lblEmptyPassword.setBounds(35, txtPassword.getY() + txtPassword.getHeight(), 180, 13);
		center.add(lblEmptyPassword);
		
		lblInvalidProfilePic = new JLabel("Introduce una imagen válida");
		lblInvalidProfilePic.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblInvalidProfilePic.setForeground(Color.RED);
		lblInvalidProfilePic.setBounds(35, fileChooserButton.getY() + fileChooserButton.getHeight(), 180, 13);
		center.add(lblInvalidProfilePic);
		
		btnDescribeYourself = new JButton(ViewConstants.DESCRIBE_YOURSELF_TEXT);
		btnDescribeYourself.setForeground(Color.WHITE);
		btnDescribeYourself.setBackground(ViewConstants.APP_GREEN_COLOR);
		btnDescribeYourself.setBounds(35, 486, 219, 36);
		center.add(btnDescribeYourself);
		addBioButtonListener(btnDescribeYourself);

		hideErrors();

	}

	private void hideErrors() {
		lblEmptyFullName.setVisible(false);
		lblEmptyUsername.setVisible(false);
		lblInvalidUsername.setVisible(false);
		lblEmptyEmail.setVisible(false);
		lblInvalidEmail.setVisible(false);
		lblEmptyPassword.setVisible(false);
		lblInvalidProfilePic.setVisible(false);

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
		JLabel haveAccountLabel = new JLabel(ViewConstants.ALREADY_REGISTERED_TEXT);
		haveAccountLabel.setLocation(EXIT_ON_CLOSE, ABORT);
		haveAccountLabel.setForeground(Color.GRAY);
		haveAccountLabel.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		southPanel.add(haveAccountLabel);

		// Añadimos el botón de registro
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setForeground(ViewConstants.APP_GREEN_COLOR);
		loginLabel.setFont(new Font(ViewConstants.APP_FONT, Font.BOLD, 13));
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
		
		
		// Intentamos leer la imagen desde la ruta. Si no es una imagen o no hay ningún archivo seleccionado, error.
		try {
			ImageIO.read(fileChooser.getSelectedFile());
		}
		
		catch (Exception e) {
			lblInvalidProfilePic.setVisible(true);
			fieldsOkay = false;
		}

		if (txtFullName.getText().isEmpty() || txtFullName.getText().equals(ViewConstants.FULLNAME_DEFAULT_TEXT)) {
			lblEmptyFullName.setVisible(true);
			txtFullName.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txUsername.getText().isEmpty() || txUsername.getText().equals(ViewConstants.USER_DEFAULT_TEXT)) {
			lblEmptyUsername.setVisible(true);
			txUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txtEmail.getText().isEmpty() || txtEmail.getText().equals(ViewConstants.EMAIL_DEFAULT_TEXT)) {
			lblEmptyEmail.setVisible(true);
			txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txtPassword.getText().isEmpty() || txtPassword.getText().equals(ViewConstants.PASSWORD_DEFAULT_TEXT)) {
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
				Codes code = controller.registerUser(txtFullName.getText(), txtEmail.getText(), 
						txUsername.getText(), txtPassword.getText(), 
						calendar.getDate(), fileChooser.getSelectedFile().getName(), bioFrame.getBio());
				
				switch(code) {
				
				case INVALID_USERNAME:
				{
					lblInvalidUsername.setVisible(true);
					txUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
					break;
				}
				
				case INVALID_EMAIL:
				{
					lblInvalidEmail.setVisible(true);
					txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
					break;
				}
				
				default:
				{
					break;
				}
				
				
				}	
				
			}
		});
	}
	
	private void addFileChooseButtonHandler(JButton button) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(null);
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
	
	private void addBioButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bioFrame.setVisible(true);
			}
		});
		
	}
}
