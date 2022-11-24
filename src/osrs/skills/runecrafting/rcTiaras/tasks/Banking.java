package osrs.skills.runecrafting.rcTiaras.tasks;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import javafx.application.Platform;
import misc.CustomPlayerSense;
import osrs.skills.runecrafting.rcTiaras.RcTiaras;

public class Banking extends Task {

    private RcTiaras bot;

    public Banking(RcTiaras bot) {
        this.bot = bot;
    }

    public boolean validate() {
        Player me = Players.getLocal();
        return RcTiaras.BANK_AREA.contains(me) && (!Inventory.contains("Tiara") || !Inventory.contains(RcTiaras.talisman));
    }

    public void execute() {
        Player me = Players.getLocal();
        String talisman = RcTiaras.talisman;

        if (Bank.isOpen()) {
            Bank.depositAllExcept("Tiara", RcTiaras.talisman);
            return;
        }

        if (!Bank.isOpen() && me != null && RcTiaras.BANK_AREA.contains(me) && (!Inventory.contains("Tiara") && !Inventory.contains(RcTiaras.talisman))) {
            Bank.open();
            Execution.delayUntil(Bank::isOpen, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
            return;
        }

        if (Bank.isOpen()) {
            if (!Inventory.isEmpty() && (!Inventory.contains("Tiara") || !Inventory.contains(talisman))) {
                Bank.depositInventory();
                Execution.delayUntil(Inventory::isEmpty, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                return;
            }
            withdraw();
        }

        if (Inventory.contains(talisman) && Inventory.contains("Tiara")) {
            Bank.close();
            Execution.delayUntil(() -> !Bank.isOpen(), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
            return;
        }

        if (Bank.getQuantity("Tiara") < 14 && Bank.getQuantity(talisman) < 14 && Bank.isOpen()) {
            bot.stop(",out of items");
            return;
        }

        Platform.runLater(() -> bot.updateInfo());

    }

    private void withdraw() {
        Player me = Players.getLocal();

        if (me != null && Bank.isOpen() && Bank.getQuantity("Tiara") > 14 && Bank.getQuantity(RcTiaras.talisman) > 14) {
            if (Inventory.getQuantity("Tiara") < 14) {
                Bank.withdraw("Tiara",14 - Inventory.getQuantity("Tiara"));
                return;
            }
            if (Inventory.getQuantity("Tiara") == 14 && Inventory.getQuantity(RcTiaras.talisman) < 14) {
                Bank.withdraw(RcTiaras.talisman, 14 - Inventory.getQuantity(RcTiaras.talisman));
                return;
            }
            if (Inventory.getQuantity("Tiara") == 14 && Inventory.getQuantity(RcTiaras.talisman) == 14) {
                Bank.close();
                return;
            }
            if (Inventory.getQuantity("Tiara") > 14 || Inventory.getQuantity(RcTiaras.talisman) > 14) {
                Bank.depositInventory();
            }
        } else {
            bot.stop("Out of talismans or tiaras");
        }
    }

}
