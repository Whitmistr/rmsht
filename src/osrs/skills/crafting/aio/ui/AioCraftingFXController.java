package osrs.skills.crafting.aio.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import osrs.skills.crafting.aio.AioCrafting;

import java.net.URL;
import java.util.ResourceBundle;


public class AioCraftingFXController implements Initializable {

    private AioCrafting bot;

    @FXML
    private ComboBox<String> craftingTypeBox, itemNameBox;

    @FXML
    private Button StartButton;

    public AioCraftingFXController(AioCrafting bot) {
        this.bot = bot;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.craftingTypeBox.getItems().addAll("Gems", "Glass items", "Leather", "Hard leather", "Dragon leather", "Battlestaves");
        this.craftingTypeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            itemNameBox.getItems().clear();
            switch (this.craftingTypeBox.getSelectionModel().getSelectedIndex()) {
                case 0:
                    itemNameBox.getItems().addAll("Uncut opal", "Uncut jade", "Uncut red topaz", "Uncut sapphire", "Uncut emerald", "Uncut ruby", "Uncut diamond", "Uncut dragonstone", "Uncut onyx");
                    break;
                case 1:
                    itemNameBox.getItems().addAll("Beer glass", "Candle lantern", "Oil lamp", "Vial", "Fishbowl", "Unpowered staff orb", "Lantern lens", "Light orb");
                    break;
                case 2:
                    itemNameBox.getItems().addAll("Leather gloves", "Leather boots", "Leather cowl", "Leather vambraces", "Leather body", "Leather chaps");
                    break;
                case 3:
                    itemNameBox.getItems().addAll("Hardleather body");
                    break;
                case 4:
                    itemNameBox.getItems().addAll("Green dragonhide vambraces", "Green dragonhide chaps", "Green dragonhide body", "Blue dragonhide vambraces", "Blue dragonhide chaps", "Blue dragonhide body", "Red dragonhide vambraces", "Red dragonhide chaps", "Red dragonhide body", "Black dragonhide vambraces", "Black dragonhide chaps", "Black dragonhide body");
                    break;
                case 5:
                    itemNameBox.getItems().addAll("Air battlestaff", "Water battlestaff", "Earth battlestaff", "Fire battlestaff");
                    break;
            }
        });
        StartButton.setOnAction(getStartButtonAction());
        this.craftingTypeBox.setOnAction(getCraftEvent());
    }

    private EventHandler<ActionEvent> getStartButtonAction() {
        return event -> {
            try {
                AioCrafting.itemType = craftingTypeBox.getValue();
                AioCrafting.itemName = itemNameBox.getValue();
                switch (AioCrafting.itemName.substring(0, 3).trim()) {
                    case "Lea":
                        AioCrafting.hide = "Leather";
                        break;
                    case "Har":
                        AioCrafting.hide = "Hard leather";
                        break;
                    case "Gre":
                        AioCrafting.hide = "Green dragon leather";
                        break;
                    case "Blu":
                        AioCrafting.hide = "Blue dragon leather";
                        break;
                    case "Red":
                        AioCrafting.hide = "Red dragon leather";
                        break;
                    case "Bla":
                        AioCrafting.hide = "Black dragon leather";
                        break;
                }

                switch (AioCrafting.itemName.substring(AioCrafting.itemName.length() - 3, AioCrafting.itemName.length()).trim()) {
                    case "ces":
                        AioCrafting.hidesReq = 1;
                        break;
                    case "aps":
                        AioCrafting.hidesReq = 2;
                        break;
                    case "ody":
                        AioCrafting.hidesReq = 3;
                        break;
                }

                switch (AioCrafting.itemName.substring(0, 3).trim()) {
                    case "Air":
                        AioCrafting.orb = "Air orb";
                        break;
                    case "Wat":
                        AioCrafting.orb = "Water orb";
                        break;
                    case "Ear":
                        AioCrafting.orb = "Earth orb";
                        break;
                    case "Fir":
                        AioCrafting.orb = "Fire orb";
                        break;
                }
                bot.currentTaskString = "Selected: " + AioCrafting.itemName;
                AioCrafting.scriptStarted = true;
                AioCrafting.stopWatch.start();
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
