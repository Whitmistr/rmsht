package osrs.skills.crafting.aio.tasks;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.osrs.local.hud.interfaces.MakeAllInterface;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import misc.CustomPlayerSense;
import osrs.skills.crafting.aio.AioCrafting;

import java.util.Objects;

public class BattlestaffCraftTask extends Task {

    private AioCrafting bot;
    private Player local;
    private SpriteItem orb;
    private SpriteItem staff;

    public BattlestaffCraftTask(AioCrafting bot) {
        this.bot = bot;
    }

    @Override
    public boolean validate() {
        return AioCrafting.itemType.equalsIgnoreCase("Battlestaves");
    }

    @Override
    public void execute() {
        orb = Inventory.getItems(AioCrafting.orb).last();
        staff = Inventory.getItems("Battlestaff").random();

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
            if (MakeAllInterface.selectItem(AioCrafting.itemName, true)) {
                Execution.delayUntil(() -> !MakeAllInterface.isOpen(), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                Execution.delayWhile(() -> Inventory.contains(AioCrafting.orb) && !ChatDialog.isOpen(), () -> Objects.requireNonNull(local).getAnimationId() != -1, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
            }
        }
    }

    private void itemHandler() {
        if (Inventory.getSelectedItem() == null) {
            Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            if (orb.interact("Use")) {
                Execution.delayUntil(() -> Inventory.getSelectedItem() != null, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            }
        } else if (staff.interact("Use")) {
            Execution.delayUntil(MakeAllInterface::isOpen, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
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
        if (Inventory.containsAnyExcept("Battlestaff", AioCrafting.orb)) {
            Bank.depositAllExcept("Battlestaff", AioCrafting.orb);
            return;
        }

        if (Inventory.getQuantity("Battlestaff") > 14 || Inventory.getQuantity(AioCrafting.orb) > 14) {
            Bank.depositInventory();
            return;
        }
        withdraw();
    }

    private void withdraw() {
        if (!Inventory.contains(AioCrafting.orb)) {
            if (Bank.getQuantity(AioCrafting.orb) > 14) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw(AioCrafting.orb, 14 - Inventory.getQuantity(AioCrafting.orb))) {
                    Execution.delayUntil(() -> Inventory.contains(AioCrafting.orb), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("Out of: " + AioCrafting.orb);
            }
            return;
        }
        if (Inventory.contains(AioCrafting.orb) && !Inventory.contains("Battlestaff")) {
            if (Bank.getQuantity("Battlestaff") > 14) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw("Battlestaff", 14 - Inventory.getQuantity("Battlestaff"))) {
                    Execution.delayUntil(() -> Inventory.contains("Battlestaff"), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("Out of: " + "Battlestaffs");
            }
        }
    }

    private void bankCloser() {
        Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
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
        return orb == null || staff == null;
    }

    private boolean shouldCloseBank() {
        return Bank.isOpen();
    }

    private boolean shouldUseItems() {
        return !MakeAllInterface.isOpen() && Objects.requireNonNull(local).getAnimationId() == -1;
    }
}