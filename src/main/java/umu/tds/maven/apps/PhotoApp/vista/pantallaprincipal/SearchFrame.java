package umu.tds.maven.apps.PhotoApp.vista.pantallaprincipal;

import javax.swing.JFrame;

public class SearchFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int BUSQUEDA_WIDTH = 200;
	private static final int BUSQUEDA_HEIGHT = 400;

	public SearchFrame(String query) {
		/*
		 * List<DomainObject> busqueda = PhotoAppController.getInstance().search(query);
		 * this.setSize(BUSQUEDA_WIDTH, BUSQUEDA_HEIGHT); JPanel panel = new JPanel();
		 * panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); for (DomainObject d
		 * : busqueda) { System.out.println("query: " + d.getCode()); if (d instanceof
		 * User) { User user = (User) d; JPanel panelUser = new JPanel();
		 * PhotoAppController.getInstance().getProfilePic(user);
		 * LoggedFrame.fixSize(panelUser, BUSQUEDA_WIDTH, 30); panelUser.setLayout(new
		 * BoxLayout(panelUser, BoxLayout.X_AXIS)); JLabel foto = new
		 * JLabel(PhotoAppController.getInstance().getProfilePic(user));
		 * LoggedFrame.fixSize(foto, 50, 30); System.out.println("query: " +
		 * PhotoAppController.getInstance().getProfilePic(user)); JLabel nombre = new
		 * JLabel(PhotoAppController.getInstance().getUsername(user));
		 * LoggedFrame.fixSize(foto, 100, 30); panelUser.add(foto);
		 * panelUser.add(nombre); panel.add(panelUser); } } JScrollPane scroll = new
		 * JScrollPane(panel); LoggedFrame.fixSize(scroll, BUSQUEDA_WIDTH,
		 * BUSQUEDA_HEIGHT); scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
		 * VERTICAL_SCROLLBAR_ALWAYS);
		 * scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.
		 * HORIZONTAL_SCROLLBAR_NEVER); this.add(scroll); this.setVisible(true);
		 */
	}

}
