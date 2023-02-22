package umu.tds.maven.apps.PhotoApp.vista.mostrarpost;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.vista.constantes.ViewConstants;

/** Clase JFrame usada para mostrar un post */

@SuppressWarnings("serial")
public abstract class ShowPostFrame extends JFrame {
	
	//--- TAMAÑOS PREDETERMINADOS ----
	
	// Tamaño de la ventana
	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HEIGHT = 500; 
	
	// Tamaños de los paneles
	protected static final Dimension NORTH_PANEL_DIMENSION = new Dimension(FRAME_WIDTH, 40);
	protected static final Dimension WEST_PANEL_DIMENSION = new Dimension(FRAME_WIDTH/2, 400);
	protected static final Dimension EAST_PANEL_DIMENSION = new Dimension(FRAME_WIDTH/2-50, 400);
	protected static final Dimension SOUTH_PANEL_DIMENSION = new Dimension(FRAME_WIDTH, 40);
	
	// -----------------------
	
	// Controlador
	protected PhotoAppController controller = PhotoAppController.getInstance();
	
	// Id del usuario del que mostrará el post
	protected int userId;
	
	// Paneles que componen el frame
	// Panel norte, que contendrá el título del post o el JText para escribir un título
	protected JPanel northPane;
	// Panel oeste, que contendrá la imagen/imágenes del post
	protected JPanel westPane;
	// Panel este, que contendrá el JTextArea para comentar o dar una descripción al post
	protected JPanel eastPane;
	// Panel sur, que contendrá los botones para salir, comentar, dar like, etc al post
	protected JPanel southPane;
	
	// Botón para salir del frame
	private JButton salirButton;
	
	// JTextArea para la descripción/comentario
	protected JTextArea commentTxtArea;
	
	
	// Constructor de la clase
	public ShowPostFrame(int userId) {
		
		this.userId = userId;

		initialize();
	}
	
	// Método para inicializar los valores del Frame
	protected void initialize() {
		
		// TODO centrar el frame, layout...
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);
		// ...
		
		// Creamos los paneles
		createNorthPane();
		createWestPane();
		createEastPane();
		createSouthPane();
		
		// Añadimos los listeners
		addListeners();
	}
	
	// Método para crear el panel norte 
	protected void createNorthPane() {
		
		northPane = new JPanel();
		northPane.setPreferredSize(NORTH_PANEL_DIMENSION);
		getContentPane().add(northPane, BorderLayout.NORTH);
	}
	
	// Método para crear el panel oeste 
	protected void createWestPane() {
		
		westPane = new JPanel();
		westPane.setPreferredSize(WEST_PANEL_DIMENSION);
		westPane.setLayout(new BorderLayout());
		getContentPane().add(westPane, BorderLayout.WEST);
		
	}
	
	// Método para crear el panel este 
	protected void createEastPane() {
		
		eastPane = new JPanel();
		eastPane.setPreferredSize(EAST_PANEL_DIMENSION);
		eastPane.setLayout(new BorderLayout());
		getContentPane().add(eastPane, BorderLayout.EAST);

		
		// Creamos el área de texto
		commentTxtArea = new JTextArea();
		commentTxtArea.setFocusable(false);
		commentTxtArea.setForeground(Color.GRAY);
		commentTxtArea.setFont(new Font(ViewConstants.APP_FONT, Font.PLAIN, 14));
		eastPane.add(commentTxtArea, BorderLayout.CENTER);
		
	}
	
	// Método para crear el panel sur 
	protected void createSouthPane() {
		
		southPane = new JPanel();
		southPane.setPreferredSize(SOUTH_PANEL_DIMENSION);
		getContentPane().add(southPane, BorderLayout.SOUTH);
		
		// Utilizamos el FlowLayout para que los botones se vayan añadiendo ordenadamente
		southPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		// Creamos el botón para salir del frame
		salirButton = new JButton("Salir");
		salirButton.setBackground(ViewConstants.APP_GREEN_COLOR);
		southPane.add(salirButton);		
	}
	
	
	// Método para crear los listeners
	protected void addListeners() {

		// Añadimos al botón de salir el listener para que cierre la ventana
		salirButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Cerrar frame
				dispose();
			}
		});
		
	}

	
}
