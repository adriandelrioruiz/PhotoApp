package umu.tds.maven.apps.PhotoApp.vista;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

@SuppressWarnings("serial")
public class SetBioFrame extends JFrame {
	
	private JTextArea textArea;
	
	public SetBioFrame() {
		textArea = new JTextArea(ViewConstants.BIO_DEFAULT_TEXT);
		textArea.setColumns(10);
		textArea.setForeground(Color.GRAY);
		textArea.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 13));
		textArea.setFocusable(false);
		addTextFieldHandler(textArea, ViewConstants.BIO_DEFAULT_TEXT);
		// La hacemos scrollable
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane, BorderLayout.CENTER);
		
		// Panel botones
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		
		// Botón aceptar
		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setForeground(Color.WHITE);
		btnAccept.setBackground(ViewConstants.APP_GREEN_COLOR);
		addAcceptButtonListener(btnAccept);
		buttonsPanel.add(btnAccept);
		
		add(buttonsPanel, BorderLayout.SOUTH);
		
		setSize(400, 300);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
	}
	
	
	private void addAcceptButtonListener(JButton button) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textArea.getText().length() > ViewConstants.BIO_LENGTH)
					JOptionPane.showMessageDialog(null, ViewConstants.BIO_DIALOG_LENGHT_EXCEEDED);
				else
					dispose();
			}
		});
	}
	
	
	private void addTextFieldHandler(JTextComponent textField, String defaultText) {

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
					textField.setFocusable(false);
				}
			}
		});
	}
	
	public String getBio() {
		// Si está puesto el texto por defecto, devolvemos la cadena vacía
		if (textArea.getText().equals(ViewConstants.BIO_DEFAULT_TEXT))
			return "";
		return textArea.getText();
	}
}
