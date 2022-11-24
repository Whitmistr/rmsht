package osrs.skills.smithing.smelting.tasks;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import misc.CustomPlayerSense;
import osrs.skills.smithing.smelting.AioSmelting;

import java.util.Objects;

public class Mode3Task extends Task {

    /*
     * This mode withdraws a mould + 2 items of x quantities.
     */

    private AioSmelting bot;

    private Player local;
    private WebPath webPath;
    private SpriteItem mould;
    private SpriteItem item1, item2;

    public Mode3Task(AioSmelting bot) {
        this.bot = bot;
    }

    public boolean validate() {
        return AioSmelting.mode.equals("3");
    }

    public void execute() {
        mould = Inventory.getItems(AioSmelting.mould).first();
        item1 = Inventory.getItems(AioSmelting.item1).random();
        item2 = Inventory.getItems(AioSmelting.item2).random();
        if ((local = Players.getLocal()) == null) {
            return;
        }
        if (shouldBank()) {
            bank();
            return;
        }
        if (shouldCloseBank()) {
            bankCloser();
            return;
        }
        if (shouldUseItems()) {
            furnaceHandler();
            return;
        }
        makeXHandler();
    }

    private void makeXHandler() {
        if (AioSmelting.itemType.equals("Silver items")) {
            InterfaceComponent component = getInterfaceComponent();
            if (component != null) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (component.interact("Craft", AioSmelting.itemName)) {
                    Execution.delayUntil(() -> Objects.requireNonNull(local).getAnimationId() != -1, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                    Execution.delayWhile(() -> Inventory.contains(AioSmelting.item1) && !ChatDialog.isOpen(), () -> Objects.requireNonNull(local).getAnimationId() != -1, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
                }
            }
            return;
        }
        if (AioSmelting.itemType.equals("Gold items")) {
            Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            InterfaceComponent component = getInterfaceComponent();
            if (component != null) {
                if (component.interact("Make " + AioSmelting.itemName)) {
                    Execution.delayUntil(() -> Objects.requireNonNull(local).getAnimationId() != -1, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                    Execution.delayWhile(() -> Inventory.contains(AioSmelting.item1) && !ChatDialog.isOpen(), () -> Objects.requireNonNull(local).getAnimationId() != -1, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
                }
            }
        }
    }

    private void furnaceHandler() {
        LocatableEntity furnace = GameObjects.newQuery().actions("Smelt").results().nearest();
        if (furnace != null) {
            if (!AioSmelting.FURNACE_AREA.contains(local) && !local.isMoving()) {
                reassignPath();
                if (webPath != null) {
                    webPath.step();
                    Execution.delayUntil(furnace::isVisible, () -> local.isMoving(), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
                }
            } else {
                if (furnace.isVisible()) {
                    if (furnace.interact("Smelt")) {
                        Execution.delayUntil(() -> getInterfaceComponent() != null, CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY_HUGE.getAsInteger(),CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY_HUGE.getAsInteger());
                    }
                } else {
                    Camera.concurrentlyTurnTo(furnace, Random.nextDouble(0.300, 0.600));
                }
            }
        }
    }

    private void bank() {
        if (!Bank.isOpen()) {
            if (Inventory.getSelectedItem() != null) {
                if (Mouse.click(Mouse.Button.LEFT)) {
                    return;
                }
            }
            openBank();
            return;
        }
        if (Inventory.getQuantity(AioSmelting.item1) > 27 || Inventory.getQuantity(AioSmelting.item2) > 27 || Inventory.getQuantity(AioSmelting.mould) > 1) {
            Bank.depositInventory();
            return;
        }
        if (Inventory.containsAnyExcept(AioSmelting.item1, AioSmelting.item2, AioSmelting.mould)) {
            Bank.depositAllExcept(AioSmelting.item1, AioSmelting.item2, AioSmelting.mould);
            return;
        }
        if (Inventory.isFull() && (!Inventory.contains(AioSmelting.item1) || !Inventory.contains(AioSmelting.item2) || !Inventory.contains(AioSmelting.mould))) {
            Bank.depositAllExcept(AioSmelting.item1, AioSmelting.item2, AioSmelting.mould);
            return;
        }
        withdraw();
    }

    private void withdraw() {
        if (!Inventory.contains(AioSmelting.mould)) {
            if (Bank.getQuantity(AioSmelting.mould) > 0) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw(AioSmelting.mould, 1)) {
                    Execution.delayUntil(() -> Inventory.contains(AioSmelting.mould), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("No " + AioSmelting.mould + " Found");
            }
            return;
        }
        if (Inventory.contains(AioSmelting.mould) && Inventory.getQuantity(AioSmelting.item1) < AioSmelting.item1qt) {
            if (Bank.getQuantity(AioSmelting.item1) > AioSmelting.item1qt) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw(AioSmelting.item1, AioSmelting.item1qt - Inventory.getQuantity(AioSmelting.item1))) {
                    Execution.delayUntil(() -> Inventory.contains(AioSmelting.item1), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("Out of: " + AioSmelting.item1);
            }
            return;
        }
        if (Inventory.contains(AioSmelting.mould) && Inventory.getQuantity(AioSmelting.item1) == AioSmelting.item1qt && Inventory.getQuantity(AioSmelting.item2) < AioSmelting.item2qt) {
            if (Bank.getQuantity(AioSmelting.item2) > AioSmelting.item2qt) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw(AioSmelting.item2, AioSmelting.item2qt - Inventory.getQuantity(AioSmelting.item2))) {
                    Execution.delayUntil(() -> Inventory.contains(AioSmelting.item2), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("Out of: " + AioSmelting.item2);
            }
        }

    }

    private void bankCloser() {
        if (Bank.close(CustomPlayerSense.Key.JOJO_USE_BANK_HOTKEYS.getAsBoolean())) {
            Execution.delayUntil(() -> !Bank.isOpen(), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
        }
    }

    private void openBank() {
        LocatableEntity bank = Npcs.newQuery().actions("Bank").results().nearest();
        if (bank == null) {
            bank = GameObjects.newQuery().actions("Bank").results().nearest();
        }
        if (bank != null) {
            if (!AioSmelting.BANK_AREA.contains(local) && !local.isMoving()) {
                reassignPath();
                if (webPath != null) {
                    webPath.step();
                    Execution.delayUntil(bank::isVisible, () -> local.isMoving(), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
                }
            } else {
                if (bank.isVisible()) {
                    if (bank.interact("Bank")) {
                        Execution.delayUntil(Bank::isOpen, local::isMoving, CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY_HUGE.getAsInteger(),CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY_HUGE.getAsInteger());
                    }
                } else {
                    Camera.concurrentlyTurnTo(bank, Random.nextDouble(0.300, 0.600));
                }
            }
        }
    }


    private boolean shouldBank() {
        return mould == null || item1 == null || item2 == null;
    }

    private boolean shouldCloseBank() {
        return Bank.isOpen();
    }

    private boolean shouldUseItems() {
        return getInterfaceComponent() == null && Objects.requireNonNull(local).getAnimationId() == -1;
    }

    private InterfaceComponent getInterfaceComponent() {
        switch (AioSmelting.itemType) {
            case "Gold items":
                return Interfaces.newQuery().containers(446).types(InterfaceComponent.Type.MODEL).visible().actions("Make " + AioSmelting.itemName).results().first();
            case "Silver items":
                return Interfaces.newQuery().containers(6).visible().actions("Craft").names(AioSmelting.itemName).results().first();
            default:
                return null;
        }
    }

    private void reassignPath() {
        webPath = Traversal.getDefaultWeb().getPathBuilder().buildTo(walkToBank(AioSmelting.item1, AioSmelting.item2, AioSmelting.mould, AioSmelting.BANK_AREA) ? AioSmelting.BANK_AREA.getRandomCoordinate() : AioSmelting.FURNACE_AREA.getRandomCoordinate());
    }

    private boolean walkToBank(String thing, String thing2, String thing3, Area bankArea) {
        return (!Inventory.contains(thing) || !Inventory.contains(thing2) || !Inventory.contains(thing3)) && !bankArea.contains(local);
    }
}