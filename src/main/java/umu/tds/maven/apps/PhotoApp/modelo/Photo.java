package umu.tds.maven.apps.PhotoApp.modelo;

public class Photo extends Publication{

	private int code;
	private String path;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
