package umu.tds.maven.apps.PhotoApp.vista.loginregistro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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

import com.toedter.calendar.JCalendar;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;
import umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal.UploadPhotoFrame;

/** Clase abstracta que engloba a la vista de registro normal que un usuario usa para registrarse
 * por primera vez y a la vista que el usuario usa para editar su perfil */

@SuppressWarnings("serial")
public abstract class AbstractRegisterFrame extends JFrame {
	
	// JPanels
	protected JPanel northPanel;
	protected JPanel centerPanel;
	protected JPanel southPanel;
	
	protected JTextField txtUsername;
	protected JTextField txtPassword;
	protected PhotoAppController controller;
	// TODO quitar atributos
	protected JTextField txtEmail;
	protected JTextField txtFullName;
	protected JCalendar calendar;

	// JLabels para los campos inválidos
	protected JLabel lblEmptyPassword;
	protected JLabel lblInvalidProfilePic;
	
	// Para elegir foto de perfil
	protected JFileChooser fileChooser;
	protected JButton fileChooserButton;
	
	// Frame para escribir la bio
	protected SetBioFrame bioFrame;
	private JButton btnDescribeYourself;
	
	// String que contiene la ruta de la foto de perfil
	protected String profilePic;
	

	/**
	 * Create the frame.
	 */
	public AbstractRegisterFrame() {
		getContentPane().setBackground(Color.WHITE);
		controller = PhotoAppController.getInstance();
		fileChooser = new JFileChooser();
	}
	

	protected void initialize() {
		setTitle(ViewConstants.WINDOWS_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createTitlePane();
		createSouthPane();
		createRegisterPane();

		setBounds();
		setVisible(true);
	}
	
	protected abstract void setBounds();

	// Método para crear el panel que llevará el nombre de la aplicación
	private void createTitlePane() {
		northPanel = new JPanel();
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
	protected void createRegisterPane() {
		
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(null);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		fileChooserButton = new JButton(ViewConstants.CHOOSE_AN_IMAGE_TEXT);
		fileChooserButton.setForeground(Color.WHITE);
		fileChooserButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		fileChooserButton.setBounds(35, 21, 219, 36);
		centerPanel.add(fileChooserButton);

		txtFullName = new JTextField();
		txtFullName.setForeground(Color.GRAY);
		txtFullName.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtFullName.setFocusable(false);
		txtFullName.setColumns(10);
		txtFullName.setBounds(35, fileChooserButton.getY() + 55, 219, 31);
		centerPanel.add(txtFullName);

		// Text field para el nombre de usuario o email
		txtUsername = new JTextField();
		txtUsername.setForeground(Color.GRAY);
		txtUsername.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtUsername.setBounds(35, txtFullName.getY() + 55, 219, 31);
		centerPanel.add(txtUsername);
		txtUsername.setColumns(10);
		txtUsername.setFocusable(false);

		txtEmail = new JTextField();
		txtEmail.setForeground(Color.GRAY);
		txtEmail.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtEmail.setFocusable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(35, txtUsername.getY() + 55, 219, 31);
		centerPanel.add(txtEmail);

		// Text field para la contraseña
		txtPassword = new JPasswordField();
		txtPassword.setForeground(Color.GRAY);
		txtPassword.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		txtPassword.setBounds(35, txtEmail.getY() + 55, 219, 31);
		centerPanel.add(txtPassword);
		txtPassword.setColumns(10);


		/*ProfilePicPane north = new ProfilePicPane(this, "img/default-profpic.png");
		north.setBounds(0, 0, 296, 108);
		center.add(north);
		north.setLayout(null);*/
		

		// Botón para acceder al texto de presentación
		btnDescribeYourself = new JButton(ViewConstants.DESCRIBE_YOURSELF_TEXT);
		btnDescribeYourself.setForeground(Color.WHITE);
		btnDescribeYourself.setBackground(ViewConstants.APP_GREEN_COLOR);
		btnDescribeYourself.setBounds(35, txtPassword.getY() + 55, 219, 36);
		centerPanel.add(btnDescribeYourself);

		setTextFieldsDefault();
		addErrorLabels();
		hideErrors();
		addListeners();
	}

	protected void createSouthPane() {
		
		southPanel = new JPanel();
		southPanel.setBackground(getContentPane().getBackground());
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
	
	}

	protected void addErrorLabels() {
		lblEmptyPassword = new JLabel("Introduce una contraseña");
		lblEmptyPassword.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblEmptyPassword.setForeground(Color.RED);
		lblEmptyPassword.setBounds(35, txtPassword.getY() + txtPassword.getHeight(), 180, 13);
		centerPanel.add(lblEmptyPassword);
		
		lblInvalidProfilePic = new JLabel("Introduce una imagen válida");
		lblInvalidProfilePic.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 11));
		lblInvalidProfilePic.setForeground(Color.RED);
		lblInvalidProfilePic.setBounds(35, fileChooserButton.getY() + fileChooserButton.getHeight(), 180, 13);
		centerPanel.add(lblInvalidProfilePic);
	}
	
	protected void hideErrors() {
		lblEmptyPassword.setVisible(false);
		lblInvalidProfilePic.setVisible(false);
		
		Border border = new JTextField().getBorder();
		txtPassword.setBorder(border);
	}
	
	protected abstract void setTextFieldsDefault();
	
	protected boolean checkFields() {
		boolean fieldsOkay = true;
		hideErrors();
		
		// Intentamos leer la imagen desde la ruta. Si no es una imagen o no hay ningún archivo seleccionado, error.
		
		if (!UploadPhotoFrame.isValidImageFormat(profilePic)) {
			lblInvalidProfilePic.setVisible(true);
			fieldsOkay = false;
		}
		
		else {
			try {
				ImageIO.read(new File(profilePic));
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
	
			if (txtPassword.getText().isEmpty() || txtPassword.getText().equals(ViewConstants.PASSWORD_DEFAULT_TEXT)) {
				lblEmptyPassword.setVisible(true);
				txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
				fieldsOkay = false;
			}
		}

		return fieldsOkay;
	}

	protected void addListeners() {
		addFileChooseButtonHandler(fileChooserButton);
		addBioButtonListener(btnDescribeYourself);
	}
		
	protected final void addTextFieldListener(JTextField textField, String defaultText) {

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
	
	private void addFileChooseButtonHandler(JButton button) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(null);
				if (fileChooser.getSelectedFile() != null)
					profilePic = fileChooser.getSelectedFile().toString();
			}
		});
	}
}
