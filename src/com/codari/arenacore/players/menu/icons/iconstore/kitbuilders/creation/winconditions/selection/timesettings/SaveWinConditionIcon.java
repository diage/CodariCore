package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.KitBuilder;
import com.codari.arenacore.players.builders.kit.KitBuilderListener;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveWinConditionIcon extends ExecutableIcon {

	public SaveWinConditionIcon(Combatant combatant) {
		super(Material.SUGAR_CANE_BLOCK, combatant, "Save Win Condition");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(KitBuilderListener.currentKitBuilders.containsKey(player.getName())) {
			KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
			((CombatantCore) this.getCombatant()).getDynamicMenuManager().addWinConditionIcon(kitBuilder.getWinConditionName());
			kitBuilder.submitWinCondition();		
			player.sendMessage(ChatColor.GREEN + "You have successfully submitted a Win Condition.");
		} else {
			player.sendMessage(ChatColor.RED + "You must select a name for the Kit Builder before you add a Win Condition.");
		}
	}

}
