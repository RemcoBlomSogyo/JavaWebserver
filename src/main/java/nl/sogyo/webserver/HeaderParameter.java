package nl.sogyo.webserver;

public class HeaderParameter {
	
	private String name;
	private String value;
	
	public HeaderParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
