package umu.tds.maven.apps.PhotoApp.vista.eventoscomunes;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.text.JTextComponent;

public class SetDefaultTextListener extends FocusAdapter {
<<<<<<< HEAD
	
	// Texto por defecto
		private String defaultText;
		private JTextComponent componente;
		
		public SetDefaultTextListener(String defaultText, JTextComponent componente) {
			this.defaultText = defaultText;
			this.componente = componente;
		}
=======

	// Texto por defecto
	private String defaultText;
	private JTextComponent componente;

	public SetDefaultTextListener(String defaultText, JTextComponent componente) {
		this.defaultText = defaultText;
		this.componente = componente;
	}
>>>>>>> branch 'main' of https://github.com/adriandelrioruiz/PhotoApp.git

	@Override
	public void focusLost(FocusEvent e) {
		if (componente.getText().equals("")) {
			componente.setText(defaultText);
		}
	}

}
