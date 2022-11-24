package osrs.skills.herblore.aio.tasks;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.osrs.local.hud.interfaces.MakeAllInterface;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import misc.CustomPlayerSense;
import osrs.skills.herblore.aio.AioHerblore;

import java.util.Objects;


public class Mode2Task extends Task {

    /*
     * This mode withdraws 2 items with x quantities.
     */

    private AioHerblore bot;

    private Player local;
    private SpriteItem primaryIngredient, secondaryIngredient;

    public Mode2Task(AioHerblore bot) {
        this.bot = bot;
    }

    public boolean validate() {
        return AioHerblore.mode.equals("2");
    }

    public void execute() {
        primaryIngredient = Inventory.getItems(AioHerblore.primaryIngredient).first();
        secondaryIngredient = Inventory.getItems(AioHerblore.secondaryIngredient).first();
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
            itemHandler();
            return;
        }
        makeXHandler();
    }

    private void makeXHandler() {
        if (MakeAllInterface.isOpen()) {
            if (MakeAllInterface.getSelectedQuantity() != 0) {
                MakeAllInterface.setSelectedQuantity(0);
                return;
            }
            Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            if (MakeAllInterface.selectItem(AioHerblore.itemName, true)) {
                Execution.delayUntil(() -> !MakeAllInterface.isOpen(), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                Execution.delayWhile(() -> (Inventory.contains(AioHerblore.primaryIngredient) || Inventory.contains(AioHerblore.primaryIngredient)) && !ChatDialog.isOpen(), () -> Objects.requireNonNull(local).getAnimationId() != -1, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
            }
        }
    }

    private void itemHandler() {
        if (Inventory.getSelectedItem() == null) {
            if (primaryIngredient.interact("Use")) {
                Execution.delayUntil(() -> Inventory.getSelectedItem() != null, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            }
        } else if (secondaryIngredient.interact("Use")) {
            Execution.delayUntil(MakeAllInterface::isOpen, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
            Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
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
        if (Inventory.getQuantity(AioHerblore.primaryIngredient) > AioHerblore.primaryQt || Inventory.getQuantity(AioHerblore.secondaryIngredient) > AioHerblore.seconrdaryQt) {
            Bank.depositInventory();
            return;
        }
        if (Inventory.containsAnyExcept(AioHerblore.primaryIngredient, AioHerblore.secondaryIngredient)) {
            Bank.depositAllExcept(AioHerblore.primaryIngredient, AioHerblore.secondaryIngredient);
            return;
        }
        if (Inventory.isFull() && (!Inventory.contains(AioHerblore.primaryIngredient) || !Inventory.contains(AioHerblore.secondaryIngredient))) {
            Bank.depositAllExcept(AioHerblore.primaryIngredient, AioHerblore.secondaryIngredient);
            return;
        }
        withdraw();
    }

    private void withdraw() {
        if (Inventory.getQuantity(AioHerblore.primaryIngredient) < AioHerblore.primaryQt) {
            if (Bank.getQuantity(AioHerblore.primaryIngredient) > AioHerblore.primaryQt) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw(AioHerblore.primaryIngredient, AioHerblore.primaryQt - Inventory.getQuantity(AioHerblore.primaryIngredient))) {
                    Execution.delayUntil(() -> Inventory.contains(AioHerblore.primaryIngredient), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("Out of: " + AioHerblore.primaryIngredient);
            }
            return;
        }
        if (Inventory.getQuantity(AioHerblore.primaryIngredient) == AioHerblore.primaryQt && Inventory.getQuantity(AioHerblore.secondaryIngredient) < AioHerblore.seconrdaryQt) {
            if (Bank.getQuantity(AioHerblore.secondaryIngredient) > AioHerblore.seconrdaryQt) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw(AioHerblore.secondaryIngredient, AioHerblore.seconrdaryQt - Inventory.getQuantity(AioHerblore.secondaryIngredient))) {
                    Execution.delayUntil(() -> Inventory.contains(AioHerblore.secondaryIngredient), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("Out of: " + AioHerblore.secondaryIngredient);
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
            if (bank.isVisible()) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (bank.interact("Bank")) {
                    Player local = Players.getLocal();
                    if (local == null) {
                        return;
                    }
                    Execution.delayUntil(Bank::isOpen, local::isMoving, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
                }
            } else {
                Camera.concurrentlyTurnTo(bank, Random.nextDouble(0.300, 0.600));
            }
        }
    }


    private boolean shouldBank() {
        return primaryIngredient == null || secondaryIngredient == null;
    }

    private boolean shouldCloseBank() {
        return Bank.isOpen();
    }

    private boolean shouldUseItems() {
        return !MakeAllInterface.isOpen() && Objects.requireNonNull(local).getAnimationId() == -1;
    }

    private boolean walkToBank(String thing, String thing2, Area bankArea) {
        return (!Inventory.contains(thing) || !Inventory.contains(thing2)) && !bankArea.contains(local);
    }
}