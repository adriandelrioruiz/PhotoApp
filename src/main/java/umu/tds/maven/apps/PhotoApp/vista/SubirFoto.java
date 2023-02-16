package umu.tds.maven.apps.PhotoApp.vista;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.Border;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;

import java.awt.Color;
import java.awt.Component;

public class SubirFoto {
	 JFileChooser fileChooser = new JFileChooser();
	
     //fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "jpeg"));
     int returnValue = fileChooser.showOpenDialog(null);{
     if (returnValue == JFileChooser.APPROVE_OPTION) {
    	 JFrame frame=new JFrame();
         frame.setSize(700,420);
		JPanel contenedor=(JPanel) frame.getContentPane();
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
		Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
		JTextField titulo=new JTextField();
        VentanaPrincipal.fixSize(titulo,700,50);
        titulo.setText("Añade titulo a tu Foto");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(border);
        contenedor.add(titulo);
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        File selectedFile = fileChooser.getSelectedFile();
        String path=selectedFile.getAbsolutePath();
        Image image = Toolkit.getDefaultToolkit().getImage(path);
 		// Escalar la imagen a un tamaño específico
 			image = image.getScaledInstance(340, 300, Image.SCALE_SMOOTH);
         JLabel label = new JLabel(new ImageIcon(image));
         VentanaPrincipal.fixSize(label,340,300);
         
         // Set the border of the button
         label.setBorder(border);
         panel.add(label);
         JTextField description=new JTextField();
         VentanaPrincipal.fixSize(description,340,300);
         description.setText("Añade un comentario a tu Foto");
         description.setBorder(border);
         panel.add(description);
         contenedor.add(panel);
         JButton boton= new JButton("Subir Foto");
         VentanaPrincipal.fixSize(boton,200,30);
         boton.setAlignmentX(Component.CENTER_ALIGNMENT);
         contenedor.add(boton);
         frame.setVisible(true);
         boton.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  SubirFoto(path,description.getText(),titulo.getText());
		    	  frame.setVisible(false);
		      }
		    });
     }
}
 private void SubirFoto(String path,String description,String titulo) {
	 PhotoAppController.getInstance().addPhoto(titulo, description, path);
 }    
}