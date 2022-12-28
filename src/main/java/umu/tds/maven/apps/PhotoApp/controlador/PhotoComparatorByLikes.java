package umu.tds.maven.apps.PhotoApp.controlador;

import java.util.Comparator;

import umu.tds.maven.apps.PhotoApp.modelo.Photo;

	//Comparator para ordenar las fotos por likes

	public class PhotoComparatorByLikes implements Comparator<Photo> {

		@Override
		public int compare(Photo o1, Photo o2) {
			return ((Integer)o1.getLikes()).compareTo((Integer)o2.getLikes());
		}
		
	}
