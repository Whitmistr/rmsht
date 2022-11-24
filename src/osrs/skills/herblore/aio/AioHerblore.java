package osrs.skills.herblore.aio;

import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.core.LoopingThread;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.task.TaskBot;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import misc.CustomPlayerSense;
import osrs.skills.herblore.aio.tasks.*;
import osrs.skills.herblore.aio.ui.AioHerbloreFXGui;
import osrs.skills.herblore.aio.ui.AioHerbloreInfoUI;
import osrs.skills.herblore.aio.ui.Info;

public class AioHerblore extends TaskBot implements InventoryListener, EmbeddableUI {

    public static StopWatch stopWatch = new StopWatch();

    //UI
    private AioHerbloreFXGui configUI;
    private AioHerbloreInfoUI infoUI;
    public Info info;
    private SimpleObjectProperty<Node> botInterfaceProperty;

    // UI Info
    public Player player;
    public static String xpHr;
    public String currentTaskString;
    public static Boolean scriptStarted = false;
    public static String itemType, itemName, runTime, primaryIngredient, secondaryIngredient, mode;
    public static String mortar = "Pestle and mortar";
    public static int primaryQt;
    public static int seconrdaryQt;
    public static final int startExperience = Skill.HERBLORE.getExperience();

    // Default Constructor
    public AioHerblore() {
        setEmbeddableUI(this);
    }

    @Override
    public ObjectProperty<? extends Node> botInterfaceProperty() {
        if (botInterfaceProperty == null) {
            botInterfaceProperty = new SimpleObjectProperty<>(configUI = new AioHerbloreFXGui(this));
            infoUI = new AioHerbloreInfoUI(this);
            new LoopingThread(this::updateInfo, 1000).start();
        }
        return botInterfaceProperty;
    }

    @Override
    public void onStart(String... args) {
        setLoopDelay(50, 100);
        CustomPlayerSense.initializeKeys();
        Mouse.setPathGenerator(Mouse.MLP_PATH_GENERATOR);
        Mouse.setSpeedMultiplier(CustomPlayerSense.Key.JOJO_MOUSE_SPEED_MULTIPLIER.getAsDouble());
        add(new WaitTask(this), new Test(this), new Mode1Task(this), new Mode2Task(this), new Mode3Task(this));
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
        AioHerblore.runTime = AioHerblore.stopWatch.toString();
        int gainedExperience = Skill.HERBLORE.getExperience() - AioHerblore.startExperience;
        Double xphrdbl = (gainedExperience * 3600000D / AioHerblore.stopWatch.getRuntime());
        AioHerblore.xpHr = xphrdbl.toString();
    }
}
