package univ_lorraine.iut.java.privatechat.model;

public enum MessageType {
	INIT("INITIALISATION"),
	MESSAGE("MESSAGE"),
	CLOSE("FERMETURE"),
	CONNECT("CONNEXION");

	private final String label;

	MessageType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
