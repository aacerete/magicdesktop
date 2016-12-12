package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Controller {
    public AnchorPane apMainPane;
    public ListView<Card> lvLlistaCartes;
    public ArrayList<Card> cartes;
    public ImageView ivCartes;
    public TextField cardName;
    public Group colors;
    public Group rarities;

    public void initialize() throws MalformedURLException {

        cartes = CardApi.getCards();

        // Personalitzem la CellFactory
        lvLlistaCartes.setCellFactory((list) -> {
            return new ListCell<Card>() {
                @Override
                public void updateItem(Card item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        System.out.println(item.toString());
                        if(item.getImageUrl() != null) {
                            Image image = new Image(item.getImageUrl(), 75, 75, true, true, true);
                            setGraphic(new ImageView(image));
                        }
                        setText(item.getName());
                    }
                }
            };
        });

        // Afegir llista observable d'items
        ObservableList<Card> cards = FXCollections.observableArrayList(cartes);
        lvLlistaCartes.setItems(cards);

        // Afegir un item
        //lvLlistaCartes.getItems().add(new Person("Pepi", "icon.png"));

        // Handle ListView selection changes with a listener
        lvLlistaCartes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(!newValue.getImageUrl().isEmpty()) {
                        Image image = new Image(newValue.getImageUrl());
                        ivCartes.setImage(image);
                    }
                    cardName.setText(newValue.getName());
                }
        );

        getFilterColors();
    }


    public void refresh(){
        String colors = getFilterColors();
        String rarities = getFilterRarity();
        lvLlistaCartes.getItems().removeAll();
        System.out.println("lvLlista: "+lvLlistaCartes.getItems().size());
        String filter = "colors="+colors+"&rarity="+rarities;
        cartes = CardApi.getCards(filter);
        ObservableList<Card> cards = FXCollections.observableArrayList(cartes);
        lvLlistaCartes.getItems().setAll(cards);


    }

    public String getFilterColors(){
        String filter = "";
        for (int i = 0; i < colors.getChildren().size(); i++) {
            CheckBox check = (CheckBox) colors.getChildren().get(i);
            if(check.isSelected()) {
                filter += check.getText()+"|";
            }
        }
        if(filter.length() > 0) {
            filter = filter.substring(0, filter.length() - 1);
        }
        filter = filter.replace(" ","%20");
        return filter;
    }

    public String getFilterRarity(){
        String filter = "";
        for (int i = 0; i < rarities.getChildren().size(); i++) {
            CheckBox check = (CheckBox) rarities.getChildren().get(i);
            if(check.isSelected()) {
                filter += check.getText()+"|";
            }
        }
        if(filter.length() > 0) {
            filter = filter.substring(0, filter.length() - 1);
        }
        filter = filter.replace(" ","%20");
        return filter;
    }
}