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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.toedter.calendar.JCalendar;

import umu.tds.maven.apps.PhotoApp.controlador.Codes;
import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

public class RegisterView extends AbstractRegisterView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JCalendar calendar;

	// JLabels para los campos inválidos
	private JLabel lblEmptyFullName;
	private JLabel lblEmptyUsername;
	private JLabel lblInvalidUsername;
	private JLabel lblEmptyEmail;
	private JLabel lblInvalidEmail;
	

	/**
	 * Create the frame.
	 */
	public RegisterView() {
		super();
		bioFrame = new SetBioFrame(ViewConstants.BIO_DEFAULT_TEXT);
		initialize();
	}

	@Override
	protected void setBounds() {
		setBounds(100, 100, 310, 751);
		setLocationRelativeTo(null);
	}

	@Override
	protected void createRegisterPane() {
		
		super.createRegisterPane();
		txtPassword.setFocusable(false);

		// Botón de registro
		JButton registerButton = new JButton(ViewConstants.REGISTER_TEXT);
		registerButton.setForeground(Color.WHITE);
		registerButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		registerButton.setBounds(35, 547, 219, 31);
		centerPanel.add(registerButton);
		addRegisterButtonHandler(registerButton);

		calendar = new JCalendar();
		calendar.setBounds(35, bioFrame.getY() + 100, 219, 150);
		centerPanel.add(calendar);

		/*ProfilePicPane north = new ProfilePicPane(this, "img/default-profpic.png");
		north.setBounds(0, 0, 296, 108);
		center.add(north);
		north.setLayout(null);*/

	}
	
	@Override
	protected void addErrorLabels() {
		super.addErrorLabels();
		
		lblEmptyFullName = new JLabel("Introduce tu nombre completo");
		lblEmptyFullName.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyFullName.setForeground(Color.RED);
		lblEmptyFullName.setBounds(35, txtFullName.getY() + txtFullName.getHeight(), 152, 13);
		centerPanel.add(lblEmptyFullName);

		lblEmptyUsername = new JLabel("Introduce un nombre de usuario");
		lblEmptyUsername.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyUsername.setForeground(Color.RED);
		lblEmptyUsername.setBounds(35, txtUsername.getY() + txtUsername.getHeight(), 180, 13);
		centerPanel.add(lblEmptyUsername);

		lblInvalidUsername = new JLabel("El nombre de usuario ya existe");
		lblInvalidUsername.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblInvalidUsername.setForeground(Color.RED);
		lblInvalidUsername.setBounds(35, txtUsername.getY() + txtUsername.getHeight(), 180, 13);
		centerPanel.add(lblInvalidUsername);

		lblEmptyEmail = new JLabel("Introduce un email");
		lblEmptyEmail.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyEmail.setForeground(Color.RED);
		lblEmptyEmail.setBounds(35, txtEmail.getY() + txtEmail.getHeight(), 180, 13);
		centerPanel.add(lblEmptyEmail);

		lblInvalidEmail = new JLabel("El email ya está registrado");
		lblInvalidEmail.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblInvalidEmail.setForeground(Color.RED);
		lblInvalidEmail.setBounds(35, txtEmail.getY() + txtEmail.getHeight(), 180, 13);
		centerPanel.add(lblInvalidEmail);

	}
	
	@Override
	protected void addListeners() {
		super.addListeners();
		addTextFieldListener(txtUsername, ViewConstants.USER_DEFAULT_TEXT);
		addTextFieldListener(txtFullName, ViewConstants.FULLNAME_DEFAULT_TEXT);
		addTextFieldListener(txtEmail, ViewConstants.EMAIL_DEFAULT_TEXT);
		addTextFieldListener(txtPassword, ViewConstants.PASSWORD_DEFAULT_TEXT);
	}

	@Override
	protected void hideErrors() {
		super.hideErrors();
		lblEmptyFullName.setVisible(false);
		lblEmptyUsername.setVisible(false);
		lblInvalidUsername.setVisible(false);
		lblEmptyEmail.setVisible(false);
		lblInvalidEmail.setVisible(false);

		Border border = new JTextField().getBorder();
		txtFullName.setBorder(border);
		txtUsername.setBorder(border);
		txtEmail.setBorder(border);
	}

	// Método para crear el panel que llevará el registro
	@Override
	protected void createSouthPane() {
		super.createSouthPane();

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
	
	@Override
	protected void setTextFieldsDefault() {
		txtFullName.setText(ViewConstants.FULLNAME_DEFAULT_TEXT);
		txtUsername.setText(ViewConstants.USER_DEFAULT_TEXT);
		txtEmail.setText(ViewConstants.EMAIL_DEFAULT_TEXT);
		txtPassword.setText(ViewConstants.PASSWORD_DEFAULT_TEXT);
	}

	private void addLoginLabelHandler(JLabel registerLabel) {
		registerLabel.addMouseListener(new MouseAdapter() {
			// Para que cuando el ratón pase por encima de "Register" se ponga el cursor de
			// la mano
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
				new LoginView();
			}
		});

	}

	@Override
	protected boolean checkFields() {

		boolean fieldsOkay = super.checkFields();
		// TODO QUITAR????


		if (txtFullName.getText().isEmpty() || txtFullName.getText().equals(ViewConstants.FULLNAME_DEFAULT_TEXT)) {
			lblEmptyFullName.setVisible(true);
			txtFullName.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txtUsername.getText().isEmpty() || txtUsername.getText().equals(ViewConstants.USER_DEFAULT_TEXT)) {
			lblEmptyUsername.setVisible(true);
			txtUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldsOkay = false;
		}

		if (txtEmail.getText().isEmpty() || txtEmail.getText().equals(ViewConstants.EMAIL_DEFAULT_TEXT)) {
			lblEmptyEmail.setVisible(true);
			txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
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
				String bio = bioFrame.getBio();
				if (bio.equals(ViewConstants.BIO_DEFAULT_TEXT))
					bio = "";
				
				Codes code = controller.registerUser(txtFullName.getText(), txtEmail.getText(), 
						txtUsername.getText(), txtPassword.getText(), 
						calendar.getDate(), fileChooser.getSelectedFile().getName(), bio);
				
				switch(code) {
				
				case INVALID_USERNAME:
				{
					lblInvalidUsername.setVisible(true);
					txtUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
					break;
				}
				
				case INVALID_EMAIL:
				{
					lblInvalidEmail.setVisible(true);
					txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
					break;
				}
				
				case OK:
				{
					// TODO meter constantes?
					JButton btnGoToLogin = new JButton("Volver al login");
					JOptionPane.showMessageDialog(btnGoToLogin, "El registro se ha completado con éxito");
					dispose();
					new LoginView();
				}
				
				default:
				{
					break;
				}
				
				
				}	
				
			}

		});
	}


}
