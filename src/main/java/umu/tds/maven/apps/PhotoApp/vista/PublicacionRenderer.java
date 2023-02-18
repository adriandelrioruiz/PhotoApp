package umu.tds.maven.apps.PhotoApp.vista;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class PublicacionRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        
        //JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        // Obtiene el objeto Publicacion de la lista
        Publicacion publicacion = (Publicacion) value;
        
        // Establece el título y el texto en la etiqueta
        //label.setText("<html><b>" + publicacion.getTitulo() + "</b><br>" + publicacion.getTexto() + "</html>");
        
        return publicacion;
    }
}