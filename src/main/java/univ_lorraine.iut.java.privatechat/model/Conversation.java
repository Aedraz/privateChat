package univ_lorraine.iut.java.privatechat.model;

import javafx.collections.ObservableList;
import java.io.Serializable;

public class Conversation implements Serializable {

    private ObservableList<Message> messages;
    private Contact interlocutor;

    public Conversation(ObservableList<Message> messages, Contact interlocutor) {
        this.messages = messages;
        this.interlocutor = interlocutor;
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Contact getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(Contact interlocutor) {
        this.interlocutor = interlocutor;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "messages=" + messages +
                ", interlocutor=" + interlocutor +
                '}';
    }
}
