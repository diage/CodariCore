package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.timedactions.selection;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.builders.kit.KitBuilderListener;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class TimedActionAddIcon extends ExecutableIcon{
	private String timedActionName;
	
	public TimedActionAddIcon(Combatant combatant, String timedActionName) {
		super(Material.FURNACE, combatant, timedActionName);
		this.timedActionName = timedActionName;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(KitBuilderListener.currentKitBuilders.containsKey(player.getName())) {
			TimedAction timedAction = ((LibraryCore) Codari.getLibrary()).createTimedAction(this.timedActionName, player);
			KitBuilderListener.currentKitBuilders.get(player.getName()).submitTimedAction(timedAction);
			((CombatantCore) this.getCombatant()).getDynamicMenuManager().addTimedActionIcon(this.timedActionName);
			player.sendMessage(ChatColor.GREEN + "You have successfully submitted a Timed Action!");
		} else {
			player.sendMessage(ChatColor.RED + "You must select a name for the Kit Builder before you add a Timed Action.");
		}
	}

}
