package umu.tds.maven.apps.PhotoApp.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import umu.tds.maven.apps.PhotoApp.modelo.DomainObject;
import umu.tds.maven.apps.PhotoApp.modelo.Notification;
import umu.tds.maven.apps.PhotoApp.modelo.Photo;

public class NotificationAdapterTDS extends AdapterTDS implements INotificationAdapterDAO {
	
	public static final String NOTIFICATION = "notification";
	public static final String DATE = "date";
	public static final String PHOTO = "photo";
	
	private static NotificationAdapterTDS instance;
	private SimpleDateFormat dateFormat;
	
	public static NotificationAdapterTDS getInstance() {
		if (instance == null)
			instance = new NotificationAdapterTDS();
		return instance;
	}
	
	public NotificationAdapterTDS() {	
		super();
		dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	}
	
	
	@Override
	protected Entidad objectToEntity(DomainObject o) {
		Entidad eNotification = new Entidad();
		Notification notification = (Notification) o;

		eNotification.setNombre(NOTIFICATION);
		eNotification.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(DATE, dateFormat.format(notification.getDate())),
						new Propiedad(PHOTO, String.valueOf(notification.getPhoto().getCode())))));

		return eNotification;
	}

	@Override
	protected DomainObject entityToObject(Entidad en) {

		// Estos serán los atributos del Post que queremos recuperar
		Date date;
		Photo photo;
		
		// Recuperamos los atributos de la persistencia
		date = null;
		try {
			date = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(en, DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		photo = PhotoAdapterTDS.getInstance().getPhoto(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(en, PHOTO)));
		
		Notification notification = new Notification(date, photo);
		
		notification.setCode(en.getId());
		
		return notification;
	}

	@Override
	public Notification getNotification(int code) {
		Entidad eNotification;
		Notification notification = null;

		// Recuperamos la entidad
		eNotification = servPersistencia.recuperarEntidad(code);
		if (eNotification != null)
		// Convertimos la entidad en un objeto notification
		try {
			notification = (Notification) entityToObject(eNotification);
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return notification;
	}

	@Override
	public void addNotification(Notification notification) {
		// Si el post ya está registrado, no se registra
		Entidad eNotification = null;
		try {
			eNotification = servPersistencia.recuperarEntidad(notification.getCode());
		} catch (NullPointerException e) {
		}
		if (eNotification != null)
			return;
		
		// Creamos entidad post
		eNotification = objectToEntity(notification);
		// registrar entidad post
		eNotification = servPersistencia.registrarEntidad(eNotification);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		notification.setCode(eNotification.getId());
		
	}

	@Override
	public void deleteNotification(int code) {
		Entidad eNotification = servPersistencia.recuperarEntidad(code);
		servPersistencia.borrarEntidad(eNotification);
	}
	
	@Override
	public int getNotificationCodeByPost(int postCode) {
		List<Entidad> entities = servPersistencia.recuperarEntidades(NOTIFICATION);
		for (Entidad entity : entities)
			for (Propiedad p : entity.getPropiedades())
				if (p.getNombre().equals(PHOTO)) {
					if (p.getValor().equals(String.valueOf(postCode)))
						return entity.getId();
				}
					
		return 0;
	}
	
	
	// Usamos esta función para obtener notifications a través de un string con varios códigos
	public List<Notification> getAllNotificationsFromCodes(String codes) {
		List<Notification> notificationsList = new LinkedList<>();
		if (codes != null && !codes.isEmpty()) {
			StringTokenizer strTok = new StringTokenizer(codes, " ");
			NotificationAdapterTDS adapter = getInstance();
			while (strTok.hasMoreTokens()) {
				notificationsList.add(adapter.getNotification(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return notificationsList;
	}
	
	public String getCodesFromAllNotifications(List<Notification> notifications) {
		String codes = "";
		for (Notification notification : notifications) {
			codes += notification.getCode() + " ";
		}
		return codes.trim();
	}
	
	// TODO para pruebas
		public void deleteAll() {
			List<Entidad> entities = servPersistencia.recuperarEntidades(NOTIFICATION);
			entities.stream().forEach((e)->servPersistencia.borrarEntidad(e));
		}
		

}
