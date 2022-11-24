package osrs.skills.crafting.aio;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.framework.core.LoopingThread;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.task.TaskBot;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import misc.CustomPlayerSense;
import osrs.skills.crafting.aio.tasks.*;
import osrs.skills.crafting.aio.ui.AioCraftingFXGui;
import osrs.skills.crafting.aio.ui.AioCraftingInfoUI;
import osrs.skills.crafting.aio.ui.Info;

public class AioCrafting extends TaskBot implements InventoryListener, EmbeddableUI {

    public static StopWatch stopWatch = new StopWatch();

    //UI
    private AioCraftingFXGui configUI;
    private AioCraftingInfoUI infoUI;
    public Info info;
    private SimpleObjectProperty<Node> botInterfaceProperty;

    // UI Info
    public Player player;
    public static String xpHr;
    public String currentTaskString;
    public static Boolean scriptStarted = false;
    public static String itemType, itemName, hide, runTime, orb;
    public static int hidesReq;
    public static final int startExperience = Skill.CRAFTING.getExperience();

    // Default Constructor
    public AioCrafting() {
        setEmbeddableUI(this);
    }

    @Override
    public ObjectProperty<? extends Node> botInterfaceProperty() {
        if (botInterfaceProperty == null) {
            botInterfaceProperty = new SimpleObjectProperty<>(configUI = new AioCraftingFXGui(this));
            infoUI = new AioCraftingInfoUI(this);
            new LoopingThread(this::updateInfo, 1000).start();
        }
        return botInterfaceProperty;
    }

    @Override
    public void onStart(String... args) {
        setLoopDelay(150, 250);
        CustomPlayerSense.initializeKeys();
        Mouse.setPathGenerator(Mouse.MLP_PATH_GENERATOR);
        Mouse.setSpeedMultiplier(CustomPlayerSense.Key.JOJO_MOUSE_SPEED_MULTIPLIER.getAsDouble());
        add(new WaitTask(this), new GemCraftTask(this),new GlassCraftTask(this), new LeatherCraftTask(this), new DragonLeatherCraftTask(this), new BattlestaffCraftTask(this), new HardLeatherTask(this));
        getEventDispatcher().addListener(this);
    }

    public void setToInfoProperty() {
        botInterfaceProperty.set(infoUI);
    }

    public void updateInfo() {
        try {
            getPlatform().invokeAndWait(this::updateExp);
            info = new Info(xpHr, stopWatch.getRuntimeAsString(), currentTaskString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> infoUI.update());
    }


    public void updateExp() {
        AioCrafting.runTime = AioCrafting.stopWatch.toString();
        int gainedExperience = Skill.CRAFTING.getExperience() - AioCrafting.startExperience;
        Double xphrdbl = (gainedExperience * 3600000D / AioCrafting.stopWatch.getRuntime());
        AioCrafting.xpHr = xphrdbl.toString();
    }
}
