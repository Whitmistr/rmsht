package osrs.skills.runecrafting.rcTiaras.ui;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import osrs.skills.runecrafting.rcTiaras.RcTiaras;

import java.net.URL;
import java.util.ResourceBundle;

public class RcTiarasFXController implements Initializable {

    private RcTiaras bot;

    @FXML
    private ComboBox<String> locationBox, altarBox;

    @FXML
    private Button StartButton;

    public RcTiarasFXController(RcTiaras bot) {
        this.bot = bot;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.locationBox.getItems().addAll("Falador", "Varrock", "Edgeville", "Draynor Village");
        this.locationBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            altarBox.getItems().clear();
            switch (this.locationBox.getSelectionModel().getSelectedIndex()) {
                case 0:
                    altarBox.getItems().addAll("Air", "Mind");
                    break;
                case 1:
                    altarBox.getItems().addAll("Earth");
                    break;
                case 2:
                    altarBox.getItems().addAll("Body");
                    break;
                case 3:
                    altarBox.getItems().addAll("Water");
                    break;
            }
        });
        StartButton.setOnAction(getStartButtonAction());
        this.locationBox.setOnAction(getCraftEvent());
    }

    public EventHandler<ActionEvent> getStartButtonAction() {
        return event -> {
            try {
                RcTiaras.location = locationBox.getValue();

                switch (altarBox.getValue()) {
                    case "Air":
                        RcTiaras.altar = altarBox.getValue();
                        RcTiaras.talisman = "Air hide";
                        RcTiaras.INSIDE_ALTAR = new Area.Circular(new Coordinate(2844, 4834, 0), 16);
                        RcTiaras.ALTAR_AREA = new Area.Circular(new Coordinate(2986, 3291, 0), 8);
                        RcTiaras.BANK_AREA = new Area.Rectangular(new Coordinate(3014, 3355, 0), new Coordinate(3010, 3357, 0));
                        break;
                    case "Mind":
                        RcTiaras.altar = altarBox.getValue();
                        RcTiaras.talisman = "Mind hide";
                        RcTiaras.INSIDE_ALTAR = new Area.Circular(new Coordinate(2787, 4841, 0), 16);
                        RcTiaras.ALTAR_AREA = new Area.Circular(new Coordinate(2982, 3512, 0), 8);
                        RcTiaras.BANK_AREA = new Area.Rectangular(new Coordinate(2947, 3368, 0), new Coordinate(2945, 3369, 0));
                        break;
                    case "Earth":
                        RcTiaras.altar = altarBox.getValue();
                        RcTiaras.talisman = "Earth hide";
                        RcTiaras.INSIDE_ALTAR = new Area.Circular(new Coordinate(2659, 4839, 0), 15);
                        RcTiaras.ALTAR_AREA = new Area.Circular(new Coordinate(3306, 3475, 0), 9);
                        RcTiaras.BANK_AREA = new Area.Rectangular(new Coordinate( 3254, 3419, 0), new Coordinate(3251, 3421, 0));
                        break;
                    case "Body":
                        RcTiaras.altar = altarBox.getValue();
                        RcTiaras.talisman = "Body hide";
                        RcTiaras.INSIDE_ALTAR = new Area.Circular(new Coordinate(2522, 4839, 0), 16);
                        RcTiaras.ALTAR_AREA = new Area.Circular(new Coordinate(3053, 3444, 0), 9);
                        RcTiaras.BANK_AREA = new Area.Rectangular(new Coordinate(3093, 3497, 0), new Coordinate(3097, 3488, 0));
                        break;
                    case "Water":
                        RcTiaras.altar = altarBox.getValue();
                        RcTiaras.talisman = "Water hide";
                        RcTiaras.INSIDE_ALTAR = new Area.Circular(new Coordinate(2716, 4836, 0), 16);
                        RcTiaras.ALTAR_AREA = new Area.Circular(new Coordinate(3183, 3163, 0), 9);
                        RcTiaras.BANK_AREA = new Area.Rectangular(new Coordinate(3092, 3241, 0), new Coordinate(3093, 3244, 0));
                        break;
                }


                Platform.runLater(() -> bot.setToInfoProperty());
                bot.currentTaskString = "Altar: " + bot.altar + "; Talisman: " + bot.talisman;
                RcTiaras.scriptStarted = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public EventHandler<ActionEvent> getCraftEvent() {
        return event -> {
            if (locationBox.getSelectionModel().getSelectedItem() != null) {
                StartButton.setDisable(false);
            } else {
                StartButton.setDisable(true);
            }
        };
    }
}
