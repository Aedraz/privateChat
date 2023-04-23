package univ_lorraine.iut.java.privatechat.model;

import javafx.scene.control.ListCell;
import univ_lorraine.iut.java.privatechat.Client;
import univ_lorraine.iut.java.privatechat.controller.ChatController;

public class ConversationListCell extends ListCell<Conversation> {

    public ConversationListCell() {
        super();
        this.setOnMouseClicked(event -> {
            Conversation conversation = getItem();
            if (conversation != null) {
                Contact contact = conversation.getInterlocutor();
                String[] args = {contact.getPseudo(), contact.getIp(), contact.getPort().toString()};
                Client.main(args);
                ChatController.main(args);
            }
        });
    }

    @Override
    public void updateItem(Conversation conversation, boolean empty) {
        super.updateItem(conversation, empty);
        if (empty || conversation == null) {
            setText(null);
        } else {
            setText(conversation.getInterlocutor().getPseudo());
        }
    }
}
