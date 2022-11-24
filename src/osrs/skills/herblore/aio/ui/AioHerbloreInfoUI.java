package osrs.skills.herblore.aio.ui;

import com.runemate.game.api.hybrid.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import osrs.skills.herblore.aio.AioHerblore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AioHerbloreInfoUI extends GridPane implements Initializable {

    private AioHerblore bot;

    @FXML
    Label xpHr, runTime, currentTask;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setVisible(true);
    }

    public AioHerbloreInfoUI(AioHerblore bot) {
        this.bot = bot;

        FXMLLoader loader = new FXMLLoader();
        Future<InputStream> stream = bot.getPlatform().invokeLater(() -> Resources.getAsStream("osrs/skills/herblore/aio/ui/InfoUI.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        AioHerblore.runTime = AioHerblore.stopWatch.toString();

        try {
            loader.load(stream.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            Info i = bot.info;
            xpHr.textProperty().set("" + i.xpHr);
            runTime.textProperty().set("" + i.runTime);
            currentTask.textProperty().set(i.currentTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
