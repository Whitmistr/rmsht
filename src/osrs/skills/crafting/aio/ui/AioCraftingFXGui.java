package osrs.skills.crafting.aio.ui;

import com.runemate.game.api.hybrid.util.Resources;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import osrs.skills.crafting.aio.AioCrafting;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AioCraftingFXGui extends GridPane implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setVisible(true);
    }

    public AioCraftingFXGui(AioCrafting bot) {
        FXMLLoader loader = new FXMLLoader();

        Future<InputStream> stream = bot.getPlatform().invokeLater(() -> Resources.getAsStream("osrs/skills/crafting/aio/ui/AioCraftingGUI.fxml"));

        loader.setController(new AioCraftingFXController(bot));

        loader.setRoot(this);

        try {
            loader.load(stream.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.err.println("Error loading GUI");
            e.printStackTrace();
        }
    }
}