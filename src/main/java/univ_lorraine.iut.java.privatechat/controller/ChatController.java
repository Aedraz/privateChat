package univ_lorraine.iut.java.privatechat.controller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import univ_lorraine.iut.java.privatechat.App;
import univ_lorraine.iut.java.privatechat.Serveur;
import univ_lorraine.iut.java.privatechat.model.Contact;
import univ_lorraine.iut.java.privatechat.model.Conversation;
import univ_lorraine.iut.java.privatechat.model.ConversationListCell;
import univ_lorraine.iut.java.privatechat.model.Message;

public class ChatController implements DataController {
    @FXML private ListView<Conversation> convListView;
    private ObservableList<Conversation> convList = FXCollections.observableArrayList();

    @FXML private Button btnAddNewContact;

    public void addConv(Conversation conv) {
        convList.add(conv);
    }

    public void initialize() {
        String userLogin = App.getUser();
        if (convListView != null) {
            convListView.setItems(convList);
            convListView.setCellFactory(listView -> new ConversationListCell());
        }

        File contactFile = new File(App.getUser() + "_contact.obj");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        if (contactFile.exists()) {
            try {
                fis = new FileInputStream(contactFile);
                ois = new ObjectInputStream(fis);
                List<Conversation> list = (List) ois.readObject();
                for (Conversation conv : list) {
                    convList.add(conv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    ois.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void uninitialize() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            List<Conversation> convs = new ArrayList<>(convList);

            File contactFile = new File(App.getUser() + "_contact.obj");
            fos = new FileOutputStream(contactFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(convs);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void addNewContact() throws IOException {
        uninitialize();
        App.setWindowSize(520, 400);
        App.setRoot("AddContact");
    }

    @FXML
    private void logout() throws IOException {
        uninitialize();
        App.setWindowSize(520, 400);
        App.setRoot("login");
    }

    @FXML
    public TextField inputFld;

    @FXML
    private TextArea msgArea;

    @FXML
    private void sendMsg(ActionEvent event) {
        String msgTxt = inputFld.getText();
        inputFld.clear();
        Message msg = new Message();
        msg.setContent(msgTxt);
        msg.setSender(App.getUser());
        msg.setSendedDate(LocalDateTime.now());
        if (msg != null) {
            System.out.println(msg);
            msgArea.appendText(msg.getSender() + " : " + msg.getContent() + "\n");
        }
    }

    @Override
    public void setData(Object inputData) {
        if (inputData instanceof Contact) {
            var receivedContact = (Contact) inputData;
            convList.add(new Conversation(null, receivedContact));
        }
    }

    private static Contact staticContact;

    public static void main(String[] args) {
        staticContact = new Contact(args[0], args[1], Integer.parseInt(args[2]));
    }
}
