package osrs.skills.runecrafting.rcTiaras;

import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.core.LoopingThread;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.task.TaskBot;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import misc.CustomPlayerSense;
import osrs.skills.runecrafting.rcTiaras.tasks.*;
import osrs.skills.runecrafting.rcTiaras.ui.Info;
import osrs.skills.runecrafting.rcTiaras.ui.RcTiarasFXGui;
import osrs.skills.runecrafting.rcTiaras.ui.RcTiarasInfoUI;


public class RcTiaras extends TaskBot implements InventoryListener, EmbeddableUI {

    public static StopWatch stopWatch = new StopWatch();

    private RcTiarasFXGui configUI;
    private RcTiarasInfoUI infoUI;
    public Info info;
    private SimpleObjectProperty<Node> botInterfaceProperty;

    public Player player;
    public static String xpHr;
    public String currentTaskString;
    public static Boolean scriptStarted = false;
    public static String location, altar, talisman, runTime;
    public static final int startExperience = Skill.RUNECRAFTING.getExperience();
    public static Area.Circular INSIDE_ALTAR = new Area.Circular(new Coordinate(0, 0, 0), 0);
    public static Area.Circular ALTAR_AREA = new Area.Circular(new Coordinate(0, 0, 0), 0);
    public static Area.Rectangular BANK_AREA = new Area.Rectangular(new Coordinate(0, 0, 0), new Coordinate(0, 0, 0));

    public RcTiaras() {
        setEmbeddableUI(this);
    }

    @Override
    public ObjectProperty<? extends Node> botInterfaceProperty() {
        if (botInterfaceProperty == null) {
            botInterfaceProperty = new SimpleObjectProperty<>(configUI = new RcTiarasFXGui(this));
            infoUI = new RcTiarasInfoUI(this);
            new LoopingThread(this::updateInfo, 1000).start();
        }
        return botInterfaceProperty;
    }

    @Override
    public void onStart(String... args) {
        setLoopDelay(100, 200);
        stopWatch.start();
        Mouse.setPathGenerator(Mouse.MLP_PATH_GENERATOR);
        CustomPlayerSense.initializeKeys();
        Mouse.setSpeedMultiplier(CustomPlayerSense.Key.JOJO_MOUSE_SPEED_MULTIPLIER.getAsDouble());
        add(new WaitTask(this), new Walk(this), new LeaveAltar(this), new EnterAltar(this), new Craft(this), new Banking(this));
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
        RcTiaras.runTime = RcTiaras.stopWatch.toString();
        int gainedExperience = Skill.RUNECRAFTING.getExperience() - RcTiaras.startExperience;
        Double xphrdbl = (gainedExperience * 3600000D / RcTiaras.stopWatch.getRuntime());
        RcTiaras.xpHr = xphrdbl.toString();
    }

    public Area getInsideAltar() {
        return INSIDE_ALTAR;
    }

    public Area getAltarArea() {
        return ALTAR_AREA;
    }

    public Area getBankArea() {
        return BANK_AREA;
    }
}
