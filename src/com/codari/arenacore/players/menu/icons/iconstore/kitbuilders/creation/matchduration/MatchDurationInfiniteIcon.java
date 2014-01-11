package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.KitBuilder;
import com.codari.arenacore.players.builders.kit.KitBuilderListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class MatchDurationInfiniteIcon extends ExecutableIcon {

	public MatchDurationInfiniteIcon(Combatant combatant) {
		super(Material.SPONGE, combatant, "Set Match Duration to Infinite");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(KitBuilderListener.currentKitBuilders.containsKey(player.getName())) {
			KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
			kitBuilder.setTime(true);
			player.sendMessage(ChatColor.GREEN + "Match Duration set to Infinite");
		} else {
			player.sendMessage(ChatColor.RED + "Failed to set Match Duration - You must select a name for the Kit Builder first!");
		}
	}
}
