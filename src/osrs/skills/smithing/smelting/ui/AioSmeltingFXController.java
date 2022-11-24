package osrs.skills.smithing.smelting.ui;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import osrs.skills.smithing.smelting.AioSmelting;

import java.net.URL;
import java.util.ResourceBundle;


public class AioSmeltingFXController implements Initializable {

    private AioSmelting bot;

    @FXML
    private ComboBox<String> smeltingTypeBox, itemNameBox, smeltingLocationBox;

    @FXML
    private Button StartButton;

    public AioSmeltingFXController(AioSmelting bot) {
        this.bot = bot;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.smeltingLocationBox.getItems().addAll("Al kharid", "Edgeville");
        this.smeltingTypeBox.getItems().addAll("Bars", "Silver items", "Gold items");
        this.smeltingTypeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            itemNameBox.getItems().clear();
            switch (this.smeltingTypeBox.getSelectionModel().getSelectedIndex()) {
                case 0:
                    itemNameBox.getItems().addAll("Bronze bar", "Iron bar", "Silver bar", "Steel bar", "Gold bar", "Mithril bar", "Adamantite bar", "Runite bar");
                    break;
                case 1:
                    itemNameBox.getItems().addAll("Holy symbol", "Tiara", "Opal ring", "Opal necklace", "Opal bracelet", "Opal amulet", "Jade ring", "Jade necklace", "Jade bracelet", "Jade amulet", "Topaz ring", "Topaz necklace", "Topaz bracelet", "Topaz amulet");
                    break;
                case 2:
                    itemNameBox.getItems().addAll("Gold ring", "Sapphire ring", "Emerald ring", "Ruby ring", "Diamond ring", "Dragonstone ring", "Gold necklace", "Sapphire necklace", "Emerald necklace", "Ruby necklace", "Diamond necklace", "Dragonstone necklace", "Gold bracelet", "Sapphire bracelet", "Emerald bracelet", "Ruby bracelet", "Diamond bracelet", "Dragonstone bracelet", "Gold amulet (u)", "Sapphire amulet (u)", "Emerald amulet (u)", "Ruby amulet (u)", "Diamond amulet (u)", "Dragonstone amulet (u)");
                    break;
                case 3:
                    itemNameBox.getItems().addAll("Cannonballs");
                    break;
            }
        });
        StartButton.setOnAction(getStartButtonAction());
        this.smeltingTypeBox.setOnAction(getCraftEvent());
    }

    private EventHandler<ActionEvent> getStartButtonAction() {
        return event -> {
            try {
                AioSmelting.itemType = smeltingTypeBox.getValue();
                AioSmelting.itemName = itemNameBox.getValue();
                AioSmelting.location = smeltingLocationBox.getValue();

                switch (AioSmelting.itemType.substring(0, 3).trim()) {
                    case "Bar":
                        switch (AioSmelting.itemName.substring(0, 3).trim()) {
                            case "Bro":
                                AioSmelting.item1 = "Copper ore";
                                AioSmelting.item1qt = 14;
                                AioSmelting.item2 = "Tin ore";
                                AioSmelting.item2qt = 14;
                                AioSmelting.mode = "2";
                                break;
                            case "Iro":
                                AioSmelting.item1 = "Iron ore";
                                AioSmelting.item1qt = 28;
                                AioSmelting.mode = "1";
                                break;
                            case "Sil":
                                AioSmelting.item1 = "Silver ore";
                                AioSmelting.item1qt = 28;
                                AioSmelting.mode = "1";
                                break;
                            case "Ste":
                                AioSmelting.item1 = "Iron ore";
                                AioSmelting.item1qt = 9;
                                AioSmelting.item2 = "Coal";
                                AioSmelting.item1qt = 18;
                                AioSmelting.mode = "2";
                                break;
                            case "Gol":
                                AioSmelting.item1 = "Gold ore";
                                AioSmelting.item1qt = 28;
                                AioSmelting.mode = "1";
                                break;
                            case "Mit":
                                AioSmelting.item1 = "Mithril ore";
                                AioSmelting.item1qt = 5;
                                AioSmelting.item2 = "Coal";
                                AioSmelting.item1qt = 20;
                                AioSmelting.mode = "2";
                                break;
                            case "Ada":
                                AioSmelting.item1 = "Adamantite ore";
                                AioSmelting.item1qt = 4;
                                AioSmelting.item2 = "Coal";
                                AioSmelting.item2qt = 24;
                                AioSmelting.mode = "2";
                                break;
                            case "Run":
                                AioSmelting.item1 = "Runite ore";
                                AioSmelting.item1qt = 3;
                                AioSmelting.item2 = "Coal";
                                AioSmelting.item2qt = 24;
                                AioSmelting.mode = "2";
                                break;
                        }
                        break;
                    case "Sil":
                        AioSmelting.item1 = "Silver bar";
                        switch (AioSmelting.itemName.substring(AioSmelting.itemName.length() - 4, AioSmelting.itemName.length())) {
                            case "mbol":
                                AioSmelting.item1qt = 27;
                                AioSmelting.mode = "4";
                                AioSmelting.mould = "Holy mould";
                                break;
                            case "iara":
                                AioSmelting.item1qt = 27;
                                AioSmelting.mode = "4";
                                AioSmelting.mould = "Tiara mould";
                                break;
                            case "ring":
                                AioSmelting.item1qt = 13;
                                AioSmelting.mode = "3";
                                AioSmelting.mould = "Ring mould";
                                break;
                            case "lace":
                                AioSmelting.item1qt = 13;
                                AioSmelting.mode = "3";
                                AioSmelting.mould = "Necklace mould";
                                break;
                            case "elet":
                                AioSmelting.item1qt = 13;
                                AioSmelting.mode = "3";
                                AioSmelting.mould = "Bracelet mould";
                                break;
                            case "ulet":
                                AioSmelting.item1qt = 13;
                                AioSmelting.mode = "3";
                                AioSmelting.mould = "Amulet mould";
                                break;
                        }
                        switch (AioSmelting.itemName.substring(0, 3).trim()) {
                            case "Opa":
                                AioSmelting.item2 = "Opal";
                                AioSmelting.item2qt = 13;
                                break;
                            case "Jad":
                                AioSmelting.item2 = "Jade";
                                AioSmelting.item2qt = 13;
                                break;
                            case "Top":
                                AioSmelting.item2 = "Topaz";
                                AioSmelting.item2qt = 13;
                                break;
                        }
                        break;
                    case "Gol":
                        AioSmelting.item1 = "Gold bar";
                        if (AioSmelting.itemName.equalsIgnoreCase("Gold ring") || AioSmelting.itemName.equalsIgnoreCase("Gold necklace") || AioSmelting.itemName.equalsIgnoreCase("Gold Bracelet") || AioSmelting.itemName.equalsIgnoreCase("Gold Amulet (u)")) {
                            switch (AioSmelting.itemName.substring(AioSmelting.itemName.length() - 3, AioSmelting.itemName.length()).trim()) {
                                case "ing":
                                    AioSmelting.mould = "Ring mould";
                                    AioSmelting.item1qt = 27;
                                    AioSmelting.mode = "4";
                                    break;
                                case "ace":
                                    AioSmelting.mould = "Necklace mould";
                                    AioSmelting.item1qt = 27;
                                    AioSmelting.mode = "4";
                                    break;
                                case "let":
                                    AioSmelting.mould = "Bracelet mould";
                                    AioSmelting.item1qt = 27;
                                    AioSmelting.mode = "4";
                                    break;
                                case "(u)":
                                    AioSmelting.mould = "Amulet mould";
                                    AioSmelting.item1qt = 27;
                                    AioSmelting.mode = "4";
                                    break;
                            }
                        } else {
                            switch (AioSmelting.itemName.substring(AioSmelting.itemName.length() - 3, AioSmelting.itemName.length()).trim()) {
                                case "ing":
                                    AioSmelting.item1qt = 13;
                                    AioSmelting.mode = "3";
                                    AioSmelting.mould = "Ring mould";
                                    break;
                                case "ace":
                                    AioSmelting.item1qt = 13;
                                    AioSmelting.mode = "3";
                                    AioSmelting.mould = "Necklace mould";
                                    break;
                                case "let":
                                    AioSmelting.item1qt = 13;
                                    AioSmelting.mode = "3";
                                    AioSmelting.mould = "Bracelet mould";
                                    break;
                                case "(u)":
                                    AioSmelting.item1qt = 13;
                                    AioSmelting.mode = "3";
                                    AioSmelting.mould = "Amulet mould";
                                    break;
                            }
                            switch (AioSmelting.itemName.substring(0, 3).trim()) {
                                case "Sap":
                                    AioSmelting.item2 = "Sapphire";
                                    AioSmelting.item2qt = 13;
                                    break;
                                case "Eme":
                                    AioSmelting.item2 = "Emerald";
                                    AioSmelting.item2qt = 13;
                                    break;
                                case "Rub":
                                    AioSmelting.item2 = "Ruby";
                                    AioSmelting.item2qt = 13;
                                    break;
                                case "Dia":
                                    AioSmelting.item2 = "Diamond";
                                    AioSmelting.item2qt = 13;
                                    break;
                                case "Dra":
                                    AioSmelting.item2 = "Dragonstone";
                                    AioSmelting.item2qt = 13;
                                    break;
                            }
                            break;
                        }
                        break;
                    case "Ste":
                        AioSmelting.item1 = "Steel bar";
                        AioSmelting.item1qt = 27;
                        AioSmelting.mode = "4";
                        AioSmelting.mould = "Ammo mould";
                        break;
                }

                switch (AioSmelting.itemName.substring(AioSmelting.itemName.length() - 3, AioSmelting.itemName.length()).trim()) {
                    case "ces":
                        break;
                }

                switch (AioSmelting.location) {
                    case "Al kharid":
                        AioSmelting.FURNACE_AREA = new Area.Rectangular(new Coordinate(3276, 3185, 0), new Coordinate(3273, 3187, 0));
                        AioSmelting.BANK_AREA = new Area.Rectangular(new Coordinate(3269, 3170, 0), new Coordinate(3270, 3164, 0));
                        break;
                    case "Edgeville":
                        AioSmelting.FURNACE_AREA = new Area.Rectangular(new Coordinate(3109, 3499, 0), new Coordinate(3107, 3497, 0));
                        AioSmelting.BANK_AREA = new Area.Rectangular(new Coordinate(3098, 3494, 0), new Coordinate(3096, 3495, 0));
                        break;
                }

                bot.currentTaskString = "Selected: " + AioSmelting.itemName;
                AioSmelting.scriptStarted = true;
                AioSmelting.stopWatch.start();
                Platform.runLater(() -> bot.setToInfoProperty());

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private EventHandler<ActionEvent> getCraftEvent() {
        return event -> {
            if (smeltingTypeBox.getSelectionModel().getSelectedItem() != null && smeltingLocationBox.getSelectionModel().getSelectedItem() != null) {
                StartButton.setDisable(false);
            } else {
                StartButton.setDisable(true);
            }
        };
    }
}
