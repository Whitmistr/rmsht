package osrs.skills.smithing.smelting;

import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
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
import osrs.skills.smithing.smelting.tasks.*;
import osrs.skills.smithing.smelting.ui.AioSmeltingFXGui;
import osrs.skills.smithing.smelting.ui.AioSmeltingInfoUI;
import osrs.skills.smithing.smelting.ui.Info;

public class AioSmelting extends TaskBot implements InventoryListener, EmbeddableUI {

    public static StopWatch stopWatch = new StopWatch();

    //UI
    private AioSmeltingFXGui configUI;
    private AioSmeltingInfoUI infoUI;
    public Info info;
    private SimpleObjectProperty<Node> botInterfaceProperty;

    // UI Info
    public Player player;
    public static String xpHr;
    public String currentTaskString;
    public static Boolean scriptStarted = false;
    public static String itemType, itemName, mould, item1, item2, mode, runTime, location;
    public static Area.Rectangular FURNACE_AREA = new Area.Rectangular(new Coordinate(0, 0, 0), new Coordinate(0, 0, 0));
    public static Area.Rectangular BANK_AREA = new Area.Rectangular(new Coordinate(0, 0, 0), new Coordinate(0, 0, 0));
    public static int item1qt, item2qt;
    public static final int startExperience = Skill.CRAFTING.getExperience();
    public static final int smithingStartExperience = Skill.SMITHING.getExperience();

    // Default Constructor
    public AioSmelting() {
        setEmbeddableUI(this);
    }

    @Override
    public ObjectProperty<? extends Node> botInterfaceProperty() {
        if (botInterfaceProperty == null) {
            botInterfaceProperty = new SimpleObjectProperty<>(configUI = new AioSmeltingFXGui(this));
            infoUI = new AioSmeltingInfoUI(this);
            new LoopingThread(this::updateInfo, 1000).start();
        }
        return botInterfaceProperty;
    }

    @Override
    public void onStart(String... args) {
        setLoopDelay(200, 400);
        Mouse.setPathGenerator(Mouse.MLP_PATH_GENERATOR);
        CustomPlayerSense.initializeKeys();
        Mouse.setSpeedMultiplier(CustomPlayerSense.Key.JOJO_MOUSE_SPEED_MULTIPLIER.getAsDouble());
        add(new WaitTask(this), new TestTask(this), new Mode4Task(this), new Mode3Task(this), new Mode2Task(this), new Mode1Task(this));
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
        AioSmelting.runTime = AioSmelting.stopWatch.toString();
        int gainedExperience = (Skill.CRAFTING.getExperience() - AioSmelting.startExperience) + (Skill.SMITHING.getExperience() - smithingStartExperience);
        Double xphrdbl = (gainedExperience * 3600000D / AioSmelting.stopWatch.getRuntime());
        AioSmelting.xpHr = xphrdbl.toString();
    }
}
