package univ_lorraine.iut.java.privatechat.model;

import java.io.Serializable;

public class Contact implements Serializable {

    private final String pseudo;
    private final String ip;
    private final Integer port;

    public Contact(String pseudo, String ip, Integer port) {
        this.pseudo = pseudo;
        this.ip = ip;
        this.port = port;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "pseudo='" + pseudo + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
