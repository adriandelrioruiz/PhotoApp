package umu.tds.maven.apps.PhotoApp.vista;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import umu.tds.maven.apps.PhotoApp.controlador.PhotoAppController;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.User;

public class BusquedaFrame extends JFrame{
	private static final int BUSQUEDA_WIDTH=200;
	private static final int BUSQUEDA_HEIGHT=400;
	public BusquedaFrame(String query) {
		List<DomainObject> busqueda=PhotoAppController.getInstance().search(query);
		this.setSize(BUSQUEDA_WIDTH,BUSQUEDA_HEIGHT);
		JPanel panel= new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for (DomainObject d : busqueda) {
			//System.out.println("query: "+d.getCode());
			if(d instanceof User) {
				User user=(User) d;
				JPanel panelUser= new JPanel();
				PhotoAppController.getInstance().getProfilePic(user);
				VentanaPrincipal.fixSize(panelUser,BUSQUEDA_WIDTH,30);
				panelUser.setLayout(new BoxLayout(panelUser, BoxLayout.X_AXIS));
				JLabel foto=new JLabel(PhotoAppController.getInstance().getProfilePic(user));
				VentanaPrincipal.fixSize(foto,50,30);
				//System.out.println("query: "+PhotoAppController.getInstance().getProfilePic(user));
				JLabel nombre=new JLabel(PhotoAppController.getInstance().getUsername(user));
				VentanaPrincipal.fixSize(foto,100,30);
				panelUser.add(foto);
				panelUser.add(nombre);
				panel.add(panelUser);
			}
		}
		JScrollPane scroll=new JScrollPane(panel);
		VentanaPrincipal.fixSize(scroll,BUSQUEDA_WIDTH,BUSQUEDA_HEIGHT);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroll);
		 this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
