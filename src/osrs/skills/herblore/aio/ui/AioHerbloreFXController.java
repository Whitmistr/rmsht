package osrs.skills.herblore.aio.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import osrs.skills.herblore.aio.AioHerblore;
import osrs.skills.herblore.aio.enums.Potion;
import osrs.skills.herblore.aio.enums.Tar;
import osrs.skills.herblore.aio.enums.UnfPotion;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AioHerbloreFXController implements Initializable {

    private AioHerblore bot;

    private Potion potions;

    @FXML
    private ComboBox<String> craftingTypeBox, itemNameBox;

    @FXML
    private Button StartButton;

    public AioHerbloreFXController(AioHerblore bot) {
        this.bot = bot;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //to do "Special potions" "Make tar"
        this.craftingTypeBox.getItems().addAll("Clean herbs", "Make unfs", "Make potions");
        this.craftingTypeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            itemNameBox.getItems().clear();
            switch (this.craftingTypeBox.getSelectionModel().getSelectedIndex()) {
                case 0:
                    itemNameBox.getItems().addAll("Grimy guam leaf", "Grimy marrentill", "Grimy tarromin", "Grimy harralander", "Grimy ranarr weed", "Grimy toadflax", "Grimy irit leaf", "Grimy avantoe", "Grimy kwuarm", "Grimy snapdragon", "Grimy cadantine", "Grimy lantadyme", "Grimy dwarf weed", "Grimy torstol");
                    break;
                case 1:
                    for (UnfPotion unfp : UnfPotion.values()) {
                        itemNameBox.getItems().add(unfp.getUnfPotion());
                    }
                    break;
                case 2:
                    for (Potion p : Potion.values()) {
                        itemNameBox.getItems().add(p.getFinalPotion());
                    }
                    break;
                case 3:
                    for (Tar tar : Tar.values()) {
                        itemNameBox.getItems().add(tar.getFinalProduct());
                    }
                    break;
                case 4:
                    itemNameBox.getItems().addAll("Super combat potion(4)");
                    break;
            }
        });
        StartButton.setOnAction(getStartButtonAction());
        this.craftingTypeBox.setOnAction(getCraftEvent());
    }

    private EventHandler<ActionEvent> getStartButtonAction() {
        return event -> {
            try {
                AioHerblore.itemType = craftingTypeBox.getValue();
                AioHerblore.itemName = itemNameBox.getValue();
                switch (AioHerblore.itemType) {
                    case "Clean herbs":
                        AioHerblore.mode = "1";
                        break;
                    case "Make unfs":
                        AioHerblore.mode = "2";
                        for (UnfPotion unfp : UnfPotion.values()) {
                            if (AioHerblore.itemName.equals(unfp.getUnfPotion())) {
                                AioHerblore.primaryIngredient = unfp.getPrimary();
                                AioHerblore.primaryQt = unfp.getPrimaryQt();
                                AioHerblore.secondaryIngredient = unfp.getSecondary();
                                AioHerblore.seconrdaryQt = unfp.getSecondaryQt();
                                break;
                            }
                        }
                        break;
                    case "Make potions":
                        AioHerblore.mode = "2";
                        for (Potion p : Potion.values()) {
                            if (AioHerblore.itemName.equals(p.getFinalPotion())) {
                                AioHerblore.primaryIngredient = p.getPrimary();
                                AioHerblore.primaryQt = p.getPrimaryQt();
                                AioHerblore.secondaryIngredient = p.getSecondary();
                                AioHerblore.seconrdaryQt = p.getSecondaryQt();
                                break;
                            }
                        }
                        break;
                    case "Make tar":
                        AioHerblore.mode = "3";
                        for (Tar tar : Tar.values()) {
                            if (AioHerblore.itemName.equals(tar.getFinalProduct())) {
                                AioHerblore.primaryIngredient = tar.getTar();
                                AioHerblore.primaryQt = tar.getTarQt();
                                AioHerblore.secondaryIngredient = tar.getHerb();
                                AioHerblore.seconrdaryQt = tar.getHerbQt();
                                break;
                            }
                        }
                        break;
                    case "Special potions":
                        AioHerblore.mode = "4";
                        break;
                }

                bot.currentTaskString = "Selected: " + AioHerblore.itemName;
                AioHerblore.scriptStarted = true;
                AioHerblore.stopWatch.start();
                Platform.runLater(() -> bot.setToInfoProperty());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private EventHandler<ActionEvent> getCraftEvent() {
        return event -> {
            if (craftingTypeBox.getSelectionModel().getSelectedItem() != null) {
                StartButton.setDisable(false);
            } else {
                StartButton.setDisable(true);
            }
        };
    }
}
