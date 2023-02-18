package umu.tds.maven.apps.PhotoApp.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.awt.Cursor;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class Menu extends JPanel {
	/**
	 * jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
        });
private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {
        //Acciones
    }
	 */
	private static final long serialVersionUID = 1L;
	private static final int TITULO_WIDTH=250;
	private static final int ESPACIO1_WIDTH=20;
	private static final int ESPACIO2_WIDTH=250;
	private static final int ESPACIO3_WIDTH=20;
	private static final int BUTTON_WIDTH=30;
	private static final int BUTTON_HEIGHT=30;
	private static final int USER_PHOTO_HEIGHT=50;
	private static final int USER_PHOTO_WIDTH=50;
	public static final int MENU_HEIGHT=90 ;
	private static final int SEARCH_HEIGHT=25;
	private static final int SEARCH_WIDTH=100;
	private JTextField txtTexto;
	private JButton uploadButton,lupaButton,userButton,premiumButton,titulo;
	private ImageIcon image;
	//PARTE DE ARRIBA DE LA PANTALLA
	public Menu(VentanaPrincipal ini,JPanel contenedor) {
			VentanaPrincipal.fixSize(this,VentanaPrincipal.WINDOW_WIDTH,MENU_HEIGHT);
			//this.setLayout(new BoxLayout(this));
			this.setBackground(Color.WHITE);
			//NOMBRE DE LA APLICACION
			titulo=new JButton("PhotoTDS");
			titulo.setFont(new Font("Book Antiqua",Font.BOLD,22));
			 this.setButton(titulo,0, TITULO_WIDTH, MENU_HEIGHT);;
			//BOTON PARA SUBIR FOTO
			 
			uploadButton = new JButton(this.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, "iconUploadPhoto.png"));
			this.setButton(uploadButton, VentanaPrincipal.WINDOW_WIDTH/2 -100, BUTTON_WIDTH, BUTTON_HEIGHT);
			//PARTE DE BUSCAR 
			txtTexto=new JTextField();
			txtTexto.setAlignmentX(VentanaPrincipal.WINDOW_WIDTH/2 - 50);
			VentanaPrincipal.fixSize(txtTexto,SEARCH_WIDTH,SEARCH_HEIGHT);
			txtTexto.setText("Search");
			txtTexto.setFont(new Font("Book Antiqua",Font.BOLD,12));
			txtTexto.setEditable(true);
			txtTexto.setOpaque(true);
			this.add(txtTexto);
			txtTexto.setColumns(10);
			//Bonton lupa
			 lupaButton = new JButton(this.getIcon(BUTTON_WIDTH, SEARCH_HEIGHT, "icon_lupa.png"));
			 this.setButton(lupaButton, VentanaPrincipal.WINDOW_WIDTH/2 +50, BUTTON_WIDTH, BUTTON_HEIGHT);
			//FOTO USER 
			 //PhotoAppController.getInstance().getProfilePic();
			//image=this.getIcon(USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT, "raro.png");
			 userButton = new JButton(this.getIcon(USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT,PhotoAppController.getInstance().getProfilePic()));
			 this.setButton(userButton,VentanaPrincipal.WINDOW_WIDTH-100,USER_PHOTO_WIDTH,USER_PHOTO_HEIGHT);
			//BOTON PREMIUN
			 premiumButton = new JButton(this.getIcon(BUTTON_WIDTH,BUTTON_HEIGHT,"icon_tres_lineas.png"));
			setButton(premiumButton,VentanaPrincipal.WINDOW_WIDTH-50,BUTTON_WIDTH,BUTTON_HEIGHT);
			contenedor.add(this,BorderLayout.NORTH);
			
			titulo.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			        // Recargar pagina principal
			      }
			    });
			
			uploadButton.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			        // Mostrar una ventana de diálogo para SUBIR FOTO
			    	 SubirFoto cargar=new SubirFoto();
			      }
			    });
			lupaButton.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			    	  String query=txtTexto.getText();
			    	  System.out.println("query: " + query);
			    	  new BusquedaFrame(query);
			      }
			    });
			userButton.addActionListener(new ActionListener() {
			      @Override
			      public void actionPerformed(ActionEvent e) {
			        //IR A LA PAGINA DE USER
			    	  JFrame frame = new JFrame();
			    	  frame.setSize(100,200);
				        //frame.setIconImage(image.getImage());
				       JLabel label = new JLabel();
				       label.setIcon(image);
				       frame.getContentPane().add(label);
				        // Mostrar la ventana
				        frame.setVisible(true);
			      }
			    });
			
			
			premiumButton.addActionListener(new ActionListener() {
			      @Override
			      public void actionPerformed(ActionEvent e) {
			    	  if(PhotoAppController.getInstance().isPremium()) {
			    		  new PremiumWindow();//llamar a la clase ventana premium para mostrar frame
			    	  }else {
			    		  new DescuentoFrame();
			    	  }
			    	  
			      }
			    });
			
	
	}
	private   ImageIcon getIcon(int width,int height,String filename ) {
		/*ClassLoader classLoader = getClass().getClassLoader();
		URL imageUrl = classLoader.getResource("img\\"+filename); // Ruta relativa de la imagen
		ImageIcon imageIcon = new ImageIcon(imageUrl);*/
		Image image;
		//image = Toolkit.getDefaultToolkit().getImage(imageUrl);
		image = Toolkit.getDefaultToolkit().getImage(ViewConstants.RUTA_FOTOS+filename);
		// Escalar la imagen a un tamaño específico
		
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		// Crear un ImageIcon a partir de la imagen escalada
		return new ImageIcon(image);
		//return imageIcon;
	}
	private void setComponent(JComponent component,int x,int y,int width,int height) {
		component.setAlignmentX(x);
		VentanaPrincipal.fixSize(component,TITULO_WIDTH,MENU_HEIGHT);
		this.add(component);
	}
	private void mostrarMenu(ActionEvent e) {
		// PopupMenu menu = new PopupMenu();
		 JPopupMenu menu = new JPopupMenu();
	        JMenuItem cut = new JMenuItem("Cut");
	        JMenuItem copy = new JMenuItem("Copy");
	        JMenuItem paste = new JMenuItem("Paste");

	        //menu.add(open);
	        menu.add(cut);
	        menu.add(copy);
	        menu.add(paste); 
	        //menu.show(e.getComponent(), e.getX(), e.getY());
		 } 
	private void setButton(JButton boton,int x,int width,int height) {
		this.add(boton);
		boton.setBounds(x,150,width,height);
		boton.setAlignmentX(x);
		//VentanaPrincipal.fixSize(boton,width,height);
		boton.setBackground(null);
		boton.setBorderPainted(false);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
	}
	
}