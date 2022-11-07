package umu.tds.maven.apps.PhotoApp.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegisterView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFullName;
	private JTextField txtEmail;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtDateofbirth;
	private JTextField txtBio;
	private JTextField txtProfilePic;


	/**
	 * Create the frame.
	 */
	public RegisterView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtFullName = new JTextField();
		txtFullName.setText("Full Name");
		txtFullName.setBounds(161, 10, 96, 19);
		contentPane.add(txtFullName);
		txtFullName.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setText("Email");
		txtEmail.setBounds(161, 39, 96, 19);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setText("Username");
		txtUsername.setBounds(161, 68, 96, 19);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("Password");
		txtPassword.setBounds(161, 100, 96, 19);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		txtDateofbirth = new JTextField();
		txtDateofbirth.setText("DateOfBirth");
		txtDateofbirth.setBounds(161, 129, 96, 19);
		contentPane.add(txtDateofbirth);
		txtDateofbirth.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(172, 232, 85, 21);
		contentPane.add(btnNewButton);
		
		txtBio = new JTextField();
		txtBio.setText("Bio");
		txtBio.setBounds(161, 158, 96, 19);
		contentPane.add(txtBio);
		txtBio.setColumns(10);
		
		txtProfilePic = new JTextField();
		txtProfilePic.setText("Profile Pic");
		txtProfilePic.setBounds(161, 187, 96, 19);
		contentPane.add(txtProfilePic);
		txtProfilePic.setColumns(10);
	}
}
