import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int TITULO_WIDTH=250;
	private static final int ESPACIO1_WIDTH=20;
	private static final int ESPACIO2_WIDTH=150;
	private static final int ESPACIO3_WIDTH=20;
	private static final int BUTTON_WIDTH=30;
	private static final int BUTTON_HEIGHT=30;
	private static final int USER_PHOTO_HEIGHT=50;
	private static final int USER_PHOTO_WIDTH=50;
	private static final int MENU_HEIGHT=60;
	private static final int SEARCH_HEIGHT=25;
	private static final int SEARCH_WIDTH=100;
	private JTextField txtTexto;
	
	//PARTE DE ARRIBA DE LA PANTALLA
	public Menu(JFrame ventana,JPanel contenedor) {
			VentanaPrincipal.fixSize(this,contenedor.getWidth(),MENU_HEIGHT);
			this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
			this.setBackground(Color.WHITE);
			//NOMBRE DE LA APLICACION
			JLabel titulo=new JLabel("PhotoTDS");
			titulo.setFont(new Font("Book Antiqua",Font.BOLD,22));
			VentanaPrincipal.fixSize(titulo,TITULO_WIDTH,MENU_HEIGHT);
			this.add(titulo,BorderLayout.WEST);//PONER A LA IZQUIERDA DE LA CAJA
			//BOTON PARA SUBIR FOTO
			JButton uploadButton = new JButton(this.getIcon(BUTTON_WIDTH, BUTTON_HEIGHT, "iconUploadPhoto.png"));
			VentanaPrincipal.fixSize(uploadButton,BUTTON_WIDTH,BUTTON_HEIGHT);
			this.add(uploadButton);//PONER EN EL CENTRO
			//ESPACIO1	
			JLabel espacio1=new JLabel();
			VentanaPrincipal.fixSize(espacio1,ESPACIO1_WIDTH,MENU_HEIGHT);
			this.add(espacio1);
			//PARTE DE BUSCAR 
			txtTexto=new JTextField();
			VentanaPrincipal.fixSize(txtTexto,SEARCH_WIDTH,SEARCH_HEIGHT);
			txtTexto.setText("Search");
			txtTexto.setFont(new Font("Book Antiqua",Font.BOLD,12));
			txtTexto.setEditable(true);
			txtTexto.setOpaque(true);
			//this.add(Box.createHorizontalStrut(10));
			this.add(txtTexto, BorderLayout.CENTER);//PONER EN EL CENTRO
			txtTexto.setColumns(10);
			JButton lupaButton = new JButton(this.getIcon(BUTTON_WIDTH, SEARCH_HEIGHT, "icon_lupa.png"));
			VentanaPrincipal.fixSize(lupaButton,BUTTON_WIDTH,SEARCH_HEIGHT);
			this.add(lupaButton,BorderLayout.WEST);
			
			//ESPACIO ENTRE LUPA Y FOTOUSER
			JLabel espacio2=new JLabel();
			VentanaPrincipal.fixSize(espacio2,ESPACIO2_WIDTH,MENU_HEIGHT);
			this.add(espacio2);
			
			//FOTO USER Y BOTON PREMIUM
			JLabel fotoUser=new JLabel(this.getIcon(USER_PHOTO_WIDTH, USER_PHOTO_HEIGHT, "raro.png"));
			VentanaPrincipal.fixSize(fotoUser,USER_PHOTO_WIDTH,USER_PHOTO_HEIGHT);
			this.add(fotoUser,BorderLayout.EAST);
			
			//ESPACIO ENTRE USER Y BOTON 
			JLabel espacio3=new JLabel();
			VentanaPrincipal.fixSize(espacio3,ESPACIO3_WIDTH,MENU_HEIGHT);
			this.add(espacio3);
			JButton premiumButton = new JButton(this.getIcon(BUTTON_WIDTH,BUTTON_HEIGHT,"icon_tres_lineas.png"));
			VentanaPrincipal.fixSize(premiumButton,BUTTON_WIDTH,BUTTON_HEIGHT);
			this.add(premiumButton,BorderLayout.WEST);
			contenedor.add(this,BorderLayout.NORTH);
			uploadButton.addActionListener(new ActionListener() {
			      @Override
			      public void actionPerformed(ActionEvent e) {
			        // Mostrar una ventana de diálogo para SUBIR FOTO
			        String comment = JOptionPane.showInputDialog(ventana, "Ingrese un comentario:");
			        System.out.println("Comentario: " + comment);
			      }
			    });
			lupaButton.addActionListener(new ActionListener() {
			      @Override
			      public void actionPerformed(ActionEvent e) {
			        // COGER EL STRING DE TXT Y LLAMAR A CONTROLADOR PARA BUSCAR
			        String comment = JOptionPane.showInputDialog(ventana, "Ingrese un comentario:");
			        System.out.println("Comentario: " + comment);
			      }
			    });
			premiumButton.addActionListener(new ActionListener() {
			      @Override
			      public void actionPerformed(ActionEvent e) {
			        // Mostrar una ventana de diálogo CON LA FUNCIONALIDAD PREMIUM
			    	  //llamar a la clase ventana premium
			    	  //
			    	  PremiumWindow premium=new PremiumWindow("Miguel");
			      }
			    });
	
	}
	private ImageIcon getIcon(int width,int height,String filename ) {
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/"+filename));
		// Escalar la imagen a un tamaño específico
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		// Crear un ImageIcon a partir de la imagen escalada
		return new ImageIcon(image);

		
	}
	
}
