package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.winafter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.KitBuilderListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SetFalseIcon extends ExecutableIcon {

	public SetFalseIcon(Combatant combatant) {
		super(Material.ARROW, combatant, "FALSE");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(KitBuilderListener.currentKitBuilders.containsKey(player.getName())) {
			KitBuilderListener.currentKitBuilders.get(player.getName()).setWinConditionAfter(false);
			player.sendMessage(ChatColor.GREEN + "Win Condition Set to Before Win Condition Time");
		} else {
			player.sendMessage(ChatColor.RED + "You must select a name for the Kit Builder before you add a Win Condition.");
		}
	}

}
