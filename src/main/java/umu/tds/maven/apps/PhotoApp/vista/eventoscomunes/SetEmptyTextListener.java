package umu.tds.maven.apps.PhotoApp.vista.eventoscomunes;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.text.JTextComponent;

/** Action Listener para que al clicar en un texto desaparezca su texto por defecto */

public class SetEmptyTextListener extends MouseAdapter{
	
	// Texto por defecto
	private String defaultText;
	private JTextComponent componente;
	
	public SetEmptyTextListener(String defaultText, JTextComponent componente) {
		this.defaultText = defaultText;
		this.componente = componente;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		componente.setFocusable(true);
		componente.grabFocus();
		if (componente.getText().equals(defaultText))
			componente.setText("");
	}
	
	
	
	
	

}
